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

@Stateful
public class Category implements CategoryBean {
	@PersistenceContext
	private	EntityManager em;

	public void add(String name) {
		CategoryEntity category=new CategoryEntity();
		category.setName(name);
		em.persist(category);
	}

	public List<CategoryEntity> getAll() {
		return em.createNamedQuery("category.getAll").getResultList();
	}

	public void edit(int id, String name) throws Exception {
		CategoryEntity category;
		category=(CategoryEntity)em.createNamedQuery("category.getById").setParameter("id", id).getSingleResult();
		if (category==null)
			throw new Exception("Category with "+id+" not found");
		category.setName(name);
	}
}

