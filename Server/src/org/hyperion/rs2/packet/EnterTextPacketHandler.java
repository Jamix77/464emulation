package org.hyperion.rs2.packet;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.net.Packet;
import org.hyperion.rs2.util.NameUtils;

/**
 * A packet sent when the player enters a custom amount for banking etc.
 * @author Graham Edgecombe
 *
 */
public class EnterTextPacketHandler implements PacketHandler {

	@Override
	public void handle(Player player, Packet packet) {
		String text = NameUtils.formatName(packet.getRS2String());
		if(player.getAttribute("cutScene") != null) {
			return;
		}
		if(text.length() > 12) {
			text = text.substring(0, 12);
		}
		if(player.getInterfaceState().isEnterAmountInterfaceOpen()) {
			player.getInterfaceState().closeEnterTextInterface(text);
		}
	}

}
