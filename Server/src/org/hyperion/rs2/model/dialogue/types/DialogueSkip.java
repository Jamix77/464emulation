package org.hyperion.rs2.model.dialogue.types;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.dialogue.Dialogue;
import org.hyperion.rs2.model.dialogue.DialogueType;

/**
 * Skips to a certain point in the dialogue
 * @author jamix77
 *
 */
public class DialogueSkip implements DialogueType {

	/**
	 * What index to skip to
	 */
	private final int index;

/**
 * whether handle should be called immediately after to resume with no extra effort.	
 */
	private final boolean resume;
	
	/**
	 * Constructor
	 * @param index
	 */
	public DialogueSkip(int index) {
		this(index,true);
	}
	
	/**
	 * The other constructor (cooler)
	 * @param index
	 * @param resume
	 */
	public DialogueSkip(int index, boolean resume) {
		this.index = index;
		this.resume = resume;
	}

	/**
	 * Handles from the super super.
	 */
	@Override
	public void handle(Player player, Dialogue dialogue) {
		dialogue.skipTo(index);
		if (resume) {
			dialogue.handle();
		}
	}

}
