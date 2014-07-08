package de.redcrystal.webapp.model.fpga;

import java.io.Serializable;
import java.util.List;

/**
 * the definition of a fpga device.
 * <br/>
 * one device consist of multiple register
 * 
 * @author Tran
 * 
 */
public class FpgaDevice implements Serializable {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = -7300415269391085928L;
    
    /** the virtual servo name*/
    private String name;
    
    /** the device address*/
    private int address;
    
    /** the list of register*/
    private List<FpgaRegister> registers;

    /**
     * @return the registers
     */
    public List<FpgaRegister> getRegisters() {
        return registers;
    }

    /**
     * @param registers the registers to set
     */
    public void setRegisters(List<FpgaRegister> registers) {
        this.registers = registers;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public int getAddress() {
        return address;
    }
    
    /**
     * @return the address
     */
    public String getAddressInHexString() {
        return formatHexString(Integer.toHexString(address));
    }

    /**
     * @param address the address to set
     */
    public void setAddress(int address) {
        this.address = address;
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

}
