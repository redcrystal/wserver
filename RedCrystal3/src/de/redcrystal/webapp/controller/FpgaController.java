/**
 * 
 */
package de.redcrystal.webapp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

import de.redcrystal.jni.FpgaConnector;
import de.redcrystal.jni.SpiJni;
import de.redcrystal.webapp.model.FpgaDeviceTableModel;
import de.redcrystal.webapp.model.FpgaRegisterTableModel;
import de.redcrystal.webapp.model.fpga.FpgaDevice;
import de.redcrystal.webapp.model.fpga.FpgaDeviceAddress;
import de.redcrystal.webapp.model.fpga.FpgaRegister;
import de.redcrystal.webapp.model.fpga.VMotorDevices;
import de.redcrystal.webapp.model.fpga.VServoDevices;

/**
 * the controller for the page spitest.xhtml
 * 
 * @author mngo
 * 
 */
@ManagedBean
@ViewScoped
public class FpgaController implements Serializable {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = 3538949941970278722L;

    /** Logger object */
    private static final Logger LOGGER = Logger.getLogger(FpgaController.class);

    /** the tx data */
    private String tx = "";

    /** teh rx data */
    private String rx = "";

    /** the routing table from FPGA */
    private List<FpgaRegister> routingList = new ArrayList<FpgaRegister>();

    /** the data table model in UI */
    private FpgaRegisterTableModel routingDataTableModel;

    /** the gpio table from FPGA */
    private List<FpgaRegister> gpioList = new ArrayList<FpgaRegister>();

    /** the data table model in UI */
    private FpgaRegisterTableModel motorDataTableModel;

    /** the vmotor block table from FPGA */
    private List<FpgaRegister> motorList = new ArrayList<FpgaRegister>();

    /** the data table model in UI */
    private FpgaRegisterTableModel servoDataTableModel;

    /** the vservo block table from FPGA */
    private List<FpgaRegister> servoList = new ArrayList<FpgaRegister>();

    /** the servo devices */
    private List<FpgaDevice> servoDevices = new ArrayList<FpgaDevice>();

    /** the selected device */
    private FpgaDevice selectedDevice;

    /** the data table model of servo devices */
    private FpgaDeviceTableModel servoDevicesTableModel;

    /** the data table model of motor devices */
    private FpgaDeviceTableModel motorDevicesTableModel;

    /** the motor devices */
    private List<FpgaDevice> motorDevices = new ArrayList<FpgaDevice>();
    
    /** the data table model in UI */
    private FpgaRegisterTableModel gpioDataTableModel;

    /**
     * the constructor
     */
    public FpgaController() {
    }

    /**
     * PostConstructor to initialize data
     */
    @PostConstruct
    public void init() {
        // for routing table
        initRoutingTable();
        // for GPIO table
        initGpioTable();
        // for VMotor block
        initVMotorTable();
        // for VServo block
        initVServoTable();
        // for VServo devices
        initVServoDevicesTable();
        // for VMotor devices
        initVMotorDevicesTable();
    }
    
    /**
     * to initialize VServo devices table
     */
    public void initVMotorDevicesTable() {
        motorDevices.clear();
        motorDevices.addAll(new VMotorDevices().getMotorDeviceList());
        // read actual value from fpga
        for (FpgaDevice servoDevice : motorDevices) {
            for (FpgaRegister register : servoDevice.getRegisters()) {
                int value = FpgaConnector.readFpgaRegister(register.getAddress(), servoDevice.getAddress());
                register.setValue(value);
            }
        }
        motorDevicesTableModel = new FpgaDeviceTableModel(motorDevices);

    }

    /**
     * to initialize VServo devices table
     */
    public void initVServoDevicesTable() {
        servoDevices.clear();
        servoDevices.addAll(new VServoDevices().getServoDeviceList());
        // read actual value from fpga
        for (FpgaDevice servoDevice : servoDevices) {
            for (FpgaRegister register : servoDevice.getRegisters()) {
                int value = FpgaConnector.readFpgaRegister(register.getAddress(), servoDevice.getAddress());
                register.setValue(value);
            }
        }
        servoDevicesTableModel = new FpgaDeviceTableModel(servoDevices);

    }

    /**
     * to initialize VServo table
     */
    public void initVServoTable() {
        servoList.clear();
        for (int i = 0; i < 20; i++) {
            int index = i;
            int address = i * 4;
            int value = FpgaConnector.readFpgaRegister(address, FpgaDeviceAddress.VSERVO_BLOCK.getAddress());
            FpgaRegister servo = new FpgaRegister(index, address, value);
            servoList.add(servo);
        }
        servoDataTableModel = new FpgaRegisterTableModel(servoList);
    }

