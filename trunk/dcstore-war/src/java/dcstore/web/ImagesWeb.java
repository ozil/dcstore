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
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
    @ManagedProperty(value = "#{resource['img:no_image.jpg']}")
    private String noImage;

    public String getNoImage() {
        return noImage;
    }

    public void setNoImage(String noImage) {
        this.noImage = noImage;
    }

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
        path = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("imgdir");
        if (!path.endsWith("/")) {
            path += "/";
        }

        return path;
    }

    public String getImgPath(Long idProduct, Long idImage) {
        String path = this.getImgDir();
        path += idProduct.toString() + "-" + idImage.toString() + ".jpg";
        return path;
    }

    public String getImgURLPrefix() {
        String path;
        path = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("imgurl");
        if (!path.endsWith("/")) {
            path += "/";
        }
        return path;
    }

    public String getImgURL(Long idProduct, Long idImage) {
        String path;
        path = this.getImgURLPrefix();
        path += idProduct.toString() + "-" + idImage.toString() + ".jpg";

        return path;
    }

    public void add() {
        Long idImage = -1L;

        try {
            if (!FilenameUtils.getExtension(file.getName()).equals("jpg")) {
                throw new Exception("Only jpg files are supported");
            }

            if (this.getIdProduct() == null) {
                throw new Exception("Could not read id product");
            }

            idImage = imageBean.add(this.getIdProduct());
            if (idImage <= 0) {
                throw new Exception("Getting new image id failed");
            }

            FileOutputStream out = new FileOutputStream(this.getImgPath(idProduct, idImage));
            IOUtils.copy(file.getInputStream(), out);
            out.close();
        } catch (Exception e) {
            try {
                if (idImage > 0) {
                    imageBean.del(idImage);
                }
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Rollbacking image from db failed"));
            }

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

    public List<String> getImagesURL() {
        List<String> ret = null;

        try {
            List<ImageEntity> images = null;
            images = imageBean.getForProduct(idProduct);
            ret = new LinkedList<String>();

            for (ImageEntity img : images) {
                ret.add(this.getImgURL(idProduct, img.getId()));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while fetching images"));
        }
        
        return ret;
    }

    public void del() {
        Long idImage = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        try {
            String path = getImgPath(idProduct, idImage);
            File f = new File(path);
            imageBean.del(idImage);
            f.delete();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while deleting image"));
        }
    }

    public boolean isCover(Long idImage) {
        ImageEntity image;
        image = imageBean.get(idImage);
        return image.isCover();
    }

    public void toggleCover() {
        Long idImage;
        try {
            idImage = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
            imageBean.toggleCover(idImage);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while changing cover flag"));
        }
    }

    public String getCoverURL(Long idProduct) {
        Long idImage = 0L;

        try {
            idImage = imageBean.getCoverId(idProduct);
        } catch (Exception e) {
        }

        String path;

        if (idImage > 0) {
            path = this.getImgURL(idProduct, idImage);
        } else {
            path = noImage;
        }

        return path;
    }
}
