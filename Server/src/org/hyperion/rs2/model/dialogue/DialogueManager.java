package org.hyperion.rs2.model.dialogue;

import org.hyperion.rs2.model.Player;

/**
 * A manager for the dialogue system.
 * @author jamix77
 *
 */
public class DialogueManager {

	/**
	 * The player who owns this manager.
	 */
	private final Player player;
	
	/**
	 * The current dialogue.
	 */
	private Dialogue currentDialogue;
	
	/**
	 * Constructs a new dialogue manager for the player.
	 * @param player
	 */
	public DialogueManager(Player player) {
		this.player = player;
	}
	
	/**
	 * Handles the dialogue, and checks if the dialogue is nulled.
	 */
	public void handle() {
		if (currentDialogue == null) {
			return;
		}
		currentDialogue.handle();
	}
	
	/**
	 * Starts a new dialogue!
	 * @param dialogue - the dialogue to start.
	 */
	public void start(Dialogue dialogue) {
		if (currentDialogue != null) {
			end();
		}
		this.currentDialogue = dialogue;
		handle();
	}
	
	/**
	 * Ends the dialogue.
	 */
	public void end() {
		player.getInterfaceState().interfaceClosed();
		currentDialogue = null;
	}

	
	public Dialogue getCurrentDialogue() {
		return currentDialogue;
	}

}
