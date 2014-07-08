/**
 * copyright 2013, redcrystal.de 
 */
package de.redcrystal.webapp.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import de.redcrystal.jni.FpgaConnector;
import de.redcrystal.webapp.Constants;
import de.redcrystal.webapp.model.FormProperty;
import de.redcrystal.webapp.model.fpga.FpgaDeviceAddress;
import de.redcrystal.webapp.model.xml.Component;
import de.redcrystal.webapp.model.xml.ComponentList;
import de.redcrystal.webapp.model.xml.ComponentProperty;
import de.redcrystal.webapp.util.Utilities;

/**
 * the controller class for page circuit design
 * 
 * @author mngo
 * 
 */
@ManagedBean
@ViewScoped
public class HardwareDesignController implements Serializable {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = -3953065547758051242L;

    /** Logger object */
    private static final Logger LOGGER = Logger.getLogger(HardwareDesignController.class);

    /** list of available component from xml file */
    private List<Component> availableComponents;

    /** the selected component id */
    private String selectedComponentId;

    /** the selected component list */
    private List<Component> selectedComponents;

    /** the selected component */
    private Component selectedComponent;

    /** the dynamic form model */
    private DynaFormModel dynaForm;

    /**
     * 
     */
    public HardwareDesignController() {

    }

    /**
     * to initialize data
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("init() called");
        LOGGER.debug("read components from xml file");
        availableComponents = new ArrayList<Component>();
        selectedComponents = new ArrayList<Component>();
        try {
            String fileName = Utilities.getRealWebContentPath() + Constants.COMPONENTS_CONFIG_FILE;
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(ComponentList.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ComponentList list = (ComponentList) jaxbUnmarshaller.unmarshal(file);

            for (Component aComponent : list.getComponents()) {
                availableComponents.add(aComponent);
            }
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
        }
        // init fpga
        initFpga();
    }

    /**
     * initialize fpga: enable output and ServoBlock
     */
    private void initFpga() {
        LOGGER.debug("init fpga to enable output and Servo block.");
        // Enable output: write to GPIO controller at addresses 0x04 and 0x00 with value = 0x01
        FpgaConnector.writeToFpgaRegister8bit(0x04, 0x01, FpgaDeviceAddress.GPIO_CONTROLLER.getAddress());
        FpgaConnector.writeToFpgaRegister8bit(0x00, 0x01, FpgaDeviceAddress.GPIO_CONTROLLER.getAddress());
        // Enable Servo block: write to servo block at the address 0x24 with value = 0x01, 0x4c with value 0x61 and 0x48 with value 0xa8
        FpgaConnector.writeToFpgaRegister8bit(0x24, 0x01, FpgaDeviceAddress.VSERVO_BLOCK.getAddress());
    }

