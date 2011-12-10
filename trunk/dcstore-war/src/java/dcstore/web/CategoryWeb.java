// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;
import dcstore.jpa.CategoryEntity;
import dcstore.ejb.CategoryBeanLocal;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Pattern;

@ManagedBean
@RequestScoped
public class CategoryWeb {

    @EJB
    private CategoryBeanLocal categoryBean;
    private int id;
    @Pattern(regexp="[A-Za-z0-9-_ ]+")
    private String name;
    @Pattern(regexp="[A-Za-z0-9-_ ]*")
    private String nameNew;

    public String getNameNew() {
        return nameNew;
    }
        
    public void setNameNew(String nameNew) {
        this.nameNew = nameNew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<CategoryEntity> getAll() {
        return categoryBean.getAll();
    }
    
    public void add() throws Exception {
        categoryBean.add(nameNew);
        this.nameNew="";
    }

    public void edit() throws Exception {
        categoryBean.edit(id, name);
        FacesContext.getCurrentInstance().getExternalContext().dispatch("admin-categories.xhtml?id=-1");
    }
    
    public void del(int id) throws Exception {
        categoryBean.del(id);
    }
}