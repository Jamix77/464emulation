package org.hyperion.rs2.action.impl;

import org.hyperion.rs2.action.Action;
import org.hyperion.rs2.model.Animation;
import org.hyperion.rs2.model.GameObject;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.World;


public abstract class SkillAction extends Action {

	/**
	 * Creates the harvesting action for the specified player.
	 * 
	 * @param player
	 *            The player to create the action for.
	 */
	public SkillAction(Player player) {
		super(player, 1); //0
	}

	/**
	 * Gets the amount of cycles between each attempt
	 * 
	 * @return The amount of cycles between each attempt.
	 */
	public abstract int getCycleCount();
	
	/**
	 * The amount of cycles before the object is interacted with
	 * 
	 * @return
	 */
	public abstract int getFirstCycleCount();

	/**
	 * Gets the game object we are harvesting.
	 * 
	 * @return The game object we are harvesting.
	 */
	public abstract GameObject getGameObject();

	/**
	 * Gets the game object that replaces the object we harvest.
	 * 
	 * @return The game object that replaces the object we harvest.
	 */
	public abstract GameObject getReplacementNode();

	/**
	 * Gets the game objects maximum health.
	 * 
	 * @return The game objects maximum health.
	 */
	public abstract int getNodeMaxHealth();

	/**
	 * Gets the amount of cycles it takes for the object to respawn.
	 * 
	 * @return The amount of cycles it takes for the object to respawn.
	 */
	public abstract int getNodeRespawnTimer();
	
	/**
	 * Gets the message sent when the player successfully harvests from the
	 * object.
	 * 
	 * @return The message sent when the player successfully harvests from the
	 *         object.
	 */
	public abstract String getSuccessfulHarvestMessage();

	/**
	 * Gets the reward from harvesting the object.
	 * 
	 * @return The reward from harvesting the object.
	 */
	public abstract Item getReward();

	/**
	 * Gets the skill we are using to harvest.
	 * 
	 * @return The skill we are using to harvest.
	 */
	public abstract int getSkill();

	/**
	 * Gets the required level to harvest this object.
	 * 
	 * @return The required level to harvest this object.
	 */
	public abstract int getRequiredLevel();

	/**
	 * Gets the experience granted for each item that is successfully harvested.
	 * 
	 * @return The experience granted for each item that is successfully
	 *         harvested.
	 */
	public abstract double getExperience();

	/**
	 * Gets the message sent when the player's level is too low to harvest this
	 * object.
	 * 
	 * @return The message sent when the player's level is too low to harvest
	 *         this object.
	 */
	public abstract String getLevelTooLowMessage();

	/**
	 * Gets the message sent when the harvest successfully begins.
	 * 
	 * @return The message sent when the harvest successfully begins.
	 */
	public abstract String getHarvestStartedMessage();


	/**
	 * Gets the message sent when the player has a full inventory.
	 * 
	 * @return The message sent when the player has a full inventory.
	 */
	public abstract String getInventoryFullMessage();

	/**
	 * Gets the animation played whilst harvesting the object.
	 * 
	 * @return The animation played whilst harvesting the object.
	 */
	public abstract Animation getAnimation();
	
	public abstract byte getAnimationTimer();
	
	public abstract byte getSoundTimer();

	/**
	 * Performs extra checks that a specific harvest event independently uses,
	 * e.g. checking for a pickaxe in mining.
	 */
	public abstract boolean canHarvest();

	public abstract int getRepeatedSound();

	public abstract int getHarvestedSound();

	public abstract int getInventoryFullSound(); // ?

	/**
	 * Represents whether the player gets the harvest this cycle.
	 * 
	 * @return true if success false if not
	 */
	public abstract double chance();

	public abstract double randomEventChance(); // chance to get this event

	public abstract void spawnRandom(); // initialize random

	public abstract void removeBait();

	public abstract int getBait();

	/**
	 * This starts the actions animation and requirement checks, but prevents
	 * the harvest from immediately executing.
	 */
	private boolean started = false;

