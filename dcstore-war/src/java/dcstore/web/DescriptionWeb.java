// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.web;

import dcstore.ejb.DescriptionBeanLocal;
import dcstore.ejb.ProductBeanLocal;
import dcstore.jpa.ProductEntity;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author dcebula
 */
@ManagedBean
@SessionScoped
public class DescriptionWeb {

    private Long idProduct;
    private ProductEntity product;
    @EJB
    private ProductBeanLocal productBean;
    @EJB
    private DescriptionBeanLocal descriptionBean;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /** Creates a new instance of DescriptionWeb */
    public DescriptionWeb() {
    }

    public String getHeaderInfo() {
        String info = "Editing product description";
        if (product != null && product.getName().length() > 0) {
            info += " for product " + product.getName();
        }
        return info;
    }

    public String go(Long idProduct) {
        try {
            this.idProduct = idProduct;
            product = productBean.getById(idProduct);
            if (product.getDescription() != null) {
                this.desc = product.getDescription().getBody();
            } else {
                this.desc = "";
            }
            return "admin-description.xhtml";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while getting description view"));
            return "";
        }
    }

    public String save() {
        try {
            descriptionBean.set(idProduct, desc);
            return "admin-products.xhtml";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while saving description"));
            return "";
        }
    }
}
