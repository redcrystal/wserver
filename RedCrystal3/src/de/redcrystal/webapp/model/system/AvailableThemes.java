/**
 * copyright 2013, redcrystal.de 
 */
package de.redcrystal.webapp.model.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * list of all available themes
 * 
 * @author mngo
 * 
 */
public class AvailableThemes {
    /** the singleton object */
    private static AvailableThemes instance = null;

    /** the map of all available themes */
    private final HashMap<String, Theme> themesAsMap;

    /** the list of available themes */
    private final List<Theme> themes;

    /**
     * @return the instance
     */
    public static AvailableThemes getInstance() {
	if (instance == null) {
	    instance = new AvailableThemes();
	}
	return instance;
    }

    /**
     * Constructor
     */
    private AvailableThemes() {
	final List<String> themeNames = new ArrayList<String>();

	themeNames.add("afterdark");
	themeNames.add("afternoon");
	themeNames.add("afterwork");
	themeNames.add("aristo");
	themeNames.add("black-tie");
	themeNames.add("blitzer");
	themeNames.add("bluesky");
	themeNames.add("bootstrap");
	themeNames.add("casablanca");
	themeNames.add("cruze");
	themeNames.add("cupertino");
	themeNames.add("dark-hive");
	themeNames.add("dot-luv");
	themeNames.add("delta");
	themeNames.add("eggplant");
	themeNames.add("excite-bike");
	themeNames.add("flick");
	themeNames.add("glass-x");
	themeNames.add("home");
	themeNames.add("hot-sneaks");
	themeNames.add("humanity");
	themeNames.add("le-frog");
	themeNames.add("midnight");
	themeNames.add("mint-choc");
	themeNames.add("overcast");
	themeNames.add("pepper-grinder");
	themeNames.add("redmond");
	themeNames.add("rocket");
	themeNames.add("sam");
	themeNames.add("smoothness");
	themeNames.add("south-street");
	themeNames.add("start");
	themeNames.add("sunny");
	themeNames.add("swanky-purse");
	themeNames.add("trontastic");
	themeNames.add("ui-darkness");
	themeNames.add("ui-lightness");
	themeNames.add("vader");

	themesAsMap = new HashMap<String, Theme>();
	themes = new ArrayList<Theme>();

	for (final String themeName : themeNames) {
	    final Theme theme = new Theme();
	    theme.setName(themeName);
	    theme.setImage("/resources/images/themeswitcher/" + themeName
		    + ".png");
	    themes.add(theme);
	    themesAsMap.put(theme.getName(), theme);
	}
    }

    /**
     * 
     * @param name
     *            the theme's name
     * @return them object by name
     */
    public Theme getThemeForName(String name) {
	return themesAsMap.get(name);
    }

    /**
     * @return the themes
     */
    public List<Theme> getThemes() {
	return themes;
    }
}
