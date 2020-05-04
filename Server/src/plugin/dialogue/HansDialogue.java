package plugin.dialogue;

import org.hyperion.plugin.InitializablePlugin;
import org.hyperion.plugin.Plugin;
import org.hyperion.plugin.PluginManager;
import org.hyperion.plugin.PluginManifest;
import org.hyperion.plugin.PluginType;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.NPCDefinition;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.dialogue.Dialogue;
import org.hyperion.rs2.model.dialogue.types.DialogueOptions;
import org.hyperion.rs2.model.dialogue.types.DialogueOptions.DialogueOption;
import org.hyperion.rs2.model.dialogue.types.DialogueOptions.DialogueOptionHandler;

@InitializablePlugin
@PluginManifest(authors = { "jamix77" }, version = 1.5, type = PluginType.DIALOGUE)
public class HansDialogue extends Dialogue {

	private static final NPC HANS = new NPC(NPCDefinition.forId(0), null, null, null, 0);
	
	public HansDialogue() {super(null);}
	
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
						d.skipTo(30);
						d.handle();
					}
				})
				
				))
		.add(30, end());
	} 

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		return new HansDialogue((Player)arg);
	}

	@Override
	public void init() throws Throwable {
		PluginManager.getDialoguePlugins().put(0,this);
	}
}
