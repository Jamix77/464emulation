package dialogues;

import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.NPCDefinition;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.dialogue.Dialogue;
import org.hyperion.rs2.model.dialogue.types.DialogueOptions;
import org.hyperion.rs2.model.dialogue.types.DialogueOptions.DialogueOption;
import org.hyperion.rs2.model.dialogue.types.DialogueOptions.DialogueOptionHandler;

public class HansDialogue extends Dialogue {

	private static final NPC HANS = new NPC(NPCDefinition.forId(0), null, null, null, 0);
	
	public HansDialogue(Player player) {
		super(player);
	}

	@Override
	public void generate() {
		add(0,player("Suck my penis","You cunter","Fuckfuckfuck"))
		.add(1, player("yeetyeetyet","ur nan is dumb"))
		.add(2, skip(5))
		.add(5, npc(HANS,"shid and fud"))
		.add(6, skip(20))
		.add(20, new DialogueOptions(
				
				new DialogueOption("penis", new DialogueOptionHandler() {
					@Override
					public void run(Player p, Dialogue d) {
						p.getActionSender().sendMessage("PENIS BUTTON CLICKED, HURRAY");
						d.skipTo(30);
						d.handle();
					}
				}),
				
				new DialogueOption("notpenis", new DialogueOptionHandler() {
					@Override
					public void run(Player p, Dialogue d) {
						p.getActionSender().sendMessage("NOTPENIS BUTTON CLICKED, HURRAY");
						d.skipTo(50);
						d.handle();
					}
				})
				
				))
		.add(30, end())
		.add(50, end());
	}
}
