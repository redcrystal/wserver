/**
 * 
 */
package de.redcrystal.webapp.model.fpga;

import java.io.Serializable;

/**
 * a FPGA cell
 * 
 * @author mngo
 * 
 */
public class FpgaRegister implements Serializable {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = -1975304773962470293L;

    /** the I/O pin */
    private int index;

    /** the I/O address */
    private int address;

    /** the current value of routing cell */
    private int value;

    /**
     * the default constructor
     */
    public FpgaRegister() {
    }

    /**
     * constructor with params
     * 
     * @param iopin
     *            the iopin to set
     * @param address
     *            the address to set
     * @param value
     *            the value to set
     */
    public FpgaRegister(int index, int address, int value) {
        super();
        this.index = index;
        this.address = address;
        this.value = value;
    }

    /**
     * @return the iopin
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index
     *            the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the address
     */
    public int getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(int address) {
        this.address = address;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * converts address to hex string
     */
    public String getAddressInHexString() {
        return formatHexString(Integer.toHexString(address));
    }

    /**
     * converts address to hex string
     */
    public String getValueInHexString() {
        return formatHexString(Integer.toHexString(value));
    }

    /**
     * converts address to hex string
     */
    public String getIndexInHexString() {
        return formatHexString(Integer.toHexString(index));
    }

    /**
     * to format hex string in capital letters of the length 2
     * 
     * @param hexValue
     *            the hex value
     * @return the formatted string
     */
    private String formatHexString(String hexValue) {
        if (hexValue != null && hexValue.length() < 2) {
            hexValue = "0" + hexValue;
        }
        return hexValue.toUpperCase();
    }

    /**
     * to set value by a hex string
     * 
     * @param valueInHexString
     *            the value in hex
     */
    public void setValueInHexString(String valueInHexString) {
        try {
            value = Integer.decode("0x" + valueInHexString);
        } catch (Exception e) {
            value = 0;
        }
    }
}
