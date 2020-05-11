package plugin.skills.woodcutting;

import org.hyperion.plugin.InitializablePlugin;
import org.hyperion.plugin.Plugin;
import org.hyperion.plugin.PluginManifest;
import org.hyperion.plugin.PluginType;
import org.hyperion.plugin.impl.OptionHandler;
import org.hyperion.rs2.action.Action;
import org.hyperion.rs2.model.Animation;
import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.GameObject;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.util.Misc;

/**
 * A plugin for handling all the events of woodcutting.
 * @author jamix77
 *
 */
@InitializablePlugin
@PluginManifest(authors = { "jamix77" }, version = 1.0, type = PluginType.ACTION)
public class WoodcuttingPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		return new WoodcuttingPlugin();
	}

	@Override
	/**
	 * Adds object -> plugin handlers for every tree to come here.
	 */
	public void init() throws Throwable {
		for (Tree tree : Tree.values()) {
			for (int id : tree.getTreeIds()) {
				object(id,"chop down");
			}
		}
	}

	@Override
	/**
	 * Handles the woodcutting
	 */
	public boolean handle(Player player, Object node, String option) {
		GameObject treeObj = ((GameObject)node);
		Axe axe = Axe.getBestAxe(player);
		Tree tree = Tree.forId(treeObj.getId());
		if (tree == null) {//checks if that the object is an actual tree from the enum.
			player.getActionSender().sendMessage("Error: Tree variable is null, WoodcuttingPlugin.java, please report this to a developer.");
			return true;
		}
		if(player.getSkills().getLevel(Skills.WOODCUTTING) < tree.getLevel()) {//if the player has the min wc lvl.
			player.getActionSender().sendMessage("You do not have the required level to cut down that tree.");
			return true;
		}
		if(axe == null) {//if the player has no axe.
			player.getActionSender().sendMessage("You do not have an axe that you can use.");
			return true;
		}
		if(!player.getInventory().hasRoomFor(tree.getLog())) {
			player.getActionSender().sendMessage("There is not enough space in your inventory.");
			return true;
		}
		player.getActionSender().sendMessage("You swing your axe at the tree...");
		player.playAnimation(axe.getAnimation());
		Action action = new Action(player, 0) {
			
			@Override
			public void stop() {
				player.playAnimation(Animation.DEFAULT);
				super.stop();
			}

			@Override
			public CancelPolicy getCancelPolicy() {
				return CancelPolicy.ALWAYS;
			}

			@Override
			public StackPolicy getStackPolicy() {
				return StackPolicy.NEVER;
			}

			@Override
			public AnimationPolicy getAnimationPolicy() {
				return AnimationPolicy.RESET_ALL;
			}

			@Override
			public void execute() {
				
				
				GameObject obj = player.getRegion().getGameObject(treeObj.getLocation());
				if (obj != null) {
					if (obj.getId() != treeObj.getId()) {
						this.stop();
						
						return;
					}
				} else {
					this.stop();
					return;
				}
					player.playAnimation(axe.getAnimation());
					if (this.getTicks() == 0) {
						this.setTicks(4);
						return;
					}
					if (!player.getInventory().hasRoomFor(tree.getLog())) {
						player.getActionSender().sendMessage("There is not enough space in your inventory.");
						this.stop();
						return;
					}
					if (!cut(player,tree,axe)) {return;}
					player.getInventory().add(tree.getLog());
					player.getActionSender().sendMessage("You get some " + tree.getLog().getDefinition().getName().toLowerCase() + ".");
					player.getSkills().addExperience(Skills.WOODCUTTING, tree.getExperience());
					if (Misc.random(8) == 4 || tree.isSingleChop()) {
						GameObject stump = new GameObject(treeObj.getLocation(),tree.getStump(),treeObj.getType(),treeObj.getDirection(),false);
						World.getWorld().replaceObject(treeObj, stump, (int)tree.getRestoreTime()/600);
						this.stop();
						return;
					}
				
			}
			
		};
		player.getActionQueue().addAction(action);
		
		
		return true;
	}
	
	/**
	 * If the player has chopped a log from the tree.
	 * @param player
	 * @param tree
	 * @param axe
	 * @return
	 */
	public boolean cut(Player player, Tree tree, Axe axe) {
		 int skill = Skills.WOODCUTTING;
	        int level = player.getSkills().getLevel(skill)+1;
	        double hostRatio = Math.random() * (100.0 * 0.125);
	        double clientRatio = Math.random() * ((level - tree.getLevel()) * (1.0 + axe.getRatio()));
	        return hostRatio < clientRatio;
	}
	

}
