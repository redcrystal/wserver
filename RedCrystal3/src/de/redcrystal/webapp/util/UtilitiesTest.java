package de.redcrystal.webapp.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Tran
 * 
 */
public class UtilitiesTest {

    /**
     * 
     */
    @Test
    public void testConvertTo16BitString() {
        int value = 120;
        String bitString = Utilities.convertTo16BitString(value);
        System.out.println(bitString);
        System.out.println(bitString.substring(0,8));
        System.out.println(bitString.substring(8,16));
        Assert.assertTrue(true);
        int newValue = Integer.parseInt(bitString, 2);
        System.out.println(newValue);
    }
}
