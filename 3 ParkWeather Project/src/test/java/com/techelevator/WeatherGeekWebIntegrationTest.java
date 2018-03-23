package com.techelevator;

import static org.junit.Assert.assertEquals;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class WeatherGeekWebIntegrationTest {

	private static WebDriver webDriver;
	
	@BeforeClass
	public static void openWebBrowserForTesting() {	
		String homeDir = System.getProperty("user.home");
		System.setProperty("webdriver.chrome.driver", homeDir+"/dev-tools/chromedriver/chromedriver");
		webDriver = new ChromeDriver();
	}
	
	@Before
	public void openHomePage() {
		webDriver.get("http://localhost:8080/m3-java-capstone/");
	
	}
	
	@AfterClass
	public static void closeWebBrowser() {
		webDriver.close();
	}
	
	@Test
	public void navigate_to_home_page() {
		WebElement homeLink = webDriver.findElement(By.cssSelector("h2"));
		assertEquals("Our Parks", homeLink.getText());
	}
	/*
	@Test
	public void navigate_to_Everglades_detail_page() {
		WebElement parkDetailLink = webDriver.findElement(By.linkText("Everglades National Park"));
		parkDetailLink.click();
		assertEquals("http://localhost:8080/m3-java-capstone/parkDetail?parkCode=ENP", webDriver.getCurrentUrl());
	}
	*/
	@Test
	public void change_temperature_scale_to_celcius_Everglades() {
		WebElement parkDetailLink = webDriver.findElement(By.linkText("Everglades National Park"));
		parkDetailLink.click();
		WebElement celciusLink = webDriver.findElement(By.linkText("°C"));
		celciusLink.click();
		assertEquals("High 27 °C", celciusLink.getText());
	}
	
	@Test
	public void navigate_to_survey_input_page() {
		WebElement surveyLink = webDriver.findElement(By.linkText("SURVEY"));
		surveyLink.click();
		assertEquals("http://localhost:8080/m3-java-capstone/surveyInput", webDriver.getCurrentUrl());
	}
	
	@Test
	public void submit_survey() {
		webDriver.get("http://localhost:8080/m3-java-capstone/surveyInput");
	
		WebElement favoriteNatPark = webDriver.findElement(By.xpath("/html/body/div[@id='main-content']/form[@id='survey']/select[@id='parkCode']"));
		new Select(favoriteNatPark).selectByVisibleText("Everglades National Park");
		WebElement emailField = webDriver.findElement(By.name("email"));
		emailField.sendKeys("testemail");
		WebElement state = webDriver.findElement(By.id("state"));
		new Select(state).selectByVisibleText("OH");
		WebElement activityCheckbox = webDriver.findElement(By.id("crossfit"));
		activityCheckbox.click();
		WebElement submitButton = webDriver.findElement(By.className("submit"));
		submitButton.click();
		assertEquals("http://localhost:8080/m3-java-capstone/surveyOutput", webDriver.getCurrentUrl());
	}
}
