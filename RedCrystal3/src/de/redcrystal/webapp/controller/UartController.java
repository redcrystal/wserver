/**
 * 
 */
package de.redcrystal.webapp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

import de.redcrystal.communication.*;


/**
 * the controller for the page spitest.xhtml
 * 
 * @author Tran
 * 
 */
@ManagedBean
@ViewScoped
public class UartController implements Serializable {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = 3538949941970278722L;

    /** Logger object */
    private static final Logger LOGGER = Logger.getLogger(UartController.class);

    /** the tx data */
    private String tx = "";

    /** the rx data */
    private String rx = "";
    
    /** the uart port */
    private UartCommunication uart;
    

    public UartController() {
    	
    }

    /**
     * PostConstructor to initialize data
     */
    @PostConstruct
    public void init() {
    	uart = new UartCommunication();
    	uart.init();
    	
    }
    


    /**
     * @return the tx
     */
    public String getTx() {
        return tx;
    }

    /**
     * @param tx
     *            the tx to set
     */
    public void setTx(String tx) {
        this.tx = tx;
    }

    /**
     * @return the rx
     */
    public String getRx() {
        return rx;
    }

    /**
     * @param rx
     *            the rx to set
     */
    public void setRx(String rx) {
        this.rx = rx;
    }

    /**
     * send tx to Uart Port
     * 
     * @return empty string
     */
    public String transferToUart() {
        LOGGER.info("send to fpga for tx = " + tx);
        if (tx == null || tx.isEmpty()) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Action failed", "Tx is empty");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }
      
        
        uart.transfer(tx);
        rx=uart.receiver();
        
        LOGGER.info("Rx " + rx);
        return "";
    }


}
