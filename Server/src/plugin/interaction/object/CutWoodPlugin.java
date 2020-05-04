package plugin.interaction.object;

import org.hyperion.plugin.InitializablePlugin;
import org.hyperion.plugin.Plugin;
import org.hyperion.plugin.PluginManager;
import org.hyperion.plugin.PluginManifest;
import org.hyperion.plugin.PluginType;
import org.hyperion.plugin.impl.OptionHandler;
import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.dialogue.Dialogue;

@InitializablePlugin
@PluginManifest(authors = { "jamix77" }, version = 1.2, type = PluginType.ACTION)
public class CutWoodPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		return new CutWoodPlugin();
	}

	@Override
	public void init() throws Throwable {
		PluginManager.getOptionHandlerPlugins().put("object:cut-wood", this);
	}

	@Override
	public boolean handle(Player player, Entity node, String option) {
		player.getActionSender().sendMessage("Cut wood stuff yeet");
		return true;
	}



}
