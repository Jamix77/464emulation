package org.hyperion.rs2.packet;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.net.Packet;

public class ChatConfigurationPacketHandler implements PacketHandler {

	@Override
	public void handle(Player player, Packet packet) {
		int privateBefore = player.getInterfaceState().getPrivateChat();
		player.getInterfaceState().setPublicChat(packet.get());
		player.getInterfaceState().setPrivateChat(packet.get());
		player.getInterfaceState().setTrade(packet.get());
		if(privateBefore != player.getInterfaceState().getPrivateChat()) { //private chat has been toggled
			player.getPrivateChat().updateFriendList(true);
		}
	}

}