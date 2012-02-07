// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author dcebula
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "product.all", query = "select p from ProductEntity p"),
    @NamedQuery(name = "product.getById", query = "select p from ProductEntity p where p.id=:id")
})
@Table(name = "dc_products")
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long id;
    @NotNull
    @Size(min = 1, max = 64)
    @Pattern(regexp = "[A-Za-z0-9-_ ]+")
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    @NotNull
    private CategoryEntity category;
    @NotNull
    @Column(name = "price")
    private double price;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_tax", referencedColumnName = "id_tax")
    private TaxEntity tax;
    @OneToMany(mappedBy = "product")
    private List<ImageEntity> images;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_description", referencedColumnName = "id_description")
    private DescriptionEntity description;

    public DescriptionEntity getDescription() {
        return description;
    }

    public void setDescription(DescriptionEntity description) {
        this.description = description;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> images) {
        this.images = images;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TaxEntity getTax() {
        return tax;
    }

    public void setTax(TaxEntity tax) {
        this.tax = tax;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPriceWithTax() {
        double vt = tax.getRate();
        vt /= 100;
        vt += 1;

        price *= vt;
        price *= 100;
        price = Math.round(price);
        price /= 100;

        return price;
    }   
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductEntity)) {
            return false;
        }
        ProductEntity other = (ProductEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
