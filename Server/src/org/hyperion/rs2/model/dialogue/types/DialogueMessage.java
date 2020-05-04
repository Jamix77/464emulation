package org.hyperion.rs2.model.dialogue.types;

import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.dialogue.Dialogue;
import org.hyperion.rs2.model.dialogue.DialogueExpression;
import org.hyperion.rs2.model.dialogue.DialogueType;
import org.hyperion.rs2.net.ActionSender;

/**
 * A singular message box type.
 * @author jamix77
 *
 */
public class DialogueMessage implements DialogueType {
	
	/**
	 * The messages to go along with the "face"
	 */
	private final String[] messages;
	
	/**
	 * The entity, should be node when I have item messages too.
	 * TODO
	 */
	private final Entity entity;
	
	/**
	 * The expression of the face
	 */
	private final DialogueExpression expression;

	/**
	 * Constructor.
	 * @param entity
	 * @param expression
	 * @param strings
	 */
	public DialogueMessage(Entity entity, DialogueExpression expression, String...strings) {
		this.messages = strings;
		this.entity = entity;
		this.expression = expression;
	}

	/**
	 * Handles the handle call from super supers.
	 */
	@Override
	public void handle(Player player,Dialogue dialogue) {
		for (int i = 0; i < messages.length;i++)
		if (entity.isNPC()) {//for npc
			player.getActionSender().sendDialogue(((NPC)entity).getDefinition().getName(), ActionSender.DialogueType.NPC, ((NPC)entity).getDefinition().getId(), expression,
					messages);
		} else if (entity.isPlayer()) {//for player
			player.getActionSender().sendDialogue(player.getName(), ActionSender.DialogueType.PLAYER, -1, expression,
					messages);
		}
		
		
		dialogue.increment();//remember to increment to avoid issues :)
	}

}
