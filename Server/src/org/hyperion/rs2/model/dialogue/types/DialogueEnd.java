package org.hyperion.rs2.model.dialogue.types;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.dialogue.Dialogue;
import org.hyperion.rs2.model.dialogue.DialogueType;

/**
 * Ends the dialogue.
 * @author jamix77
 *
 */
public class DialogueEnd implements DialogueType {

	public DialogueEnd() {}

	@Override
	public void handle(Player player, Dialogue dialogue) {
		player.getDialogueManager().end();
	}

}
