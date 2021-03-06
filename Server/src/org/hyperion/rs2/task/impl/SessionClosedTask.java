package org.hyperion.rs2.task.impl;

import java.net.SocketAddress;
import java.util.logging.Logger;

import org.apache.mina.core.session.IoSession;
import org.hyperion.rs2.GameEngine;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.task.Task;
import org.hyperion.rs2.tickable.Tickable;


/**
 * A task that is executed when a session is closed.
 * @author Graham Edgecombe
 *
 */
public class SessionClosedTask implements Task {

	/**
	 * Logger instance.
	 */
	private static final Logger logger = Logger.getLogger(SessionClosedTask.class.getName());
	
	/**
	 * The session that closed.
	 */
	private IoSession session;
	
	/**
	 * Creates the session closed task.
	 * @param session The session.
	 */
	public SessionClosedTask(IoSession session) {
		this.session = session;
	}

	@Override
	public void execute(GameEngine context) {
		SocketAddress address = (SocketAddress) session.getAttribute("remote");
		logger.fine("Session closed : " + address);
		if(session.containsAttribute("player")) {
			final Player p = (Player) session.getAttribute("player");
			if(p != null) {
				if(p.getCombatState().getLastHitTimer() > System.currentTimeMillis()) {
					World.getWorld().submit(new Tickable(100) {
						@Override
						public void execute() {
							World.getWorld().unregister(p);
							this.stop();
						}
					});
				} else {
					World.getWorld().unregister(p);
				}
			}
		}
	}

}
