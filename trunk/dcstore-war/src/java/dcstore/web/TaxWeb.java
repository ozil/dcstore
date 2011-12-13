// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.web;

import dcstore.ejb.TaxBeanLocal;
import dcstore.jpa.TaxEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author dcebula
 */
@ManagedBean
@RequestScoped
public class TaxWeb {

    @EJB
    private TaxBeanLocal taxBean;
    private Double rateNew;
    private int[] idDelete;

    public int[] getIdDelete() {
        return idDelete;
    }

    public void setIdDelete(int[] idDelete) {
        this.idDelete = idDelete;
    }

    public Double getRateNew() {
        return rateNew;
    }

    public void setRateNew(Double rateNew) {
        this.rateNew = rateNew;
    }

    /** Creates a new instance of TaxWeb */
    public TaxWeb() {
    }
    
    public void add() {
        taxBean.add(rateNew);
        rateNew=null;
    }
    
    public void del() {
        for (int id : idDelete)
            taxBean.del(id);
    }

    public List<TaxEntity> getAll() {
        return taxBean.getAll();
    }
}
