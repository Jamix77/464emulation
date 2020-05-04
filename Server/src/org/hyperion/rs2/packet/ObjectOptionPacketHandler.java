package org.hyperion.rs2.packet;

import org.hyperion.rs2.action.impl.CoordinateAction;
import org.hyperion.rs2.action.impl.ProductionAction;
import org.hyperion.rs2.model.GameObject;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.region.Region;
import org.hyperion.rs2.net.Packet;

/**
 * Object option packet handler.
 * 
 * @author Graham Edgecombe
 * 
 */
public class ObjectOptionPacketHandler implements PacketHandler {

	private static final int OPTION_1 = 44, OPTION_2 = 119,
			ITEM_ON_OBJECT = 103, OBJECT_EXAMINE = 97;

	@Override
	public void handle(Player player, Packet packet) {
		if (player.getAttribute("busy") != null) {
			return;
		}
		player.getActionQueue().clearRemovableActions();
		switch (packet.getOpcode()) {
		case OPTION_1:
			handleOption1(player, packet);
			break;
		case OPTION_2:
			handleOption2(player, packet);
			break;
		case ITEM_ON_OBJECT:
			handleOptionItem(player, packet);
			break;
		case OBJECT_EXAMINE:
			handleExamine(player, packet);
			break;
		}
	}

	private void handleExamine(Player player, Packet packet) {
		int objectId = packet.getShortA();
		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemExamine", new Object[] { "ID: " + objectId });
		// player.getActionSender().sendMessage(ObjectDef.forId(objectId).name +
		// " " + objectId);
		player.getActionSender().sendMessage("rofl examine obj.");
	}

	/**
	 * Handles the option 1 packet.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleOption1(final Player player, Packet packet) {
		final int id = packet.getLEShort();
		final int x = packet.getShort();
		final int y = packet.getLEShort();
		int z = player.getLocation().getZ();
		final Location loc = Location.create(x, y, z);

		Region r = player.getRegion();
		final GameObject obj = r.getGameObject(loc, id);
		if (obj == null) {
			return;
		}
		//player.addCoordinateAction(player.getWidth(), player.getHeight(),
		//		location, 0, 0, 0, action);
	}

	/**
	 * Handles the option 2 packet.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleOption2(final Player player, Packet packet) {
		final int x = packet.getShort() & 0xFFFF;
		final int id = packet.getLEShortA() & 0xFFFF;
		final int y = packet.getLEShortA() & 0xFFFF;
		int z = player.getLocation().getZ();

		final Location loc = Location.create(x, y, z);

		final GameObject obj = player.getRegion().getGameObject(loc, id);
		if (obj == null) {
			return;
		}
		player.face(player.getLocation().oppositeTileOfEntity(obj));

		player.getActionSender().sendDebugPacket(packet.getOpcode(), "ObjOpt2",
				new Object[] { "ID: " + id, "Loc: " + loc });

	}

	/**
	 * Handles the item on object packet.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleOptionItem(final Player player, Packet packet) {
		final int y = packet.getShortA();
		final int slot = packet.getShort();
		final int x = packet.getLEShortA();
		@SuppressWarnings("unused")
		final int interfaceId = packet.getInt2() >> 16;
		@SuppressWarnings("unused")
		final int itemId = packet.getShortA();
		final int id = packet.getLEShort();
		int z = player.getLocation().getZ();
		System.out.println("object " + id);
		final Location loc = Location.create(x, y, z);

		final Item item = player.getInventory().get(slot);
		if (item == null) {
			return;
		}

		final GameObject obj = player.getRegion().getGameObject(loc, id);
		if (obj == null) {
			return;
		}
		player.face(player.getLocation().oppositeTileOfEntity(obj));

		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemOnObject", new Object[] { "ID: " + id, "Loc: " + loc });

	}

}
