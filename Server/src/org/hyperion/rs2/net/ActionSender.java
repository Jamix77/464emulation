 package org.hyperion.rs2.net;

import org.hyperion.rs2.Constants;
import org.hyperion.rs2.clipping.WorldMapObjectsLoader;
import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.GameObject;
import org.hyperion.rs2.model.GroundItem;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.Mob;
import org.hyperion.rs2.model.Mob.InteractionMode;
import org.hyperion.rs2.model.Palette;
import org.hyperion.rs2.model.Palette.PaletteTile;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.RequestManager.RequestState;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.model.Sound;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.model.boundary.BoundaryManager;
import org.hyperion.rs2.model.combat.impl.MagicCombatAction.SpellBook;
import org.hyperion.rs2.model.container.Equipment;
import org.hyperion.rs2.model.container.Inventory;
import org.hyperion.rs2.model.container.impl.EquipmentContainerListener;
import org.hyperion.rs2.model.container.impl.InterfaceContainerListener;
import org.hyperion.rs2.model.container.impl.WeaponContainerListener;
import org.hyperion.rs2.model.dialogue.DialogueExpression;
import org.hyperion.rs2.model.region.Region;
import org.hyperion.rs2.net.Packet.Type;


/**
 * A utility class for sending packets.
 * @author Graham Edgecombe
 *
 */
public class ActionSender {
	
	/**
	 * The player.
	 */
	private Player player;
	
	/**
	 * The player's ping count.
	 */
	private int pingCount;
	
	/**
	 * Creates an action sender for the specified player.
	 * @param player The player to create the action sender for.
	 */
	public ActionSender(Player player) {
		this.player = player;
	}
	
