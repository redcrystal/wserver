/**
 * copyright 2013, redcrytal.de
 */
package de.redcrystal.webapp.model.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


/**
 * to test the class {@link ComponentList}
 * 
 * @author mngo
 * 
 */
public class TestComponentList {
    /**
     * to convert java object to xml file
     */
    @Ignore
    @Test
    public void convertToXML() {
        ComponentList list = new ComponentList();

        /** the component 1 */
        Component component1 = new Component("Motor", "motor.jpg", "That is the description string");
        component1.setId("1");

        List<ComponentProperty> properties = new ArrayList<ComponentProperty>();
        properties.add(new ComponentProperty("On/Off", "boolean", true));
        properties.add(new ComponentProperty("Velocity", "int", true));
        properties.add(new ComponentProperty("Direction", "boolean", true));
        properties.add(new ComponentProperty("Address", "int", true));
        component1.setProperties(properties);
        list.addComponent(component1);

        /** the component 2 */
        Component component2 = new Component("Sensor", "sensor.jpg", "That is an light sensor");
        properties = new ArrayList<ComponentProperty>();
        properties.add(new ComponentProperty("On/Off", "boolean", true));
        properties.add(new ComponentProperty("Address", "int", true));
        component2.setProperties(properties);
        component2.setId("2");
        list.addComponent(component2);

        try {

            File file = new File("WebContent/xml/components.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ComponentList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(list, file);
            jaxbMarshaller.marshal(list, System.out);
            Assert.assertTrue(true);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * to read xml file
     */
    @Test
    public void readFromXml() {
        try {
            File file = new File("WebContent/xml/components.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ComponentList.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ComponentList list = (ComponentList) jaxbUnmarshaller.unmarshal(file);
            Assert.assertNotNull(list);
            Assert.assertNotNull(list.getComponents());

            for (Component aComponent : list.getComponents()) {
                System.out.println("------------ id = " + aComponent.getId() + " --------------");
                System.out.println("Name = " + aComponent.getName());
                System.out.println("Description = " + aComponent.getName());
                System.out.println("properties");
                for (ComponentProperty property : aComponent.getProperties()) {
                    System.out.println("["+property.getName() + ", " + property.getType() + ", " + property.isRequired()+"]");
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
