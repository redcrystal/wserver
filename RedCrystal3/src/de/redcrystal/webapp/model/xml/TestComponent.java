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

import org.junit.Assert;
import org.junit.Test;

/**
 * to test class component
 * 
 * @author Tran
 * 
 */
public class TestComponent {
    /**
     * to convert java object to xml file
     */
    @Test
    public void convertToXML() {
        Component component = new Component("Motor", "motor.jpg", "That is the description string");
        component.setId("2012");
        
        List<ComponentProperty> properties = new ArrayList<ComponentProperty>();
        properties.add(new ComponentProperty("On/Off", "boolean", true));
        properties.add(new ComponentProperty("Velocity", "int", true));
        properties.add(new ComponentProperty("Direction", "boolean", true));
        component.setProperties(properties);
        try {

            File file = new File("WebContent/xml/components.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Component.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(component, file);
            jaxbMarshaller.marshal(component, System.out);
            Assert.assertTrue(true);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        
    }
}
