// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import dcstore.jpa.ImageEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dcebula
 */
@Local
public interface ImageBeanLocal {

    Long add(Long idProduct);
    
    List<ImageEntity> getAll();
    
    List<ImageEntity> getForProduct(Long idProduct);
    
}
