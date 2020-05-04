package org.hyperion.rs2.model.dialogue;

import org.hyperion.rs2.model.Player;

/**
 * An interface for a unified DialogueType.
 * @author jamix77
 *
 */
public interface DialogueType {
	
	/**
	 * Handles the dialogue at each stage.
	 * @param player
	 * @param dialogue
	 */
	public void handle(Player player, Dialogue dialogue);

}
