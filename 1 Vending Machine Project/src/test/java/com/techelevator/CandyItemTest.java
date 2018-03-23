package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CandyItemTest {
	
	CandyItem sparky; 
	
	@Before
	public void setup() {
		sparky = new CandyItem(); 
	}
	
	@Test
	public void test_get_and_set_names() {
		sparky.setName("fido");
		Assert.assertEquals("fido", sparky.getName());
		
	}
	
	@Test
	public void test_get_and_set_price() {
		sparky.setPrice(10.00);
		Assert.assertEquals(10.00, sparky.getPrice() , 0.0001d);
		
	}
	
	@Test
	public void test_purchase_when_quantity_greater_than_zero() {
		sparky.purchase();
		Assert.assertEquals(4, sparky.getQuantity());
	
	}
	
	@Test
	public void test_purchase_when_quantity_less_than_zero() {
		sparky.purchase();
		sparky.purchase();
		sparky.purchase();
		sparky.purchase();
		sparky.purchase();
		sparky.purchase();
		Assert.assertEquals(0, sparky.getQuantity());
	
	}
	
}
