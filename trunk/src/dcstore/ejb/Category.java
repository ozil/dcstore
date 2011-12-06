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

@Stateful
public class Category implements CategoryBean {
	@PersistenceContext
	private	EntityManager em;

	public void add(String name) {
		CategoryEntity category=new CategoryEntity();
		category.setName(name);
		em.persist(category);
	}
}

