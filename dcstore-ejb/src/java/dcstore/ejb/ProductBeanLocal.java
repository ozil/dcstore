/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public void add(String name, int group);
}
