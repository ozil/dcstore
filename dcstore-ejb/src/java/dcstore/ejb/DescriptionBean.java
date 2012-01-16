// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import dcstore.jpa.DescriptionEntity;
import dcstore.jpa.ProductEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dcebula
 */
@Stateless
public class DescriptionBean implements DescriptionBeanLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void set(Long idProduct, String body) {
        ProductEntity product = (ProductEntity) em.createNamedQuery("product.getById").setParameter("id", idProduct).getSingleResult();       
        DescriptionEntity desc;
        desc = product.getDescription();
        if (desc==null) {
            desc = new DescriptionEntity();
            desc.setProduct(product);
            desc.setBody(body);
            em.persist(desc);
        }
        else
            desc.setBody(body);        
    }
    
}
