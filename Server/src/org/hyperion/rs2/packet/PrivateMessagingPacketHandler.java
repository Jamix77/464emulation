package org.hyperion.rs2.packet;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.PrivateChat.ClanRank;
import org.hyperion.rs2.net.Packet;
import org.hyperion.rs2.util.TextUtils;

public class PrivateMessagingPacketHandler implements PacketHandler {

	@Override
	public void handle(Player player, Packet packet) {
		long nameAsLong = packet.getLong();
		if(player.getAttribute("cutScene") != null) {
			return;
		}
		switch(packet.getOpcode()) {
		case 238:
			int size = packet.getLength() - 8;
			byte[] data = new byte[size];
			packet.get(data);
			String unpacked = TextUtils.textUnpack(data, size);
			byte[] packed = new byte[size];
			TextUtils.textPack(packed, unpacked);
			player.getPrivateChat().sendMessage(nameAsLong, packed);
			break;
		case 197:
			player.getPrivateChat().addFriend(nameAsLong, ClanRank.FRIEND);
			break;
		case 133:
			player.getPrivateChat().removeFriend(nameAsLong);
			break;
		case 102:
			player.getPrivateChat().addIgnore(nameAsLong);
			break;
		case 214:
			player.getPrivateChat().removeIgnore(nameAsLong);
			break;
		}
	}
		
}