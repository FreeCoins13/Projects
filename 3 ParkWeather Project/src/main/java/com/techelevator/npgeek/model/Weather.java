package com.techelevator.npgeek.model;

public class Weather {
	
	private String parkCode;
	private int dayNum;
	private int low;
	private int high;
	private String forecast;
	
	public String getParkCode() {
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}
	public int getDayNum() {
		return dayNum;
	}
	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public String getForecast() {
		return forecast;
	}
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
	
	public String getAdvisory() {
		
		String result = "";
		switch(this.getForecast()) {
		case "snow": result="Pack some snowshoes! ";
		break;
		case "rain": result="Pack your raingear and wear rainboots! ";
		break;
		case "thunderstorms": result="Seek shelter and avoid hiking on exposed ridges! ";
		break;
		case "sun": result="Pack some sunblock! ";
		break;
		}
	
		int high = this.getHigh();
		int low = this.getLow();
		
		if (high > 75) {
			result += "Don't forget to bring an extra gallon of water! ";
		}
		if (low < 20) {
			result += "Don't catch hypothermia! ";
		}
		if((high - low) > 20) {
			result += "Wear breathable layers!";
		}
		
		return result;
	}
	

}
