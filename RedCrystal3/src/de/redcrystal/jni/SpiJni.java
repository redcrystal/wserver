package de.redcrystal.jni;

import org.jwebsocket.console.JWebSocketEmbedded;

import static org.jwebsocket.console.JWebSocketEmbedded.*;

/**
 * SPI JNI: Java class based on c code to connect SPI driver
 * 
 * @author mngo
 * 
 */
public class SpiJni {
    public void close() {
    }
    public void init(){
    }
    /** to load spijni.dll in Windows or libspijni.so */

    /** to initialize spi port */
    /**
     * to transfer 16 bits = 2 bytes
     * 
     * @param tx
     *            data to be transferred
     * @param size
     *            the data size
     * @return rx data in 2 bytes
     */
    public char[] transferToFpga(char[] tx, int size) {
        char [] data;
        data= new char[]{'F','F'};
        return data;
    }

    /** main to test */
    public static void main(String[] args) {
    }
}
