// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import dcstore.jpa.SettingsEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dcebula
 */
@Stateless
public class SettingsBean implements SettingsBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void set(String name, String value) {
        SettingsEntity settingsEntity = null;

        try {
            settingsEntity = (SettingsEntity) em.createNamedQuery("settings.getByName").setParameter("name", name).getSingleResult();
            settingsEntity.setValue(value);
        } catch (NoResultException e) {
        }

        if (settingsEntity == null) {
            settingsEntity = new SettingsEntity();
            settingsEntity.setName(name);
            settingsEntity.setValue(value);
            em.persist(settingsEntity);
        }
    }

    @Override
    public String get(String name) {
        try {
            SettingsEntity settingsEntity;
            settingsEntity = (SettingsEntity) em.createNamedQuery("settings.getByName").setParameter("name", name).getSingleResult();
            return settingsEntity.getValue();
        } catch (NoResultException e) {
            return "";
        }
    }
}
