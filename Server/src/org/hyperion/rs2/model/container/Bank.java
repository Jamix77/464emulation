package org.hyperion.rs2.model.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hyperion.rs2.model.BankScreenType;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.ItemDefinition;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.container.impl.InterfaceContainerListener;
import org.hyperion.rs2.util.Misc;

/**
 * Banking utility class.
 * @author Graham Edgecombe
 *
 */
public class Bank {
	
	private int pinAppendYear = -1, pinAppendDay = -1;
	
	public static int random(int Min, int Max) {
		int i = Max;
		if (Max < Min) {
			Max = Min;
			Min = i;
		}
		return Min + (int)(Math.random() * ((Max - Min) + 1));
	}
	
	/**
	 * The bank size.
	 */
	public static final int SIZE = 352;
	
	/**
	 * The player inventory interface.
	 */
	public static final int PLAYER_INVENTORY_INTERFACE = 15;

	/**
	 * The bank inventory interface.
	 */
	public static final int BANK_INVENTORY_INTERFACE = 12;
	
	/**
	 * The deposit box interface.
	 */
	public static final int DEPOSIT_BOX_INTERFACE = 11;
	
	/**
	 * Opens the bank for the specified player.
	 * @param player The player to open the bank for.
	 */
	
	public static void opena(Player player) {
		player.getBank().shift();
		player.getActionSender().sendInterface(BANK_INVENTORY_INTERFACE, false);
		player.getActionSender().sendInterfaceInventory(PLAYER_INVENTORY_INTERFACE);
		player.getInterfaceState().addListener(player.getBank(), new InterfaceContainerListener(player, BANK_INVENTORY_INTERFACE, 89, -1));
		player.getInterfaceState().addListener(player.getInventory(), new InterfaceContainerListener(player, PLAYER_INVENTORY_INTERFACE, 0, 93));
	}
	
	public static void open(Player player, BankScreenType type) {
		player.getActionSender().removeInterface();
		switch(type) {
			case BANK:
				if(!player.pins.pinEntered && player.pins.havePin) {
					open(player, BankScreenType.BANK_PIN_ENTER);
					return;
				}
				player.getActionSender().sendInterface(BANK_INVENTORY_INTERFACE, false);
				player.getActionSender().sendInterfaceInventory(PLAYER_INVENTORY_INTERFACE);
				player.getInterfaceState().addListener(player.getBank(), new InterfaceContainerListener(player, BANK_INVENTORY_INTERFACE, 89, -1));
				player.getInterfaceState().addListener(player.getInventory(), new InterfaceContainerListener(player, PLAYER_INVENTORY_INTERFACE, 0, 93));;
				break;
			case BANK_PIN_ENTER:	
				player.getActionSender().sendString(13, 150, "Bank of 464");
				player.getActionSender().sendString(13, 151, "First click the FIRST digit.");
				randomizeNumbers(player);
				if(!player.pins.havePin) {
					player.getActionSender().sendString(13, 147, "Please choose your FOUR DIGIT PIN using the buttons below.");
					player.getActionSender().sendComponentPosition(13, 148, 1000, 1000);
				};
				player.getActionSender().sendInterface(13, false);
				break;
			case BANK_PIN_SETTINGS:
				/*for(int i = 0; i < 238; i++) {
					player.getActionSender().sendString(""+i, 14, i);
				}*/
				player.getActionSender().sendString(14, 158, "<br>No PIN set");
				player.getActionSender().sendString(14, 160, "3 days");
				player.getActionSender().sendString(14, 102, "Customers are reminded");
				player.getActionSender().sendString(14, 103, "that they should NEVER");
				player.getActionSender().sendString(14, 104, "tell anyone their Bank");
				player.getActionSender().sendString(14, 105, "PINs or passwords, nor");
				player.getActionSender().sendString(14, 106, "should they ever enter");
				player.getActionSender().sendString(14, 107, " their PINs on any website");
				player.getActionSender().sendString(14, 108, " form.");
				
				player.getActionSender().sendString(14, 110, "Have you read the PIN");
				player.getActionSender().sendString(14, 111, "Frequently Asked");
				player.getActionSender().sendString(14, 112, "Questions on the");
				player.getActionSender().sendString(14, 113, "website?");
				player.getActionSender().sendComponentPosition(14, 225, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 230, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 133, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 134, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 135, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 132, 1000, 1000);
				player.getActionSender().sendInterface(14, false);
				break;
			case BANK_PIN_PENDING:
				player.getActionSender().sendComponentPosition(14, 225, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 230, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 149, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 148, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 147, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 151, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 150, 1000, 1000);
				player.getActionSender().sendInterface(14, false);
				break;
			case BANK_PIN_ACTIVE:
				player.getActionSender().sendString(14, 158, "You have a Bank PIN");
				player.getActionSender().sendString(14, 160, "3 days");
				player.getActionSender().sendString(14, 102, "Customers are reminded");
				player.getActionSender().sendString(14, 103, "that they should NEVER");
				player.getActionSender().sendString(14, 104, "tell anyone their Bank");
				player.getActionSender().sendString(14, 105, "PINs or passwords, nor");
				player.getActionSender().sendString(14, 106, "should they ever enter");
				player.getActionSender().sendString(14, 107, " their PINs on any website");
				player.getActionSender().sendString(14, 108, " form.");
				
				player.getActionSender().sendString(14, 110, "Have you read the PIN");
				player.getActionSender().sendString(14, 111, "Frequently Asked");
				player.getActionSender().sendString(14, 112, "Questions on the");
				player.getActionSender().sendString(14, 113, "website?");
				player.getActionSender().sendComponentPosition(14, 230, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 225, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 130, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 135, 1000, 1000);
				player.getActionSender().sendComponentPosition(14, 131, 1000, 1000);
				player.getActionSender().sendInterface(14, false);
				break;
			case BANK_PIN_DELETE:
				
			case BANK_PIN_CONFIRM:
				player.getActionSender().sendString(13, 150, "Bank of Runescape");
				player.getActionSender().sendString(13, 151, "First click the FIRST digit.");
				randomizeNumbers(player);
				//for(int i = 0; i < 152; i++) {
				//player.getActionSender().sendString(13, i, ""+i);
				//}
				if(!player.pins.havePin) {
					player.getActionSender().sendString(13, 147, "Please choose your FOUR DIGIT PIN using the buttons below.");
					player.getActionSender().sendComponentPosition(13, 148, 1000, 1000);
				};
				player.getActionSender().sendInterface(13, false);
				break;
		}
	}
	
