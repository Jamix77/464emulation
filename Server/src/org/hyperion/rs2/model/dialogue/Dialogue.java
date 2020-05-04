package org.hyperion.rs2.model.dialogue;

import java.util.HashMap;

import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.dialogue.types.DialogueEnd;
import org.hyperion.rs2.model.dialogue.types.DialogueMessage;
import org.hyperion.rs2.model.dialogue.types.DialogueSkip;

/**
 * A singular piece of dialogue.
 * @author jamix77
 *
 */
public abstract class Dialogue {
	
	/**
	 * The player who currently owns this dialogue.
	 */
	private final Player player;
	
	/**
	 * The index of the dialogue chain.
	 */
	private int index = 0;
	
	/**
	 * If a button was clicked in the options menu, this would change.
	 */
	private int button = -1;

	/**
	 * A hashmap of the singular dialogues, eg. each menu, such as a singular page of text for a player.
	 */
	protected final HashMap<Integer,DialogueType> dialogues = new HashMap<Integer,DialogueType>();
	
	/**
	 * generate the dialogue for the hashmap.
	 */
	public abstract void generate();
	
	/**
	 * Get a new instance of this dialogue.
	 * @return
	 */
	public abstract Dialogue newInstance(Player player);
	
	/**
	 * Constructor.
	 * @param player
	 */
	public Dialogue(Player player) {
		this.player = player;
		generate();//generate the dialogue here, after the player is set.
	}
	
	/**
	 * Handles the dialogue.
	 */
	public void handle() {
		if (dialogues.get(getIndex()) == null) {
			player.getActionSender().sendMessage("Error in dialogue! Null! Index: " + getIndex());

			return;
		}
		dialogues.get(getIndex()).handle(player,this);
		button = -1;
		
	}
	
	/**
	 * Increment the index count by 1.
	 */
	public Dialogue increment() {
		index++;
		return this;
	}
	
	/**
	 * Skip to a certain index.
	 * @param index
	 * @return
	 */
	public Dialogue skipTo(int index) {
		this.index = index;
		return this;
	}
	
	/**
	 * Add a new dialoguetype to the hashmap
	 * @param index
	 * @param dt
	 * @return
	 */
	public Dialogue add(int index, DialogueType dt) {
		dialogues.put(index, dt);
		return this;
	}
	
	/**
	 * A player message
	 * @param msgs
	 * @return
	 */
	public DialogueMessage player(String...msgs) {
		return player(DialogueExpression.NORMAL,msgs);
	}
	
	/**
	 * A player message
	 * @param de
	 * @param msgs
	 * @return
	 */
	public DialogueMessage player(DialogueExpression de, String...msgs) {
		return new DialogueMessage(player,de,msgs);
	}
	
	/**
	 * An npc message
	 * @param npc
	 * @param msgs
	 * @return
	 */
	public DialogueMessage npc(NPC npc,String...msgs) {
		return npc(npc,DialogueExpression.NORMAL,msgs);
	}
	
	/**
	 * An npc message
	 * @param npc
	 * @param de
	 * @param msgs
	 * @return
	 */
	public DialogueMessage npc(NPC npc,DialogueExpression de, String...msgs) {
		return new DialogueMessage(npc,de,msgs);
	}
	
	/**
	 * Skip to a certain part in the dialogue, this is as a dialoguetype to allow .add(skip(index))
	 * @param index
	 * @return
	 */
	public DialogueSkip skip(int index) {
		return new DialogueSkip(index);
	}
	
	/**
	 * Ends the dialogue, this time as a dialoguetype to allow .add(end())
	 * @return
	 */
	public DialogueEnd end() {
		return new DialogueEnd();
	}

	/**
	 * Gets the index
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Gets the player
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the button pressed (if none, its = to -1)
	 * @return
	 */
	public int getButton() {
		return button;
	}

	/**
	 * Sets the button if pressed.
	 * @param button
	 */
	public void setButton(int button) {
		this.button = button;
	}

}
