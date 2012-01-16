// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import dcstore.jpa.ImageEntity;
import dcstore.jpa.ProductEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dcebula
 */
@Stateless
public class ImageBean implements ImageBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long add(Long idProduct) {
        ProductEntity product = (ProductEntity) em.createNamedQuery("product.getById").setParameter("id", idProduct).getSingleResult();
        ImageEntity image = new ImageEntity();

        image.setPosition(0);
        image.setCover(false);
        image.setProduct(product);

        em.persist(image);
        em.flush();

        return image.getId();
    }

    @Override
    public List<ImageEntity> getAll() {
        List<ImageEntity> images;
        images = em.createNamedQuery("image.getAll").getResultList();
        return images;
    }

    @Override
    public List<ImageEntity> getForProduct(Long idProduct) {
        List<ImageEntity> images;
        images = em.createNamedQuery("image.getForProduct").setParameter("id", idProduct).getResultList();
        return images;
    }

    @Override
    public void del(Long idImage) {
        ImageEntity image = (ImageEntity) em.createNamedQuery("image.getById").setParameter("id", idImage).getSingleResult();
        em.remove(image);
    }

    @Override
    public ImageEntity get(Long idImage) {
        ImageEntity image = (ImageEntity) em.createNamedQuery("image.getById").setParameter("id", idImage).getSingleResult();
        return image;
    }

    @Override
    public void toggleCover(Long idImage) {
        ImageEntity image = (ImageEntity) em.createNamedQuery("image.getById").setParameter("id", idImage).getSingleResult();
        Long idProduct;
        idProduct = image.getProduct().getId();

        List<ImageEntity> images;
        images = em.createNamedQuery("image.getForProduct").setParameter("id", idProduct).getResultList();
        for (ImageEntity img : images) {
            img.setCover(false);
        }

        image.setCover(true);
    }

    @Override
    public Long getCoverId(Long idProduct) {
        Long idImage = 0L;

        try {
            ImageEntity image;
            image = (ImageEntity) em.createNamedQuery("image.getCoverForProduct").setParameter("id", idProduct).getSingleResult();
            idImage = image.getId();
        } catch (Exception e) {
        }

        return idImage;
    }
}
