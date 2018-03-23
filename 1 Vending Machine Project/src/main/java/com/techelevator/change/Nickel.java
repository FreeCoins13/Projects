package com.techelevator.change;

public class Nickel implements Coin {
	
	@Override
	public String getName() {
		return "Nickel";
	}

	@Override
	public int getValue() {
		return 5;
	}
}
