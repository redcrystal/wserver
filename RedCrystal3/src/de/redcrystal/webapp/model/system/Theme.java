/**
 * copyright 2013, redcrystal.de 
 */
package de.redcrystal.webapp.model.system;

import java.io.Serializable;

/**
 * 
 * @author Tran
 * 
 */
public class Theme implements Serializable {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = 2718629877830573795L;

    /** the name */
    private String name;

    /** the image path */
    private String image;

    /**
     * Constructor
     */
    public Theme() {
    }

    /**
     * constructor
     * 
     * @param name
     *            the name to set
     * @param image
     *            the image to set
     */
    public Theme(final String name, final String image) {
	this.name = name;
	this.image = image;
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
     * @return the image
     */
    public String getImage() {
	return image;
    }

    /**
     * @param image
     *            the image to set
     */
    public void setImage(String image) {
	this.image = image;
    }

}
