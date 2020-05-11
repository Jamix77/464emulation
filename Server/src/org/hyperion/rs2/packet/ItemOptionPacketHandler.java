package org.hyperion.rs2.packet;

import java.util.logging.Logger;

import org.hyperion.cache.defs.ItemDef;
import org.hyperion.plugin.PluginManager;
import org.hyperion.plugin.impl.ItemOnHandler;
import org.hyperion.plugin.impl.OptionHandler;
import org.hyperion.plugin.impl.ItemOnHandler.ItemOnEvent;
import org.hyperion.plugin.impl.ItemOnHandler.ItemOnType;
import org.hyperion.rs2.action.Action;
import org.hyperion.rs2.action.impl.ConsumeItemAction;
import org.hyperion.rs2.model.Cannon;
import org.hyperion.rs2.model.GameObject;
import org.hyperion.rs2.model.GroundItem;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Shop;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.model.container.Bank;
import org.hyperion.rs2.model.container.Container;
import org.hyperion.rs2.model.container.Equipment;
import org.hyperion.rs2.model.container.Inventory;
import org.hyperion.rs2.model.region.Tile;
import org.hyperion.rs2.net.Packet;

/**
 * Remove item options.
 * 
 * @author Graham Edgecombe
 * 
 */
public class ItemOptionPacketHandler implements PacketHandler {

	/**
	 * The logger instance.
	 */
	private static final Logger logger = Logger
			.getLogger(ItemOptionPacketHandler.class.getName());

	/**
	 * Option drop/destroy opcode.
	 */
	private static final int OPTION_DROP_DESTROY = 247;

	/**
	 * Option pickup opcode.
	 */
	private static final int OPTION_PICKUP = 216;

	/**
	 * Option examine opcode.
	 */
	private static final int OPTION_EXAMINE = 205;

	/**
	 * Option 1 opcode.
	 */
	private static final int OPTION_1 = 177;

	/**
	 * Option 2 opcode.
	 */
	private static final int OPTION_2 = 88;

	/**
	 * Option 3 opcode.
	 */
	private static final int OPTION_3 = 159;

	/**
	 * Option 4 opcode.
	 */
	private static final int OPTION_4 = 86;

	/**
	 * Option 5 opcode.
	 */
	private static final int OPTION_5 = 220;

	/**
	 * Click 1 opcode.
	 */
	private static final int CLICK_1 = 101;

	/**
	 * Click 2 opcode.
	 */
	private static final int CLICK_2 = 4;

	/**
	 * Not sure what the packet is actually called.
	 */
	private static final int CLICK_3 = 212;
	/**
	 * Item on item opcode.
	 */
	private static final int ITEM_ON_ITEM = 166;

	@Override
	public void handle(Player player, Packet packet) {
		if (player.getAttribute("cutScene") != null) {
			return;
		}
		if (player.getAttribute("busy") != null) {
			return;
		}
		switch (packet.getOpcode()) {
		case OPTION_DROP_DESTROY:
			handleItemOptionDrop(player, packet);
			break;
		case OPTION_PICKUP:
			handleItemOptionPickup(player, packet);
			break;
		case OPTION_EXAMINE:
			handleItemOptionExamine(player, packet);
			break;
		case OPTION_1:
			handleItemOption1(player, packet);
			break;
		case OPTION_2:
			handleItemOption2(player, packet);
			break;
		case OPTION_3:
			handleItemOption3(player, packet);
			break;
		case OPTION_4:
			handleItemOption4(player, packet);
			break;
		case OPTION_5:
			handleItemOption5(player, packet);
			break;
		case CLICK_1:
			handleItemOptionClick1(player, packet);
			break;
		case CLICK_2:
			handleItemOptionClick2(player, packet);
			break;
		case CLICK_3:
			handleItemOptionClick3(player, packet);
			break;
		case ITEM_ON_ITEM:
			handleItemOptionItem(player, packet);
			break;
		}
	}

