/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dcstore.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.fileupload.FileUpload;

/**
 *
 * @author dcebula
 */
@ManagedBean
@SessionScoped
public class ImagesWeb {
    private Long idProduct;
    private FileUpload fileUpload;

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }
    
    /** Creates a new instance of ImagesWeb */
    public ImagesWeb() {
    }
    
    public String go(Long idProduct) {
        
        
        this.setIdProduct(idProduct);
        return "admin-images.xhtml";
    }
}
