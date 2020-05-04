package org.hyperion.rs2.model.container;

public class BankInfo {
	
	public int[] tempPinNumber2 = new int[4];
	public int[] tempPinNumber = new int[4];
	public boolean pinEntered = false;
	public boolean havePin = false;
	public boolean enterAgain = false;
	public int pinNumber[] = new int[4];
	public State state = State.FIRST;
	public int number[] = new int[10];
	public int childId[] = new int[10];

}