	/**
	 * Sends an inventory interface.
	 * @param interfaceId The interface id.
	 * @param inventoryInterfaceId The inventory interface id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfaceInventory(int inventoryInterfaceId) {
		return sendInterface(Constants.MAIN_WINDOW, Constants.SIDE_TABS, inventoryInterfaceId, false);
	}
	
	/**
	 * Sends a chatbox interface.
	 * @param interfaceId The interface id.
	 * @param inventoryInterfaceId The inventory interface id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendChatboxInterface(int inventoryInterfaceId) {
		return sendInterface(Constants.MAIN_WINDOW, 79, inventoryInterfaceId, false);
	}
	
	public ActionSender sendInterface(int windowId, int position, int interfaceId, boolean walkable) {
		if (windowId == Constants.MAIN_WINDOW && position == Constants.GAME_WINDOW) {
			player.getInterfaceState().interfaceOpened(interfaceId, walkable);
		}
		PacketBuilder pb = new PacketBuilder(238);
		pb.putInt((position << 16) + windowId);
		pb.putShort(interfaceId);
		pb.putByteC(walkable ? 1 : 0);
		player.write(pb.toPacket());
		return this;
	}

	public ActionSender sendInterfaceModel(int interfaceId, int childId, int size, int itemId) {
		player.write(new PacketBuilder(114).putLEInt(size).putShort(itemId).putLEInt(interfaceId << 16 | childId).toPacket());
		return this;
	}
	
	public ActionSender sendWalkableInterface(int interfaceId) {
		if(interfaceId == -1) {
			player.getInterfaceState().interfaceClosed();
			//player.getSession().write(new PacketBuilder(174).putInt(Constants.MAIN_WINDOW << 16 | Constants.GAME_WINDOW).toPacket()); //main game screen
			return removeInterface();
		}
		return sendInterface(Constants.MAIN_WINDOW, Constants.GAME_WINDOW, interfaceId, true);
	}


	public ActionSender sendInterface(int interfaceId, boolean interfaceClosed) {
		if(interfaceClosed) {
			player.getInterfaceState().interfaceClosed();
		}
		return sendInterface(Constants.MAIN_WINDOW, Constants.GAME_WINDOW, interfaceId, false);
	}

	public ActionSender sendChatInterface(int interfaceId) {
		return sendInterface(Constants.MAIN_WINDOW, Constants.CHAT_BOX, interfaceId, false);
	}
	
	public ActionSender sendDefaultChatbox() {
		player.write(new PacketBuilder(156).put((byte) player.getInterfaceState().getPublicChat()).put((byte) player.getInterfaceState().getPrivateChat()).put((byte) player.getInterfaceState().getTrade()).toPacket());
		//player.write(new PacketBuilder(17).putByteA((byte) 0).putLEShort(137).putShort(90).putShort(Constants.MAIN_WINDOW).toPacket());
		return this;
	}
	
	/**
	 * Sends all the login packets.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendLogin() {
		sendMapRegion();
		player.setActive(true);

		player.getPrivateChat().updateFriendList(true);
		sendString(Constants.MESSAGE_OF_THE_WEEK_SCREEN, 1, Constants.MESSAGE_OF_THE_WEEK);
		sendString(378, 15, Constants.SERVER_NAME + " Staff will NEVER email you. We use the message centre on this website instead.");
		int messages = 0;
		sendString(378, 16, "You have <col=" + (messages > 0 ? "55ff00" : "") + ">" + messages + "<col=ffea00> unread messages in your<br>message centre.");
		sendString(378, 12, "Welcome to " + Constants.SERVER_NAME);
		if(player.getDaysOfMembership() < 1) {
			sendString(378, 21,"You have no members");
		} else {
			sendString(378, 21,"You have unlimited days of member credit remaining"); // Tooltip
		}
		if(player.getRecoveryQuestionsLastSet().equalsIgnoreCase("never")) {
			sendString(378, 22, "You have not yet set any recovery questions.");
		} else {
			sendString(378, 22, "You have do yet set any recovery questions.");
		}
		sendString(378, 14, player.getRecoveryQuestionsLastSet().equalsIgnoreCase("never")  ? "You have not yet set any recovery questions. It is <col=ff7000>strongly <col=ffea00>recommended that you do so." +
		"If you don't you will be <col=ff7000>unable to recover your password <col=ffea00>if you forget it, or it is stolen." : "Recovery Questions Last Set:<br>" + player.getRecoveryQuestionsLastSet());
		sendString(378, 17, "You do not have a Bank PIN. Please visit a bank if you would like one.");
		String colourForMembers = "<col=ffea00>";
		if(player.getDaysOfMembership() <= 7) {
			colourForMembers = "<col=ff0000>";
		} else if(player.getDaysOfMembership() >= 20) {
			colourForMembers = "<col=55ff00>";			
		}
		sendString(378, 19, player.isMembers() ? "You have " + colourForMembers + player.getDaysOfMembership() + " <col=ffea00>days of member credit remaining." : "You are not a member. Choose to subscribe and you'll get loads of extra benefits and features.");
	
		String lastLoggedInPrefix = player.getLastLoggedInDays() + " days ago";
		if(player.getLastLoggedIn() - System.currentTimeMillis() < 0x5265c00L) {
			lastLoggedInPrefix = "earlier today";
		} else if(player.getLastLoggedIn() - System.currentTimeMillis() < (0x5265c00L * 2)) {
			lastLoggedInPrefix = "yesterday";
		}
		if(player.getLastLoggedIn() == 0) {
			sendString(378, 13, "This is your first time playing, enjoy " + Constants.SERVER_NAME + "!");
		} else {
			sendString(378, 13, "You last logged in <col=ff0000>" + lastLoggedInPrefix + " <col=000000>from: <col=ff0000>" + player.getSession().getAttribute("remote").toString().substring(1).substring(0, player.getSession().getAttribute("remote").toString().indexOf(":")-1));
		}
		if(player.getLastLoggedIn() == 0) {
			player.getActionSender().sendInterface(269, false);
			player.getInventory().add(new Item(995, 60000));
			player.getInventory().add(new Item(556, 250));
			player.getInventory().add(new Item(558, 250));
			player.getInventory().add(new Item(557, 400));
			player.getInventory().add(new Item(555, 400));
			player.getInventory().add(new Item(554, 400));
		}
		player.setLastLoggedIn(System.currentTimeMillis());
		player.setLastLoggedInFrom(player.getSession().getAttribute("remote").toString());
		
		sendWindowPane(549);
		sendInterface(Constants.LOGIN_SCREEN, 2, 378, true);
		sendInterface(Constants.LOGIN_SCREEN, 3, Constants.MESSAGE_OF_THE_WEEK_SCREEN, true);
		sendSidebarInterfaces();			
		
		sendString(550, 2, "Friends List - World 1");
		sendFriends();
		sendIgnores();
		sendSkills();
		sendMessage("Welcome to " + Constants.SERVER_NAME + ".");
		//sendMessage("Latest Update: " + Constants.UPDATE + ".");
		
		
		sendInteractionOption("null", 1, true); // null or fight
		sendInteractionOption("null", 2, false); // challenge = duel arena only
		sendInteractionOption("Follow", 3, false);
		sendInteractionOption("Trade with", 4, false);
		sendInteractionOption("Pat", 5, false);
		
		updateRunningConfig();
		sendRunEnergy();
		updateBrightnessConfig();
		updateSplitPrivateChatConfig();
		updateAcceptAidConfig();
		updateMouseButtonsConfig();
		updateChatEffectsConfig();
		updateBankConfig();
		updateAutoRetaliateConfig();
		
		
		player.getCombatState().calculateBonuses();
		sendBonuses();
		
		sendConfig(313, 255); //SkillCape Off + First Part Of Emotes
		sendConfig(465, 511); // 2nd part Of Emotes
		sendConfig(802, 511); // 3rd Part Of Emotes
		sendConfig(1085, 700); // 4th Part Of Emotes

		player.checkForSkillcapes();
			
		InterfaceContainerListener inventoryListener = new InterfaceContainerListener(player, Inventory.INTERFACE, 0, 93);
		player.getInventory().addListener(inventoryListener);
		
		InterfaceContainerListener equipmentListener = new InterfaceContainerListener(player, Equipment.INTERFACE, 28, 94);
		player.getEquipment().addListener(equipmentListener);
		player.getEquipment().addListener(new EquipmentContainerListener(player));
		player.getEquipment().addListener(new WeaponContainerListener(player));

		sendAreaInterface(null, player.getLocation());
		return this;
	}

	/**
	 * Sends the packet to construct a map region.
	 * @param palette The palette of map regions.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendConstructMapRegion(Palette palette) {
		player.setLastKnownRegion(player.getLocation());
		PacketBuilder bldr = new PacketBuilder(241, Type.VARIABLE_SHORT);
		bldr.putShortA(player.getLocation().getRegionY() + 6);
		bldr.startBitAccess();
		for(int z = 0; z < 4; z++) {
			for(int x = 0; x < 13; x++) {
				for(int y = 0; y < 13; y++) {
					PaletteTile tile = palette.getTile(x, y, z);
					bldr.putBits(1, tile != null ? 1 : 0);
					if(tile != null) {
						bldr.putBits(26, tile.getX() << 14 | tile.getY() << 3 | tile.getZ() << 24 | tile.getRotation() << 1);
					}
				}
			}
		}
		bldr.finishBitAccess();
		bldr.putShort(player.getLocation().getRegionX() + 6);
	//	//player.write(bldr.toPacket());
		return this;
	}
	
	/**
	 * Sends the 'Window Pane' 
	 * @param interfaceId the interfaceId to make the window pane
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendWindowPane(int interfaceId) {
		player.write(new PacketBuilder(77).putLEShortA(interfaceId).toPacket());
		return this;
	}
	
	public ActionSender sendChildColour(int interfaceId, int childId, int colour) {
		player.write(new PacketBuilder(244).putInt(interfaceId << 16 | childId).putShortA((short) colour).toPacket());
		return this;
	}
	
	public ActionSender sendMusic(int music) {
		player.getSession().write(new PacketBuilder(5).putShort(music).toPacket());
		return this;
	}
	
	/**
	 * Sends the player's skills.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSkills() {
		for(int i = 0; i < Skills.SKILL_COUNT; i++) {
			sendSkill(i);
		}
		return this;
	}
	
	/**
	 * Sends a specific skill.
	 * @param skill The skill to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSkill(int skill) {
		PacketBuilder bldr = new PacketBuilder(190);
		bldr.putByteS((byte) skill);
		bldr.putLEInt((int) player.getSkills().getExperience(skill));
		if(skill == Skills.PRAYER) {
			bldr.put((byte) Math.ceil(player.getSkills().getPrayerPoints()));
		} else {
			bldr.put((byte) player.getSkills().getLevel(skill));
		}
		player.getSession().write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends all the sidebar interfaces.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSidebarInterfaces() {
		final int[] icons = Constants.SIDEBAR_INTERFACES[0];
		final int[] interfaces = Constants.SIDEBAR_INTERFACES[1];
		for(int i = 0; i < icons.length; i++) {
			if(i == 7) {
				sendSidebarInterface(icons[i], SpellBook.forId(player.getCombatState().getSpellBook()).getInterfaceId());
			} else {
				sendSidebarInterface(icons[i], interfaces[i]);
			}
		}
		return this;
	}
	
	/**
	 * Sends a specific sidebar interface.
	 * @param icon The sidebar icon.
	 * @param interfaceId The interface id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSidebarInterface(int icon, int interfaceId) {
		if(icon == 86) {
			player.getInterfaceState().setOpenAutocastType(-1);
		}
		return sendInterface(Constants.MAIN_WINDOW, icon, interfaceId, true);
	}
	
	/**
	 * Sends a message.
	 * @param message The message to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendMessage(String message) {
		player.write(new PacketBuilder(108, Type.VARIABLE).putRS2String(message).toPacket());
		return this;
	}
	
	/**
	 * Sends a debug message.
	 * @param message The message to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendDebugMessage(String message) {
		return player.isDebugMode() ? sendMessage("<col=ff0000>" + message) : this;
	}
	
	/**
	 * Sends a debug message.
	 * @param message The message to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendDebugPacket(int opCode, String description, Object[] params) {
		String paramString = "";
		for(Object object : params) {
			paramString += object.toString() + "    ";
		}
		return sendDebugMessage("------------------------------------------------------------------------------------------")
			  .sendDebugMessage("Pkt            " + opCode + "  " + description)
			  .sendDebugMessage("------------------------------------------------------------------------------------------")
			  .sendDebugMessage("Params    " + paramString)
			  .sendDebugMessage("------------------------------------------------------------------------------------------");
	}
	
	/**
	 * Sends the map region load command.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendMapRegion() {
		player.setLastKnownRegion(player.getLocation());
		PacketBuilder pb = new PacketBuilder(221, Type.VARIABLE_SHORT);
		pb.putShortA(player.getLocation().getRegionY());
		for (int xCalc = (player.getLocation().getRegionX() - 6) / 8; xCalc <= (player.getLocation().getRegionX() + 6) / 8; xCalc++) {
			for (int yCalc = (player.getLocation().getRegionY() - 6) / 8; yCalc <= (player.getLocation().getRegionY() + 6) / 8; yCalc++) {
				int region = yCalc + (xCalc << 8); // 1786653352
				WorldMapObjectsLoader.loadMaps(region);
					pb.putInt1(0);
					pb.putInt1(0);
					pb.putInt1(0);
					pb.putInt1(0);
			}
		}
		pb.putLEShort(player.getLocation().getRegionX());
		pb.putShort(player.getLocation().getLocalX());
		pb.put((byte) player.getLocation().getZ());
		pb.putShort(player.getLocation().getLocalY());
		player.write(pb.toPacket());
		return this;
	}
	
	/**
	 * Sends the logout packet.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendLogout() {
		if(player.getAttribute("busy") != null) {
			return this;
		}
		if(player.getCombatState().getLastHitTimer() > System.currentTimeMillis()) {
			sendMessage("You can't logout until 10 seconds after the end of combat.");
			return this;
		}
		player.write(new PacketBuilder(167).toPacket());
		return this;
	}
	
	/**
	 * Sends a packet to update a group of items.
	 * @param interfaceId The interface id.
	 * @param items The items.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendUpdateItems(int interfaceId, int childId, int type,
			Item[] items) {
		PacketBuilder bldr = new PacketBuilder(92, Type.VARIABLE_SHORT);
		bldr.putInt(interfaceId << 16 | childId);
		bldr.putShort(type);
		bldr.putShort(items.length);
		for (Item item : items) {
			if (item != null) {
				int count = item.getCount();
				if (count > 254) {
					bldr.putByteC((byte) 255);
					bldr.putInt(count);
				} else {
					bldr.putByteC((byte) count);
				}
				bldr.putLEShort(item.getId() + 1);
			} else {
				bldr.putByteC((byte) 0);
				bldr.putLEShort(0);
			}
		}
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends a packet to update a single item.
	 * @param interfaceId The interface id.
	 * @param slot The slot.
	 * @param item The item.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendUpdateItem(int interfaceId, int childId, int type, int slot, Item item) {
		PacketBuilder bldr = new PacketBuilder(120, Type.VARIABLE_SHORT);
		bldr.putInt(interfaceId << 16 | childId);
		bldr.putShort(type);
		bldr.putSmart(slot);
		if (item != null) {
			bldr.putShort(item.getId() + 1);
			int count = item.getCount();
			if (count > 254) {
				bldr.put((byte) 255);
				bldr.putInt(count);
			} else {
				bldr.put((byte) count);
			}
		} else {
			bldr.putShort(0);
			bldr.put((byte) -1);
		}
		player.write(bldr.toPacket());
		return this;
	}
	
	/**
	 * Sends a packet to update multiple (but not all) items.
	 * @param interfaceId The interface id.
	 * @param slots The slots.
	 * @param items The item array.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendUpdateItems(int interfaceId, int childId, int type, int[] slots, Item[] items) {
		PacketBuilder bldr = new PacketBuilder(120, Type.VARIABLE_SHORT);
		bldr.putInt(interfaceId << 16 | childId);
		bldr.putShort(type);
		for (int slot : slots) {
			Item item = items[slot];
			bldr.putSmart(slot);
			if (item != null) {
				bldr.putShort(item.getId() + 1);
				int count = item.getCount();
				if (count > 254) {
					bldr.put((byte) 255);
					bldr.putInt(count);
				} else {
					bldr.put((byte) count);
				}
			} else {
				bldr.putShort(0);
				bldr.put((byte) -1);
			}
		}
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends the enter amount interface.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendEnterAmountInterface() {
		player.getActionSender().sendRunScript(Constants.NUMERICAL_INPUT_INTERFACE, new Object[] { "Enter amount:" }, "s");
		return this;
	}

	/**
	 * Sends the enter amount interface.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendEnterTextInterface(String question) {
		player.getActionSender().sendRunScript(Constants.ALPHA_NUMERICAL_INPUT_INTERFACE, new Object[] { question }, "s");
		return this;
	}
	
	/**
	 * Sends the player an option.
	 * @param slot The slot to place the option in the menu.
	 * @param top Flag which indicates the item should be placed at the top.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInteractionOption(String option, int slot, boolean top) {
		PacketBuilder bldr = new PacketBuilder(72, Type.VARIABLE);
		bldr.putRS2String(option);
		bldr.putByteS((byte) slot);
		bldr.putByteC(top ? (byte) 1 : (byte) 0);
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends a string.
	 * @param id The interface id.
	 * @param string The string.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendString(int interfaceId, int child, String string) {
		PacketBuilder bldr = new PacketBuilder(47, Type.VARIABLE_SHORT);
		bldr.putInt1(interfaceId << 16 | child);
		bldr.putRS2String(string);
		player.write(bldr.toPacket());
		return this;
	}
	
	/**
	 * Sends a model in an interface.
	 * @param id The interface id.
	 * @param zoom The zoom.
	 * @param model The model id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfaceModel(int id, int zoom, int model) {
		PacketBuilder bldr = new PacketBuilder(246);
		bldr.putLEShort(id).putShort(zoom).putShort(model);
		////player.write(bldr.toPacket());
		return this;
	}
	
	/**
	 * Sends a specific skill.
	 * @param skill The skill to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendPing() {
		PacketBuilder bldr = new PacketBuilder(238, Type.VARIABLE_SHORT);
		bldr.putInt(pingCount++ > 0xF42400 ? pingCount = 1 : pingCount);
		//player.write(bldr.toPacket());
		return this;
	}
	
	/**
	 * Sets a client configuration.
	 * @param id The id.
	 * @param value The value.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendConfig(int id, int value){
		if(value < 128) {
			PacketBuilder bldr = new PacketBuilder(245);
			bldr.putShortA(id);
			bldr.put((byte) value);
			player.getSession().write(bldr.toPacket());
		} else {
			PacketBuilder bldr = new PacketBuilder(37);
			bldr.putShortA(id);
			bldr.putLEInt(value);
			player.getSession().write(bldr.toPacket());
		}
		return this;
	}
	
	/**
	 * Sets a config on an interface.
	 * @param interfaceId The interface id.
	 * @param childId The child id.
	 * @param hidden The hidden flag.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfaceConfig(int interfaceId, int childId, boolean hidden) {
		PacketBuilder bldr = new PacketBuilder(142);
		bldr.putByteS((byte) (hidden ? 1 : 0));
		bldr.putShort(childId);
		bldr.putShort(interfaceId);
		player.write(bldr.toPacket());
		return this;
	}
	
	/**
	 * Updates the player's running state.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateRunningConfig() {
		sendConfig(173, player.getWalkingQueue().isRunningToggled() ? 1 : 0);
		sendInterfaceConfig(261, 51, !player.getWalkingQueue().isRunningToggled());
		sendInterfaceConfig(261, 52, player.getWalkingQueue().isRunningToggled());
		return this;
	}

	/**
	 * Sends the player's energy.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendRunEnergy() {
		player.write(new PacketBuilder(163).put((byte) player.getWalkingQueue().getEnergy()).toPacket());
		return this;
	}
	
	/**
	 * Sends the player's brightness setting.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateBrightnessConfig() {
		return sendConfig(166, player.getSettings().getBrightnessSetting());
	}
	
	/**
	 * Sends the player's split private chat flag.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateSplitPrivateChatConfig() {
		sendRunScript(83, new Object[0], "");
		sendConfig(287, player.getSettings().splitPrivateChat() ? 1 : 0);
		sendInterfaceConfig(261, 55, !player.getSettings().splitPrivateChat());
		sendInterfaceConfig(261, 56, player.getSettings().splitPrivateChat());
		return this;
	}
	
	/**
	 * Sends the player's accept aid flag.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateAcceptAidConfig() {
		sendConfig(427, player.getSettings().isAcceptingAid() ? 1 : 0);
		sendInterfaceConfig(261, 59, !player.getSettings().isAcceptingAid());
		sendInterfaceConfig(261, 60, player.getSettings().isAcceptingAid());
		return this;
	}
	
	/**
	 * Sends the player's two mouse buttons flag.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateMouseButtonsConfig() {
		sendConfig(170, player.getSettings().twoMouseButtons() ? 0 : 1);
		sendInterfaceConfig(261, 57, !player.getSettings().twoMouseButtons());
		sendInterfaceConfig(261, 58, player.getSettings().twoMouseButtons());
		return this;
	}
	
	/**
	 * Sends the player's special bar configurations.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateSpecialConfig() {
		sendConfig(300, player.getCombatState().getSpecialEnergy() * 10);
		sendConfig(301, player.getCombatState().isSpecialOn() ? 1 : 0);
		return this;
	}
	
	/**
	 * Sends the player's two mouse buttons flag.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateChatEffectsConfig() {
		sendConfig(171, player.getSettings().chatEffects() ? 0 : 1);
		sendInterfaceConfig(261, 53, !player.getSettings().chatEffects());
		sendInterfaceConfig(261, 54, player.getSettings().chatEffects());
		return this;
	}
	
	/**
	 * Sends the player's banking configs.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateBankConfig() {
		sendConfig(304, player.getSettings().isSwapping() ? 0 : 1);
		sendConfig(115, player.getSettings().isWithdrawingAsNotes() ? 1 : 0);
		return this;
	}
	
	/**
	 * Sends the player's auto retaliate config.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateAutoRetaliateConfig() {
		return sendConfig(172, player.getSettings().isAutoRetaliating() ? 0 : 1);
	}

	/**
	 * Sends your location to the client.
	 * @param location The location.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendArea(Location location, int xOffset, int yOffset) {
		PacketBuilder bldr = new PacketBuilder(132);
		int regionX = player.getLastKnownRegion().getRegionX(), regionY = player.getLastKnownRegion().getRegionY();
		bldr.put((byte) ((location.getY() - ((regionY-6) * 8)) + yOffset));
		bldr.put((byte) ((location.getX() - ((regionX-6) * 8)) + xOffset));
		player.write(bldr.toPacket());
		return this;
	}

	public ActionSender compassId(int id) {
		PacketBuilder bldr = new PacketBuilder(143);
		bldr.put((byte) id);
		player.getSession().write(bldr.toPacket());
		return this;
	}
	public ActionSender multiId(int id) {//still looking 4 if it still uses packet
		PacketBuilder bldr = new PacketBuilder(10);
		bldr.putShort((byte) id);
		player.getSession().write(bldr.toPacket());
		return this;
	}
	/**
	 * Shows an item on the ground.
	 * @param item The ground item.
	 * @return The action sender instance, for chaining.
	 */		
	public ActionSender sendGroundItem(GroundItem item) {
		if(item == null || item.getItem().getId() < 1 || item.getLocation().getX() < 1 || item.getLocation().getY() < 1 || item.getItem().getCount() < 1) {
			return this;
		}
		sendArea(item.getLocation(), 0, 0);
		PacketBuilder bldr = new PacketBuilder(112);
		bldr.putShort(item.getItem().getId());
		bldr.putLEShort(item.getItem().getCount());
		bldr.putByteS((byte) 0);
		player.getSession().write(bldr.toPacket());
		return this;
	}
	
