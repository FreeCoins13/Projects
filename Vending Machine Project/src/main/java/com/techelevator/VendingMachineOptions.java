package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException; 

public class VendingMachineOptions {
	
	public Map<String,MasterItem> getMapOfItems() throws FileNotFoundException { 
	
	File vmOptions = new File("/Users/sgirard/workspace/pair-exercises/java-week4-pair-exercise/m1-capstone/vendingmachine.csv"); 
		
	Map<String, MasterItem> foodOptions = new LinkedHashMap<String, MasterItem>(); //how we get the names, price, slot number for our food options
		
	String options = ""; 	
		
	try(Scanner fileScanner = new Scanner(vmOptions)) {
	
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine(); 
			String[] temp = line.split("\\|"); 
			
				switch (temp[0].charAt(0)) {
				case 'A': 
					ChipItem chips = new ChipItem(); 
					chips.setName(temp[1]);
					chips.setSlot(temp[0]);
					chips.setPrice(Double.parseDouble(temp[2]));
					foodOptions.put(temp[0], chips); 
					break; 
					
				case 'B': 
					CandyItem candy = new CandyItem(); 
					candy.setName(temp[1]);
					candy.setSlot(temp[0]);
					candy.setPrice(Double.parseDouble(temp[2]));
					foodOptions.put(temp[0], candy); 
					break;
					
				case 'C': 
					DrinkItem drink = new DrinkItem(); 
					drink.setName(temp[1]);
					drink.setSlot(temp[0]);
					drink.setPrice(Double.parseDouble(temp[2]));
					foodOptions.put(temp[0], drink); 
					break; 
					
				case 'D': 
					GumItem gum = new GumItem(); 
					gum.setName(temp[1]);
					gum.setSlot(temp[0]);
					gum.setPrice(Double.parseDouble(temp[2]));
					foodOptions.put(temp[0], gum); 
					break;
					
				default: 
					System.out.println("Invalid source file!");
			}
		}
	}
	return foodOptions;
	}
}

