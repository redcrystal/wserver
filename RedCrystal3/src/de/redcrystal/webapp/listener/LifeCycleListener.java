/**
 * copyright 2013, redcrystal.de
 */
package de.redcrystal.webapp.listener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.redcrystal.webapp.UserProperty;
import de.redcrystal.webapp.util.Utilities;

/**
 * the life cycle listener
 * 
 * @author mngo
 * 
 */
public class LifeCycleListener implements PhaseListener {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = 1145007007961037344L;

    /** Logger object */
    private static final Logger LOGGER = Logger.getLogger(LifeCycleListener.class);

    /** the list of protected content */
    private static List<String> authorizedPages = Arrays.asList("/views/design.xhtml", "/views/fpga.xhtml");

    @Override
    public void afterPhase(PhaseEvent arg0) {
        boolean protectedView = authorizedPages.contains(getViewId());
        UserProperty userProperty = Utilities.getUserProperty();
        if (protectedView
                && (userProperty == null || userProperty.getUserName() == null || userProperty.getUserName().isEmpty() || !userProperty.isLogged())) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletResponse response = (HttpServletResponse) context.getResponse();
            try {
                response.sendRedirect(context.getRequestContextPath() + "/login.jsf");
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent arg0) {
       
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    /**
     * 
     * @return current view index
     */
    public String getViewId() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc != null && fc.getViewRoot() != null) {
            return fc.getViewRoot().getViewId();
        }
        return null;
    }

}
