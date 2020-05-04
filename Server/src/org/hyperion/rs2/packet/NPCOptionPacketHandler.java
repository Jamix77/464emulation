package org.hyperion.rs2.packet;

import org.hyperion.rs2.Constants;
import org.hyperion.rs2.ScriptManager;
import org.hyperion.rs2.action.Action;
import org.hyperion.rs2.model.DialogueManager;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.NPCDefinition;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.model.Mob.InteractionMode;
import org.hyperion.rs2.model.combat.impl.MagicCombatAction;
import org.hyperion.rs2.model.combat.impl.MagicCombatAction.Spell;
import org.hyperion.rs2.model.combat.impl.MagicCombatAction.SpellBook;
import org.hyperion.rs2.model.combat.impl.MagicCombatAction.SpellType;
import org.hyperion.rs2.net.Packet;

/**
 * Remove item options.
 * @author Graham Edgecombe
 *
 */
public class NPCOptionPacketHandler implements PacketHandler {

	private static final int OPTION_1 = 156, OPTION_2 = 19, OPTION_ATTACK = 129, OPTION_SPELL = 69, OPTION_EXAMINE = 176;

	@Override
	public void handle(Player player, Packet packet) {
		if(player.getAttribute("cutScene") != null) {
			return;
		}
		if(player.getInterfaceAttribute("fightPitOrbs") != null) {
			return;
		}
		switch(packet.getOpcode()) {
		case OPTION_1:
			handleOption1(player, packet);
			break;
		case OPTION_2:
			handleOption2(player, packet);
			break;
		case OPTION_ATTACK:
			handleOptionAttack(player, packet);
			break;
		case OPTION_SPELL:
			handleOptionSpell(player, packet);
			break;
		case OPTION_EXAMINE:
			handleOptionExamine(player, packet);
			break;
		}
	}