    /**
     * to add a component
     */
    public void addComponent() {
        LOGGER.debug("add components");
        int listSize = selectedComponents.size();
        Component aComponent = null;
        for (Component component : availableComponents) {
            if (component.getId().equals(selectedComponentId)) {
                aComponent = Component.clone(component);
                aComponent.setIndex(listSize);
                break;
            }
        }
        selectedComponents.add(aComponent);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Added a component", aComponent.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * event when users select a component
     * 
     * @return null string
     */
    public String onComponentSelect() {
        LOGGER.debug("onComponentSelect() called.");
        // init dyna form
        dynaForm = new DynaFormModel();
        if (selectedComponent.getProperties() != null) {
            for (ComponentProperty aProperty : selectedComponent.getProperties()) {
                DynaFormRow row = dynaForm.createRegularRow();
                DynaFormLabel label = row.addLabel(aProperty.getName());
                DynaFormControl control = null;
                if (aProperty.getValue() == null) {
                    control = row.addControl(
                            new FormProperty(aProperty.getName(), aProperty.isRequired(), aProperty.getType(), aProperty.getFormatedDefaultValue()),
                            aProperty.getType());
                } else {
                    control = row.addControl(
                            new FormProperty(aProperty.getName(), aProperty.isRequired(), aProperty.getType(), aProperty.getValue()),
                            aProperty.getType());
                }
                label.setForControl(control);
            }
        }
        return null;
    }

    /**
     * @return the availableComponents
     */
    public List<Component> getAvailableComponents() {
        return availableComponents;
    }

    /**
     * @param availableComponents
     *            the avialableComponents to set
     */
    public void setAvailableComponents(List<Component> availableComponents) {
        this.availableComponents = availableComponents;
    }

    /**
     * @return the selectedComponentId
     */
    public String getSelectedComponentId() {
        return selectedComponentId;
    }

    /**
     * @param selectedComponentId
     *            the selectedComponentId to set
     */
    public void setSelectedComponentId(String selectedComponentId) {
        this.selectedComponentId = selectedComponentId;
    }

    /**
     * @return the selectedComponents
     */
    public List<Component> getSelectedComponents() {
        return selectedComponents;
    }

    /**
     * @param selectedComponents
     *            the selectedComponents to set
     */
    public void setSelectedComponents(List<Component> selectedComponents) {
        this.selectedComponents = selectedComponents;
    }

    /**
     * @return the selectedComponent
     */
    public Component getSelectedComponent() {
        return selectedComponent;
    }

    /**
     * @param selectedComponent
     *            the selectedComponent to set
     */
    public void setSelectedComponent(Component selectedComponent) {
        this.selectedComponent = selectedComponent;
    }

    /**
     * @return the dynaForm
     */
    public DynaFormModel getDynaForm() {
        return dynaForm;
    }

    /**
     * @param dynaForm
     *            the dynaForm to set
     */
    public void setDynaForm(DynaFormModel dynaForm) {
        this.dynaForm = dynaForm;
    }

    /**
     * 
     * @return the null string
     */
    public String submitDynaForm() {
        LOGGER.info("submitDynaForm() called. ");
        if (selectedComponent == null) {
            LOGGER.warn("None of device is selected.");
            return null;
        }
        LOGGER.debug("The selected device: " + selectedComponent.getName());
        List<FormProperty> properties = getFormProperties();
        List<ComponentProperty> componentProperties = new ArrayList<ComponentProperty>();
        for (FormProperty aProp : properties) {
            ComponentProperty cProperty = new ComponentProperty(aProp.getName(), aProp.getType(), aProp.isRequired());
            cProperty.setValue(aProp.getFormattedValue());
            componentProperties.add(cProperty);
        }
        selectedComponent.setProperties(componentProperties);

        /** write to fpga */
        // for VSERVO
        if (selectedComponent.getName().startsWith("Servo")) {
            int io = 0;
            float period = 0f;
            float dutyCycle = 0f;
            int pulseNumber = 0;
            for (ComponentProperty property : componentProperties) {
                try {
                    String componentName = property.getName().trim();
                    if (componentName.equalsIgnoreCase("IO")) {
                        io = new Integer((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Period")) {
                        period = new Float((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Duty Cycle")) {
                        dutyCycle = new Float((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Pulse Number")) {
                        pulseNumber = new Integer((String) property.getValue());
                    }
                } catch (Exception e) {
                    LOGGER.error("error while coventering value: " + e.getMessage(), e);
                }
            }
            int deviceAddress = Integer.decode(selectedComponent.getAddress());
            int deviceId = Integer.decode(selectedComponent.getId());
            controlServo(io, deviceAddress, deviceId, period, dutyCycle, pulseNumber);
        }	else

        // for Motor
        if (selectedComponent.getName().startsWith("Motor")) {
            int io = 0;
            float period = 0f;
            float dutyCycle = 0f;
            for (ComponentProperty property : componentProperties) {
                try {
                    String componentName = property.getName().trim();
                    if (componentName.equalsIgnoreCase("IO")) {
                        io = new Integer((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Period")) {
                        period = new Float((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Duty Cycle")) {
                        dutyCycle = new Float((String) property.getValue());
                    }
                } catch (Exception e) {
                    LOGGER.error("error while coventering value: " + e.getMessage(), e);
                }
            }
            int deviceAddress = Integer.decode(selectedComponent.getAddress());
            int deviceId = Integer.decode(selectedComponent.getId());
            controlMotor(io,deviceAddress,deviceId,period,dutyCycle);

        }	else 

        if (selectedComponent.getName().startsWith("RobotArm")) {
            float dutyCycle0 = 0f;
            float dutyCycle1 = 0f;
            float dutyCycle2 = 0f;
            float dutyCycle3 = 0f;
            float dutyCycle4 = 0f;
            float dutyCycle5 = 0f;

            for (ComponentProperty property : componentProperties) {
                try {
                    String componentName = property.getName().trim();
                    if (componentName.equalsIgnoreCase("Servo 0")) {
                        dutyCycle0 = new Float((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Servo 1")) {
                        dutyCycle1 = new Float((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Servo 2")) {
                        dutyCycle2 = new Float((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Servo 3")) {
                        dutyCycle3 = new Float((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Servo 4")) {
                        dutyCycle4 = new Float((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Servo 5")) {
                        dutyCycle5 = new Float((String) property.getValue());
                    }
                } catch (Exception e) {
                    LOGGER.error("error while coventering value: " + e.getMessage(), e);
                }
            }
            controlRobotArm(dutyCycle0, dutyCycle1, dutyCycle2, dutyCycle3, dutyCycle4, dutyCycle5);
        }   else
        if (selectedComponent.getName().startsWith("Quadcopter")) {
            float dutyCycle0 = 0f;
            float dutyCycle1 = 0f;
            float dutyCycle2 = 0f;
            float dutyCycle3 = 0f;

            for (ComponentProperty property : componentProperties) {
                try {
                    String componentName = property.getName().trim();
                    if (componentName.equalsIgnoreCase("Servo 0")) {
                        dutyCycle0 = new Float((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Servo 1")) {
                        dutyCycle1 = new Float((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Servo 2")) {
                        dutyCycle2 = new Float((String) property.getValue());
                    } else if (componentName.equalsIgnoreCase("Servo 3")) {
                        dutyCycle3 = new Float((String) property.getValue());
                    }
                } catch (Exception e) {
                    LOGGER.error("error while coventering value: " + e.getMessage(), e);
                }
            }
            controlQuadcopter(dutyCycle0, dutyCycle1, dutyCycle2, dutyCycle3);
        }
        return null;
    }

    /**
     * to control robot arm
     * 
     * @param dutyCycle0
     * @param dutyCycle1
     * @param dutyCycle2
     * @param dutyCycle3
     * @param dutyCycle4
     * @param dutyCycle5
     */
    private void controlRobotArm(float dutyCycle0, float dutyCycle1, float dutyCycle2, float dutyCycle3, float dutyCycle4, float dutyCycle5) {
        float period = 20f;
        int pulseNumber = 80;
        // servo 0
        controlServo(9, 0xC0, 0x08, period, dutyCycle0, pulseNumber);
        // servo 1
        controlServo(11, 0xC1, 0x09, period, dutyCycle1, pulseNumber);
        // servo 2
        controlServo(15, 0xC2, 0x0A, period, dutyCycle2, pulseNumber);
        // servo 3
        controlServo(17, 0xC3, 0x0B, period, dutyCycle3, pulseNumber);
        // servo 4
        controlServo(19, 0xC4, 0x0C, period, dutyCycle4, pulseNumber);
        // servo 5
        controlServo(21, 0xC5, 0x0D, period, dutyCycle5, pulseNumber);
    }

    /**
     *
     * @param dutyCycle0
     * @param dutyCycle1
     * @param dutyCycle2
     * @param dutyCycle3
     */
    private void controlQuadcopter(float dutyCycle0, float dutyCycle1, float dutyCycle2, float dutyCycle3) {
        float period = 20f;
        // servo 0
        controlMotor(9, 0x40, 0x00, period, dutyCycle0);
        // servo 1
        controlMotor(11, 0x41, 0x01, period, dutyCycle1);
        // servo 2
        controlMotor(15, 0x42, 0x02, period, dutyCycle2);
        // servo 3
        controlMotor(17, 0x43, 0x03, period, dutyCycle3);
    }



    public String fastControlServo(String ServoID,String dutyCycle){


        int io=0;
        int deviceAddress = 0;
        switch (Integer.parseInt(ServoID)) {
            case 0x08:  io = 9; deviceAddress=0xc0;
                        break;
            case 0x09:  io = 11; deviceAddress=0xc1;
                        break;
            case 0x0a:  io = 15; deviceAddress=0xc2;
                        break;
            case 0x0b:  io = 17; deviceAddress=0xc3;
                        break;
            case 0x0c:  io = 19; deviceAddress=0xc4;
                        break;
            case 0x0d:  io = 21; deviceAddress=0xc5;
                        break;
        }

        controlServo(io,deviceAddress,Integer.parseInt(ServoID),(float) 20,Float.parseFloat(dutyCycle),10);
        return "";
    }

    /**
     * to control a servo
     * 
     * @param io
     * @param deviceAddress
     * @param deviceId
     * @param period
     * @param dutyCycle
     */
    private void controlServo(int io, int deviceAddress, int deviceId, float period, float dutyCycle, int pulseNumber) {
        // write to routing table
        FpgaConnector.writeToRoutingTable(io * 4, deviceId);
        // write to register 0 at 0x00 with value 0x01
        FpgaConnector.writeToFpgaRegister8bit(0x00, 0x01, deviceAddress);

        // System.out.println("write period=" + period);
        String divisorHigh = Utilities.convertToHexString(FpgaConnector.readFpgaRegister(0x4C, FpgaDeviceAddress.VSERVO_BLOCK.getAddress()));
        String divisorLow = Utilities.convertToHexString(FpgaConnector.readFpgaRegister(0x48, FpgaDeviceAddress.VSERVO_BLOCK.getAddress()));
        int divisor = Integer.decode("0x" + divisorHigh + divisorLow);
        int servoClock = 50000000 / divisor; // 50 Mhz = 50 000 000 hz
        int fpgaPeriod = (int) ((servoClock * period) / 1000);

        FpgaConnector.writeToFpgaRegister16bit(0x04, fpgaPeriod, deviceAddress);

        // System.out.println("write duty cycle=" + dutyCycle);
        int fpgaDutyCycle = (int) ((dutyCycle * servoClock) / 1000);
        FpgaConnector.writeToFpgaRegister16bit(0x0C, fpgaDutyCycle, deviceAddress);

        // System.out.println("write pulse number=" + pulseNumber);
        FpgaConnector.writeToFpgaRegister16bit(0x14, pulseNumber, deviceAddress);
    }

    /**
     * control motor
     * @param io
     * @param deviceAddress
     * @param deviceId
     * @param period
     * @param dutyCycle
     */
    private void controlMotor(int io, int deviceAddress, int deviceId, float period, float dutyCycle) {
        // write to routing table
        FpgaConnector.writeToRoutingTable(io * 4, deviceId);
        // write to register 0 at 0x00 with value 0x01
        FpgaConnector.writeToFpgaRegister8bit(0x00, 0x01, deviceAddress);

        // System.out.println("write period=" + period);
        String divisorHigh = Utilities.convertToHexString(FpgaConnector.readFpgaRegister(0x4C, FpgaDeviceAddress.VMOTOR_BLOCK.getAddress()));
        String divisorLow = Utilities.convertToHexString(FpgaConnector.readFpgaRegister(0x48, FpgaDeviceAddress.VMOTOR_BLOCK.getAddress()));
        int divisor = Integer.decode("0x" + divisorHigh + divisorLow);
        int motorClock = 50000000 / divisor; // 50 Mhz = 50 000 000 hz

        int fpgaPeriod = (int) ((motorClock * period) / 1000);

        FpgaConnector.writeToFpgaRegister16bit(0x04,fpgaPeriod, deviceAddress);

        // System.out.println("write duty cycle=" + dutyCycle);
        int fpgaDutyCycle = (int) ((dutyCycle * motorClock) / 1000);
        FpgaConnector.writeToFpgaRegister16bit(deviceAddress, fpgaDutyCycle, 0x0C);

    }


    /**
     * @return list of dynamic form properties
     */
    public List<FormProperty> getFormProperties() {
        if (dynaForm == null) {
            return null;
        }
        List<FormProperty> formProperties = new ArrayList<FormProperty>();
        for (DynaFormControl control : dynaForm.getControls()) {
            formProperties.add((FormProperty) control.getData());
        }
        return formProperties;
    }

}
