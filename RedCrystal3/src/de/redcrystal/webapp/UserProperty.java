/**
 * copyright 2013, redcrystal.de 
 */
package de.redcrystal.webapp;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.redcrystal.webapp.model.system.AvailableThemes;
import de.redcrystal.webapp.model.system.Theme;
import de.redcrystal.webapp.util.Utilities;

/**
 * the user property
 * 
 * @author Tran
 * 
 */
@ManagedBean(name = "userProperty")
@SessionScoped
public class UserProperty implements Serializable {
    /**
     * generated serial version id
     */
    private static final long serialVersionUID = -844552354039409563L;

    /** the user name */
    private String userName;

    /** logged user */
    private boolean logged;

    /** the current theme */
    private Theme currentTheme;

    /** all available themes */
    private List<Theme> availableThemes;

    /**
     * the constructor
     */
    public UserProperty() {
        availableThemes = AvailableThemes.getInstance().getThemes();
        String theme = "rocket";//rocket Utilities.getCookie(Constants.THEME);
        if (theme != null && AvailableThemes.getInstance().getThemeForName(theme) != null) {
            currentTheme = AvailableThemes.getInstance().getThemeForName(theme);
        } else {
            currentTheme = AvailableThemes.getInstance().getThemeForName("rocket");
        }
        logged = false;
        userName = null;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the currentTheme
     */
    public Theme getCurrentTheme() {
        return currentTheme;
    }

    /**
     * @param currentTheme
     *            the currentTheme to set
     */
    public void setCurrentTheme(Theme currentTheme) {
        this.currentTheme = currentTheme;
        Utilities.saveCookie(Constants.THEME, currentTheme.getName(), 5184000); // 60 days
    }

    /**
     * @return the availableThemes
     */
    public List<Theme> getAvailableThemes() {
        return availableThemes;
    }

    /**
     * @return the logged
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * @param logged
     *            the logged to set
     */
    public void setLogged(boolean logged) {
        this.logged = logged;
    }

}
