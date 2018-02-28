package com.techelevator;

import org.junit.*;

public class KataRomanNumeralsTest {
	
	KataRomanNumerals test;
	
	@Before
	public void setup() {
	test = new KataRomanNumerals();
	}
	
	@Test
	public void roman_to_number_I_is_added_until_3() {
		Assert.assertEquals(1, test.convertToNumber("I"));
		Assert.assertEquals(2, test.convertToNumber("II"));
		Assert.assertEquals(3, test.convertToNumber("III"));
		
	}

	@Test
	public void roman_to_number_test_all_whole_numbers() {
		Assert.assertEquals(1000, test.convertToNumber("M"));
		Assert.assertEquals(500, test.convertToNumber("D"));
		Assert.assertEquals(100, test.convertToNumber("C"));
		Assert.assertEquals(50, test.convertToNumber("L"));
		Assert.assertEquals(10, test.convertToNumber("X"));
		Assert.assertEquals(5, test.convertToNumber("V"));
	}
	
	@Test
	public void roman_to_number_test_all_combined_numbers() {
		Assert.assertEquals(4, test.convertToNumber("IV"));
		Assert.assertEquals(9, test.convertToNumber("IX"));
		Assert.assertEquals(40, test.convertToNumber("XL"));
		Assert.assertEquals(90, test.convertToNumber("XC"));
		Assert.assertEquals(400, test.convertToNumber("CD"));
		Assert.assertEquals(900, test.convertToNumber("CM"));
	}
	
	@Test
	public void roman_to_number_test_larger_combinations() {
		Assert.assertEquals(369, test.convertToNumber("CCCLXIX"));
		Assert.assertEquals(1990, test.convertToNumber("MCMXC"));
	
	}
	
	
	@Test
	public void number_to_roman_test_whole_numbers() {
		Assert.assertEquals("M", test.convertToRoman(1000));
		Assert.assertEquals("D", test.convertToRoman(500));
		Assert.assertEquals("C", test.convertToRoman(100));
		Assert.assertEquals("L", test.convertToRoman(50));
		Assert.assertEquals("X", test.convertToRoman(10));
		Assert.assertEquals("V", test.convertToRoman(5));
		Assert.assertEquals("I", test.convertToRoman(1));
	}
	
	@Test
	public void number_to_roman_test_numbers_without_subtraction() {
		Assert.assertEquals("MDL", test.convertToRoman(1550));
		Assert.assertEquals("DCCCXXVIII", test.convertToRoman(828));
		Assert.assertEquals("MMMCCLXX", test.convertToRoman(3270));
	}
	
	@Test
	public void number_to_roman_test_numbers_with_subtraction() {
		Assert.assertEquals("CML", test.convertToRoman(950));
		Assert.assertEquals("CDL", test.convertToRoman(450));
		Assert.assertEquals("CCCLXIX", test.convertToRoman(369));
		Assert.assertEquals("MCMXC", test.convertToRoman(1990));
	}
}