	public static void clickNumber(Player player, int button) {	
		player.getActionSender().sendString(13, 150, "Bank of Runescape");
		if(!player.pins.havePin)
			player.getActionSender().sendComponentPosition(13, 148, 1000, 1000);
		if(!player.pins.havePin) {
			if(!player.pins.enterAgain) {
				player.getActionSender().sendString(13, 147, "Please choose your FOUR DIGIT PIN using the buttons below.");
				if(player.pins.state == State.FIRST) {
					for(int i = 0; i < 10; i++) {
						if(player.pins.childId[i] == (button+10)) {
							player.pins.tempPinNumber[0] = player.pins.number[i];
						}
					}
					player.getActionSender().sendString(13, 151, "Now click the SECOND digit.");
					player.getActionSender().sendString(13, 140, "*");
					player.pins.state = State.SECOND;
				} else if(player.pins.state == State.SECOND) {
					for(int i = 0; i < 10; i++) {
						if(player.pins.childId[i] == (button+10)) {
							player.pins.tempPinNumber[1] = player.pins.number[i];
						}
					}
					player.getActionSender().sendString(13, 151, "Time for the THIRD digit.");
					player.getActionSender().sendString(13, 141, "*");
					player.pins.state = State.THIRD;
				} else if(player.pins.state == State.THIRD) {
					for(int i = 0; i < 10; i++) {
						if(player.pins.childId[i] == (button+10)) {
							player.pins.tempPinNumber[2] = player.pins.number[i];
						}
					}
					player.getActionSender().sendString(13, 151, "Finally, the FOURTH digit.");
					player.getActionSender().sendString(13, 142, "*");
					player.pins.state = State.FOURTH;
				} else if(player.pins.state == State.FOURTH) {
					for(int i = 0; i < 10; i++) {
						if(player.pins.childId[i] == (button+10)) {
							player.pins.tempPinNumber[3] = player.pins.number[i];
						}
					}
					player.pins.state = State.FIRST;
					for(int i = 140; i <= 143; i++)
						player.getActionSender().sendString(13, i, "?");
					player.pins.enterAgain = true;
					clickNumber(player, -1);
					player.getActionSender().sendString(13, 151, "First click the FIRST digit.");
				}
				randomizeNumbers(player);
			} else if(player.pins.enterAgain) {
				player.getActionSender().sendString(13, 147, "Now please enter that number again!");
				if(player.pins.state == State.FIRST) {
					player.getActionSender().sendString(13, 151, "Now click the SECOND digit.");
					if(button != -1) {
						for(int i = 0; i < 10; i++) {
							if(player.pins.childId[i] == (button+10)) {
								player.pins.tempPinNumber2[0] = player.pins.number[i];
							}
						}
						player.getActionSender().sendString(13, 140, "*");
						player.pins.state = State.SECOND;
					}
				} else if(player.pins.state == State.SECOND) {
					for(int i = 0; i < 10; i++) {
						if(player.pins.childId[i] == (button+10)) {
							player.pins.tempPinNumber2[1] = player.pins.number[i];
						}
					}
					player.getActionSender().sendString(13, 151, "Time for the THIRD digit.");
					player.getActionSender().sendString(13, 141, "*");
					player.pins.state = State.THIRD;
				} else if(player.pins.state == State.THIRD) {
					for(int i = 0; i < 10; i++) {
						if(player.pins.childId[i] == (button+10)) {
							player.pins.tempPinNumber2[2] = player.pins.number[i];
						}
					}
					player.getActionSender().sendString(13, 151, "Finally, the FOURTH digit.");
					player.getActionSender().sendString(13, 142, "*");
					player.pins.state = State.FOURTH;
				} else if(player.pins.state == State.FOURTH) {
					for(int i = 0; i < 10; i++) {
						if(player.pins.childId[i] == (button+10)) {
							player.pins.tempPinNumber2[3] = player.pins.number[i];
						}
					}
					for(int i = 0; i < 4; i++) {
						if(player.pins.tempPinNumber[i] != player.pins.tempPinNumber2[i]) {
							player.getActionSender().sendMessage("You've entered Bank PIN wrong!");
							player.getActionSender().removeInterface();
							return;
						}
					}
					open(player, BankScreenType.BANK_PIN_ACTIVE);
					player.pins.pinNumber = player.pins.tempPinNumber;
					player.pins.havePin = true;
					return;
				}
				player.getActionSender().sendString(13, 150, "Bank of Runescape");
				if(!player.pins.havePin)
					player.getActionSender().sendComponentPosition(13, 148, 1000, 1000);
				if(button != -1)
				randomizeNumbers(player);
			}
		} else if(player.pins.havePin) {
			if(player.pins.state == State.FIRST) {
				for(int i = 0; i < 10; i++) {
					if(player.pins.childId[i] == (button+10)) {
						player.pins.tempPinNumber[0] = player.pins.number[i];
					}
				}
				player.getActionSender().sendString(13, 151, "Now click the SECOND digit.");
				player.getActionSender().sendString(13, 140, "*");
				player.pins.state = State.SECOND;
			} else if(player.pins.state == State.SECOND) {
				for(int i = 0; i < 10; i++) {
					if(player.pins.childId[i] == (button+10)) {
						player.pins.tempPinNumber[1] = player.pins.number[i];
					}
				}
				player.getActionSender().sendString(13, 151, "Time for the THIRD digit.");
				player.getActionSender().sendString(13, 141, "*");
				player.pins.state = State.THIRD;
			} else if(player.pins.state == State.THIRD) {
				for(int i = 0; i < 10; i++) {
					if(player.pins.childId[i] == (button+10)) {
						player.pins.tempPinNumber[2] = player.pins.number[i];
					}
				}
				player.getActionSender().sendString(13, 151, "Finally, the FOURTH digit.");
				player.getActionSender().sendString(13, 142, "*");
				player.pins.state = State.FOURTH;
			} else if(player.pins.state == State.FOURTH) {
				for(int i = 0; i < 10; i++) {
					if(player.pins.childId[i] == (button+10)) {
						player.pins.tempPinNumber[3] = player.pins.number[i];
					}
				}
				for(int i = 0; i < 4; i++) {
					if(player.pins.tempPinNumber[i] != player.pins.pinNumber[i]) {
						player.getActionSender().sendMessage("You've entered Bank PIN wrong!");
						player.getActionSender().removeInterface();
						//player.getActionSender().sendSound(1042, 1, 0);
						return;
					}
				}
				player.pins.state = State.FIRST;
				player.pins.pinEntered = true;
				open(player, BankScreenType.BANK);
				//player.getActionSender().sendSound(1040, 1, 0);
				return;
			}
			randomizeNumbers(player);
		}
	}
	
