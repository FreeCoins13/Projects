package com.techelevator;

import com.techelevator.change.*;
import org.junit.Test; 
import org.junit.Before;
import org.junit.Assert; 

public class MakeChangeTest {
	
	MakeChange rupert;  
	
	@Before
		public void setup() {
		rupert = new MakeChange(); 
	}
	
	@Test
		public void expected_quarters_returned() { 
		Assert.assertEquals( "2 Quarter(s) ", rupert.makeChange(0.5));
	}
	
	@Test
	public void expected_dimes_returned() { 
	Assert.assertEquals( "2 Dime(s) ", rupert.makeChange(0.2));
}
	
	@Test
	public void expected_nickels_returned() { 
	Assert.assertEquals( "1 Nickel(s) ", rupert.makeChange(0.05));
}
	
}
