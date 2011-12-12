// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import dcstore.jpa.ProductEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dcebula
 */
@Local
public interface ProductBeanLocal {

    public List<ProductEntity> getAll();

    public void add(String name, Long idCategory);

    public void edit(Long id, String name, Long idCategory);

    public void del(Long id);
}
