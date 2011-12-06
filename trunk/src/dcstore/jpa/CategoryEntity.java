// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="dc_categories")
public class CategoryEntity {
	@Id
	@Column(name="id_category")
	private int id;
	@Column(name="name")
	private String name;

	public int getId() {
		return id;	
	}

	public void setId(int id) {
		this.id=id;	
	}

	public String getName() {
		return name;	
	}

	public void setName(String name) {
		this.name=name;	
	}
}

