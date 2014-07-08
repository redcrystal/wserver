package de.redcrystal.webapp.model.fpga;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * 
 * 
 * VSERVO_0(0xC0),
 * 
 * VSERVO_1(0xC1),
 * 
 * VSERVO_2(0xC2),
 * 
 * VSERVO_3(0xC3),
 * 
 * VSERVO_4(0xC4),
 * 
 * VSERVO_5(0xC5),
 * 
 * VSERVO_6(0xC6),
 * 
 * VSERVO_7(0xC7); end of virtual motor definition
 * 
 * @author Tran
 * 
 */
public class VServoDevices {

    /** the list of servo devices */
    private List<FpgaDevice> servoDeviceList;

    /** the constructor */
    public VServoDevices() {
        servoDeviceList = new ArrayList<FpgaDevice>();
        int startAddress = 0xC0;
        for (int i = 0; i < 8; i++) {
            FpgaDevice servo = new FpgaDevice();
            servo.setName("VServo_" + i);
            servo.setAddress(startAddress+i);
            List<FpgaRegister> registers = new ArrayList<FpgaRegister>();
            FpgaRegister register0 = new FpgaRegister(0, 0x00, 0);
            FpgaRegister register1 = new FpgaRegister(1, 0x04, 0);
            FpgaRegister register2 = new FpgaRegister(2, 0x08, 0);
            FpgaRegister register3 = new FpgaRegister(3, 0x0C, 0);
            FpgaRegister register4 = new FpgaRegister(4, 0x10, 0);
            FpgaRegister register5 = new FpgaRegister(5, 0x14, 0);
            FpgaRegister register6 = new FpgaRegister(6, 0x18, 0);
            registers.add(register0);
            registers.add(register1);
            registers.add(register2);
            registers.add(register3);
            registers.add(register4);
            registers.add(register5);
            registers.add(register6);
            servo.setRegisters(registers);
            servoDeviceList.add(servo);
        }
    }

    /**
     * @return the servoDeviceList
     */
    public List<FpgaDevice> getServoDeviceList() {
        return servoDeviceList;
    }

    /**
     * @param servoDeviceList
     *            the servoDeviceList to set
     */
    public void setServoDeviceList(List<FpgaDevice> servoDeviceList) {
        this.servoDeviceList = servoDeviceList;
    }

}
