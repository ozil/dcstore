// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.web;

import dcstore.ejb.ProductBeanLocal;
import dcstore.jpa.ProductEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Pattern;

/**
 *
 * @author dcebula
 */
@ManagedBean
@RequestScoped
public class ProductWeb {

    @EJB
    private ProductBeanLocal productBean;
    private Long id;
    @Pattern(regexp = "[A-Za-z0-9-_ ]*")
    private String name;
    private Long idCategory;
    @Pattern(regexp = "[A-Za-z0-9-_ ]*")
    private String nameEdit;
    private Long idCategoryEdit;
    private double price;
    private Long idTax;

    public Long getIdTax() {
        return idTax;
    }

    public void setIdTax(Long idTax) {
        this.idTax = idTax;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getIdCategoryEdit() {
        return idCategoryEdit;
    }

    public void setIdCategoryEdit(Long idCategoryEdit) {
        this.idCategoryEdit = idCategoryEdit;
    }

    public String getNameEdit() {
        return nameEdit;
    }

    public void setNameEdit(String nameEdit) {
        this.nameEdit = nameEdit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Creates a new instance of ProductWeb */
    public ProductWeb() {
    }

    public void add() {
        try {
            productBean.add(name, idCategory, price, idTax);
            this.name = null;
            this.idCategory = 0L;
            this.price = 0;
            this.idTax = 0L;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing request: " + e.getMessage()));
        }
    }

    public List<ProductEntity> getAll() {
        List<ProductEntity> ret = null;

        try {
            ret = productBean.getAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing request: " + e.getMessage()));
        }

        return ret;
    }

    public void edit() {
        try {
            productBean.edit(id, nameEdit, idCategoryEdit);
            FacesContext.getCurrentInstance().getExternalContext().redirect("admin-products.xhtml?id=-1");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing request: " + e.getMessage()));
        }
    }

    public void delete(Long id) {
        try {
            productBean.del(id);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing request: " + e.getMessage()));
        }
    }
}
