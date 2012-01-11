// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import dcstore.jpa.ImageEntity;
import dcstore.jpa.ProductEntity;
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
        ProductEntity product=(ProductEntity)em.createNamedQuery("product.getById").setParameter("id", idProduct).getSingleResult();
        ImageEntity image=new ImageEntity();
        
        image.setPosition(0);
        image.setCover(false);
        image.setProduct(product);
        
        em.persist(image);
        em.flush();
        
        return image.getId();
    }
    
}
