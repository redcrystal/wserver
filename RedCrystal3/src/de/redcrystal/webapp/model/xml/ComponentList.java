/**
 * copyright 2013, redcrytal.de
 */
package de.redcrystal.webapp.model.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * the component list
 * 
 * @author Tran
 * 
 */
@XmlRootElement(name = "root")
public class ComponentList {

    /** the component list */
    private List<Component> components;

    /**
     * @return the components
     */
    public List<Component> getComponents() {
        return components;
    }

    /**
     * @param components
     *            the components to set
     */
    @XmlElementWrapper(name = "components")
    @XmlElement(name = "component")
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    /**
     * to add a component into list
     * 
     * @param component
     *            the object to add
     */
    public void addComponent(Component component) {
        if (components == null){
            components = new ArrayList<Component>();
        }
        components.add(component);
    }
}
