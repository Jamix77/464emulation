package plugin.skills.prayer;

import org.hyperion.plugin.InitializablePlugin;
import org.hyperion.plugin.Plugin;
import org.hyperion.plugin.PluginManifest;
import org.hyperion.plugin.PluginType;
import org.hyperion.plugin.impl.OptionHandler;
import org.hyperion.rs2.model.Animation;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.model.Sound;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.tickable.Tickable;

/**
 * the plugin to handle bone burying!
 * @author jamix77
 *
 */
@InitializablePlugin
@PluginManifest(authors = { "jamix77" }, version = 1.0, type = PluginType.ACTION)
public class BoneBuryingPlugin extends OptionHandler {
	
	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		return new BoneBuryingPlugin();
	}

	@Override
	public void init() throws Throwable {
		for (Bones b : Bones.values()) {
			item(b.getItemId(),"bury");
		}
	}

	@Override
	public boolean handle(Player player, Object node, String option) {
		player.getInterfaceState().interfaceClosed();
		Bones b = Bones.forId(((Item)node).getId());
		player.lock(2,true);
		player.getActionSender().sendMessage("You dig a hole in the ground...");
		player.playAnimation(Animation.create(827));
		player.playSound(Sound.create(2738, (byte)10,2));
		World.getWorld().submit(new Tickable(2) {

			@Override
			public void execute() {
				player.getInventory().remove((Item)node);
				player.getSkills().addExperience(Skills.PRAYER, b.getExperience());
				player.getActionSender().sendMessage("You bury the bones");
				stop();
			}
			
		});
		return true;
	}

}
