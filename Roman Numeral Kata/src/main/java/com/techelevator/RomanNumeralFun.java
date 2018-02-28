package com.techelevator;

import java.util.Scanner;
import com.techelevator.KataRomanNumerals;

public class RomanNumeralFun {

	public static void main(String[] args) {
		
		Scanner userInput = new Scanner(System.in);
		boolean keepGoing = true;
		
		while (keepGoing) {
		
			System.out.println("Please enter a number to convert to roman numeral:");
			int userNumber = userInput.nextInt(); //this needs a nextLine
			userInput.nextLine();
			System.out.println(userNumber + " in Roman Numerals is: " + convertToRoman(userNumber));
			
			System.out.println("Want to enter another number? Enter y to continue or n to stop.");
			String choice = userInput.nextLine();
			if (choice.contentEquals("y")) {
				continue;
			} else {
				keepGoing = false;
			}
		}
		System.out.println("Thanks for playing!");
		userInput.close();
	}
		
		public static String convertToRoman (int number) {
			
			String result = "";
			
			
			while (number >= 1000) {
				result += "M";
				number -= 1000;
			}
			while (number >= 500) {
				
				if (number >= 900) {
					result += "CM";
					number -= 900;
					continue;
				}
				
				result += "D";
				number -= 500;
			}
			while (number >= 100) {
				
				if (number >= 400) {
					result += "CD";
					number -= 400;
					continue;
				}
				result += "C";
				number -= 100;
			}
			while (number >= 50) {
				
				if (number >= 90) {
					result += "XC";
					number -= 90;
					continue;
				}
				result += "L";
				number -= 50;
			}
			
			while (number >= 10) {
				
				if (number >= 40) {
					result += "XL";
					number -= 40;
					continue;
				}
				result += "X";
				number -= 10;
			}
			
			while (number >= 5) {
				
				if (number >= 9) {
					result += "IX";
					number -= 9;
					continue;
				}
				result += "V";
				number -= 5;
			}
			
			while (number >= 1) {
				
				if (number >= 4) {
					result += "IV";
					number -= 4;
					continue;
				}
				result += "I";
				number -= 1;
			}
			
				
			return result;
		}

}
