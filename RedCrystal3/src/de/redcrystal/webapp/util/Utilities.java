/**
 * copyright 2013, redcrystal.de 
 */
package de.redcrystal.webapp.util;

import java.io.File;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.redcrystal.webapp.UserProperty;

/**
 * the utilities class
 * 
 * @author mngo
 * 
 */
public class Utilities {

    /**
     * gets Servlet Request
     * 
     * @return HttpServletRequest
     */
    public static HttpServletRequest getServletRequest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return (HttpServletRequest) facesContext.getExternalContext().getRequest();
    }

    /**
     * gets Servlet Response
     * 
     * @return HttpServletResponse
     */
    public static HttpServletResponse getServletResponse() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return (HttpServletResponse) facesContext.getExternalContext().getResponse();
    }

    /**
     * saves cookie
     * 
     * @param name
     *            cookie name
     * @param value
     *            cookie value * @param timeout_seconds expire time in seconds
     */
    public static void saveCookie(String name, String value, int timeout_seconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(timeout_seconds);
        HttpServletResponse response = getServletResponse();
        response.addCookie(cookie);
    }

    /**
     * saves cookie with default time out
     * 
     * @param name
     *            cookie name
     * @param value
     *            cookie value
     */
    public static void saveCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        HttpServletResponse response = getServletResponse();
        response.addCookie(cookie);
    }

    /**
     * gets cookie
     * 
     * @param name
     *            cookie name
     * @return cookie value as String
     */
    public static String getCookie(String name) {
        Cookie[] cookies = getServletRequest().getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(name)) {
                    return cookies[i].getValue();
                }
            }
        }
        return null;
    }

    /**
     * @return absolute path of the web content directory
     */
    public static String getRealWebContentPath() {
        String absolutePath = getAbsolutePath();
        String[] temp;
        String delimiter = "WEB-INF";
        temp = absolutePath.split(delimiter);
        String result = temp[0];
        result = result.replace("%20", " ");
        return result;
    }

    /**
     * @return as String absolute path of this Class
     */
    private static String getAbsolutePath() {
        java.security.ProtectionDomain pd = Utilities.class.getProtectionDomain();
        if (pd == null) {
            return null;
        }
        java.security.CodeSource cs = pd.getCodeSource();
        if (cs == null) {
            return null;
        }
        java.net.URL url = cs.getLocation();
        if (url == null) {
            return null;
        }
        File f = new File(url.getFile());
        return f.getAbsolutePath();
    }

    /**
     * Get the User property object from the http session
     * 
     * @return User property object from http session
     */
    public static UserProperty getUserProperty() {
        HttpSession session = getHttpSession();
        return (UserProperty) session.getAttribute("userProperty");
    }

    /**
     * Bind the user property into the session attribute
     * 
     * @param aProperty
     *            User property object to bind into the session attribute
     */
    public static void setUserProperty(UserProperty aProperty) {
        HttpSession session = getHttpSession();
        session.setAttribute("userProperty", aProperty);
    }

    /**
     * Get the Http session from the faces context.
     * 
     * @return Return any existing session instance associated with the current request, or return null if there is no such session.
     */
    public static HttpSession getHttpSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session == null) {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        }
        return session;
    }

    /**
     * to format hex string in capital letters of the length 2
     * 
     * @param value
     *            the int value
     * @return the formatted string
     */
    public static String convertToHexString(int value) {
        String hexValue = Integer.toHexString(value);
        if (hexValue != null && hexValue.length() < 2) {
            hexValue = "0" + hexValue;
        }
        return hexValue.toUpperCase();
    }

    /**
     * convert a integer value to 16 bit binary
     * 
     * @param value
     *            the value
     * @return string of length 16
     */
    public static String convertTo16BitString(int value) {
        String ret = Integer.toBinaryString(value);
        while (ret.length() < 16) {
            ret = "0" + ret;
        }
        if (ret.length() > 16) {
            ret = ret.substring(ret.length() - 16, ret.length());
        }
        return ret;
    }
}
