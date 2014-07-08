/**
 * copyright 2013, redcrystal.de 
 */
package de.redcrystal.webapp.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.redcrystal.webapp.UserProperty;
import de.redcrystal.webapp.util.Utilities;

/**
 * 
 * login page controller
 * 
 * @author Tran
 * 
 */
@ManagedBean
@ViewScoped
public class LoginController implements Serializable {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = 1887600046096714580L;

    /** Logger object */
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    /** the RedCrystal's properties */
    private Properties properties;

    /** the user name */
    private String username;

    /** the password */
    private String password;

    /**
     * to initializes data
     */
    @PostConstruct
    public void initialize() {
        try {
            properties = new Properties();
            String path = this.getClass().getResource("/redcrystal.properties").getPath();
            path = path.replace("%20", " ");
            properties.load(new FileInputStream(path));

            UserProperty userProperty = Utilities.getUserProperty();
            if (userProperty != null && userProperty.isLogged()) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                HttpServletResponse response = (HttpServletResponse) context.getResponse();
                response.sendRedirect(context.getRequestContextPath() + "/views/design.jsf");
            }

        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * sign in function
     * 
     * @param actionEvent
     *            the action event
     */
    public void signIn(ActionEvent actionEvent) {
        LOGGER.info("sign in called");
        UserProperty userProperty = new UserProperty();
        boolean valid = false;
        if (username.equals(properties.getProperty("username")) && password.equals(properties.getProperty("password"))) {
            valid = true;
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login: Username and password don't match!",
                    "Username and password don't match!");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        if (valid) {
            userProperty.setUserName(username);
            userProperty.setLogged(true);
            Utilities.setUserProperty(userProperty);
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletResponse response = (HttpServletResponse) context.getResponse();
            try {
                response.sendRedirect(context.getRequestContextPath() + "/views/design.jsf");
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * sign out action
     * 
     * @param actionEvent
     *            the action event
     */
    public void signOut(ActionEvent actionEvent) {
        LOGGER.info("sign out called");
        UserProperty userProperty = new UserProperty();
        Utilities.setUserProperty(userProperty);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        String url = context.getRequestContextPath() + "/login.jsf";
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * @param properties
     *            the properties to set
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
  

}
