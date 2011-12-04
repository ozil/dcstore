package dcstore.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import dcstore.jpa.CategoryEntity;

@ManagedBean
@RequestScoped
public class CategoriesWeb {
	public List<CategoryEntity> getCategories() {
		List<CategoryEntity> list=new ArrayList<CategoryEntity>();
		CategoryEntity category;
		category=new CategoryEntity(); category.setId(1); category.setName("Toshiba"); list.add(category);
		category=new CategoryEntity(); category.setId(2); category.setName("Acer"); list.add(category);
		category=new CategoryEntity(); category.setId(3); category.setName("Asus"); list.add(category);
		category=new CategoryEntity(); category.setId(4); category.setName("Dell"); list.add(category);

		return list;
	}
}

