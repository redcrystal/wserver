/**
 * copyright 2013, redcrystal.de
 */
package de.redcrystal.webapp.model.xml;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import de.redcrystal.webapp.Constants;
import de.redcrystal.webapp.constraint.PropertyType;

/**
 * the component property
 * 
 * @author Tran
 * 
 */
public class ComponentProperty implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1011493457760406907L;

    /** the property name */
    private String name;

    /** the property type */
    private String type;

    /** required = true if the property is required */
    private boolean required;

    /** the default value */
    private String defaultValue;

    /** the property value */
    private Object value;

    /** the default constructor */
    public ComponentProperty() {
    }

    /**
     * the constructor with parameters
     * 
     * @param name
     *            the property name
     * @param type
     *            the property type
     * @param required
     *            is required
     */
    public ComponentProperty(String name, String type, boolean required) {
        super();
        this.name = name;
        this.type = type;
        this.required = required;
    }

    /**
     * @return the property name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    @XmlValue
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    @XmlAttribute
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @param required
     *            the required to set
     */
    @XmlAttribute
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    @XmlTransient
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue
     *            the defaultValue to set
     */
    @XmlAttribute
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 
     * @return formatted default value
     */
    public Object getFormatedDefaultValue() {
        if (type.equals(PropertyType.DATE)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
            if (defaultValue == null) {
                return simpleDateFormat.format(new Date());
            }
            return simpleDateFormat.format(defaultValue);
        } else if (type.equals(PropertyType.DATETIME)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
            if (defaultValue == null) {
                return simpleDateFormat.format(new Date());
            }
            return simpleDateFormat.format(defaultValue);
        } else if (type.equals(PropertyType.INT)) {
            if (defaultValue == null) {
                return new Integer(0);
            }
            return new Integer(defaultValue);
        } else if (type.equals(PropertyType.NUMBER)) {
            if (defaultValue == null) {
                return new Float(0.00f);
            }
            return new Float(defaultValue);
        }
        return defaultValue;
    }
}
