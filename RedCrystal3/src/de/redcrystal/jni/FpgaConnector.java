/**
 * 
 */
package de.redcrystal.jni;

import de.redcrystal.webapp.util.Utilities;
import org.apache.log4j.Logger;

import de.redcrystal.webapp.model.fpga.FpgaCommandConstants;
import de.redcrystal.webapp.model.fpga.FpgaDeviceAddress;
import de.redcrystal.webapp.model.fpga.GpioRegisterAddress;

/**
 * 
 * @author Tran
 * 
 */
public class FpgaConnector {

    /** Logger object */
    private static final Logger LOGGER = Logger.getLogger(FpgaConnector.class);

    /**
     * transfer multiple data to fpga. It will split in 16bits lock.
     * 
     * @param data
     *            the data to be transferred
     * @return rx in char array
     */
    public static char[] tranferMultipleData(char[] data) {
        LOGGER.info("tranferMultipleCommand(char[] data) called");
        SpiJni spiJni = new SpiJni();
        char rx[] = new char[data.length];
        String outputString = "";
        if (data.length >= 2) {
            for (int j = 0; j < data.length / 2; j++) {
                char[] twoBytes = new char[2];
                twoBytes[0] = data[2 * j];
                twoBytes[1] = data[2 * j + 1];
                //spiJni.init();
                char[] output = spiJni.transferToFpga(twoBytes, twoBytes.length);
                //spiJni.close();
                // save output
                rx[2 * j] = output[0];
                rx[2 * j + 1] = output[1];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < output.length; i++) {
                    String temp = String.format("%02x", (int) output[i]);
                    if (i < output.length - 1) {
                        temp = "0x" + temp + ", ";
                    } else {
                        temp = "0x" + temp;
                    }
                    sb.append(temp);
                }
                if (j != data.length / 2) {
                    outputString += sb.toString();
                } else {
                    outputString += sb.toString() + ", ";
                }
            }
        }
        LOGGER.info("rx = " + outputString);
        return rx;
    }

    /**
     * read from routing table
     * 
     * @param registerAddress
     *            the address within the component
     * @param deviceAddress
     *            Routing table, GPIO Controller, virtual motor block, virtual Servo block
     * @return the actual value
     */
    public static char readFpgaRegister(int registerAddress, int deviceAddress) {
        // read command
        // 0xCA, 0xFE : do nothing
        LOGGER.debug("execute read command");
        char[] readTx = { FpgaCommandConstants.SREAD, (char) deviceAddress, (char) registerAddress, (char) registerAddress, 0xCA, 0xFE };
        char[] readRx = FpgaConnector.tranferMultipleData(readTx);
        StringBuilder sb = new StringBuilder();
        // debug purpose
        sb.append("Rx(Read command): ");
        for (char c : readRx) {
            String temp = String.format("%02x", (int) c);
            sb.append("0x" + temp + ", ");
        }
        LOGGER.info(sb.toString());
        return readRx[readRx.length - 1];
    }

    /**
     * write to fpga register except for routing table
     * 
     * @param registerAddress
     *            the register address within the device
     * @param deviceAddress
     *            the device address
     * @param registerValue
     *            the new value of register
     * @return the rx buffer
     */
    public static char[] writeToFpgaRegister8bit(int registerAddress, int registerValue, int deviceAddress) {
        LOGGER.debug("execute write command for the device: " + deviceAddress);
        // for routing table
        // for other components
        char[] writeTx = { FpgaCommandConstants.SWRITE, (char) deviceAddress, (char) registerAddress, (char) registerValue };
        char[] writeRx = FpgaConnector.tranferMultipleData(writeTx);
        // debug purpose
        StringBuilder sb = new StringBuilder();
        sb.append("Rx(Write command): ");
        for (char c : writeRx) {
            String temp = String.format("%02x", (int) c);
            sb.append("0x" + temp + ", ");
        }
        LOGGER.info(sb.toString());
        return writeRx;
    }

    /**
     *
     * @param deviceAddress
     * @param value
     * @param registerAddress
     */
    public static String writeToFpgaRegister16bit(int registerAddress, int value, int deviceAddress) {
        String bitString16;
        bitString16 = Utilities.convertTo16BitString(value);
        char[] rx1= writeToFpgaRegister8bit(registerAddress, Integer.parseInt(bitString16.substring(8, 16), 2), deviceAddress); // 8 bits low
        char[] rx2= writeToFpgaRegister8bit(registerAddress + 4, Integer.parseInt(bitString16.substring(0, 8), 2), deviceAddress); // 8 bits high
        return rx1.toString()+ rx2.toString();

    }

    /**
     * write to routing table
     * 
     * @param registerValue
     *            the register value
     * @param registerAddress
     *            the register address
     * @return rx buffer
     */
    public static char[] writeToRoutingTable(int registerAddress, int registerValue) {
        char[] writeTx = { FpgaCommandConstants.SWRITE, (char) FpgaDeviceAddress.ROUTING_TABLE.getAddress(), (char) registerAddress,
                (char) registerValue, FpgaCommandConstants.SWRITE, (char) FpgaDeviceAddress.GPIO_CONTROLLER.getAddress(),
                GpioRegisterAddress.GPIO_INDEX_6, (char) registerAddress };
        char[] writeRx = FpgaConnector.tranferMultipleData(writeTx);
        // debug purpose
        StringBuilder sb = new StringBuilder();
        sb.append("Rx(Write command): ");
        for (char c : writeRx) {
            String temp = String.format("%02x", (int) c);
            sb.append("0x" + temp + ", ");
        }
        LOGGER.info(sb.toString());
        return writeRx;
    }

}
