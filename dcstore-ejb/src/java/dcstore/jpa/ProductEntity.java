/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author dcebula
 */
@Entity
@NamedQueries({
    @NamedQuery(name="product.all", query="select p from ProductEntity p")
})
@Table(name="dc_products")
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_product")
    private Long id;
    @NotNull
    @Size(min=1, max=64)
    @Pattern(regexp="[A-Za-z0-9-_ ]+")
    @Column(name="name")
    private String name;
    @ManyToOne
    @JoinColumn(name="id_category", referencedColumnName="id_category")
    private CategoryEntity category;

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

    @Override
    public String toString() {
        return "dcstore.jpa.ProductEntity[ id=" + id + " ]";
    }
    
}
