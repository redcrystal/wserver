package de.redcrystal.webapp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import de.redcrystal.jni.SpiJni;

import java.lang.reflect.Method;

/**
 * Servlet listener
 * 
 * @author Tran
 * 
 */
public class ServletListener implements ServletContextListener {

	 /** Logger object */
    private static final Logger LOGGER = Logger.getLogger(ServletListener.class);
    
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LOGGER.info("close spi connection");
		// close SPI
		SpiJni spiConnector = new SpiJni();
		spiConnector.close();

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		LOGGER.info("intialize spi connection");
		// open SPI
		SpiJni spiConnector = new SpiJni();
		spiConnector.init();
        /*
        try {
            final Class<?> clazz = Class.forName("org.jwebsocket.console.JWebSocketEmbedded");
            final Method method = clazz.getMethod("main", String[].class);

            final Object[] args = new Object[1];
            args[0] = new String[] {};
            method.invoke(null, args);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        */
        //JWebSocketEmbedded.main(null);

	}

}
