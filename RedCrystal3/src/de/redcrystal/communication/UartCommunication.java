package de.redcrystal.communication;

import java.io.*;

/**
 * UART: Java class to connect UART port
 * 
 * @author tran
 * 
 */
public class UartCommunication {

    private String path = "/dev/ttyS1";
    private FileInputStream in;
    private FileOutputStream out;
    private PrintStream uartPrint;
    private InputStreamReader uartReceiver;
    private Reader uartBuffer;

    /** to initialize uart port */
    public void init() {
        try {

            in = new FileInputStream(path);
            uartReceiver = new InputStreamReader(in, "UTF8");
            out = new FileOutputStream(path);
            uartPrint = new PrintStream(out);
            uartBuffer = new BufferedReader(uartReceiver);

            // uartPrint.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /** to close uart port */
    public void close() {
        try {
            in.close();
            out.close();
            uartPrint.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * to transfer data to UART
     * 
     * @param tx
     *            : data to transfer
     */
    public void transfer(String tx) {
        try {
            uartPrint.println(tx);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * receive data from UART
     * 
     * @return
     */
    public String receiver() {
        try {
            return uartBuffer.toString();
            // return "test";

        } catch (Exception e) {
            System.out.println(e);
            return "";
        }

    }

    /** main to test */
    public static void main(String[] args) {
        System.out.println("In Java - Result:");
        String tx = "Hello World";
        UartCommunication uart = new UartCommunication();
        uart.init();
        uart.transfer(tx);
        uart.close();

    }
}
