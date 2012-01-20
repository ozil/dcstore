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
    private double priceEdit;
    private Long idTaxEdit;
    private double priceWithTax;
    private double priceWithTaxEdit;
    private ProductEntity product;

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public double getPriceWithTaxEdit() {
        return priceWithTaxEdit;
    }

    public void setPriceWithTaxEdit(double priceWithTaxEdit) {
        this.priceWithTaxEdit = priceWithTaxEdit;
    }

    public double getPriceWithTax() {
        return priceWithTax;
    }

    public void setPriceWithTax(double priceWithTax) {
        this.priceWithTax = priceWithTax;
    }

    public Long getIdTaxEdit() {
        return idTaxEdit;
    }

    public void setIdTaxEdit(Long idTaxEdit) {
        this.idTaxEdit = idTaxEdit;
    }

    public double getPriceEdit() {
        return priceEdit;
    }

    public void setPriceEdit(double priceEdit) {
        this.priceEdit = priceEdit;
    }

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
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while adding product"));
        }
    }

    public List<ProductEntity> getAll() {
        List<ProductEntity> ret = null;

        try {
            ret = productBean.getAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing getting product list"));
        }

        return ret;
    }

    public void edit() {
        try {
            productBean.edit(id, nameEdit, idCategoryEdit, priceEdit, idTaxEdit);
            FacesContext.getCurrentInstance().getExternalContext().redirect("admin-products.xhtml?id=-1");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while editing product"));
        }
    }

    public void delete(Long id) {
        try {
            productBean.del(id);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while deleting product"));
        }
    }

    public void calcPrice() {
        this.price = 5;
    }

    public void fetchProduct(Long idProduct) {
        try {
            product = productBean.getById(idProduct);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while fetching product"));
        }
    }
}
