package com.techelevator;

public class CustomerMoney {
	
	private double currentMoney = 0.00;

	
	public double getCurrentMoney() {
		return currentMoney;
	}

	public void addToCurrent(double addMoney) {
		this.currentMoney += addMoney;
	}
	
	public void subractFromCurrent(double subtractAmount) {
			 if (subtractAmount <= this.currentMoney) {
				 this.currentMoney -= subtractAmount;
		}
	}
	
	//I think if we put a scanner in here, we can reference it without breaking the menu scanner

}
