package de.redcrystal.webapp.model.fpga;

/**
 * the device address
 * 
 * @author Tran
 * 
 */
public enum FpgaDeviceAddress {
    /** the routing table */
    ROUTING_TABLE(0x10),

    /** the gpio controller */
    GPIO_CONTROLLER(0x20),

    /** virtual motor block */
    VMOTOR_BLOCK(0x30),
    
    /** virtual servo block*/
    VSERVO_BLOCK(0xB0);

    /** the component address */
    private int address;

    /**
     * the constructor
     * 
     * @param address
     *            the address to set
     */
    private FpgaDeviceAddress(int address) {
        this.address = address;
    }

    /**
     * @return the address
     */
    public int getAddress() {
        return address;
    }
}
