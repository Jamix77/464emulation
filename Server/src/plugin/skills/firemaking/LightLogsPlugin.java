package plugin.skills.firemaking;

import org.hyperion.plugin.InitializablePlugin;
import org.hyperion.plugin.Plugin;
import org.hyperion.plugin.PluginManifest;
import org.hyperion.plugin.PluginType;
import org.hyperion.plugin.impl.ItemOnHandler;
import org.hyperion.rs2.action.Action;
import org.hyperion.rs2.model.Animation;
import org.hyperion.rs2.model.GameObject;
import org.hyperion.rs2.model.GroundItem;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.tickable.Tickable;

/**
 * A plugin for the firemaking skill, for lighting logs.
 * @author jamix77
 * 
 * TODO: CANDLE LIGHTING AND SUCH.
 * TODO: "light log" log on ground thingy.
 *
 */
@InitializablePlugin
@PluginManifest(authors = { "jamix77" }, version = 1.0, type = PluginType.ACTION)
public class LightLogsPlugin extends ItemOnHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) {
		return new LightLogsPlugin();
	}

	/**
	 * Adds the logs to the item handler
	 */
	@Override
	public void init() throws Throwable {
		for (Log log : Log.values()) {
			ItemOnHandler.add(ItemOnType.ITEM_ON_ITEM, 590, log.getLogId(), this);
		}
	}

	@Override
	/**
	 * When the item on handler is called.
	 */
	public void handle(ItemOnEvent e) {
		Log fire = Log.forId(e.getUsed().getId() == 590 ? ((Item) e.getUsedWith()).getId() : e.getUsed().getId());
		if (fire == null) {
			return;
		}
		//checks the level
		if (e.getPlayer().getSkills().getLevel(Skills.FIREMAKING) < fire.getLevel()) {
			e.getPlayer().getActionSender().sendMessage("You need a firemaking level of " + fire.getLevel() + " to light this log.");
			return;
		}
		//checks if the square has an object or not.
		if (e.getPlayer().getRegion().objectExists(e.getPlayer().getLocation())) {
			e.getPlayer().getActionSender().sendMessage(e.getPlayer().getRegion().getGameObject(e.getPlayer().getLocation()).getDefinition().name + "-"+e.getPlayer().getRegion().getGameObject(e.getPlayer().getLocation()).getId());
			e.getPlayer().getActionSender().sendMessage("You can't light a fire here.");
			return;
		}
		//constructs the ground object for the log
		GroundItem groundItem = new GroundItem(e.getPlayer().getName(), e.getUsed().getId() == 590 ? ((Item) e.getUsedWith()) : e.getUsed(), e.getPlayer().getLocation());
		Action a = new Action(e.getPlayer(),1) {//the action for lighting
			
			/**
			 * A count of ticks currently passed for animation handling and log checking
			 */
			int tick = 0;

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
			
			/**
			 * Does math stuff to see if the player has been successful in lighting the log.
			 * @return the success bool
			 */
			public boolean light() {
					int level = 1 + e.getPlayer().getSkills().getLevel(Skills.FIREMAKING);
					double req = fire.getLevel();
					double successChance = Math.ceil((level * 50 - req * 15) / req / 3 * 4);
					int roll = (int)(Math.random() * 10);
					return successChance >= roll;
			}
			
			/**
			 * if the player was succesful in lighting the log!
			 */
			public void lightLog() {
				if (groundItem.isRegistered()==false) {//the log has dissappeared.
					stop();
					return;
				}
				GameObject object = new GameObject(e.getPlayer().getLocation(),fire.getFireId(),10,0,false);
				World.getWorld().register(object);
				World.getWorld().unregister(groundItem);
				World.getWorld().submit(new Tickable(fire.getLife()) {//ash stuff.

					@Override
					public void execute() {
						World.getWorld().unregister(object, true);
						World.getWorld().createGroundItem(new GroundItem(e.getPlayer().getName(),new Item(592),e.getPlayer().getLocation()), e.getPlayer());
						stop();
					}
					
				});
				e.getPlayer().getWalkingQueue().moveStep();
				e.getPlayer().getSkills().addExperience(Skills.FIREMAKING, fire.getXp());
				stop();
			}

			@Override
			/**
			 * Executed every tick
			 */
			public void execute() {
				tick++;
				if (tick % 12 == 0 || tick == 1) {//if the player isnt animating
					e.getPlayer().playAnimation(Animation.create(733));
				}
				if (tick % 3 != 0) {//checks for the light check only happens every 3 ticks
					return;
				}
				
				if (light()) {//checks the math
					lightLog();
				}
			}
			
		};
		/**
		 * If the light type was item on item, ready for expansion in the future.
		 */
		if (e.type() == ItemOnType.ITEM_ON_ITEM) {
			e.getPlayer().getInventory().remove(e.getUsed().getId() == 590 ? ((Item) e.getUsedWith()) : e.getUsed());//removes log
			World.getWorld().createGroundItem(groundItem, e.getPlayer());//creeates ground item
			e.getPlayer().getActionQueue().clearAllActions();//clears current actions
			e.getPlayer().getActionQueue().addAction(a);//adds the lighting action
		}
	}


}