    /**
     * to initialize VMotor table
     */
    public void initVMotorTable() {
        motorList.clear();
        for (int i = 0; i < 20; i++) {
            int index = i;
            int address = i * 4;
            int value = FpgaConnector.readFpgaRegister(address, FpgaDeviceAddress.VMOTOR_BLOCK.getAddress());
            FpgaRegister motor = new FpgaRegister(index, address, value);
            motorList.add(motor);
        }
        motorDataTableModel = new FpgaRegisterTableModel(motorList);
    }

    /**
     * to initialize routing table
     */
    public void initRoutingTable() {
        routingList.clear();
        for (int i = 0; i < 28; i++) {
            int index = i;
            int address = i * 4;
            int value = FpgaConnector.readFpgaRegister(address, FpgaDeviceAddress.ROUTING_TABLE.getAddress());
            FpgaRegister aRouting = new FpgaRegister(index, address, value);
            routingList.add(aRouting);
        }
        routingDataTableModel = new FpgaRegisterTableModel(routingList);
    }

    /**
     * to initialize GPIO table
     */
    public void initGpioTable() {
        gpioList.clear();
        for (int i = 0; i < 8; i++) {
            int index = i;
            int address = i * 4;
            int value = FpgaConnector.readFpgaRegister(address, FpgaDeviceAddress.GPIO_CONTROLLER.getAddress());
            FpgaRegister register = new FpgaRegister(index, address, value);
            gpioList.add(register);
        }
        gpioDataTableModel = new FpgaRegisterTableModel(gpioList);
    }

    /**
     * to refresh servo data table
     * 
     * @return empty string
     */
    public String refreshServoTable() {
        initVServoTable();
        initVServoDevicesTable();
        return "";
    }

    /**
     * to refresh motor data table
     * 
     * @return empty string
     */
    public String refreshMotorTable() {
        initVMotorTable();
        initVMotorDevicesTable();
        return "";
    }

    /**
     * to refresh data table
     * 
     * @return empty string
     */
    public String refreshRoutingTable() {
        initRoutingTable();
        return "";
    }

    /**
     * to refresh data table
     * 
     * @return empty string
     */
    public String refreshGpioTable() {
        initGpioTable();
        return "";
    }

    /**
     * @return the tx
     */
    public String getTx() {
        return tx;
    }

    /**
     * @param tx
     *            the tx to set
     */
    public void setTx(String tx) {
        this.tx = tx;
    }

    /**
     * @return the rx
     */
    public String getRx() {
        return rx;
    }

    /**
     * @param rx
     *            the rx to set
     */
    public void setRx(String rx) {
        this.rx = rx;
    }

    /**
     * send tx to fpga via spi
     * 
     * @return empty string
     */
    public String transferToFpga() {
        LOGGER.info("send to fpga for tx = " + tx);
        if (tx == null || tx.isEmpty()) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Action failed", "Tx is empty");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }
        String[] splits = tx.split(",");
        char[] data = new char[splits.length];
        for (int i = 0; i < splits.length; i++) {
            String temp = splits[i].trim();
            int intValue = Integer.decode(temp);
            data[i] = (char) intValue;
        }
        rx = "";
        SpiJni spiJni = new SpiJni();
        if (data.length >= 2) {
            for (int j = 0; j < data.length / 2; j++) {
                char[] twoBytes = new char[2];
                twoBytes[0] = data[2 * j];
                twoBytes[1] = data[2 * j + 1];
                spiJni.init();
                char[] output = spiJni.transferToFpga(twoBytes, twoBytes.length);
                spiJni.close();
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
                if (j == data.length / 2) {
                    rx += sb.toString();
                } else {
                    rx += sb.toString() + ", ";
                }

            }
        }
        LOGGER.info("Rx (Heximal)= " + rx);
        return "";
    }

    /**
     * @return the routingList
     */
    public List<FpgaRegister> getRoutingList() {
        return routingList;
    }

    /**
     * @param routingList
     *            the routingList to set
     */
    public void setRoutingList(List<FpgaRegister> routingList) {
        this.routingList = routingList;
    }

