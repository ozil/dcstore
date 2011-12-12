// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import dcstore.jpa.CategoryEntity;
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
    public void add(String name) throws Exception {
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        em.persist(category);
    }

    @Override
    public void edit(Long id, String name) throws Exception {
        CategoryEntity category;
        category = (CategoryEntity) em.createNamedQuery("category.getById").setParameter("id", id).getSingleResult();
        category.setName(name);
    }

    @Override
    public void del(Long id) throws Exception {
        CategoryEntity category;
        category = (CategoryEntity) em.createNamedQuery("category.getById").setParameter("id", id).getSingleResult();
        em.remove(category);
    }
}