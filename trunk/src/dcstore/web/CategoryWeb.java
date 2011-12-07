// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import dcstore.jpa.CategoryEntity;
import dcstore.ejb.CategoryBean;
import javax.ejb.EJB;

@ManagedBean
@RequestScoped
public class CategoryWeb {
	@EJB
	private CategoryBean categoryBean;

	private int id;
	private String name;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id=id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(String name) {
		this.name=name;
	}

	public List<CategoryEntity> getAll() {
		return categoryBean.getAll();
	}
}

