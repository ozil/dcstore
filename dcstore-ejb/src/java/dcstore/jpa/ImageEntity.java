// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dcebula
 */
@Entity
@Table(name="dc_images")
@NamedQueries({
        @NamedQuery(name="image.getAll", query="select i from ImageEntity i order by i.position"),
        @NamedQuery(name="image.getForProduct",
                    query="select i "+
                          "from ImageEntity i "+
                          "where i.product.id=:id "+
                          "order by i.position"),
        @NamedQuery(name="image.getById", query="select i from ImageEntity i where i.id=:id")
})
public class ImageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_image")
    private Long id;
    @ManyToOne
    @JoinColumn(name="id_product", referencedColumnName="id_product")
    @NotNull
    private ProductEntity product;
    @NotNull
    @Column(name="position")
    private int position;
    @NotNull
    @Column(name="cover")
    private boolean cover;

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof ImageEntity)) {
            return false;
        }
        ImageEntity other = (ImageEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dcstore.jpa.ImageEntity[ id=" + id + " ]";
    }
    
}
