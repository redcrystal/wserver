/**
 * copyright 2013, redcrystal.de 
 */
package de.redcrystal.webapp.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import de.redcrystal.webapp.model.system.AvailableThemes;
import de.redcrystal.webapp.model.system.Theme;

/**
 * the them converter
 * 
 * @author Tran
 * 
 */
@FacesConverter("themeConverter")
public class ThemeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return AvailableThemes.getInstance().getThemeForName(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Theme) value).getName();
    }

}