	public static void randomizeNumbers(Player player) {
		List<Integer> childsToGive = new ArrayList<Integer>();
		for(int i = 110; i < 120; i++) {
			childsToGive.add(i);
		}
		Collections.shuffle(childsToGive);
		List<Integer> numbersToGive = new ArrayList<Integer>();
		for(int i = 0; i < 10; i++) {
			numbersToGive.add(i);
		}
		Collections.shuffle(numbersToGive);
		for(int i = 0; i < 10; i++) {
			if(player.pins.number[i] != -1) {
				player.pins.childId[i] = childsToGive.get(i);
				player.pins.number[i] = numbersToGive.get(i);
			}
			player.getActionSender().sendComponentPosition(13, player.pins.childId[i], random(0, 45), random(-40, 0));
			player.getActionSender().sendString(13, player.pins.childId[i], ""+player.pins.number[i]);
		}
		
	}
	
	private int getDaysPassedSincePinChange() {
		if (Misc.getYear() == pinAppendYear) {
			return Misc.getDayOfYear() - pinAppendDay;
		} else {
			return 365 - pinAppendDay + Misc.getDayOfYear();
		}
	}
	
	
	/**
	 * Opens the deposit box for the specified player.
	 */
	public static void openDepositBox(Player player) {
		player.getActionSender().sendInterfaceInventory(DEPOSIT_BOX_INTERFACE);
		player.getInterfaceState().addListener(player.getInventory(), new InterfaceContainerListener(player, DEPOSIT_BOX_INTERFACE, 61, -1));
	}
	
