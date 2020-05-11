package org.hyperion.plugin.impl;


import java.util.ArrayList;
import java.util.List;

import org.hyperion.cache.defs.ObjectDef;
import org.hyperion.plugin.Plugin;
import org.hyperion.plugin.PluginManager;
import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.ItemDefinition;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.NPCDefinition;
import org.hyperion.rs2.model.Player;

/**
 * Handles an interaction option.
 * @author jamix77
 */
public abstract class OptionHandler implements Plugin<Object> {

	/**
	 * Handles the interaction option.
	 * @param player The player who used the option.
	 * @param node The node the player selected an option on.
	 * @param option The option selected.
	 * @return {@code True} if successful.
	 */
	public abstract boolean handle(Player player, Object node, String option);

	public void npc(int npcId,String option) {
		NPCDefinition.forId(npcId).getHandlers().put("option:"+option, this);
	}
	
	public void object(int objId,String option) {
		ObjectDef.forId(objId).getHandlers().put("option:"+option, this);
	}
	
	public void item(int itemId, String option) {
		ItemDefinition.forId(itemId).getHandlers().put("option:"+option, this);
	}
	
	public void option(String option) {
		PluginManager.getOptionHandlerPlugins().put(option, this);
	}
	

	/**
	 * Gets the custom destination for the node.
	 * @param n The moving node.
	 * @param node The node to walk to.
	 * @return The custom destination, or {@code null} if we should use the
	 * default destination.
	 */
	public Location getDestination(Entity n, Entity node) {
		return null;
	}

	/**
	 * Gets the valid children for the wrapper id.
	 * @param wrapper the wrapper id.
	 * @return the valid children.
	 */
	public int[] getValidChildren(int wrapper) {
		final ObjectDef definition = ObjectDef.forId(wrapper);
		final List<Integer> list = new ArrayList<>();
		if (definition.childrenIDs == null) {
			System.err.println("Null child wrapper in option handler wrapperId=" + wrapper);
			return new int[] { wrapper };
		}
		for (int child : definition.childrenIDs) {
			if (child != -1 && !list.contains(child)) {
				list.add(child);
			}
		}
		int[] array = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
}