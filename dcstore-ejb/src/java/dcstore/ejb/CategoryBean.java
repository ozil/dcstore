// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import dcstore.jpa.CategoryEntity;
import dcstore.jpa.ProductEntity;
import javax.ejb.Stateful;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author dcebula
 */
@Stateful
public class CategoryBean implements CategoryBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<CategoryEntity> getAll() {
        return em.createNamedQuery("category.getAll").getResultList();
    }

    @Override
    public void add(String name) {
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        em.persist(category);
    }

    @Override
    public void edit(Long id, String name) {
        CategoryEntity category;
        category = (CategoryEntity) em.createNamedQuery("category.getById").setParameter("id", id).getSingleResult();
        category.setName(name);
    }

    @Override
    public void del(Long id) {
        CategoryEntity category;
        category = (CategoryEntity) em.createNamedQuery("category.getById").setParameter("id", id).getSingleResult();
        em.remove(category);
    }

    @Override
    public List<ProductEntity> getProducts(Long idCategory) {
        CategoryEntity category;
        category = (CategoryEntity) em.createNamedQuery("category.getById").setParameter("id", idCategory).getSingleResult();
        return category.getProducts();
    }
    
    @Override
    public CategoryEntity getById(Long idCategory) {
        CategoryEntity category = null;
        category = (CategoryEntity) em.createNamedQuery("category.getById").setParameter("id", idCategory).getSingleResult();
        return category;
    }
}