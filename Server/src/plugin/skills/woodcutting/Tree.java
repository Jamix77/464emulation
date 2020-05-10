package plugin.skills.woodcutting;

import java.util.HashMap;
import java.util.Map;

import org.hyperion.rs2.model.Item;

/**
 * Represents the trees.
 * @author jamix77
 *
 */
public enum Tree {
	/**
	 * Regular single chop tree.
	 */
	NORMAL(new int[] { 1276, 1277, 1278, 1279, 1280, 1282,
				1283, 1284, 1285, 1286, 1289, 1290, 1291, 1315, 1316, 1318,
				1319, 1330, 1331, 1332, 1365, 1383, 1384, 2409, 3033, 3034,
				3035, 3036, 3881, 3882, 3883, 5902, 5903, 5904, 14308, 14309,
				1276,1278,2409,	1277,3034,3033,10041,1282,1283,1284,
				1285,1286,1289,1290,1365,1383,1384,1291,3035,3036,1315,1316,
				1318,1319,1330,1331,1332,3879,3881,3882,3883,1280,14309},1342,25,1511,50 | 100 << 16,1,true),
	
	/**
	 * Oak tree.
	 */
	OAK(new int[] { 1281, 3037 },1349, 37.5,1521, 14 | 22 << 16, 15),
	
	/**
	 * Willow tree.
	 */
	WILLOW(new int[] { 1308, 5551, 5552, 5553 },7399,67.8, 1519, 14 | 22 << 16, 30),
	
	/**
	 * Maple tree.
	 */
	MAPLE(new int[] { 1307, 4677, 4674 },1356, 100, 1517, 58 | 100 << 16,45),
	
	/**
	 * Yew tree.
	 */
	YEW(new int[] { 1309 },1355,175, 1515, 100 | 162 << 16,60),
	
	/**
	 * Magic tree.
	 */
	MAGIC(new int[] {1306 },7402, 250, 1513, 200 | 317 << 16, 75);
	
	/**
	 * Constructors :)
	 */
	private Tree(int[] treeIds, int stump, double experience, int log, int restoreTime, int level) {
		this.treeIds = treeIds;
		this.stump = stump;
		this.experience = experience;
		this.log = new Item(log);
		this.restoreTime = restoreTime;
		this.level = level;
		this.singleChop = false;
	}
	
	private Tree(int[] treeIds, int stump, double experience, int log, int restoreTime, int level,boolean singleChop) {
		this.treeIds = treeIds;
		this.stump = stump;
		this.experience = experience;
		this.log = new Item(log);
		this.restoreTime = restoreTime;
		this.level = level;
		this.singleChop = singleChop;
	}
	
	/**
	 * The ids of all trees that fall into this type.
	 */
	private int[] treeIds;
	
	/**
	 * The stump id for the tree.
	 */
	private int stump;
	
	/**
	 * the xp given when chopped.
	 */
	private double experience;

	/**
	 * the log given when chopped
	 */
	private Item log;
	
	/**
	 * The amount of time taken to restore the tree from the stump.
	 */
	private int restoreTime;
	
	/**
	 * the level needed to chop the tree.
	 */
	private int level;
	
	/**
	 * if the tree falls after a single chop.
	 */
	private boolean singleChop;
	
	/**
	 * A map of object ids to trees.
	 */
	private static Map<Integer, Tree> trees = new HashMap<Integer, Tree>();
	
	/**
	 * Gets a tree by an object id.
	 * @param object The object id.
	 * @return The tree, or <code>null</code> if the object is not a tree.
	 */
	public static Tree forId(int object) {
		return trees.get(object);
	}
	
	/**
	 * Populates the tree map.
	 */
	static {
		for(Tree tree : Tree.values()) {
			for(int object : tree.treeIds) {
				trees.put(object, tree);
			}
		}
	}

	public int[] getTreeIds() {
		return treeIds;
	}

	public int getStump() {
		return stump;
	}

	public double getExperience() {
		return experience;
	}

	public Item getLog() {
		return log;
	}

	public int getRestoreTime() {
		return restoreTime;
	}

	public int getLevel() {
		return level;
	}

	public static Map<Integer, Tree> getTrees() {
		return trees;
	}

	public boolean isSingleChop() {
		return singleChop;
	}
	
	

}