    /**
     * onCell edit event of routing table
     * 
     * @param event
     */
    public void onCellRoutingEdit(CellEditEvent event) {
        /**
         * in the case of non sorting, non filtering -> rowIndex = ioPin
         * 
         * -> we use row index for iopin
         * 
         * in others read the whole data table and update it
         */
        LOGGER.debug("on cell edit");
        // Object oldValue = event.getOldValue();
        // Object newValue = event.getNewValue();
        int iopin = event.getRowIndex();
        FpgaRegister selected = null;
        for (FpgaRegister aRouting : routingList) {
            if (aRouting.getIndex() == iopin) {
                selected = aRouting;
                break;
            }
        }

        if (selected != null) {
            // write command
            FpgaConnector.writeToRoutingTable(selected.getAddress(), selected.getValue());
            // read command
            char realValue = FpgaConnector.readFpgaRegister(selected.getAddress(), FpgaDeviceAddress.ROUTING_TABLE.getAddress());
            selected.setValue(realValue);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Valued Changed", "New value of I/O pin " + selected.getIndex() + " is "
                    + selected.getValueInHexString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * onCell edit event of register table
     * 
     * @param event
     */
    public void onGpioRegisterEdit(CellEditEvent event) {
        /**
         * in the case of non sorting, non filtering -> rowIndex = ioPin
         * 
         * -> we use row index for iopin
         * 
         * in others read the whole data table and update it
         */
        LOGGER.debug("onCellGpioRegisterEdit");
        // Object oldValue = event.getOldValue();
        // Object newValue = event.getNewValue();
        int index = event.getRowIndex();
        FpgaRegister selected = null;
        for (FpgaRegister register : gpioList) {
            if (register.getIndex() == index) {
                selected = register;
                break;
            }
        }

        if (selected != null) {
            // write command
            FpgaConnector.writeToFpgaRegister8bit(selected.getAddress(), selected.getValue(), FpgaDeviceAddress.GPIO_CONTROLLER.getAddress());
            // read command
            char realValue = FpgaConnector.readFpgaRegister(selected.getAddress(), FpgaDeviceAddress.GPIO_CONTROLLER.getAddress());
            selected.setValue(realValue);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Valued Changed", "New value of index " + selected.getIndex() + " is "
                    + selected.getValueInHexString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * onCell edit event of motor table
     * 
     * @param event
     */
    public void onCellEditOfMotorTable(CellEditEvent event) {
        /**
         * in the case of non sorting, non filtering -> rowIndex = index
         * 
         * -> we use row index for register index
         * 
         * in others read the whole data table and update it
         */
        LOGGER.debug("onCellEditOfMotorTable");
        int index = event.getRowIndex();
        FpgaRegister selected = null;
        for (FpgaRegister register : motorList) {
            if (register.getIndex() == index) {
                selected = register;
                break;
            }
        }

        if (selected != null) {
            // write command
            FpgaConnector.writeToFpgaRegister8bit(selected.getAddress(), selected.getValue(), FpgaDeviceAddress.VMOTOR_BLOCK.getAddress());
            // read command
            char realValue = FpgaConnector.readFpgaRegister(selected.getAddress(), FpgaDeviceAddress.VMOTOR_BLOCK.getAddress());
            selected.setValue(realValue);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Valued Changed", "New value of index " + selected.getIndex() + " is "
                    + selected.getValueInHexString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * on cell edit event
     * 
     * @param event
     *            the cell edit event
     */
    public void onCellEditOfServoDevicesTable(CellEditEvent event) {
        LOGGER.debug("onCellEditOfServoDevicesTable");
        int index = event.getRowIndex();
        // FpgaRegister selected = null;
        System.out.println(index);
    }

    /**
     * onCell edit event of servo table
     * 
     * @param event
     */
    public void onCellEditOfServoTable(CellEditEvent event) {
        /**
         * in the case of non sorting, non filtering -> rowIndex = index
         * 
         * -> we use row index for register index
         * 
         * in others read the whole data table and update it
         */
        LOGGER.debug("onCellEditOfServoTable");
        int index = event.getRowIndex();
        FpgaRegister selected = null;
        for (FpgaRegister register : servoList) {
            if (register.getIndex() == index) {
                selected = register;
                break;
            }
        }

        if (selected != null) {
            // write command
            FpgaConnector.writeToFpgaRegister8bit(selected.getAddress(), selected.getValue(), FpgaDeviceAddress.VSERVO_BLOCK.getAddress());
            // read command
            char realValue = FpgaConnector.readFpgaRegister(selected.getAddress(), FpgaDeviceAddress.VSERVO_BLOCK.getAddress());
            selected.setValue(realValue);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Valued Changed", "New value of index " + selected.getIndex() + " is "
                    + selected.getValueInHexString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * on row edit event
     * 
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        System.out.println("on row select");
        FpgaRegister fpgaRegister = (FpgaRegister) event.getObject();
        System.out.println(fpgaRegister.getIndex());
    }

    /**
     * @return the routingDataTableModel
     */
    public FpgaRegisterTableModel getRoutingDataTableModel() {
        return routingDataTableModel;
    }

    /**
     * @param routingDataTableModel
     *            the routingDataTableModel to set
     */
    public void setRoutingDataTableModel(FpgaRegisterTableModel routingDataTableModel) {
        this.routingDataTableModel = routingDataTableModel;
    }

    /**
     * @return the gpioList
     */
    public List<FpgaRegister> getGpioList() {
        return gpioList;
    }

    /**
     * @param gpioList
     *            the gpioList to set
     */
    public void setGpioList(List<FpgaRegister> gpioList) {
        this.gpioList = gpioList;
    }

    /**
     * @return the gpioDataTableModel
     */
    public FpgaRegisterTableModel getGpioDataTableModel() {
        return gpioDataTableModel;
    }

    /**
     * @param gpioDataTableModel
     *            the gpioDataTableModel to set
     */
    public void setGpioDataTableModel(FpgaRegisterTableModel gpioDataTableModel) {
        this.gpioDataTableModel = gpioDataTableModel;
    }

    /**
     * @return the motorDataTableModel
     */
    public FpgaRegisterTableModel getMotorDataTableModel() {
        return motorDataTableModel;
    }

    /**
     * @param motorDataTableModel
     *            the motorDataTableModel to set
     */
    public void setMotorDataTableModel(FpgaRegisterTableModel motorDataTableModel) {
        this.motorDataTableModel = motorDataTableModel;
    }

    /**
     * @return the motorList
     */
    public List<FpgaRegister> getMotorList() {
        return motorList;
    }

    /**
     * @param motorList
     *            the motorList to set
     */
    public void setMotorList(List<FpgaRegister> motorList) {
        this.motorList = motorList;
    }

    /**
     * @return the servoDataTableModel
     */
    public FpgaRegisterTableModel getServoDataTableModel() {
        return servoDataTableModel;
    }

    /**
     * @param servoDataTableModel
     *            the servoDataTableModel to set
     */
    public void setServoDataTableModel(FpgaRegisterTableModel servoDataTableModel) {
        this.servoDataTableModel = servoDataTableModel;
    }

    /**
     * @return the servoDevicesTableModel
     */
    public FpgaDeviceTableModel getServoDevicesTableModel() {
        return servoDevicesTableModel;
    }

    /**
     * @param servoDevicesTableModel
     *            the servoDevicesTableModel to set
     */
    public void setServoDevicesTableModel(FpgaDeviceTableModel servoDevicesTableModel) {
        this.servoDevicesTableModel = servoDevicesTableModel;
    }

    /**
     * @return the selectedDevice
     */
    public FpgaDevice getSelectedDevice() {
        return selectedDevice;
    }

    /**
     * @param selectedDevice
     *            the selectedDevice to set
     */
    public void setSelectedDevice(FpgaDevice selectedDevice) {
        this.selectedDevice = selectedDevice;
    }

    /**
     * to write data to servo device
     * 
     * @return empty string
     */
    public String updateServoMotorRegisters() {
        LOGGER.debug("updateServoRegisters()");
        if (selectedDevice == null){
            LOGGER.error("None of device is selected");
            return "";
        }
        LOGGER.debug("Selected device: " + selectedDevice.getName());
        for (FpgaRegister register : selectedDevice.getRegisters()){
            FpgaConnector.writeToFpgaRegister8bit(register.getAddress(), register.getValue(), selectedDevice.getAddress());
            // read command
            char realValue = FpgaConnector.readFpgaRegister(register.getAddress(), selectedDevice.getAddress());
            register.setValue(realValue);
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Valued Changed", "The device with name = " + selectedDevice.getName() + " is updated.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "";
    }
    

    /**
     * @return the motorDevicesTableModel
     */
    public FpgaDeviceTableModel getMotorDevicesTableModel() {
        return motorDevicesTableModel;
    }

    /**
     * @param motorDevicesTableModel the motorDevicesTableModel to set
     */
    public void setMotorDevicesTableModel(FpgaDeviceTableModel motorDevicesTableModel) {
        this.motorDevicesTableModel = motorDevicesTableModel;
    }

}
