// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import javax.ejb.Local;

/**
 *
 * @author dcebula
 */
@Local
public interface DescriptionBeanLocal {

    void set(Long idProduct, String body);
}
