package org.hyperion.rs2.model.quest;

import java.util.HashMap;

import org.hyperion.rs2.model.Player;

/**
 * The home of the quests.
 * @author jamix77
 *
 */
public class QuestRepository {
	
	/**
	 * The static quests map.
	 */
	private static final HashMap<String,Quest> STATIC_QUESTS_MAP = new HashMap<String,Quest>();
	
	/**
	 * The instanced quests map.
	 */
	private final HashMap<String,Quest> QUESTS_MAP = new HashMap<String,Quest>();
	
	/**
	 * The player.
	 */
	private final Player player;
	
	/**
	 * The amount of quest points the player has.
	 */
	private int points;

	/**
	 * Constructor.
	 * @param player
	 * @throws Throwable 
	 */
	public QuestRepository(final Player player) {
		this.player = player;
		for (Quest q : STATIC_QUESTS_MAP.values()) {
			q.add(player,this);
		}
	}
	
	/**
	 * Gets the quest from the map
	 * @param name
	 * @return
	 */
	public Quest get(String name) {
		return QUESTS_MAP.getOrDefault(name, null);
	}
	
	/**
	 * Gets the quest stage from the map
	 * @param name
	 * @return
	 */
	public int getStage(String name) {
		return QUESTS_MAP.get(name).getStage();
	}

	public static HashMap<String,Quest> getStaticQuestsMap() {
		return STATIC_QUESTS_MAP;
	}

	public HashMap<String,Quest> getQuestsMap() {
		return QUESTS_MAP;
	}

}