	/**
	 * Handles npc option 1.
	 * @param player The player.
	 * @param packet The packet.
	 */
	private void handleOption1(final Player player, Packet packet) {
		int id = packet.getShortA() & 0xFFFF;
		if(id < 0 || id >= Constants.MAX_NPCS) {
			return;
		}
		if(player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		final NPC npc = (NPC) World.getWorld().getNPCs().get(id);

		player.getActionSender().sendDebugPacket(packet.getOpcode(), "NpcOpt1", new Object[] { "ID: " + npc.getDefinition().getId(), "Index: " + id });
		
		if(npc != null) {
			player.setInteractingEntity(InteractionMode.TALK, npc);
			Action action = new Action(player, 0) {
				@Override
				public void execute() {
					if(player.getCombatState().isDead()) {
						this.stop();
						return;
					}
					if(npc.getDefinition().getInteractionMenu()[0].startsWith("Talk")) {
						if(npc.getDefinition().getName().toLowerCase().contains("banker")) {
							DialogueManager.openDialogue(player, 0);
						} else {
							String scriptName = "talkTo" + npc.getDefinition().getId();
							if(!ScriptManager.getScriptManager().invokeWithFailTest(scriptName, player, npc)) {
								player.getActionSender().sendMessage(npc.getDefinedName() + " does not want to talk.");							
							}
						}
						npc.setInteractingEntity(InteractionMode.TALK, player);
					}
					this.stop();
				}
				@Override
				public AnimationPolicy getAnimationPolicy() {
					return AnimationPolicy.RESET_ALL;
				}
				@Override
				public CancelPolicy getCancelPolicy() {
					return CancelPolicy.ALWAYS;
				}
				@Override
				public StackPolicy getStackPolicy() {
					return StackPolicy.NEVER;
				}			
			};
			int distance = 1;
			if(npc.getDefinition().getName().toLowerCase().contains("banker")
							|| npc.getDefinition().getName().toLowerCase().contains("emily")
							|| npc.getDefinition().getName().toLowerCase().contains("zambo")) {
				distance = 2;
			}
			player.addCoordinateAction(player.getWidth(), player.getHeight(), npc.getLocation(), npc.getWidth(), npc.getHeight(), distance, action);
		}
	}
	
	/**
	 * Handles npc option 2.
	 * @param player The player.
	 * @param packet The packet.
	 */
	private void handleOption2(final Player player, Packet packet) {
		int id = packet.getLEShortA() & 0xFFFF;
		if(id < 0 || id >= Constants.MAX_NPCS) {
			return;
		}
		if(player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		final NPC npc = (NPC) World.getWorld().getNPCs().get(id);

		player.getActionSender().sendDebugPacket(packet.getOpcode(), "NpcOpt2", new Object[] { "ID: " + npc.getDefinition().getId(), "Index: " + id });
		
		if(npc != null) {
			player.setInteractingEntity(InteractionMode.TALK, npc);
			Action action = new Action(player, 0) {
				@Override
				public void execute() {
					if(player.getCombatState().isDead()) {
						this.stop();
						return;
					}
					if(npc.getDefinition().getInteractionMenu()[2].startsWith("Bank")) {
					//	Bank.open(player);
					} else {
						String scriptName = "tradeWith" + npc.getDefinition().getId();
						if(!ScriptManager.getScriptManager().invokeWithFailTest(scriptName, player, npc)) {
							player.getActionSender().sendMessage(npc.getDefinedName() + " does not want to trade.");							
						} else {
							npc.setInteractingEntity(InteractionMode.TALK, player);
						}
					}
					this.stop();
				}
				@Override
				public AnimationPolicy getAnimationPolicy() {
					return AnimationPolicy.RESET_ALL;
				}
				@Override
				public CancelPolicy getCancelPolicy() {
					return CancelPolicy.ALWAYS;
				}
				@Override
				public StackPolicy getStackPolicy() {
					return StackPolicy.NEVER;
				}			
			};
			int distance = 1;
			if(npc.getDefinition().getName().toLowerCase().contains("banker")
							|| npc.getDefinition().getName().toLowerCase().contains("emily")
							|| npc.getDefinition().getName().toLowerCase().contains("zambo")) {
				distance = 2;
			}
			player.addCoordinateAction(player.getWidth(), player.getHeight(), npc.getLocation(), npc.getWidth(), npc.getHeight(), distance, action);
		}
	}
	
	/**
	 * Handles npc attack option.
	 * @param player The player.
	 * @param packet The packet.
	 */
	private void handleOptionAttack(final Player player, Packet packet) {
		final int id = packet.getLEShort() & 0xFFFF;
		if(id < 0 || id >= Constants.MAX_NPCS) {
			return;
		}
		if(player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		final NPC npc = (NPC) World.getWorld().getNPCs().get(id);

		player.getActionSender().sendDebugPacket(packet.getOpcode(), "NpcAttack", new Object[] { "ID: " + npc.getDefinition().getId(), "Index: " + id });
		
		if(npc != null) {
		//player.forceChat("men");
			player.getCombatState().setQueuedSpell(null);
			player.getCombatState().startAttacking(npc, false);
		}
	}
	
	/**
	 * Handles npc spell option.
	 * @param player The player.
	 * @param packet The packet.
	 */
	private void handleOptionSpell(final Player player, Packet packet) {
		packet.getShort();
		int interfaceValue = packet.getInt();
		final int childButton = interfaceValue & 0xFFFF;
		int id = packet.getShort();
		if(id < 0 || id >= Constants.MAX_NPCS) {
			return;
		}
		if(player.getCombatState().isDead()) {
			return;
		}
		player.getActionQueue().clearRemovableActions();

		NPC npc = (NPC) World.getWorld().getNPCs().get(id);

		player.getActionSender().sendDebugPacket(packet.getOpcode(), "NpcSpell", new Object[] { "ID: " + npc.getDefinition().getId(), "Index: " + id, "Button: " + childButton });
		
		Spell spell = Spell.forId(childButton, SpellBook.forId(player.getCombatState().getSpellBook()));
		if(npc != null && spell != null) {
			if(spell.getSpellType() == SpellType.NON_COMBAT) {
				return;
			}
			MagicCombatAction.setAutocast(player, null, -1, false);
			player.getCombatState().setQueuedSpell(spell);
			player.getCombatState().startAttacking(npc, false);
		}
	}
	
	/**
	 * Handles npc option examine.
	 * @param player The player.
	 * @param packet The packet.
	 */
	private void handleOptionExamine(Player player, Packet packet) {
		int id = packet.getLEShortA() & 0xfff;
		if(id < 0 || id >= Constants.MAX_NPCS) {
			return;
		}
		if(player.getCombatState().isDead()) {
			return;
		}

		player.getActionSender().sendDebugPacket(packet.getOpcode(), "NpcExamine", new Object[] { "ID: " + id });
		
		NPCDefinition npcDef = NPCDefinition.forId(id);
		if(npcDef != null) {
			player.getActionSender().sendMessage(npcDef.getDescription());
		}
	}

}
