package org.hyperion.plugin.impl;

import java.util.HashMap;

import org.hyperion.plugin.Plugin;
import org.hyperion.plugin.impl.ItemOnHandler.ItemOnType;
import org.hyperion.rs2.model.GameObject;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;

/**
 * the abstract handler for item on item interactions
 * @author jamix77
 *
 */
public abstract class ItemOnHandler implements Plugin<Object> {

	/**
	 * The handlers in a hashmap.
	 */
	private static final HashMap<String,ItemOnHandler> HANDLERS = new HashMap<>();
	
	/**
	 * Adds to the hashmap.
	 * @param type
	 * @param usedId
	 * @param usedWithId
	 * @param handler
	 */
	public static void add(ItemOnType type, int usedId, int usedWithId, ItemOnHandler handler) {
		getHandlers().put(type.getPrefix() + ":" + usedId + "-" + usedWithId,handler);
		if (type == ItemOnType.ITEM_ON_ITEM) {
			getHandlers().put(type.getPrefix() + ":" + usedWithId + "-" + usedId, handler);//reverse usage works with this.
		}
	}
	
	/**
	 * Handle the event.
	 * @param e
	 */
	public abstract void handle(ItemOnEvent e);
	
	public static HashMap<String,ItemOnHandler> getHandlers() {
		return HANDLERS;
	}

	/**
	 * The usage event.
	 * @author jamix77
	 *
	 */
	public static class ItemOnEvent {
		private final Item used;
		
		private final Object usedWith;
		
		private final Player player;
		
		public ItemOnEvent(Item used, Object usedWith, Player player) {
			this.used = used;
			this.usedWith = usedWith;
			this.player = player;
		}
		
		public ItemOnType type() {
			return (getUsedWith() instanceof Item ? ItemOnType.ITEM_ON_ITEM : (getUsedWith() instanceof GameObject ? ItemOnType.ITEM_ON_OBJECT : (getUsedWith() instanceof NPC ? ItemOnType.ITEM_ON_NPC : (getUsedWith() instanceof Player ? ItemOnType.ITEM_ON_PLAYER : ItemOnType.UNKNOWN))));
		}

		public Player getPlayer() {
			return player;
		}

		public Item getUsed() {
			return used;
		}

		public Object getUsedWith() {
			return usedWith;
		}
		
	}
	
	/**
	 * The types of item on events.
	 * @author jamix77
	 *
	 */
	public enum ItemOnType {
		
		ITEM_ON_ITEM("ioi"),
		ITEM_ON_OBJECT("ioo"),
		ITEM_ON_NPC("ion"),
		ITEM_ON_PLAYER("iop"),
		UNKNOWN("unknown");
		
		private final String prefix;
		
		private ItemOnType(String prefix) {
			this.prefix = prefix;
		}

		public String getPrefix() {
			return prefix;
		}
		
	}
}
