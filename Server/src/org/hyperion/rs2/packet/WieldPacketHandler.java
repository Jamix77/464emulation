package org.hyperion.rs2.packet;

import org.hyperion.rs2.action.Action;
import org.hyperion.rs2.action.impl.WieldItemAction;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.model.container.Inventory;
import org.hyperion.rs2.net.Packet;
import org.hyperion.rs2.tickable.Tickable;

/**
 * Handles the 'wield' option on items.
 * @author Graham Edgecombe
 *
 */
public class WieldPacketHandler implements PacketHandler {

	@Override
	public void handle(Player player, Packet packet) {
		int slot = packet.getLEShort();
		int interfaceId = packet.getLEInt() >> 16;
		int id = packet.getLEShort();
		if(player.getAttribute("busy") != null) {
			return;
		}
		if(player.getInterfaceAttribute("fightPitOrbs") != null || player.getAttribute("cutScene") != null) {
			return;
		}

		player.getActionSender().sendDebugPacket(packet.getOpcode(), "WieldItem", new Object[] { "ID: " + id, "Interface: " + interfaceId + "Slot: " + slot });
		
		switch(interfaceId) {
		case Inventory.INTERFACE:
			if(slot >= 0 && slot < Inventory.SIZE) {
				if(player.getCombatState().isDead()) {
					return;
				}
				Item item = player.getInventory().get(slot);
				if(item != null && item.getId() == id) {
					final Action action = new WieldItemAction(player, id, slot, 0);
					if(player.getCombatState().getWeaponSwitchTimer() < 1) {
						action.execute();
					} else {
						World.getWorld().submit(new Tickable(1) {
							@Override
							public void execute() {
								action.execute();
								this.stop();
							}							
						});
//						player.getActionQueue().addAction(action);
					}
				}
			}
			break;
		}
	}

}
