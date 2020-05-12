package plugin.interaction.item.withitem;

import org.hyperion.plugin.InitializablePlugin;
import org.hyperion.plugin.Plugin;
import org.hyperion.plugin.PluginManifest;
import org.hyperion.plugin.PluginType;
import org.hyperion.plugin.impl.ItemOnHandler;
import org.hyperion.rs2.model.Player;

@InitializablePlugin
@PluginManifest(authors = { "jamix77" }, version = 1.0, type = PluginType.ACTION)
public class TestWithItemThingy extends ItemOnHandler {

	public TestWithItemThingy() {
	}

	@Override
	public Plugin<Object> newInstance(Object arg) {
		return new TestWithItemThingy();
	}

	@Override
	public void init() throws Throwable 
	{
		ItemOnHandler.add(ItemOnType.ITEM_ON_ITEM, 1042, 1044, this);
		ItemOnHandler.add(ItemOnType.ITEM_ON_OBJECT, 1042, 1276, this);
	}

	@Override
	public void handle(ItemOnEvent e) {
		Player p = e.getPlayer();
		p.getActionSender().sendMessage("A test for the item handler");
	}

}