	/**
	 * Withdraws an item.
	 * @param player The player.
	 * @param slot The slot in the player's inventory.
	 * @param id The item id.
	 * @param amount The amount of the item to deposit.
	 */
	public static void withdraw(Player player, int slot, int id, int amount) {
		Item item = player.getBank().get(slot);
		if(item == null) {
			return;
		}
		if(item.getId() != id) {
			return;
		}
		int transferAmount = item.getCount();
		if(transferAmount >= amount) {
			transferAmount = amount;
		} else if(transferAmount == 0) {
			return;
		}
		int newId = item.getId();
		if(player.getSettings().isWithdrawingAsNotes()) {
			if(item.getDefinition().isNoteable()) {
				newId = item.getDefinition().getNotedId();
			}
		}
		ItemDefinition def = ItemDefinition.forId(newId);
		if(def.isStackable()) {
			if(player.getInventory().freeSlots() <= 0 && player.getInventory().getById(newId) == null) {
				player.getActionSender().sendMessage("You don't have enough inventory space to withdraw that many.");
			}
		} else {
			int free = player.getInventory().freeSlots();
			if(transferAmount > free) {
				player.getActionSender().sendMessage("You don't have enough inventory space to withdraw that many.");
				transferAmount = free;
			}
		}
		if(player.getInventory().add(new Item(newId, transferAmount))) {
			int newAmount = item.getCount() - transferAmount;
			if(newAmount <= 0) {
				player.getBank().set(slot, null);
			} else {
				player.getBank().set(slot, new Item(item.getId(), newAmount));
			}
		} else {
			player.getActionSender().sendMessage("You don't have enough inventory space to withdraw that many.");
		}
	}
	
	/**
	 * Deposits an item.
	 * @param player The player.
	 * @param slot The slot in the player's inventory.
	 * @param id The item id.
	 * @param amount The amount of the item to deposit.
	 */
	public static void deposit(Player player, int slot, int id, int amount) {
		boolean inventoryFiringEvents = player.getInventory().isFiringEvents();
		player.getInventory().setFiringEvents(false);
		try {
			Item item = player.getInventory().get(slot);
			if(item == null) {
				return;
			}
			if(item.getId() != id) {
				return;
			}
			int transferAmount = player.getInventory().getCount(id);
			if(transferAmount >= amount) {
				transferAmount = amount;
			} else if(transferAmount == 0) {
				return;
			}
			boolean noted = item.getDefinition().isNoted();
			if(item.getDefinition().isStackable() || noted) {
				int bankedId = noted ? item.getDefinition().getNormalId() : item.getId();
				if(player.getBank().freeSlots() < 1 && player.getBank().getById(bankedId) == null) {
					player.getActionSender().sendMessage("You don't have enough space in your bank account.");
				}
				int newInventoryAmount = item.getCount() - transferAmount;
				Item newItem;
				if(newInventoryAmount <= 0) {
					newItem = null;
				} else {
					newItem = new Item(item.getId(), newInventoryAmount);
				}
				if(!player.getBank().add(new Item(bankedId, transferAmount))) {
					player.getActionSender().sendMessage("You don't have enough space in your bank account.");
				} else {
					player.getInventory().set(slot, newItem);
					player.getInventory().fireItemsChanged();
					player.getBank().fireItemsChanged();
				}
			} else {
				if(player.getBank().freeSlots() < transferAmount) {
					player.getActionSender().sendMessage("You don't have enough space in your bank account.");
				}
				if(!player.getBank().add(new Item(item.getId(), transferAmount))) {
					player.getActionSender().sendMessage("You don't have enough space in your bank account.");
				} else {
					for(int i = 0; i < transferAmount; i++) {
						if (amount == 1)
							player.getInventory().set(slot, null); //This fixes it for despositing 1 item
						else
							player.getInventory().set(player.getInventory().getSlotById(item.getId()), null);
					}
					player.getInventory().fireItemsChanged();
				}
			}
		} finally {
			player.getInventory().setFiringEvents(inventoryFiringEvents);
		}
	}
}
