/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dcstore.ejb;

import dcstore.jpa.ProductEntity;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dcebula
 */
@Stateful
public class ProductBean implements ProductBeanLocal {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void add(String name, int group) {
        ProductEntity product=new ProductEntity();
        product.setName(name);
        em.persist(product);
    }
    
    @Override
    public List<ProductEntity> getAll() {
        return em.createNamedQuery("product.all").getResultList();
    }
}
