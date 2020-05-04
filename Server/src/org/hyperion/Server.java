package org.hyperion;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hyperion.cache.fileserver.FileManager;
import org.hyperion.rs2.RS2Server;
import org.hyperion.rs2.model.World;


/**
 * A class to start both the file and game servers.
 * @author Graham Edgecombe
 *
 */
public class Server {
	
	/**
	 * The protocol version.
	 */
	public static final int VERSION = 464;
	
	/**
	 * Logger instance.
	 */
	private static final Logger logger = Logger.getLogger(Server.class.getName());
	
	/**
	 * The entry point of the application.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		logger.info("Starting Hyperion...");
		World.getWorld(); // this starts off background loading
		try {
			new RS2Server().bind(RS2Server.PORT).start();
			FileManager.load("./data/cache/");
		} catch(Exception ex) {
			logger.log(Level.SEVERE, "Error starting Hyperion.", ex);
		///	System.exit(1);
		}
	}

}
