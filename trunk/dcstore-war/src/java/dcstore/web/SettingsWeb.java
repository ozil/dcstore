// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.web;

import dcstore.ejb.SettingsBeanLocal;
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
public class SettingsWeb {

    @EJB
    private SettingsBeanLocal settingsBean;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /** Creates a new instance of SettingsWeb */
    public SettingsWeb() {
    }

    public void Load() {
        try {
            currency = settingsBean.get("currency");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while loading settings"));
        }
    }

    public void Save() {
        try {
            settingsBean.set("currency", currency);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while loading settings"));
        }
    }
}
