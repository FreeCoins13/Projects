package com.techelevator;

public class DrinkItem implements MasterItem {
	
	private String name = ""; 
	private String slot = ""; 
	private double price = 0.00;
	private int quantity = 5; 

	@Override
	public String getName() {
		if (name.length() <= 18) {
			return name + "                 ";
		} else {
		return name;
		}
	}

	@Override
	public void setName(String name) {
		this.name = name; 
	}

	@Override
	public String getSlot() {
		return slot;
	}

	@Override
	public void setSlot(String slot) {
		this.slot = slot; 
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public void setPrice(double price) {
		this.price = price; 
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public String consume() {
		return "Glug Glug, Yum!";
	}

	@Override
	public void purchase() {
		if (this.quantity > 0) {
			this.quantity -= 1; 
		}
	}
	
	public String purchaseAndMessage(String getName) {
		
		String message ="";
		if (this.quantity == 0) {
			message += "This item is SOLD OUT";
		} else {
			message += "You selected " + getName;
			this.quantity -= 1; 
		}
		return message;
	}

}
