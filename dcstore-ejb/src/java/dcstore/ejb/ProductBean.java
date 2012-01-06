// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import dcstore.jpa.CategoryEntity;
import dcstore.jpa.ProductEntity;
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
public class ProductBean implements ProductBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(String name, Long idCategory, double price, Long idTax) {
        CategoryEntity category = (CategoryEntity) em.createNamedQuery("category.getById").setParameter("id", idCategory).getSingleResult();
        TaxEntity tax = (TaxEntity) em.createNamedQuery("tax.getById").setParameter("id", idTax).getSingleResult();
        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setTax(tax);
        em.persist(product);
    }

    @Override
    public List<ProductEntity> getAll() {
        return em.createNamedQuery("product.all").getResultList();
    }

    @Override
    public void del(Long id) {
        ProductEntity product = (ProductEntity) em.createNamedQuery("product.getById").setParameter("id", id).getSingleResult();
        em.remove(product);
    }

    @Override
    public void edit(Long id, String name, Long idCategory, double price, Long idTax) {
        CategoryEntity category = (CategoryEntity) em.createNamedQuery("category.getById").setParameter("id", idCategory).getSingleResult();
        ProductEntity product = (ProductEntity) em.createNamedQuery("product.getById").setParameter("id", id).getSingleResult();
        TaxEntity tax = (TaxEntity) em.createNamedQuery("tax.getById").setParameter("id", idTax).getSingleResult();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setTax(tax);
    }
}
