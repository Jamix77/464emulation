package plugin.quest;

import org.hyperion.plugin.InitializablePlugin;
import org.hyperion.plugin.Plugin;
import org.hyperion.plugin.PluginManifest;
import org.hyperion.plugin.PluginType;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.quest.Quest;
import org.hyperion.rs2.model.quest.QuestRepository;

/**
 * A test for quest system
 * @author jamix77
 *
 */
@InitializablePlugin
@PluginManifest(authors = { "jamix77" }, version = 1.0, type = PluginType.QUEST)
public class TestQuest extends Quest {
	
	public TestQuest() {this(null);}

	public TestQuest(Player player) {
		super("test", player);
	}

	@Override
	public Plugin<Player> newInstance(Player arg){
		return new TestQuest(arg);
	}

}
