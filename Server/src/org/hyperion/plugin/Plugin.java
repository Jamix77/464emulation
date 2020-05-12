package org.hyperion.plugin;

/**
 * Represents a plugin.
 * @author Emperor
 * @param <T> The argument type.
 */
public interface Plugin<T> {

	/**
	 * Creates a new instance.
	 * @param arg The argument.
	 * @return The plugin instance created.
	 */
	public Plugin<T> newInstance(T arg);
	
	/**
	 * Initilises the plugin on startup.
	 * @throws Throwable
	 */
	public void init() throws Throwable;

}