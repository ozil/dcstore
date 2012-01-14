// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.web;

import dcstore.ejb.ImageBeanLocal;
import dcstore.ejb.ProductBeanLocal;
import dcstore.jpa.ImageEntity;
import dcstore.jpa.ProductEntity;
import java.io.FileOutputStream;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

/**
 *
 * @author dcebula
 */
@ManagedBean
@SessionScoped
public class ImagesWeb {

    private Long idProduct;
    private ProductEntity product;
    private UploadedFile file;
    @EJB
    private ImageBeanLocal imageBean;
    @EJB
    private ProductBeanLocal productBean;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

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

        try {
            this.product = productBean.getById(idProduct);
            return "admin-images.xhtml";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while fetching product by id"));
            return "";
        }
    }

    public String getImgDir() {
        String path;
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
        path += "/resources/userdata/img/";

        return path;
    }

    public String getImgPath(Long idProduct, Long idImage) {
        String path = this.getImgDir();
        path += idProduct.toString() + "-" + idImage.toString() + ".jpg";
        return path;
    }
    
    public String getImgURL(Long idProduct, Long idImage) {
        String path;
        path="/resources/userdata/img/";
        path+=idProduct.toString() + "-" + idImage.toString() + ".jpg";
        
        return path;
    }

    public void add() {
        try {
            if (!FilenameUtils.getExtension(file.getName()).equals("jpg")) {
                throw new Exception("Only jpg files are supported");
            }

            if (this.getIdProduct() == null) {
                throw new Exception("Could not read id product");
            }

            Long idImage;
            idImage = imageBean.add(this.getIdProduct());
            if (idImage <= 0) {
                throw new Exception("Image perservation failed");
            }

            FileOutputStream out = new FileOutputStream(this.getImgPath(idProduct, idImage));
            IOUtils.copy(file.getInputStream(), out);
            out.close();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("File upload error: " + e.getMessage()));
        }
    }

    public String getHeaderInfo() {
        if (product != null && product.getName().length() > 0) {
            return "Editing images for product " + product.getName();
        } else {
            return "Editing images";
        }
    }

    public List<ImageEntity> getImages() {
        List<ImageEntity> images = null;

        try {
            images = imageBean.getForProduct(idProduct);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while fetching images"));
        }

        return images;
    }
}
