/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dcstore.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author dcebula
 */
@ManagedBean
@ApplicationScoped
public class InfoBean {

    /** Creates a new instance of InfoBean */
    public InfoBean() {
    }
    
    public String getVersion() {
        String version = null;
        
        try {
            version=FacesContext.getCurrentInstance().getExternalContext().getInitParameter("version");
        } catch (Exception e) {
            version="";
        }
        
        return "v"+version;
    }
}
