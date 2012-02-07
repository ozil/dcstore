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
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
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

    public String getImgPath(Long idProduct, Long idImage, String mode) {
        String path = this.getImgDir();
        path += idProduct.toString() + "-" + idImage.toString() + "-" + mode + ".jpg";
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

    public String getImgURL(Long idProduct, Long idImage, String mode) {
        String path;
        path = this.getImgURLPrefix();
        path += idProduct.toString() + "-" + idImage.toString() + "-" + mode + ".jpg";

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

            String imgPath = this.getImgPath(idProduct, idImage, "full");
            FileOutputStream out = new FileOutputStream(imgPath);
            IOUtils.copy(file.getInputStream(), out);
            out.close();

            this.resizeImage(imgPath, 300, 300, this.getImgPath(idProduct, idImage, "cover"));
            this.resizeImage(imgPath, 130, 130, this.getImgPath(idProduct, idImage, "category"));
            this.resizeImage(imgPath, 100, 100, this.getImgPath(idProduct, idImage, "mini"));
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
        Long idProduct = 0L;

        try {
            idProduct = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

            images = imageBean.getForProduct(idProduct);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while fetching images"));
        }

        return images;
    }

    public void del() {
        Long idImage = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        try {
            imageBean.del(idImage);
            new File(getImgPath(idProduct, idImage, "full")).delete();
            new File(getImgPath(idProduct, idImage, "cover")).delete();
            new File(getImgPath(idProduct, idImage, "category")).delete();
            new File(getImgPath(idProduct, idImage, "mini")).delete();
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
            path = this.getImgURL(idProduct, idImage, "cover");
        } else {
            path = noImage;
        }

        return path;
    }

    public String getCategoryURL(Long idProduct) {
        Long idImage = 0L;

        try {
            idImage = imageBean.getCoverId(idProduct);
        } catch (Exception e) {
        }

        String path;

        if (idImage > 0) {
            path = this.getImgURL(idProduct, idImage, "category");
        } else {
            path = noImage;
        }

        return path;
    }

    private void resizeImage(String inPath, int w, int h, String outPath) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(inPath));
            int ow, oh;
            ow = originalImage.getWidth();
            oh = originalImage.getHeight();
            double ratio = (double) ow / (double) oh;
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            int ch = (int) Math.round(w / ratio);

            BufferedImage resizedImage = new BufferedImage(w, h, type);
            Graphics2D g = resizedImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.white);
            g.fillRect(0, 0, w, h);
            g.drawImage(originalImage, 0, (int) (((float) h - (float) ch) / 2), w, ch, null);
            g.dispose();
            ImageIO.write(resizedImage, "jpg", new File(outPath));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while resizeing image: " + e.getMessage()));
        }
    }
}