	/**
	 * The current cycle time.
	 */
	public static int currentCycles = 0;

	/**
	 * The amount of cycles before an animation.
	 */
	private int lastAnimation = 0;

	private int lastSound = 6; //0

	private int missedCycles = 0;

	private double factor;
	
	public Item reward;

	public abstract boolean appearInChatBox();
	
	private boolean success;

	public abstract void getFail();

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
		return AnimationPolicy.RESET_NONE;
	}

	@Override
	public void execute() {
/*		if (getMob().getAttribute("stunned").equals(1)) {//Can't harvest if stunned
			this.stop();
			return;
		}*/
		callReward = false;
		if (!getMob().getInventory().hasRoomFor(new Item(1050))) { //we use a santa hat because the actual reward is null at this point.
			if (appearInChatBox()) {
				getMob().getActionSender().sendString(210, 0, getInventoryFullMessage());
				getMob().getActionSender().sendChatboxInterface(210);
			//	getMob().getActionSender().sendSound(getInventoryFullSound(), 100, 0);
			} else {
				getMob().getActionSender().sendMessage(getInventoryFullMessage());
			//	getMob().getActionSender().sendSound(getInventoryFullSound(), 100, 0);
			}
			getMob().playAnimation(Animation.create(-1));
			this.stop();
			return;
		}
		if (getMob().getSkills().getLevel(getSkill()) < getRequiredLevel()) {
			if (appearInChatBox()) {
				getMob().getActionSender().sendString(210, 0, getLevelTooLowMessage());
				getMob().getActionSender().sendChatboxInterface(210);
			} else {
				getMob().getActionSender().sendMessage(getLevelTooLowMessage());
			}
			getMob().playAnimation(Animation.create(-1));
			this.stop();
			return;
		}
		if (!canHarvest()) {
			this.stop();
			return;
		}
		if (getBait() > 0) {
			if (!getMob().getInventory().contains(getBait())) {
				getMob().getActionSender().sendMessage("You have run out of bait!");
				this.stop();
				return;
			}
		}
		
		
		if (!started) {
			started = true;
			getMob().getActionSender().sendMessage(getHarvestStartedMessage());
			if (getCycleCount() != 1)
			getMob().playAnimation(getAnimation());
/*			if (getSoundTimer() > 0) {
				getMob().getActionSender().sendSound(getRepeatedSound(), 1, 0);
			}*/
			
			//TODO if (getLastAnimCount() < 3)
				
			if (getGameObject() != null) { // For fishing since it has no objects as well as a safety check ;p
				getMob().face(getGameObject().getLocation().getActualLocation(getMob(), getGameObject()));

				if (getGameObject().getMaxHealth() == 0) {
					getGameObject().setMaxHealth(getNodeMaxHealth());
				}

				if (getGameObject().getCurrentHealth() < 1)
					getGameObject().setCurrentHealth(getNodeMaxHealth());


			}
			
			if (getGameObject() != null) {
				if (getGameObject().getCurrentHealth() < 1) {
					this.stop();
					return;
				}
			}
			
			if (currentCycles < 1)
			currentCycles = getFirstCycleCount(); //TODO This needs to be here, but causes small glitch
			factor = chance();
		}

		if (lastAnimation > getAnimationTimer()) { // 3 for wc, 2 for mining
			getMob().playAnimation(getAnimation()); // keeps the emote
														// playing
			if (getGameObject() != null)
				getMob().face(getGameObject().getLocation().getActualLocation(getMob(), getGameObject()));
				lastAnimation = 0;
		}

		if (lastSound > getSoundTimer()) { // 0 for wc and mining, 5 for fish?
			if (lastAnimation != 0) { // allign with animation
				//getMob().getActionSender().sendSound(getRepeatedSound(), 1, 0);
				lastSound = 0;
			}
		}

		lastSound++;
		lastAnimation++;

		if (currentCycles > 0) {
			currentCycles--;
			return;
		}

		double randomOne = ((Math.random() * 1.4) + .13);
		if (chance() > randomOne) {
			System.out.println("yes, " + chance() + " is greater than " + randomOne + ".");
			System.out.println("factor: " + factor); //formula test
			callReward = true; //allow the getReward method to be called only when needed.
			if (randomEventChance() > Math.random()) {
				//spawnRandom();
				return;
			}
			if (getCycleCount() == 1)
				getMob().playAnimation(getAnimation()); //play the animation now if you won
			factor = chance();
			missedCycles = 0;


			if (getGameObject() != null) {
				getGameObject().decreaseCurrentHealth(1);
				/**
				 * If the object has unlimited health (Rune ess) then add one health each time
				 * to prevent its depletion
				 */
				if (getGameObject().getCurrentHealth() > 1000) {
					System.out.println("unlimited health");
					getGameObject().decreaseCurrentHealth(-1); //add 1 by subtracting a negative
				}
			}
			
			reward = getReward();
			if (getSuccessfulHarvestMessage() != null)
				getMob().getActionSender().sendMessage(getSuccessfulHarvestMessage());
			getMob().getInventory().add(reward);
			callReward = false; //STOP THAT MTHERFKIN METHOD NOW SO IT LINES UP
			removeBait();
			getMob().getSkills().addExperience(getSkill(), getExperience());

			if (getGameObject() != null && getReplacementNode() != null) {
				if (getGameObject().getCurrentHealth() < 1) {
					World.getWorld().replaceObject(getGameObject(), getReplacementNode(), getNodeRespawnTimer());

					if (getHarvestedSound() > -1)
					//	getMob().getActionSender().sendSound(getHarvestedSound(), 100, 0);

					getMob().playAnimation(Animation.create(-1));
					this.stop();
					return;
				}
			}
			
			if (!getMob().getInventory().hasRoomFor(new Item(1050))) { //a santa hat is used because using the actual reward is a null at this point
				if (appearInChatBox()) {
					getMob().getActionSender().sendString(210, 0, getInventoryFullMessage()); // 0 = child
					getMob().getActionSender().sendChatboxInterface(210);
					//getMob().getActionSender().sendSound(getInventoryFullSound(), 100, 0);
				} else {
					getMob().getActionSender().sendMessage(getInventoryFullMessage());
					//getMob().getActionSender().sendSound(getInventoryFullSound(), 100, 0);
				}
				getMob().playAnimation(Animation.create(-1));
				this.stop();
				return;
			}
			currentCycles = getCycleCount();
			if (this.getCycleCount() == 1) { //Stop if only set to perform 1 cycle
				this.stop();
				return;
			}
		} else {
			getFail(); //specifically for thieving atm.
			System.out.println("No, you have missed: " + missedCycles + " times in a row." +" Your current factor is: " + factor);
			/**
			 * Small system to stop excessive failure caused by random numbers.
			 */
			switch (missedCycles) {
			case 1:
			case 2:
			case 3:
			case 4: // don't affect the ability until 5 misses
				break;
			case 5:
				factor = chance() * 1.07;
				break;
			case 6:
				factor = chance() * 1.12;
				break;
			case 7:
				factor = chance() * 1.23;
				break;
			case 8:
				factor = chance() * 1.32;
				break;
			case 9:
				factor = chance() * 1.38;
				break;
			case 10:
				factor = chance() * 1.4;
				break;
			case 12:
				factor = chance() * 1.45;
			case 15:
				factor = chance() * 1.5;
			case 18:
				factor = chance() * 1.6;
				break;
			case 20:
				factor = chance() * 2.0;
			case 30: // lol you fail
				factor = chance() * 3; //extreme safety
				break;
			}
			missedCycles++; // add one missed cycles evertime you don't get a log
			currentCycles = getCycleCount(); // reset
			return;
		}
	}
public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

/**
 * Protected allows access in this package - save memory
 */
protected boolean callReward = false;


}
