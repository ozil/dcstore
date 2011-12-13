/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dcstore.ejb;

import dcstore.jpa.TaxEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dcebula
 */
@Local
public interface TaxBeanLocal {

    public void add(double rate);
    
    public void del(int idTax);

    public List<TaxEntity> getAll();
}
