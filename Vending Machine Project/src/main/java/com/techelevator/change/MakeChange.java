package com.techelevator.change;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MakeChange {
	
	
	

	
	public String makeChange (double currentBalance) {
		Coin[] coins = new Coin[] { 	new Quarter(), new Dime(), new Nickel() };
		currentBalance *= 100;
		String result = "";
		
		Map<Coin, Integer> change = new HashMap<Coin, Integer>();
		
		for (Coin coin : coins) { //iterating through the coins Array
			
			if (currentBalance <= 0) { break; }
			int cnt = (int)currentBalance / coin.getValue();
			if (cnt > 0) {
				currentBalance = currentBalance % (coin.getValue() * cnt);
				change.put(coin, cnt);
			}
		}
		
		for (Coin coin : change.keySet()) {
			result += (change.get(coin) + " " + coin.getName() + "(s)" + " "); 
			//result += (double)coin.getValue();
		}
		//result /= 10;
		
		return result;
	}
}
