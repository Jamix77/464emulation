package org.hyperion.plugin;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

import org.hyperion.Server;
import org.hyperion.plugin.impl.OptionHandler;
import org.hyperion.rs2.model.dialogue.Dialogue;
import org.reflections.Reflections;

/**
 * Represents a class used to handle the loading of all plugins.
 * @author Emperor
 */
public final class PluginManager {
	
	/**
	 * Logger instance.
	 */
	private static final Logger logger = Logger.getLogger(Server.class.getName());

	/**
	 * The amount of plugins loaded.
	 */
	private static int pluginCount;

	/**
	 * The dialogue plugins.
	 */
	private static final HashMap<Integer,Dialogue> DIALOGUE_PLUGINS = new HashMap<Integer,Dialogue>();
	
	/**
	 * The option handler plugins.
	 */
	private static final HashMap<String,OptionHandler> OPTION_HANDLER_PLUGINS = new HashMap<String,OptionHandler>();
	
	/**
	 * The last loaded plugin.
	 */
	private static String lastLoaded;

	/**
	 * Initializes the plugin manager.
	 */
	public static void init() {
		try {
			//loadLocal(new File("plugin/"));
			load("plugin");
			logger.info("Initialized " + pluginCount + " plugins...");
		} catch (Throwable t) {
			logger.info("Error initializing Plugins -> " + t.getLocalizedMessage() + " for file -> " + lastLoaded);
			t.printStackTrace();
		}
	}

	public static void load(String root) throws Throwable {
		if (root == null || root.isEmpty()) {
			root = "plugin";
		}
		Reflections reflections = new Reflections(root);
		Set<Class<?>> reflectionsSet = reflections.getTypesAnnotatedWith(InitializablePlugin.class);
		Object[] reflectionsArray = reflectionsSet.toArray();
		int reflectionsLength = reflectionsArray.length;
		for (int i = 0; i < reflectionsLength; i++) {
			Class c = (Class) reflectionsArray[i];
			try {
				if (!c.isMemberClass() && !c.isAnonymousClass()) {
					final Plugin plugin = (Plugin) c.newInstance();
					definePlugin(plugin);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

/*	*//**
	 * Loads the plugins in the local directory.
	 * @param directory The directory.
	 * @throws Throwable When an exception occurs.
	 *//*
	@SuppressWarnings("rawtypes")
	public static void loadLocal(File directory) throws Throwable {
		final URL[] url = new URL[] { directory.toURI().toURL(), null };
		URLClassLoader loader;
		for (File file : directory.listFiles()) {
			if (file.getName().equals(".DS_Store")) {
				continue;
			}
			if (GameWorld.isEconomyWorld() && file.getPath().startsWith("plugin/pvp")) {
				continue;
			}
			if (file.isDirectory()) {
				loadLocal(file);
				continue;
			}
			String fileName = file.getName().replace(".jar", "").replace(".class", "");
			lastLoaded = fileName;
			if (loadedPlugins.contains(fileName)) {
				System.err.println("Duplicate plugin - " + fileName);
				break;
			}
			loadedPlugins.add(fileName);
			url[1] = file.toURI().toURL();
			loader = new URLClassLoader(url);
			JarFile jar = new JarFile(file);
			Enumeration<JarEntry> entries = jar.entries();
			boolean loaded = false;
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.getName().endsWith(fileName + ".class")) {
					StringBuilder sb = new StringBuilder();
					for (String path : entry.getName().split("/")) {
						if (sb.length() != 0) {
							sb.append(".");
						}
						sb.append(path);
						if (path.endsWith(".class")) {
							sb.setLength(sb.length() - 6);
							break;
						}
					}
					Files.write(new File("fuck.txt").toPath(), (sb.toString()+"\n").getBytes(), StandardOpenOption.APPEND);
					System.out.println(sb);
					try {
						final Plugin plugin = (Plugin) loader.loadClass(sb.toString()).newInstance();
						definePlugin(plugin);
						loaded = true;
					} catch (Throwable t) {
						System.err.println("Error for class at " + entry.getName());
						t.printStackTrace();
					}
				}
			}
			if (!loaded) {
				System.err.println("Failed to load plugin " + fileName + "!");
			}
			 loader.close();
			jar.close();
		}
	}*/

	/**
	 * Defines a list of plugins.
	 * @param plugins the plugins.
	 */
	public static void definePlugin(Plugin<?>... plugins) {
		int pluginsLength = plugins.length;
		for (int i = 0; i < pluginsLength; i++) {
			Plugin<?> p = plugins[i];
			definePlugin(p);
		}
	}

	/**
	 * Defines the plugin.
	 * @param plugin The plugin.
	 */
	@SuppressWarnings("unchecked")
	public static void definePlugin(Plugin<?> plugin) {
		try {
			PluginManifest manifest = plugin.getClass().getAnnotation(PluginManifest.class);
			if (manifest == null) {
				manifest = plugin.getClass().getSuperclass().getAnnotation(PluginManifest.class);
			}
			
				plugin.init();
			
			pluginCount++;
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the amount of plugins currently loaded.
	 * @return The plugin count.
	 */
	public static int getAmountLoaded() {
		return pluginCount;
	}

	

	/**
	 * Gets the pluginCount.
	 * @return the pluginCount.
	 */
	public static int getPluginCount() {
		return pluginCount;
	}

	/**
	 * Sets the pluginCount.
	 * @param pluginCount the pluginCount to set
	 */
	public static void setPluginCount(int pluginCount) {
		PluginManager.pluginCount = pluginCount;
	}

	public static HashMap<Integer,Dialogue> getDialoguePlugins() {
		return DIALOGUE_PLUGINS;
	}

	public static HashMap<String,OptionHandler> getOptionHandlerPlugins() {
		return OPTION_HANDLER_PLUGINS;
	}

	
}