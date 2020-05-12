package plugin.dialogue;

import java.util.Random;

import org.hyperion.plugin.InitializablePlugin;
import org.hyperion.plugin.Plugin;
import org.hyperion.plugin.PluginManager;
import org.hyperion.plugin.PluginManifest;
import org.hyperion.plugin.PluginType;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.dialogue.Dialogue;

@InitializablePlugin
@PluginManifest(authors = { "jamix77" }, version = 1.5, type = PluginType.DIALOGUE)
public class CitizenDialogue extends Dialogue {

	private static final int[] NPC_IDS = new int[] { 1, 2, 3, 4, 5, 6, 16, 24, 25, 170, 351, 352, 353, 354, 359, 360, 361, 362, 363, 663, 726, 727, 728, 729, 730, 1086, 2675, 2776, 3224, 3225, 3227, 5923, 5924,};
	
	public CitizenDialogue() {super();}
	
	public CitizenDialogue(Player player,NPC npc) {
		super(player,npc);
	}

	@Override
	public void generate() {
		int random = new Random().nextInt(4);
		switch (random) {
		case 0:
			add(1,npc(npc,"I'm very well thank you"))
			.add(2, skip(30));
			break;
		case 1:
			add(1,npc(npc,"Who are you?"))
			.add(2, player("I'm a bold adventurer."))
			.add(3, npc(npc,"Ah, a very noble profession."))
			.add(4, skip(30));
			break;
		case 2:
			add(1,npc(npc,"I'm fine, how are you?"))
			.add(2, player("Very well, thank you."))
			.add(3, skip(30));
			break;
		case 3:
			add(1,npc(npc,"No, I don't want to buy anything."))
			.add(2, skip(30));
			break;
		case 4:
			add(1,npc(npc,"I think we need a new king. The one we've got isn't","very good."))
			.add(2, skip(30));
			break;
		default:
			add(1,npc(npc,"My dialogue is broken","Report this to a dev","error:"+random+"-"+npc.getDefinition().id))
			.add(2, skip(30));
		}
		add(0,player("Hello, how's it going?"))
		.add(30, end());
	} 

	@Override
	public Plugin<Object> newInstance(Object arg) {
		Object[] args = (Object[]) arg;
		return new CitizenDialogue((Player)args[0],(NPC)args[1]);
	}

	@Override
	public void init() throws Throwable {
		for (int id : NPC_IDS) {
			PluginManager.getDialoguePlugins().put(id, this);
		}
	}
}
