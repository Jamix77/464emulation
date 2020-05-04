importPackage(org.hyperion.rs2.model)
importPackage(org.hyperion.rs2.model.skills.crafting)

function tradeWith2824(player, npc) {
	TannedLeather.tradeEllis(player);
}
function talkTo598(player, npc) {
	DialogueManager.openDialogue(player, 124);
}

function tradeWith598(player, npc) {
	DialogueManager.openDialogue(player, 126);
}

function talkTo5507(player, npc) {
	DialogueManager.openDialogue(player, 2300);
}
function talkTo599(player, npc) {
	DialogueManager.openDialogue(player, 133);
}

function talkTo520(player, npc) {
	DialogueManager.openDialogue(player, 57);
}

function talkTo541(player, npc) {
	DialogueManager.openDialogue(player, 214);
}

function tradeWith541(player, npc) {
	Shop.open(player, 21, 1);
}

function tradeWith545(player, npc) {
	Shop.open(player, 22, 1);
}

function tradeWith520(player, npc) {
	Shop.open(player, 0, 1);
}

function talkTo736(player, npc) {
	DialogueManager.openDialogue(player, 137);
}

function talkTo577(player, npc) {
	DialogueManager.openDialogue(player, 140);
}

function tradeWith577(player, npc) {
	Shop.open(player, 4, 1);
}

function talkTo580(player, npc) {
	DialogueManager.openDialogue(player, 143);
}

function tradeWith580(player, npc) {
	Shop.open(player, 5, 1);
}

function talkTo581(player, npc) {
	DialogueManager.openDialogue(player, 146);
}

function tradeWith581(player, npc) {
	Shop.open(player, 6, 1);
}

function talkTo594(player, npc) {
	DialogueManager.openDialogue(player, 149);
}

function tradeWith594(player, npc) {
	Shop.open(player, 7, 1);
}

function talkTo579(player, npc) {
	DialogueManager.openDialogue(player, 152);
}

function tradeWith579(player, npc) {
	Shop.open(player, 8, 1);
}

function talkTo583(player, npc) {
	DialogueManager.openDialogue(player, 155);
}

function tradeWith583(player, npc) {
	Shop.open(player, 9, 1);
}

function talkTo559(player, npc) {
	DialogueManager.openDialogue(player, 158);
}

function tradeWith559(player, npc) {
	Shop.open(player, 10, 1);
}

function talkTo558(player, npc) {
	DialogueManager.openDialogue(player, 161);
}

function tradeWith558(player, npc) {
	Shop.open(player, 11, 1);
}

function talkTo557(player, npc) {
	DialogueManager.openDialogue(player, 164);
}

function tradeWith557(player, npc) {
	Shop.open(player, 12, 1);
}

function talkTo376(player, npc) {
	DialogueManager.openDialogue(player, 167);
}

function tradeWith376(player, npc) {
	DialogueManager.openDialogue(player, 168);
}

function talkTo377(player, npc) {
	DialogueManager.openDialogue(player, 171);
}

function tradeWith377(player, npc) {
	DialogueManager.openDialogue(player, 172);
}

function talkTo380(player, npc) {
	DialogueManager.openDialogue(player, 171);
}

function tradeWith380(player, npc) {
	DialogueManager.openDialogue(player, 172);
}

function talkTo1303(player, npc) {
	if(player.completedFremennikTrials()) {
		DialogueManager.openDialogue(player, 174);
	} else {
		DialogueManager.openDialogue(player, 181);
	}	
}

function tradeWith1303(player, npc) {
	if(player.completedFremennikTrials()) {
		Shop.open(player, 13, 1);
	} else {
		player.getActionSender().sendMessage("You must complete the Fremennik Trials minigame before accessing this shop.");
	}
}

function talkTo1369(player, npc) {
	DialogueManager.openDialogue(player, 177);
}

function tradeWith1369(player, npc) {
	Shop.open(player, 14, 1);
}

function talkTo1301(player, npc) {
	DialogueManager.openDialogue(player, 188);
}

function tradeWith1301(player, npc) {
	Shop.open(player, 15, 1);
}

function talkTo549(player, npc) {
	DialogueManager.openDialogue(player, 191);
}

function tradeWith549(player, npc) {
	Shop.open(player, 16, 1);
}

function talkTo379(player, npc) {
	DialogueManager.openDialogue(player, 197);
}

function talkTo568(player, npc) {
	DialogueManager.openDialogue(player, 201);
}

function tradeWith568(player, npc) {
	Shop.open(player, 18, 1);
}

function talkTo2620(player, npc) {
	DialogueManager.openDialogue(player, 204);
}

function tradeWith2620(player, npc) {
	Shop.open(player, 19, 1);
}

function talkTo2623(player, npc) {
	DialogueManager.openDialogue(player, 207);
}

function tradeWith2623(player, npc) {
	Shop.open(player, 20, 1);
}

function talkTo2619(player, npc) {
	DialogueManager.openDialogue(player, 210);
}