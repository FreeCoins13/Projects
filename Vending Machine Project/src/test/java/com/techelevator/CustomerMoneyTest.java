package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerMoneyTest {

	CustomerMoney moneyBags; 
	
	@Before
	public void setup() {
		moneyBags = new CustomerMoney(); 
	}
	
	@Test
	public void test_get_and_add_money() {
		moneyBags.addToCurrent(5.00);
		moneyBags.addToCurrent(5.00);
		moneyBags.addToCurrent(5.00);
		Assert.assertEquals(15, moneyBags.getCurrentMoney(), 0.000001d);
	}
	
	@Test
	public void test_subtract_money_when_over_0() {
		moneyBags.addToCurrent(5.00);
		moneyBags.addToCurrent(5.00);
		moneyBags.subractFromCurrent(2.00);
		Assert.assertEquals(8, moneyBags.getCurrentMoney(), 0.001d);
	}
	
	@Test
	public void test_subtract_money_when_over_balance() {
		moneyBags.addToCurrent(5.00);
		moneyBags.subractFromCurrent(8.00);
		Assert.assertEquals(5, moneyBags.getCurrentMoney(), 0.001d);
	}
	
	
	
}
