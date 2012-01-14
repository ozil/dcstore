// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import javax.ejb.Local;
import java.util.List;
import dcstore.jpa.CategoryEntity;
import dcstore.jpa.ProductEntity;

/**
 *
 * @author dcebula
 */
@Local
public interface CategoryBeanLocal {

    void add(String name);

    List<CategoryEntity> getAll();

    void edit(Long id, String name);

    void del(Long id);

    List<ProductEntity> getProducts(Long idCategory);

    CategoryEntity getById(Long idCategory);
}