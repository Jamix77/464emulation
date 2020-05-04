package org.hyperion.rs2.model.combat.npcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hyperion.rs2.clipping.RegionClipping;
import org.hyperion.rs2.model.Animation;
import org.hyperion.rs2.model.Hit;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.Mob;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Prayers;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.model.combat.CombatAction;
import org.hyperion.rs2.model.combat.impl.AbstractCombatAction;
import org.hyperion.rs2.tickable.Tickable;
import org.hyperion.rs2.util.Misc;

public class AbyssalDemon extends AbstractCombatAction {
	
	/**
	 * The singleton instance.
	 */
	private static final AbyssalDemon INSTANCE = new AbyssalDemon();
	
	/**
	 * Gets the singleton instance.
	 * @return The singleton instance.
	 */
	public static CombatAction getAction() {
		return INSTANCE;
	}
	
	/**
	 * The random number generator.
	 */
	private final Random random = new Random();
	
	private enum CombatStyle {
		MELEE,
		
		TELEPORT
	}
	
	@Override
	public void hit(final Mob attacker, final Mob victim) {
		super.hit(attacker, victim);
		
		if(!attacker.isNPC()) {
			return; //this should be an NPC!
		}
		
		NPC npc = (NPC) attacker;
		CombatStyle style = CombatStyle.MELEE;
		
		int maxHit;
		int randomHit;
		int hitDelay = 0;
		boolean blockAnimation = false;
		final int hit = 0;

		if(attacker.getLocation().isWithinDistance(attacker, victim, 1)) {
			switch(random.nextInt(2)) {
			default:
			case 0:
				style = CombatStyle.MELEE;	
				break;
			case 1:
				style = CombatStyle.TELEPORT;
				break;
			}
		}
		
		switch(style) {
		case MELEE:
			Animation anim = attacker.getAttackAnimation();
			attacker.playAnimation(anim);
			hitDelay = 1;
			blockAnimation = true;
			
			maxHit = npc.getCombatDefinition().getMaxHit();
			if(victim.getCombatState().getPrayer(Prayers.PROTECT_FROM_MELEE)) {
				maxHit = (int) (maxHit * 0.0);
			}
			randomHit = Misc.random(maxHit);
			if(randomHit > victim.getSkills().getLevel(Skills.HITPOINTS)) {
				randomHit = victim.getSkills().getLevel(Skills.HITPOINTS);
			}
			hitDelay = randomHit;
			break;
		case TELEPORT:
			final List<Location> locations = new ArrayList<Location>();
			for (int x = attacker.getLocation().getX() - 2; x < attacker.getLocation().getX() + 2; x++) {
				for (int y = attacker.getLocation().getY() - 2; y < attacker.getLocation().getY() + 2; y++) {
					if (RegionClipping.getClippingMask(x, y, attacker.getLocation().getZ()) < 1) {
						locations.add(Location.create(x, y, attacker.getLocation().getZ()));
					}
				}
			}
			//victim.playAnimation(Animation.create(7524));
			//victim.playGraphics(Graphic.create(1275));
			victim.getAttributes().put("busy", true);
			victim.resetInteractingEntity();
			World.getWorld().submit(new Tickable(3) {
				@Override
				public void execute() {
					stop();
					victim.removeAttribute("busy");
					//	victim.playAnimation(Animation.create(7526));
					//	victim.playGraphics(Graphic.create(1280));
					victim.setTeleportTarget(locations.get(victim.getRandom().nextInt(locations.size())));
				}
			});
		}		

		World.getWorld().submit(new Tickable(hitDelay) {
			@Override
			public void execute() {
				victim.inflictDamage(new Hit(hit), attacker);
				smite(attacker, victim, hit);
				recoil(attacker, victim, hit);
				this.stop();
			}			
		});
		vengeance(attacker, victim, hit, 1);

		victim.getActiveCombatAction().defend(attacker, victim, blockAnimation);
	}

	@Override
	public int distance(Mob attacker) {
		return 1;
	}
}