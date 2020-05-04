package org.hyperion.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents a plugin manifest.
 * @author Emperor
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginManifest {

	/**
	 * Gets the plugin type.
	 * @return The plugin type.
	 */
	public PluginType type();
	
	public String[] authors();
	
	public double version();

	
	
}