/**
 * copyright 2013, redcrytal.de
 */
package de.redcrystal.webapp.model.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * the component class
 * 
 * @author Tran
 * 
 */
@XmlRootElement(name = "component")
public class Component implements Serializable {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = -6094231777514159105L;

    /** the component id */
    private String id;

    /** the component name */
    private String name;

    /** the component image path */
    private String image;

    /** the component description */
    private String description;

    /** the component address in hex string */
    private String address;

    /** the component properties */
    private List<ComponentProperty> properties;

    /** the internal index in */
    private int index;

    /**
     * the default constructor
     */
    public Component() {
    }

    /**
     * 
     * @param name
     * 
     *            the name to set
     * @param image
     *            the image to set
     * @param description
     *            the description to set
     */
    public Component(String name, String image, String description) {
        super();
        this.name = name;
        this.image = image;
        this.description = description;
    }

    /**
     * @param name
     * 
     *            the name to set
     * @param image
     *            the image to set
     * @param description
     *            the description to set
     * @param address
     *            the address to set
     */
    public Component(String name, String image, String description, String address) {
        super();
        this.name = name;
        this.image = image;
        this.description = description;
        this.address = address;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image
     *            the image to set
     */
    @XmlElement
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the properties
     */
    public List<ComponentProperty> getProperties() {
        return properties;
    }

    /**
     * @param properties
     *            the properties to set
     */
    @XmlElementWrapper(name = "properties")
    @XmlElement(name = "property")
    public void setProperties(List<ComponentProperty> properties) {
        this.properties = properties;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index
     *            the index to set
     */
    @XmlTransient
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    @XmlElement
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * to clone a new object
     * 
     * @param component
     *      the base object
     * @return
     *      the new object
     */
    public static Component clone(Component component) {
        Component aComponent = new Component();
        aComponent.setName(component.getName());
        aComponent.setDescription(component.getDescription());
        aComponent.setId(component.getId());
        aComponent.setImage(component.getImage());
        aComponent.setProperties(component.getProperties());
        aComponent.setIndex(component.getIndex());
        aComponent.setAddress(component.getAddress());
        return aComponent;
    }
}