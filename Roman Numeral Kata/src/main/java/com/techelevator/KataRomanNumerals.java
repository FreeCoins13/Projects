package com.techelevator;

public class KataRomanNumerals {

	public int convertToNumber(String numeral) {
		
		int result = 0;
		
		String fakeString = numeral + "Z";
		
		for (int i = 0; i < fakeString.length()-1; i++) {
			
			char oneChar = fakeString.charAt(i);
			String one = String.valueOf(oneChar);
			
			char twoChar = fakeString.charAt(i+1);
			String two = String.valueOf(twoChar);		
					
			String combined = one + two;
			
			switch (combined) {
			case "IV" : result += 4; i++; break;
			case "IX" : result += 9; i++; break;
			case "XL" : result += 40; i++; break;
			case "XC" : result += 90; i++; break;
			case "CD" : result += 400; i++; break;
			case "CM" : result += 900; i++; break;
			
			default : result += 0; 
			
				switch (one) {
				case "M" : result += 1000; break;
				case "D" : result += 500; break;
				case "C" : result += 100; break;
				case "L" : result += 50; break;
				case "X" : result += 10; break;
				case "V" : result += 5; break;
				case "I" : result += 1; break;
				
				default : result += 0; break;
			
				} //end second switch
				
			}//end first switch
				
		} // end for loop
			
			return result;
	}

	public String convertToRoman (int number) {
		
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
	
	
 
