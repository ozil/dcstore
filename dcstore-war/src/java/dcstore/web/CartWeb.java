// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.web;

import dcstore.ejb.ProductBeanLocal;
import dcstore.jpa.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class CartWeb {

    private List<CartPositionWeb> products = new ArrayList<CartPositionWeb>();
    @EJB
    private ProductBeanLocal productBean;

    public List<CartPositionWeb> getProducts() {
        return products;
    }

    public void setProducts(List<CartPositionWeb> products) {
        this.products = products;
    }

    /** Creates a new instance of CartWeb */
    public CartWeb() {
    }

    public void addProduct() {
        Long idProduct = 0L;
        idProduct = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

        try {
            ProductEntity product = productBean.getById(idProduct);
            int idx = products.indexOf(product);
            if (idx == -1) {
                products.add(new CartPositionWeb(product, 1));
            } else {
                products.get(idx).incQty();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while adding product to cart"));
        }
    }
}
