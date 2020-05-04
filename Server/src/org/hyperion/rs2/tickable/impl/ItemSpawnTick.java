package org.hyperion.rs2.tickable.impl;

import org.hyperion.rs2.model.GroundItem;
import org.hyperion.rs2.model.ItemSpawn;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.model.region.Region;
import org.hyperion.rs2.tickable.Tickable;

public class ItemSpawnTick extends Tickable {
	
	/**
	 * Creates the tickable to run every 60 seconds.
	 */
	public ItemSpawnTick() {
		super(100);
	}

	@Override
	public void execute() {
		for(ItemSpawn itemSpawn : ItemSpawn.getSpawns()) {
			Region r = itemSpawn.getGroundItem().getRegion();
			if(!r.getTile(itemSpawn.getLocation()).getGroundItems().contains(itemSpawn.getGroundItem())) {
				GroundItem newItem = new GroundItem("", itemSpawn.getItem(), itemSpawn.getLocation());
				World.getWorld().register(newItem, null);
				itemSpawn.setGroundItem(newItem);
			}
		}
	}

}
