// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import javax.ejb.Local;
import java.util.List;
import dcstore.jpa.CategoryEntity;

/**
 *
 * @author dcebula
 */
@Local
public interface CategoryBeanLocal {

    public void add(String name);

    public List<CategoryEntity> getAll();

    public void edit(Long id, String name);

    public void del(Long id);
}