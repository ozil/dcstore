/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dcstore.ejb;

import dcstore.jpa.TaxEntity;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dcebula
 */
@Stateful
public class TaxBean implements TaxBeanLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void add(double rate) {
        TaxEntity taxEntity=new TaxEntity();
        taxEntity.setRate(rate);
        em.persist(taxEntity);
    }
    
    @Override
    public void del(int idTax) {
        TaxEntity taxEntity=(TaxEntity)em.createNamedQuery("tax.getById").setParameter("id", idTax).getSingleResult();
        em.remove(taxEntity);
    }

    @Override
    public List<TaxEntity> getAll() {
        return em.createNamedQuery("tax.all").getResultList();
    }
}