	/**
	 * Shows an item on the ground.
	 * @param item The ground item.
	 * @return The action sender instance, for chaining.
	 */		
	public ActionSender removeGroundItem(GroundItem item) {
		if(item == null || item.getItem().getId() < 1 || item.getLocation().getX() < 1 || item.getLocation().getY() < 1) {
			return this;
		}
		sendArea(item.getLocation(), 0, 0);
		PacketBuilder bldr = new PacketBuilder(39);
		bldr.putShortA(item.getItem().getId());
		bldr.putByteS((byte) 0);
		player.getSession().write(bldr.toPacket());
		return this;
	}
	
	/**
	 * Sends all the ground items in a players area.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendGroundItemsInArea() {
		// TODO check distance!!
		for(Region r : World.getWorld().getRegionManager().getSurroundingRegions(player.getLocation())) {
			for(GroundItem item : r.getGroundItems()) {
				if(item.isOwnedBy(player.getName())) {
					sendGroundItem(item);
				}
			}
		}
		return this;
	}
	
	/**
	 * Sends all the game objects in a players area.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendGameObjectsInArea() {
		// TODO check distance!!
		Region[] regions = World.getWorld().getRegionManager().getSurroundingRegions(player.getLocation());
		for(Region r : regions) {
			for(GameObject obj : r.getGameObjects()) {
				if(!obj.isLoadedInLandscape()) {
					sendObject(obj);
				}
			}
		}
		return this;
	}

	/**
	 * Sends the players bonuses to the client.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendBonuses() {		
		int id = 108;
		for(int index = 0; index < player.getCombatState().getBonuses().length; index++) {
			if(id == 118) {
				id++;
			}
			String bonus = (Constants.BONUSES[index] + ": " + (player.getCombatState().getBonus(index) >= 0 ? "+" : "") + player.getCombatState().getBonus(index));
			if(index == 10) {
				//bonus += "" + (player.getCombatState().getBonus(12) >= 0) + player.getCombatState().getBonus(12);
			}
			sendString(Equipment.SCREEN, id++, bonus);
			if(index == 11) {
				break;
			}
		}
		return this;
	}

	/**
	 * Removes the sidebars temporarily.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeSideBars() {		
		return sendInterface(Constants.MAIN_WINDOW, 97, 149, false);
	}

	/**
	 * Removes the chatbox interface.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeChatboxInterface() {
		player.getSession().write(new PacketBuilder(137).putInt(Constants.MAIN_WINDOW << 16 | Constants.CHAT_BOX).toPacket()); //chat box screen
		player.getActionSender().sendRunScript(Constants.REMOVE_INPUT_INTERFACE, new Object[] { "" }, "");
		return this;
	}

	public ActionSender sendStopCinematics() {
		player.getSession().write(new PacketBuilder(99).toPacket());
		return this;

	}
	
	public ActionSender turnCameraLocation(int localX, int localY, int height, int constantSpeed, int variableSpeed) {
		PacketBuilder bldr = new PacketBuilder(82);
		bldr.put((byte)localX);
		bldr.put((byte)localY);
		bldr.putShort(height);
		bldr.put((byte)constantSpeed);
		bldr.put((byte)variableSpeed);
		return this;
	}
	
	public ActionSender moveCameraLocation(int localX, int localY, int height, int constantSpeed, int variableSpeed) {
		PacketBuilder bldr = new PacketBuilder(113);
		bldr.put((byte)localX);
		bldr.put((byte)localY);
		bldr.putShort(height);
		bldr.put((byte)constantSpeed);
		bldr.put((byte)variableSpeed);
		return this;
	}
	
	/**
	 * Removes the side tab interface.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeSidetabInterface() {		
		player.getSession().write(new PacketBuilder(137).putInt(Constants.MAIN_WINDOW << 16 | Constants.SIDE_TABS).toPacket()); //tabs screen
		return this;
	}

	/**
	 * Removes the side tab interface.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeInterface() {	
		if(player.getInterfaceState().isWalkableInterface()) {
			return this;
		}
		player.getInterfaceState().interfaceClosed();
		player.getSession().write(new PacketBuilder(137).putInt(Constants.MAIN_WINDOW << 16 | Constants.GAME_WINDOW).toPacket()); //main game screen
		return this;
	}
	
	/**
	 * Removes an open interface.
	 * @param id The interface id.
	 * @param child The interface child id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeAllInterfaces() {
		sendInterfacesRemovedClientSide();
		removeInterface();
		removeSidetabInterface();
		removeChatboxInterface();
		return this;
	}
	
	/**
	 * Removes an open interface.
	 * @param id The interface id.
	 * @param child The interface child id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeInterfaces(int id, int child) {
		if (id == Constants.MAIN_WINDOW && child == Constants.GAME_WINDOW) {
			sendInterfacesRemovedClientSide();
		}
		player.getSession().write(new PacketBuilder(137).putInt(id << 16 | child).toPacket());
		return this;
	}
	
	/**
	 * Sends a clientscript to the client.
	 * @param id The id.
	 * @param params Any parameters in the scrips.
	 * @param types The script types
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendRunScript(int id, Object[] params, String types) {
		PacketBuilder bldr = new PacketBuilder(69, Type.VARIABLE_SHORT);
		bldr.putRS2String(types);
		if(params.length > 0) {
			int j = 0;
			for (int i = types.length() - 1; i >= 0; i--, j++) {
				if (types.charAt(i) == 115) {
					bldr.putRS2String((String) params[j]);
				} else {
					bldr.putInt((Integer) params[j]);
				}
			}
		}
		bldr.putInt(id);
		player.write(bldr.toPacket());
		return this;
	}
	
	public void sendRunScript(int scriptId, Object[] params) {
		PacketBuilder bldr = new PacketBuilder(69, Type.VARIABLE_SHORT);
		String parameterTypes = "";
		for(int count = params.length-1; count >= 0; count--) {
			if(params[count] instanceof String)
				parameterTypes += "s"; //string
			else
				parameterTypes += "i"; //integer
		}
		bldr.putRS2String(parameterTypes);
		int index = 0;
		for (int count = parameterTypes.length() - 1;count >= 0;count--) {
			if (parameterTypes.charAt(count) == 's') 
				bldr.putRS2String((String) params[index++]);
			else
				bldr.putInt((Integer) params[index++]);
		}
		bldr.putInt(scriptId);
		player.write(bldr.toPacket());
	}
	
	public void sendInterSetItemsOptionsScript(int inter, int childId, int setItemsType, int setItemsPosition, int setItemsPosition2, String[] options) {
		Object[] parameters = new Object[6+options.length];
		int index = 0;

		for(int count = options.length-1; count >= 0; count--) {
			System.out.println(options[count]);
			parameters[index++] = options[count];
		}
		parameters[index++] = -1; //dunno but always this
		parameters[index++] = 0;//dunno but always this
		parameters[index++] = setItemsPosition2; //setitems position
		parameters[index++] = setItemsPosition; //setitems position
		parameters[index++] = setItemsType;
		parameters[index++] = inter << 16 | childId;
		sendRunScript(150, parameters);
	}
	
	public final static Object[] TRADE_PARAMETERS_2 = new Object[] {
		"", "", "", "", "Remove-X", "Remove-All", "Remove-10", "Remove-5", "Remove",
		-1, 0, 7, 4, 81, 21954608 };
	
	/**
	 * Sends an access mask to the client.
	 * @param set The set.
	 * @param window The window
	 * @param interfaceId The interface id.
	 * @param offset The offset.
	 * @param length The length.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendAccessMask(int set, int window, int interfaceId, int offset, int length) {
		PacketBuilder bldr = new PacketBuilder(254);	
		bldr.putInt(interfaceId << 16 | window);
		bldr.putLEShort(length);
		bldr.putInt2(set);
		bldr.putShortA(offset);
		player.write(bldr.toPacket());
		return this;
	}
	
	/**
	 * Interfaces are removed clientside (do not send any data to the client or
	 * this will cause a bug with opening interfaces such as Report Abuse).
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfacesRemovedClientSide() {
		if(player.getRequestManager().getAcquaintance() != null) {
			if(player.getRequestManager().getState() != RequestState.COMMENCING
				&& player.getRequestManager().getAcquaintance().getRequestManager().getState() != RequestState.COMMENCING
				&& player.getRequestManager().getState() != RequestState.ACTIVE
				&& player.getRequestManager().getAcquaintance().getRequestManager().getState() != RequestState.ACTIVE
				&& player.getRequestManager().getState() != RequestState.FINISHED
				&& player.getRequestManager().getAcquaintance().getRequestManager().getState() != RequestState.FINISHED) {
				player.getRequestManager().cancelRequest();
			} else if(player.getRequestManager().getState() == RequestState.FINISHED) {
			}
		}
		if(player.getInterfaceState().isWalkableInterface()) {
			return this;
		}
		sendAreaInterface(null, player.getLocation());
		return this;
	}
	
	/**
	 * Creates a game object on the players screen.
	 * @param obj The object to create.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendObject(GameObject obj) {
		if(obj.getId() == -1) {
			return removeObject(obj);
		}
		if(player.getLocation().getZ() != obj.getLocation().getZ()) {
			return this;
		}
		sendArea(obj.getLocation(), 0, 0);
		PacketBuilder spb = new PacketBuilder(17);
		int ot = ((obj.getType() << 2) + (obj.getDirection() & 3));
		spb.putByteA((byte) 0);
		spb.putLEShort(obj.getId());
		spb.putByteA((byte) ot);
		player.write(spb.toPacket());
		return this;
	}

	/**
	 * Removes a game object on a players screen.
	 * @param obj The object to remove.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeObject(GameObject obj) {
		if(player.getLocation().getZ() != obj.getLocation().getZ()) {
			return this;
		}
		sendArea(obj.getLocation(), 0, 0);
		PacketBuilder spb = new PacketBuilder(16);
		int ot = ((obj.getType() << 2) + (obj.getDirection() & 3));
		spb.putByteA((byte) ot);
		spb.putByteA((byte) 0);
		player.write(spb.toPacket());
		return this;
	}

	public ActionSender sendResetFlag() {
		player.getSession().write(new PacketBuilder(68).toPacket());
		return this;
	}
	public ActionSender sendSystemUpdate(int time) {
		player.getSession().write(new PacketBuilder(30).putShortA(time).toPacket());
		return this;
	}
	
	/**
	 * Animates an object.
	 * @param obj The object.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender animateObject(GameObject obj, int animationId) {
		if(player.getLocation().getZ() != obj.getLocation().getZ()) {
			return this;
		}
		sendArea(obj.getLocation(), 0, 0);
		PacketBuilder spb = new PacketBuilder(140);
		int ot = ((obj.getType() << 2) + (obj.getDirection() & 3));
		spb.putLEShortA(animationId);
		spb.put((byte) 0);
		spb.put((byte) ot);
		player.write(spb.toPacket());
		return this;
	}
	
	public ActionSender sendAnimateObject(Location location, int objectId, int objectType, int objectRotation) {
		sendArea(location, 0, 0);
		PacketBuilder bldr = new PacketBuilder(140);
		bldr.putLEShortA(objectId);
		bldr.put((byte) 0);
		bldr.put((byte) (objectType << 2 | objectRotation & 0x3));
		player.write(bldr.toPacket());
		return this;
	}
	/**
	 * Sends an animation of an interface.
	 * @param emoteId The emote id.
	 * @param interfaceId The interface id.
	 * @param childId The child id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfaceAnimation(int emoteId, int interfaceId, int childId) {
		player.getSession().write(new PacketBuilder(63).putInt2(interfaceId <<  16 | childId).putLEShort(emoteId).toPacket());
		return this;
	}

	/**
	 * Sends the player's head onto an interface.
	 * @param interfaceId The interface id.
	 * @param childId The child id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendPlayerHead(int interfaceId, int childId) {
		player.getSession().write(new PacketBuilder(8).putLEInt(interfaceId << 16 | childId).toPacket());
		return this;
	}

	/**
	 * Sends an NPC's head onto an interface.
	 * @param npcId The NPC's id.
	 * @param interfaceId The interface id.
	 * @param childId The child id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendNPCHead(int npcId, int interfaceId, int childId) {
		player.getSession().write(new PacketBuilder(207).putLEShortA(npcId).putInt(interfaceId << 16 | childId).toPacket());
		return this;
	}
	
	public enum DialogueType {
		NPC,
		PLAYER,
		OPTION,
		MESSAGE,
		MESSAGE_MODEL_LEFT,
		AGILITY_LEVEL_UP,
		ATTACK_LEVEL_UP,
		COOKING_LEVEL_UP,
		CRAFTING_LEVEL_UP,
		DEFENCE_LEVEL_UP,
		FARMING_LEVEL_UP,
		FIREMAKING_LEVEL_UP,
		FISHING_LEVEL_UP,
		FLETCHING_LEVEL_UP,
		HERBLORE_LEVEL_UP,
		HITPOINT_LEVEL_UP,
		MAGIC_LEVEL_UP,
		MINING_LEVEL_UP,
		PRAYER_LEVEL_UP,
		RANGING_LEVEL_UP,
		RUNECRAFTING_LEVEL_UP,
		SLAYER_LEVEL_UP,
		SMITHING_LEVEL_UP,
		STRENGTH_LEVEL_UP,
		THIEVING_LEVEL_UP,
		WOODCUTTING_LEVEL_UP
	}
	
	public ActionSender sendDialogue(String title, DialogueType dialogueType, int entityId, DialogueExpression animation, String... text) {
		int interfaceId = -1;
		switch(dialogueType) {
		case NPC:
			if(text.length > 4 || text.length < 1) {
				return this;
			}
			interfaceId = text.length + 240;
			if(interfaceId <= 240) {
				interfaceId = 241;
			}
			sendNPCHead(entityId, interfaceId, 0);
			sendInterfaceAnimation(animation.getId(), interfaceId, 0);
			sendString(interfaceId, 1, title);
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 2 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case PLAYER:
			if(text.length > 4 || text.length < 1) {
				return this;
			}
			interfaceId = text.length + 63;
			if(interfaceId <= 63) {
				interfaceId = 64;
			}
			sendPlayerHead(interfaceId, 0);
			sendInterfaceAnimation(animation.getId(), interfaceId, 0);
			sendString(interfaceId, 1, title);
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 2 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case OPTION:
			if(text.length > 5 || text.length < 2) {
				return this;
			}
			interfaceId = 224 + (text.length * 2);
			sendString(interfaceId, 0, title);
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 1 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case MESSAGE:
			interfaceId = 209 + text.length;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case MESSAGE_MODEL_LEFT:
			interfaceId = 519;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 1 + i, text[i]);
			}
			player.getActionSender().sendInterfaceModel(519, 0, 130, entityId);
			sendChatboxInterface(interfaceId);
			break;
		case AGILITY_LEVEL_UP:
			interfaceId = 157;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case ATTACK_LEVEL_UP:
			interfaceId = 158;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case COOKING_LEVEL_UP:
			interfaceId = 159;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case CRAFTING_LEVEL_UP:
			interfaceId = 160;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case DEFENCE_LEVEL_UP:
			interfaceId = 161;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case FARMING_LEVEL_UP:
			interfaceId = 162;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case FIREMAKING_LEVEL_UP:
			interfaceId = 163;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case FISHING_LEVEL_UP:
			interfaceId = 164;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case FLETCHING_LEVEL_UP:
			interfaceId = 165;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case HERBLORE_LEVEL_UP:
			interfaceId = 166;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case HITPOINT_LEVEL_UP:
			interfaceId = 167;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case MAGIC_LEVEL_UP:
			interfaceId = 168;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case MINING_LEVEL_UP:
			interfaceId = 169;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case PRAYER_LEVEL_UP:
			interfaceId = 170;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case RANGING_LEVEL_UP:
			interfaceId = 171;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case RUNECRAFTING_LEVEL_UP:
			interfaceId = 172;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case SLAYER_LEVEL_UP:
			interfaceId = 173;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case SMITHING_LEVEL_UP:
			interfaceId = 174;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case STRENGTH_LEVEL_UP:
			interfaceId = 175;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case THIEVING_LEVEL_UP:
			interfaceId = 176;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case WOODCUTTING_LEVEL_UP:
			interfaceId = 177;
			for(int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		}
		return this;
	}
	
	/**
	 * Sends a projectile to a location.
	 * @param start The starting location.
	 * @param finish The finishing location.
	 * @param id The graphic id.
	 * @param delay The delay before showing the projectile.
	 * @param angle The angle the projectile is coming from.
	 * @param speed The speed the projectile travels at.
	 * @param startHeight The starting height of the projectile.
	 * @param endHeight The ending height of the projectile.
	 * @param lockon The lockon index of the projectile, so it follows them if they move.
	 * @param slope The slope at which the projectile moves.
	 * @param radius The radius from the centre of the tile to display the projectile from.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendProjectile(Location start, Location finish, int id,
			int delay, int angle, int speed, int startHeight, int endHeight,
			int lockon, int slope, int radius) {
				
		int offsetX = (start.getX() - finish.getX()) * -1;
		int offsetY = (start.getY() - finish.getY()) * -1;
		sendArea(start, -3, -2);
		
		PacketBuilder bldr = new PacketBuilder(218);
		bldr.put((byte) angle);
		bldr.put((byte) offsetX);
		bldr.put((byte) offsetY);
		bldr.putShort(lockon);
		bldr.putShort(id);
		bldr.put((byte) startHeight);
		bldr.put((byte) endHeight);
		bldr.putShort(delay);
		bldr.putShort(speed);
		bldr.put((byte) slope);
		bldr.put((byte) radius);
		player.getSession().write(bldr.toPacket());	
		return this;
	}
	
	/**
	 * Sends the hint arrow ontop of an entity
	 * @param entity The entity.
	 * @param height The height of the arrow.
	 * @param position The position on the tile of the arrow (2 = middle, 3 = west, 4 = east, 5 = south, 6 = north).
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendHintArrow(Entity entity, int height, int position) {		
		PacketBuilder bldr = new PacketBuilder(160);
		if(entity.isNPC() || entity.isPlayer()) {
			bldr.put((byte) (entity.isNPC() ? 1 : 10));
			bldr.putShort(entity.getClientIndex());
			bldr.put((byte) 0);
			bldr.put((byte) 0);
			bldr.put((byte) 0);
		} else if(entity.isObject()) {
			bldr.put((byte) position);
			bldr.putShort(entity.getLocation().getX());
			bldr.putShort(entity.getLocation().getY());
			bldr.put((byte) height);
		}
		player.getSession().write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends a sound to the client.
	 * @param sound The sound to play.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender playSound(Sound sound) {		
		PacketBuilder bldr = new PacketBuilder(40);
		bldr.putShort(sound.getId()).put(sound.getVolume()).putShort(sound.getDelay());
		player.getSession().write(bldr.toPacket());
		return this;
	}
	

	/**
	 * Sends to the client that this player has sent a private message.
	 * @param nameAsLong The recepient's name, as a long.
	 * @param unpacked The unpacked message.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSentPrivateMessage(long nameAsLong, byte[] message) {
		player.write(new PacketBuilder(23, Type.VARIABLE).putLong(nameAsLong).put(message).toPacket());
		return this;
	}

	/**
	 * Sends to the client that this player has receives a private message.
	 * @param nameAsLong The senders name, as a long.
	 * @param rights The rank the sender has.
	 * @param message The unpacked message.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendRecievedPrivateMessage(long nameAsLong, int rights, byte[] message) {
		int messageCounter = player.getPrivateChat().getLastMessageIndex();
		/*
		 * If i want pming cc packet 89 :hurr:
		 */
		player.write(new PacketBuilder(50, Type.VARIABLE).putLong(nameAsLong).putShort(1).put(new byte[] { (byte) ((messageCounter << 16) & 0xFF), (byte) ((messageCounter << 8) & 0xFF), (byte) (messageCounter & 0xFF) }).put((byte) rights).put(message).toPacket());
		//player.write(new PacketBuilder(89, Type.VARIABLE).putLong(nameAsLong).putShort(1).put(new byte[] { (byte) ((messageCounter << 16) & 0xFF), (byte) ((messageCounter << 8) & 0xFF), (byte) (messageCounter & 0xFF) }).put((byte) rights).put(message).toPacket());
		return this;
	}

	/**
	 * Sends to the client that a player is logged in on a world.
	 * @param name The player's name, as a long.
	 * @param world The world they are on.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendFriend(long nameAsLong, int world, int clanRank) {
		player.write(new PacketBuilder(100).putLong(nameAsLong).putShort(world).put((byte) 0).toPacket());
		return this;
	}

	/**
	 * Sends to the client what state the player's friend list loading is at.
	 * @param status Loading = 0 Connecting = 1 OK = 2
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendFriendServer(int status) {
		player.write(new PacketBuilder(152).put((byte) status).toPacket());
		return this;
	}
	
	/**
	 * Sends all of the player's ignore list.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendFriends() {
		for(long l : player.getPrivateChat().getFriends().keySet()) {
			if(World.getWorld().getPlayerNames().get(l) != null) {
				Player p = World.getWorld().getPlayerNames().get(l);
				if ((p.getInterfaceState().getPrivateChat() == 0 || p.getInterfaceState().getPrivateChat() == 1 && p.getPrivateChat().getFriends().containsKey(player.getNameAsLong())) && !p.getPrivateChat().getIgnores().contains(player.getNameAsLong())) {
					sendFriend(p.getNameAsLong(), 1, player.getPrivateChat().getFriends().get(p.getNameAsLong()).getId());
				}
				if(p.getPrivateChat().getFriends().containsKey(player.getNameAsLong())
								&& player.getInterfaceState().getPrivateChat() == 1) { //if we've added them, but we had private set to 'friends', we need to tell them we are now online because we are friends with them
					p.getActionSender().sendFriend(player.getNameAsLong(), 1, p.getPrivateChat().getFriends().get(player.getNameAsLong()).getId());
				}
				sendFriend(l, 1, player.getPrivateChat().getFriends().get(l).getId());
			} else {
				sendFriend(l, 0, player.getPrivateChat().getFriends().get(l).getId());				
			}
		}
		return this;		
	}
	
	/**
	 * Sends all of the player's ignore list.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendIgnores() {
		for(long l : player.getPrivateChat().getIgnores()) {
			sendIgnore(l);
		}
		return this;		
	}
	
	/**
	 * Sends to the client an ignored player's name.
	 * @param nameAsLong The player's name, as a long.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendIgnore(long nameAsLong) {
		player.write(new PacketBuilder(75, Type.VARIABLE_SHORT).putLong(nameAsLong).toPacket());
		return this;		
	}
	
	public ActionSender sendAreaInterface(Location before, Location after) {
		boolean afterInPvP = BoundaryManager.isWithinBoundaryNoZ(after, "PvP Zone");
		boolean beforeInPvP = before != null ? BoundaryManager.isWithinBoundaryNoZ(before, "PvP Zone") : false;
		boolean afterInFightPits = BoundaryManager.isWithinBoundaryNoZ(after, "Fight Pits");
		
		if(afterInPvP) {
			if(!beforeInPvP) {
				if(afterInFightPits) {
					sendWalkableInterface(373);
				} else {
					sendWalkableInterface(380);
				}
				sendInteractionOption("Attack", 1, true);	
				sendInteractionOption("null", 2, false);				
			}
		} else if(BoundaryManager.isWithinBoundaryNoZ(after, "God Wars Entrance")) {
			sendWalkableInterface(481); //snow			
		} else if(beforeInPvP || before != null && BoundaryManager.isWithinBoundaryNoZ(before, "God Wars Entrance")) {
			sendWalkableInterface(-1);
			sendInteractionOption("null", 1, true);	
			sendInteractionOption("null", 2, false);						
		}
		if(BoundaryManager.isWithinBoundaryNoZ(after, "MultiCombat")) {
			//compassId(1);
			sendInterfaceConfig(Constants.MAIN_WINDOW, 78, false);
			//sendMessage("not in zone");
		} else {
			//compassId(0);
			sendInterfaceConfig(Constants.MAIN_WINDOW, 78, true);
			//sendMessage("In zone");
		}
		return this;
	}
	
	/**
	 * Resets the players following target.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendResetFollowing() {
		//player.write(new PacketBuilder(1).toPacket());
		return this;
	}
	
	/**
	 * Starts the player following a mob.
	 * @param mob The mob to follow.
	 * @param distance The distance to stop moving.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendFollowing(Mob mob, int distance) {
		PacketBuilder pb = new PacketBuilder(0);
		pb.putShort(mob.getIndex());
		pb.put((byte) (mob instanceof Player ? 0 : 1));
		pb.putShort(distance); //follow distance, set to 8 for range etc.
		//player.write(pb.toPacket());
		player.setInteractingEntity(InteractionMode.REQUEST, mob);
		return this;
	}

	public ActionSender sendComponentPosition(int interfaceId, int childId, int x, int y) {
		player.write(new PacketBuilder(201).putLEShortA(x).putLEShortA(y).putInt2(interfaceId << 16 | childId).toPacket());
		return this;
	}

	
	
}