	/**
	 * Handles item option drop.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleItemOptionDrop(Player player, Packet packet) {
		int interfaceId = packet.getInt2() >> 16;
		int id = packet.getShortA();
		int slot = packet.getLEShort();
		Item item = player.getInventory().get(slot);
		if (player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemDrop", new Object[] { "ID: " + id });

		player.getActionSender().removeAllInterfaces();
		switch (interfaceId) {
		case Inventory.INTERFACE:
			if (slot >= 0 && slot < Inventory.SIZE) {
				if (item != null && item.getId() != id) {
					return;
				}
				player.getInventory().remove(item, slot);
				World.getWorld().createGroundItem(
						new GroundItem(player.getName(), item,
								player.getLocation()), player);
			}
			break;
		default:
			logger.info("Unhandled item drop option : " + interfaceId + " - "
					+ id + " - " + slot);
			break;
		}
	}

	/**
	 * Handles item option pickup.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleItemOptionPickup(final Player player, Packet packet) {
		final int id = packet.getShort();
		int y = packet.getShortA();
		int x = packet.getLEShortA();

		if (player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		final Location location = Location.create(x, y, player.getLocation()
				.getZ());

		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemPickup", new Object[] { "ID: " + id, "Loc: " + location });

		player.getActionSender().removeAllInterfaces();
		Action action = new Action(player, 0) {
			@Override
			public CancelPolicy getCancelPolicy() {
				return CancelPolicy.ALWAYS;
			}

			@Override
			public StackPolicy getStackPolicy() {
				return StackPolicy.NEVER;
			}

			@Override
			public AnimationPolicy getAnimationPolicy() {
				return AnimationPolicy.RESET_ALL;
			}

			@Override
			public void execute() {
				Tile tile = player.getRegion().getTile(location);
				for (GroundItem g : tile.getGroundItems()) {
					if (g.getItem().getId() == id
							&& g.isOwnedBy(player.getName())) {
						if (player.getInventory().add(
								player.checkForSkillcape(g.getItem()))) {
							World.getWorld().unregister(g);
						} else {
							player.getActionSender().sendMessage(
									"Not enough space in inventory.");
						}
						break;
					}
				}
				this.stop();
			}
		};
		player.addCoordinateAction(player.getWidth(), player.getHeight(),
				location, 0, 0, 0, action);
	}

	/**
	 * Handles item option 1.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleItemOption1(Player player, Packet packet) {
		int itemId = packet.getShort();
		int interfaceShit = packet.getInt1();
		int interfaceId = interfaceShit >> 16;
		int index = packet.getShort();
		// int childId = interfaceShit & 0xffff;
		Item item = player.getInventory().get(index);
		if (player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemOpt1",
				new Object[] { "ID: " + itemId, "Interface: " + interfaceId });

		switch (interfaceId) {
		case Equipment.INTERFACE:
		case Equipment.SCREEN:
			if (index >= 0 && index < Equipment.SIZE) {
				item = player.getEquipment().get(index);
				if (!player.canEmote()) {// stops people unequipping during a
											// skillcape emote.
					return;
				}
				if (!Container.transfer(player.getEquipment(),
						player.getInventory(), index, itemId)) {
					player.getActionSender().sendMessage(
							"Not enough space in inventory.");
				} else {
					if (item != null && item.getEquipmentDefinition() != null) {
						for (int i = 0; i < item.getEquipmentDefinition()
								.getBonuses().length; i++) {
							player.getCombatState().setBonus(
									i,
									player.getCombatState().getBonus(i)
											- item.getEquipmentDefinition()
													.getBonus(i));
						}
						player.getActionSender().sendBonuses();
						if (index == Equipment.SLOT_WEAPON) {
							player.setDefaultAnimations();
						}
					}
				}
			}
			break;
		case Bank.PLAYER_INVENTORY_INTERFACE:
			if (!player.getInterfaceState().isInterfaceOpen(
					Bank.BANK_INVENTORY_INTERFACE))
				return;
			if (index >= 0 && index < Inventory.SIZE) {
				Bank.deposit(player, index, itemId, 1);
			}
			break;
		case Bank.BANK_INVENTORY_INTERFACE:
			if (!player.getInterfaceState().isInterfaceOpen(
					Bank.BANK_INVENTORY_INTERFACE))
				return;
			if (index >= 0 && index < Bank.SIZE) {
				Bank.withdraw(player, index, itemId, 1);
			}
			break;
		case Shop.SHOP_INVENTORY_INTERFACE:
			if (index >= 0 && index < Inventory.SIZE) {
				Shop.costItem(player, index, itemId);
				break;
			}
			break;
		case Shop.PLAYER_INVENTORY_INTERFACE:
			if (index >= 0 && index < Inventory.SIZE) {
				Shop.valueItem(player, index, itemId);
				break;
			}
			break;
		/*
		 * case Smithing.INTERFACE:
		 * if(player.getInterfaceAttribute("smithing_bar") != null) { Bar bar =
		 * (Bar) player.getInterfaceAttribute("smithing_bar"); int row = -1;
		 * for(int i = 0; i < bar.getItems(childId - 146).length; i++) { Item
		 * newItem = bar.getItems(childId - 146)[i]; if(newItem == null) {
		 * continue; } if(newItem.getId() == itemId) { item = newItem; row = i;
		 * } } if(item == null || row == -1) { return; }
		 * player.getActionQueue().addAction(new Smithing(player, 1, item,
		 * childId - 146, row, bar)); } break;
		 */
		default:
			logger.info("Unhandled item option 1 : " + itemId + " - " + index
					+ " - " + interfaceId + ".");
			break;
		}
	}

	/**
	 * Handles item option 2.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleItemOption2(Player player, Packet packet) {
		int interfaceShit = packet.getLEInt();
		int interfaceId = interfaceShit >> 16;
		int id = packet.getLEShort();
		int slot = packet.getLEShortA();
		// int childId = interfaceShit & 0xFFFF;
		Item item = player.getInventory().get(slot);
		if (player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemOpt2",
				new Object[] { "ID: " + id, "Interface: " + interfaceId });

		switch (interfaceId) {
		case Equipment.INTERFACE:
			if (slot >= 0 && slot < Equipment.SIZE) {
				item = player.getEquipment().get(slot);
				if (item != null) {
					switch (item.getId()) {
					case 2550:
						player.getActionSender()
								.sendMessage(
										"Your Ring of Recoil can deal "
												+ player.getCombatState()
														.getRingOfRecoil()
												+ " more points of damage before shattering.");
						break;
					default:
						player.getActionSender().sendMessage(
								"There is no way to operate that item.");
						break;
					}
				}
			}
			break;
		case Bank.PLAYER_INVENTORY_INTERFACE:
			if (!player.getInterfaceState().isInterfaceOpen(
					Bank.BANK_INVENTORY_INTERFACE))
				return;
			if (slot >= 0 && slot < Inventory.SIZE) {
				Bank.deposit(player, slot, id, 5);
			}
			break;
		case Bank.BANK_INVENTORY_INTERFACE:
			if (!player.getInterfaceState().isInterfaceOpen(
					Bank.BANK_INVENTORY_INTERFACE))
				return;
			if (slot >= 0 && slot < Bank.SIZE) {
				Bank.withdraw(player, slot, id, 5);
			}
			break;
		case Shop.SHOP_INVENTORY_INTERFACE:
			if (slot >= 0 && slot < Inventory.SIZE) {
				Shop.buyItem(player, slot, id, 1);
				break;
			}
			break;
		case Shop.PLAYER_INVENTORY_INTERFACE:
			if (slot >= 0 && slot < Inventory.SIZE) {
				Shop.sellItem(player, slot, id, 1);
				break;
			}
			break;
		/*
		 * case Smithing.INTERFACE:
		 * if(player.getInterfaceAttribute("smithing_bar") != null) { Bar bar =
		 * (Bar) player.getInterfaceAttribute("smithing_bar"); int row = -1;
		 * for(int i = 0; i < bar.getItems(childId - 146).length; i++) { Item
		 * newItem = bar.getItems(childId - 146)[i]; if(newItem == null) {
		 * continue; } if(newItem.getId() == id) { item = newItem; row = i; } }
		 * if(item == null || row == -1) { return; }
		 * player.getActionQueue().addAction(new Smithing(player, 5, item,
		 * childId - 146, row, bar)); } break;
		 */
		default:
			logger.info("Unhandled item option 2 : " + id + " - " + slot
					+ " - " + interfaceId + ".");
			break;
		}
	}

	/**
	 * Handles item option 3.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleItemOption3(Player player, Packet packet) {
		int interfaceShit = packet.getLEInt();
		int interfaceId = interfaceShit >> 16;
		int slot = packet.getLEShort();
		int id = packet.getLEShort();
		// int childId = interfaceShit & 0xFFFF;
		// Item item = player.getInventory().get(slot);
		if (player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemOpt3",
				new Object[] { "ID: " + id, "Interface: " + interfaceId });

		switch (interfaceId) {
		case Bank.PLAYER_INVENTORY_INTERFACE:
			if (!player.getInterfaceState().isInterfaceOpen(
					Bank.BANK_INVENTORY_INTERFACE))
				return;
			if (slot >= 0 && slot < Inventory.SIZE) {
				Bank.deposit(player, slot, id, 10);
			}
			break;
		case Bank.BANK_INVENTORY_INTERFACE:
			if (!player.getInterfaceState().isInterfaceOpen(
					Bank.BANK_INVENTORY_INTERFACE))
				return;
			if (slot >= 0 && slot < Bank.SIZE) {
				Bank.withdraw(player, slot, id, 10);
			}
			break;
		case Shop.SHOP_INVENTORY_INTERFACE:
			if (slot >= 0 && slot < Inventory.SIZE) {
				Shop.buyItem(player, slot, id, 5);
				break;
			}
			break;
		case Shop.PLAYER_INVENTORY_INTERFACE:
			if (slot >= 0 && slot < Inventory.SIZE) {
				Shop.sellItem(player, slot, id, 5);
				break;
			}
			break;
		/*
		 * case Smithing.INTERFACE:
		 * if(player.getInterfaceAttribute("smithing_bar") != null) { Bar bar =
		 * (Bar) player.getInterfaceAttribute("smithing_bar"); int row = -1;
		 * for(int i = 0; i < bar.getItems(childId - 146).length; i++) { Item
		 * newItem = bar.getItems(childId - 146)[i]; if(newItem == null) {
		 * continue; } if(newItem.getId() == id) { item = newItem; row = i; } }
		 * if(item == null || row == -1) { return; }
		 * player.getActionQueue().addAction(new Smithing(player, 10, item,
		 * childId - 146, row, bar)); } break;
		 */
		default:
			logger.info("Unhandled item option 3 : " + id + " - " + slot
					+ " - " + interfaceId + ".");
			break;
		}
	}

	/**
	 * Handles item option 4.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleItemOption4(Player player, Packet packet) {
		int slot = packet.getLEShort() & 0xFFFF;
		int id = packet.getShort() & 0xFFFF;
		int interfaceSet = packet.getInt2();
		int interfaceId = interfaceSet >> 16;
		if (player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemOpt4",
				new Object[] { "ID: " + id, "Interface: " + interfaceId });

		switch (interfaceId) {
		case Bank.PLAYER_INVENTORY_INTERFACE:
			if (!player.getInterfaceState().isInterfaceOpen(
					Bank.BANK_INVENTORY_INTERFACE))
				return;
			if (slot >= 0 && slot < Inventory.SIZE) {
				Bank.deposit(player, slot, id,
						player.getInventory().getCount(id));
			}
			break;
		case Bank.BANK_INVENTORY_INTERFACE:
			if (!player.getInterfaceState().isInterfaceOpen(
					Bank.BANK_INVENTORY_INTERFACE))
				return;
			if (slot >= 0 && slot < Bank.SIZE) {
				Bank.withdraw(player, slot, id, player.getBank().getCount(id));
			}
			break;
		case Shop.SHOP_INVENTORY_INTERFACE:
			if (slot >= 0 && slot < Inventory.SIZE) {
				Shop.buyItem(player, slot, id, 10);
				break;
			}
			break;
		case Shop.PLAYER_INVENTORY_INTERFACE:
			if (slot >= 0 && slot < Inventory.SIZE) {
				Shop.sellItem(player, slot, id, 10);
				break;
			}
			break;
		default:
			logger.info("Unhandled item option 4 : " + id + " - " + slot
					+ " - " + interfaceId + ".");
			break;
		}
	}

	/**
	 * Handles item option 5.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleItemOption5(Player player, Packet packet) {
		int id = packet.getShortA();
		int slot = packet.getLEShort();
		int interfaceId = packet.getInt() >> 16;
		if (player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemOpt5",
				new Object[] { "ID: " + id, "Interface: " + interfaceId });

		switch (interfaceId) {
		case Bank.PLAYER_INVENTORY_INTERFACE:
			if (!player.getInterfaceState().isInterfaceOpen(
					Bank.BANK_INVENTORY_INTERFACE))
				return;
			if (slot >= 0 && slot < Inventory.SIZE) {
				player.getInterfaceState().openEnterAmountInterface(
						interfaceId, slot, id);
			}
			break;
		case Bank.BANK_INVENTORY_INTERFACE:
			if (!player.getInterfaceState().isInterfaceOpen(
					Bank.BANK_INVENTORY_INTERFACE))
				return;
			if (slot >= 0 && slot < Bank.SIZE) {
				player.getInterfaceState().openEnterAmountInterface(
						interfaceId, slot, id);
			}
			break;
		default:
			logger.info("Unhandled item option 5 : " + id + " - " + slot
					+ " - " + interfaceId + ".");
			break;
		}
	}

	/**
	 * Handles item option examine.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleItemOptionExamine(Player player, Packet packet) {
		int id = packet.getShort() & 0xFFFF;

		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemExamine", new Object[] { "ID: " + id });

		Item item = new Item(id);
		if (item.getDefinition() != null) {
			player.getActionSender().sendMessage(
					item.getDefinition().getDescription());
		}
	}

	/**
	 * Handles item option 1.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleItemOptionClick1(final Player player, Packet packet) {
		int interfaceId = packet.getInt1() >> 16;
		int slot = packet.getLEShort();
		int id = packet.getLEShort();
		Item item = player.getInventory().get(slot);
		if (player.getCombatState().isDead()) {
			return;
		}
		if (item.getId() != id) {
			return;
		}
		if (slot < 0 || slot > 27) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		player.getActionSender().sendDebugPacket(
				packet.getOpcode(),
				"ItemClick1",
				new Object[] { "ID: " + id,
						"Interface: " + interfaceId + "Slot:" + slot });

		switch (interfaceId) {
		case Inventory.INTERFACE:
			if (slot >= 0 && slot < Inventory.SIZE) {
				item = player.getInventory().get(slot);
				if (item == null || id != item.getId()) {
					return;
				}

			
			OptionHandler p = item.getDefinition().getHandlers().get("option:" + ItemDef.forId(item.getId()).actions[0].toLowerCase());
			if (p == null) {
				p = (OptionHandler) PluginManager.getOptionHandlerPlugins().get("item:" +ItemDef.forId(item.getId()).actions[0].toLowerCase());
			}
			if (p != null) {
				p.handle(player, item, ItemDef.forId(item.getId()).actions[0].toLowerCase());
			}
				break;
			}
		}
	}

	private void handleItemOptionClick3(Player player, Packet packet) {
		int interfaceShit = packet.getInt1();
		int interfaceId = interfaceShit >> 16;
		int slot = packet.getLEShortA();
		int id = packet.getLEShort();
		Item item = player.getInventory().get(slot);
		if (player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		System.out.printf("ID: " + id + "Interface: " + interfaceId + " Slot:"
				+ slot);

		switch (interfaceId) {
		case Inventory.INTERFACE:
			if (slot >= 0 && slot < Inventory.SIZE) {
				item = player.getInventory().get(slot);
				if (item == null || id != item.getId()) {
					return;
				}
				break;
			}
		}
	}

	private void handleItemOptionClick2(Player player, Packet packet) {
		int interfaceId = packet.getLEShortA() >> 16;
		int id = packet.getShort();
		int slot = packet.getLEShort();
		Item item = player.getInventory().get(slot);
		if (player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		player.getActionSender().sendDebugPacket(
				packet.getOpcode(),
				"ItemClick2",
				new Object[] { "ID: " + id,
						"Interface: " + interfaceId + " Slot:" + slot });

		switch (interfaceId) {
		case Inventory.INTERFACE:
			if (slot >= 0 && slot < Inventory.SIZE) {
				item = player.getInventory().get(slot);
				if (item == null || id != item.getId()) {
					return;
				}
				switch (item.getId()) {
				case 3842:
					player.getActionSender().sendMessage("ddd");
					break;
				default:
					break;
				}
				break;
			}
		}
	}

	/**
	 * Handles item on item option.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleItemOptionItem(final Player player, Packet packet) {
		int slot = packet.getLEShort();
		int usedWithSlot = packet.getShort();
		@SuppressWarnings("unused")
		int withId = packet.getLEShortA();
		int interfaceId = packet.getInt2() >> 16;
		@SuppressWarnings("unused")
		int withInterfaceId = packet.getLEInt() >> 16;
		int id = packet.getShortA();
		@SuppressWarnings("unused")
		Item item = player.getInventory().get(slot);
		if (player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemOnItem",
				new Object[] { "ID: " + id, "Interface: " + interfaceId });

		Item usedItem = null;
		Item withItem = null;

		switch (interfaceId) {
		case Inventory.INTERFACE:
			if (slot >= 0 && slot < Inventory.SIZE) {
				usedItem = player.getInventory().get(slot);
				withItem = player.getInventory().get(usedWithSlot);

				if (player.getCombatState().isDead())
					return;

				ItemOnHandler h = ItemOnHandler.getHandlers().get(ItemOnType.ITEM_ON_ITEM.getPrefix() + ":" + usedItem.getId() + "-" + withItem.getId());
				if (h != null) {
					h.handle(new ItemOnEvent(usedItem,withItem,player));
					return;
				}
				player.getActionSender().sendMessage(
						"Nothing interesting happens.");
				break;
			}
		}
	}

}
