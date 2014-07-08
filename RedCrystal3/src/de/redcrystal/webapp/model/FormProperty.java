/**
 * copyright 2013, redcrystal.de
 */
package de.redcrystal.webapp.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import de.redcrystal.webapp.Constants;
import de.redcrystal.webapp.constraint.PropertyType;
import de.redcrystal.webapp.model.xml.ComponentProperty;

/**
 * the form property for dyna form
 * 
 * @author mngo
 * 
 */
public class FormProperty implements Serializable {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = -1716293886798450034L;

    /** the property name */
    private String name;

    /** the property value */
    private Object value;

    /** if the property is required */
    private boolean required;

    /** the list of select items */
    private List<SelectItem> selectItems;

    /** the property type */
    private String type;

    /**
     * the default constructor
     */
    public FormProperty() {

    }

    /**
     * 
     * @param name
     *            the name to set
     * @param required
     *            the required to set
     * @param type
     *            the type to set
     */
    public FormProperty(String name, boolean required, String type) {
        super();
        this.name = name;
        this.required = required;
        this.type = type;
    }

    /**
     * 
     * @param name
     *            the name to set
     * @param required
     *            the required to set
     * @param type
     *            the type to set
     * @param value
     *            the value to set
     */
    public FormProperty(String name, boolean required, String type, Object value) {
        super();
        this.name = name;
        this.required = required;
        this.type = type;
        this.value = value;
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
    public void setName(String name) {
        this.name = name;
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
    public void setValue(Object value) {
        this.value = value;
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
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return the selectItems
     */
    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    /**
     * @param selectItems
     *            the selectItems to set
     */
    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
    }

    /**
     * 
     * @return formatted value
     */
    public Object getFormattedValue() {
        if (value instanceof Date && type.equals(PropertyType.DATE)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
            return simpleDateFormat.format(value);
        } else if (value instanceof Date && type.equals(PropertyType.DATETIME)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
            return simpleDateFormat.format(value);
        }
        return value;
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
    public void setType(String type) {
        this.type = type;
    }
}
