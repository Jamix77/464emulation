package org.hyperion.rs2.model.dialogue.types;

import java.util.Arrays;
import java.util.LinkedList;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.dialogue.Dialogue;
import org.hyperion.rs2.model.dialogue.DialogueExpression;
import org.hyperion.rs2.model.dialogue.DialogueType;
import org.hyperion.rs2.net.ActionSender;

/**
 * The dialogue options menu DialogueType
 * @author jamix77
 *
 */
public class DialogueOptions implements DialogueType {
	
	/**
	 * a linkedlist of the options
	 */
	private final LinkedList<DialogueOption> dialogueOptions = new LinkedList<DialogueOption>();
	
	/**
	 * an array of the option strings.
	 */
	private final String[] optionStrings;
	
	/**
	 * Constructor
	 * TODO: needs to be rewritten.
	 * @param dialogueOptions
	 */
	public DialogueOptions(DialogueOption...dialogueOptions) {
		this.dialogueOptions.addAll(Arrays.asList(dialogueOptions));
		optionStrings = new String[dialogueOptions.length];
		for (int i = 0 ; i < optionStrings.length; i++) {
			optionStrings[i] = dialogueOptions[i].option;
		}
	}

	/**
	 * Handles from super super.
	 */
	@Override
	public void handle(Player player, Dialogue dialogue) {
		if (dialogue.getButton() == -1) {//if its only displaying/
			player.getActionSender().sendDialogue("Select an Option", ActionSender.DialogueType.OPTION, -1, DialogueExpression.NORMAL, 
					optionStrings);
		} else {//a button was pressed.
			dialogueOptions.get(dialogue.getButton()).handler.run(player, dialogue);
		}
	}
	
	/**
	 * A singular dialogue option.
	 * @author jamix77
	 *
	 */
	public static class DialogueOption {
		
		public final String option;
		
		public final DialogueOptionHandler handler;
		
		public DialogueOption(String option, DialogueOptionHandler r) {
			this.option = option;
			this.handler = r;
		}
		
	}
	
	/**
	 * The interface for the handler.
	 * @author jamix77
	 *
	 */
	public static interface DialogueOptionHandler {

		/**
		 * When that dialogue button is pressed, this runs.
		 * @param player
		 * @param dialogue
		 */
		public default void run(Player player, Dialogue dialogue) {
		}
		
	}

}
