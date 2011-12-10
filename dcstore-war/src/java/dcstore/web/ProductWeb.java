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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author dcebula
 */
@ManagedBean
@RequestScoped
public class ProductWeb {
    @EJB
    private ProductBeanLocal productBean;
    
    private String name;
    private int group;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
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
        productBean.add(name, group);
    }
    
    public List<ProductEntity> getAll() {
        return productBean.getAll();
    }
}
