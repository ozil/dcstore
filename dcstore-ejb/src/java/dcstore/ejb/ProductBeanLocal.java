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

    List<ProductEntity> getAll();
    
    ProductEntity getById(Long idProduct);

    void add(String name, Long idCategory, double price, Long idTax);

    void edit(Long id, String name, Long idCategory, double price, Long idTax);

    void del(Long id);        
}
