package com.techelevator.npgeek;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.techelevator.npgeek.model.JDBCParkDAO;
import com.techelevator.npgeek.model.ParkDAO;
import com.techelevator.npgeek.model.Weather;
import com.techelevator.npgeek.model.WeatherDAO;

@Controller
@SessionAttributes({"temperature", "parkCode"})
public class parkController {
	
	ParkDAO parkDAO;
	WeatherDAO weatherDAO;
	String fahrenheit = "fahrenheit";
	String celcius = "celcius";
	
	@Autowired
	public parkController(ParkDAO parkDAO, WeatherDAO weatherDAO) {
		this.parkDAO = parkDAO;
		this.weatherDAO = weatherDAO;
	}
	
	@RequestMapping(path="/", method=RequestMethod.GET)
	public String displayHomePage(ModelMap modelMap) {
		modelMap.addAttribute("parks", parkDAO.getAllParks());
		return "homePage";
		}
	
	@RequestMapping(path="/parkDetail", method=RequestMethod.GET)
	public String displayParkDetail(@RequestParam String parkCode, ModelMap modelMap, @RequestParam(value="temperature", required=false) String temperature) {
		modelMap.addAttribute("park", parkDAO.getParkByCode(parkCode));
		
		List<Weather> advisoryList = new ArrayList<Weather>();
		advisoryList = weatherDAO.getWeatherByParkCode(parkCode);
		modelMap.addAttribute("advisoryList",advisoryList);
		
		if (temperature != null) {
			modelMap.addAttribute("temperature", temperature);
		}
		modelMap.addAttribute("parkCode", parkCode);
		String userchoice = (String)modelMap.get("temperature");
		
		this.setTemperatureScaleByUserChoice(userchoice, parkCode, modelMap);
		
		return "parkDetail";
		}
	
	public void setTemperatureScaleByUserChoice (String userchoice, String parkCode, ModelMap modelMap) {
		
		List<Weather> weatherList = new ArrayList<Weather>();
		weatherList = weatherDAO.getWeatherByParkCode(parkCode);
		
		//check session attribute to see if the user has selected celcius or fahrenheit
		if  (userchoice == null) {
			modelMap.addAttribute("temperature", fahrenheit);
			modelMap.addAttribute("weatherList", weatherList);
			modelMap.addAttribute("temp", "°F");
		} else if (userchoice.equals(celcius)) {
			for (Weather weather : weatherList) {
				
				int fahrenheit = weather.getHigh();
				int fahrenheitlow = weather.getLow();
				weather.setHigh(convertToCelcius(fahrenheit));
				weather.setLow(convertToCelcius(fahrenheitlow));
			}
			modelMap.addAttribute("weatherList", weatherList);
			modelMap.addAttribute("temp", "°C");
		} else {
			modelMap.addAttribute("weatherList", weatherList);
			modelMap.addAttribute("temp", "°F");
		}		
		
	}

	
	public int convertToCelcius(int fahrenheit) {
		int celcius = (fahrenheit - 32) * 5 / 9;
		return celcius;
	}

}
