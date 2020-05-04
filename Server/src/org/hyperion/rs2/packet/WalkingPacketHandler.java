package org.hyperion.rs2.packet;

import org.hyperion.rs2.clipping.pf.DefaultPathFinder;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.net.Packet;

/**
 * A packet which handles walking requests.
 * @author Graham Edgecombe
 *
 */
public class WalkingPacketHandler implements PacketHandler {

	@Override
	public void handle(Player player, Packet packet) {
		int size = packet.getLength();
		if (packet.getOpcode() == 143) {
			size -= 14;
		}
		if(player.getAttribute("busy") != null) {
			return;
		}
		if(player.getAttribute("cutScene") != null) {
			return;
		}
		if(player.getInterfaceAttribute("fightPitOrbs") != null) {
			return;
		}
		
		if(packet.getOpcode() != 59) { //force walking
			player.getCombatState().setQueuedSpell(null);
			player.resetInteractingEntity();
			player.getActionQueue().clearAllActions();
			player.getActionSender().removeAllInterfaces();
		}


		player.getWalkingQueue().reset();
		
		if(!player.getCombatState().canMove()) {
			if(packet.getOpcode() != 59) { //force walking
				player.getActionSender().sendMessage("A magical force stops you from moving.");
			}
			return;
		}
		if(!player.canEmote()) {
			return; //stops walking during skillcape animations.
		}

		final int steps = (size - 5) / 2;
		final int[][] path = new int[steps][2];
		final boolean runSteps = packet.getByteS() == 1;
		int firstY = packet.getLEShort();
		int firstX = packet.getShortA();
		for (int i = 0; i < steps; i++) {
			path[i][0] = packet.getByteS();
			path[i][1] = packet.getByteS();
		}

		player.getWalkingQueue().setRunningQueue(runSteps);
		//	player.getWalkingQueue().addStep(firstX, firstY);
			if (steps > 0) {
				firstX += path[steps - 1][0];
				firstY += path[steps - 1][1];
			}
	        if (firstX < 0 || firstY < 0)
	        	return;
			World.getWorld().doPath(new DefaultPathFinder(), player, firstX, firstY);
		}

}
