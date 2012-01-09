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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author dcebula
 */
@ManagedBean
@RequestScoped
public class TaxWeb {

    @EJB
    private TaxBeanLocal taxBean;
    private Integer rateNew;
    private int[] idDelete;

    public int[] getIdDelete() {
        return idDelete;
    }

    public void setIdDelete(int[] idDelete) {
        this.idDelete = idDelete;
    }

    public Integer getRateNew() {
        return rateNew;
    }

    public void setRateNew(Integer rateNew) {
        this.rateNew = rateNew;
    }

    /** Creates a new instance of TaxWeb */
    public TaxWeb() {
    }

    public void add() {
        try {
            taxBean.add(rateNew);
            rateNew = null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing request: " + e.getMessage()));
        }
    }

    public void del() {
        try {
            for (int id : idDelete) {
                taxBean.del(id);
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing request: " + e.getMessage()));
        }
    }

    public List<TaxEntity> getAll() {
        List<TaxEntity> ret = null;

        try {
            ret = taxBean.getAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing request: " + e.getMessage()));
        }

        return ret;
    }
}
