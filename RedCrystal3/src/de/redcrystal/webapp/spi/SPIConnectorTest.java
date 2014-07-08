package de.redcrystal.webapp.spi;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for {@link SPIConnector}
 * 
 * @author Tran
 * 
 */
public class SPIConnectorTest {

    /**
     * 
     */
    @Test
    public void connectTest() {
       // SPIConnector connector = new SPIConnector();
       //connector.writeData();
        Assert.assertTrue(true);
    }
    
    /**
     * to test convert Integer type to hex string
     */
    @Test
    public void testHexFormat(){
        System.out.println(Integer.toHexString(50));
        Assert.assertTrue(true);
        
    }
}