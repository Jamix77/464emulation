package org.hyperion.rs2.packet;

import java.util.logging.Logger;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.net.Packet;


/**
 * A packet handler that is called when an interface is closed.
 * @author Graham Edgecombe
 *
 */
public class CloseInterfacePacketHandler implements PacketHandler {

	/**
	 * The logger instance.
	 */
	private static final Logger logger = Logger.getLogger(CloseInterfacePacketHandler.class.getName());

	@Override
	public void handle(Player player, Packet packet) {
		if(packet.getOpcode() == 240) {
			int interfaceShit = packet.getInt();
			int interfaceId = interfaceShit >> 16;
			int childId = interfaceShit & 0xffff;
            player.getActionSender().removeChatboxInterface().sendInterfacesRemovedClientSide();
           
			switch(interfaceId) {
			case 7:
			case 64:
			case 65:
			case 66:
			case 67:
			case 241:
			case 242:
			case 243:
			case 244:
			case 157:
			case 158:
			case 159:
			case 160:
			case 161:
			case 162:
			case 163:
			case 164:
			case 165:
			case 166:
			case 167:
			case 168:
			case 169:
			case 170:
			case 171:
			case 172:
			case 173:
			case 174:
			case 175:
			case 176:
			case 177:
			case 519:
				player.getDialogueManager().handle();
				break;
			case 210:
			case 211:
			case 212:
			case 213:
			case 214:
			case 228:
			case 229:
			case 230:
			case 231:
			case 232:
			case 233:
			case 234:
			case 235:
			case 236:
			case 237:
			case 238:
				if (player.getDialogueManager().getCurrentDialogue() == null) {
					player.getActionSender().removeChatboxInterface();
					break;
				}
				player.getDialogueManager().getCurrentDialogue().setButton(childId-1);
				player.getDialogueManager().handle();
				break;
			default:
				logger.info("Unhandled close interface : " + interfaceId + " - " + childId);
				break;
			}
		} else {
			player.getActionSender().removeChatboxInterface().sendInterfacesRemovedClientSide();
		}
	}

}
