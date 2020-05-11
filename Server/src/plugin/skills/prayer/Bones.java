package plugin.skills.prayer;

import java.util.HashMap;

/**
 * Represents the bones.
 * @author jamix77
 */
public enum Bones {
	
	BONES(526, 4.5), 
	WOLF_BONES(2859, 4.5), 
	BURNST_BONES(528, 4.5), 
	MONKEY_BONES(3183, 5), 
	MONKEY_BONES2(3179, 5), 
	BAT_BONES(530, 5.3), 
	BIG_BONES(532, 15), 
	JOGRE_BONES(3125, 15), 
	ZOGRE_BONES(4812, 12.5), 
	SHAIKAHAN_BONES(3123, 25), 
	BABY_DRAGON_BONES(534, 30), 
	WYVERN_BONES(6812, 50), 
	DRAGON_BONES(536, 72), 
	FAYRG(4830, 84), 
	RAURG_BONES(4832, 96), 
	DAGANNOTH(6729, 125), 
	OURG_BONES(4834, 140);

	/**
	 * Holds all bones.
	 */
	private static HashMap<Integer, Bones> bones = new HashMap<Integer, Bones>();


	/**
	 * The bone item id.
	 */
	private int itemId;

	/**
	 * The experience given by burying the bone.
	 */
	private double experience;

	/**
	 * Construct a new {@code Bones} {@code Object}.
	 * @param itemId The item id.
	 * @param experience The experience given by burying the bone.
	 */
	private Bones(int itemId, double experience) {
		this.itemId = itemId;
		this.experience = experience;
	}

	/**
	 * Get the bone experience given when you bury the bone.
	 * @return The experience.
	 */
	public double getExperience() {
		return experience;
	}

	public int getItemId() {
		return itemId;
	}

	/**
	 * Get the bone.
	 * @param itemId The item id.
	 * @return The bone.
	 */
	public static Bones forId(int itemId) {
		return bones.get(itemId);
	}

	/**
	 * Construct the bones.
	 */
	static {
		for (Bones bone : Bones.values()) {
			bones.put(bone.itemId, bone);
		}
	}
}