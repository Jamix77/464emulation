package org.hyperion.rs2.model.quest;

import org.hyperion.plugin.Plugin;
import org.hyperion.rs2.model.Player;

/**
 * A quest
 * @author jamix77
 *
 */
public abstract class Quest implements Plugin<Player> {

	/**
	 * The name of the quest.
	 */
	private final String name;
	
	/**
	 * The player who is on the quest.
	 */
	private final Player player;
	
	/**
	 * The stage of completion
	 */
	private int stage = 0;
	
	/**
	 * Constructor
	 * @param name
	 * @param player
	 */
	public Quest(final String name,Player player) {
		this.name = name;
		this.player = player;
	}
	
	@Override
	/**
	 * Called on first initilisation
	 */
	public void init() throws Throwable {
		QuestRepository.getStaticQuestsMap().put(name, this);
	}
	
	/**
	 * Adds the quest to the instanced map.
	 * @param player
	 * @param qr
	 */
	public void add(Player player,QuestRepository qr) {
		qr.getQuestsMap().put(name,(Quest) newInstance(player));
	} 

	public String getName() {
		return name;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}


}
