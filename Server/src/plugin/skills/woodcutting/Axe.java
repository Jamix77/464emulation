package plugin.skills.woodcutting;

import java.util.HashMap;
import java.util.Map;

import org.hyperion.rs2.model.Animation;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Skills;

/**
 * An enum of axes to use in woodcutting.
 * @author jamix77
 *
 */
public enum Axe {
	/**
	 * Dragon axe.
	 */
	DRAGON(6739, 61, 2846,0.85D),
	
	/**
	 * Rune axe.
	 */
	RUNE(1359, 41, 867,0.65D),
	
	/**
	 * Adamant axe.
	 */
	ADAMANT(1357, 31, 869,0.45D),
	
	/**
	 * Mithril axe.
	 */
	MITHRIL(1355, 21, 871,0.3D),
	
	/**
	 * Black axe.
	 */
	BLACK(1361, 6, 873,0.25D),
	
	/**
	 * Steel axe.
	 */
	STEEL(1353, 6, 875,0.2D),
	
	/**
	 * Iron axe.
	 */
	IRON(1349, 1, 877,0.1D),
	
	/**
	 * Bronze axe.
	 */
	BRONZE(1351, 1, 879,0.05D);
	
	/**
	 * The id.
	 */
	private int id;
	
	/**
	 * The level.
	 */
	private int level;
	
	/**
	 * The animation.
	 */
	private Animation animation;
	
	/**
	 * The chopping chance ratio;
	 */
	private double ratio;
	
	/**
	 * A map of object ids to axes.
	 */
	private static Map<Integer, Axe> axes = new HashMap<Integer, Axe>();
	
	/**
	 * Gets a axe by an object id.
	 * @param object The object id.
	 * @return The axe, or <code>null</code> if the object is not a axe.
	 */
	public static Axe forId(int object) {
		return axes.get(object);
	}
	
	/**
	 * Gets a players best axe.
	 */
	public static Axe getBestAxe(Player player) {
		Axe axe = null;
		for(Axe x : Axe.values()) {///lets get the best axe that the player has.
			if((player.getEquipment().contains(x.getId()) || player.getInventory().contains(x.getId())) && player.getSkills().getLevel(Skills.WOODCUTTING) >= x.getRequiredLevel()) {
				axe = x;
				break;
			}
		}
		return axe;
	}
	
	/**
	 * Populates the tree map.
	 */
	static {
		for(Axe axe : Axe.values()) {
			axes.put(axe.id, axe);
		}
	}
	
	/**
	 * Creates the axe.
	 * @param id The id.
	 * @param level The required level.
	 * @param animation The animation id.
	 */
	private Axe(int id, int level, int animation, double ratio) {
		this.id = id;
		this.level = level;
		this.animation = Animation.create(animation);
		this.ratio = ratio;
	}
	
	/**
	 * Gets the id.
	 * @return The id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the required level.
	 * @return The required level.
	 */
	public int getRequiredLevel() {
		return level;
	}
	
	/**
	 * Gets the animation id.
	 * @return The animation id.
	 */
	public Animation getAnimation() {
		return animation;
	}
	
	/**
	 * Gets the ratio for chopping
	 * @return ratio int
	 */
	public double getRatio() {
		return ratio;
	}

}
