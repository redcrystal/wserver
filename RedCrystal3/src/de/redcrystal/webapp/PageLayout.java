/**
 * copyright 2013, redcrystal.de 
 */
package de.redcrystal.webapp;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.extensions.model.layout.LayoutOptions;

/**
 * 
 * to define page layout properties
 * 
 * @author Tran
 * 
 */
@ApplicationScoped
@ManagedBean(eager = true)
public class PageLayout {
    /** the page layout option in JSON string */
    private String options;

    /**
     * @return the options
     */
    public String getOptions() {
	return options;
    }

    /**
     * @param options
     *            the options to set
     */
    public void setOptions(String options) {
	this.options = options;
    }

    /**
     * to initialize data
     */
    @PostConstruct
    protected void initialize() {
	LayoutOptions layoutOptions = new LayoutOptions();

	// for all panes
	LayoutOptions panes = new LayoutOptions();
	panes.addOption("resizable", true);
	panes.addOption("closable", true);
	panes.addOption("slidable", false);
	panes.addOption("resizeWithWindow", false);
	panes.addOption("resizeWhileDragging", true);
	layoutOptions.setPanesOptions(panes);

	// north pane : header
	LayoutOptions north = new LayoutOptions();
	north.addOption("resizable", false);
	north.addOption("closable", false);
	north.addOption("size", 60);
	north.addOption("spacing_open", 0);
	layoutOptions.setNorthOptions(north);

	// south pane : footer
	LayoutOptions south = new LayoutOptions();
	south.addOption("resizable", false);
	south.addOption("closable", false);
	south.addOption("size", 28);
	south.addOption("spacing_open", 0);
	layoutOptions.setSouthOptions(south);

	// center pane
	LayoutOptions center = new LayoutOptions();
	center.addOption("resizable", false);
	center.addOption("closable", false);
	center.addOption("resizeWhileDragging", false);
	center.addOption("minWidth", 200);
	center.addOption("minHeight", 60);
	layoutOptions.setCenterOptions(center);

	// west pane
	LayoutOptions west = new LayoutOptions();
	west.addOption("size", 210);
	west.addOption("minSize", 180);
	west.addOption("maxSize", 500);
	layoutOptions.setWestOptions(west);

	// east pane
	LayoutOptions east = new LayoutOptions();
	east.addOption("size", 448);
	east.addOption("minSize", 180);
	east.addOption("maxSize", 650);
	layoutOptions.setEastOptions(east);

	// nested pane
	LayoutOptions childEastOptions = new LayoutOptions();
	east.setChildOptions(childEastOptions);

	// east center
	LayoutOptions eastCenter = new LayoutOptions();
	eastCenter.addOption("minHeight", 60);
	childEastOptions.setCenterOptions(eastCenter);

	// south-center pane
	LayoutOptions southCenter = new LayoutOptions();
	southCenter.addOption("size", "70%");
	southCenter.addOption("minSize", 60);
	childEastOptions.setSouthOptions(southCenter);

	// serialize option to JSON string
	options = layoutOptions.toJson();
    }
}
