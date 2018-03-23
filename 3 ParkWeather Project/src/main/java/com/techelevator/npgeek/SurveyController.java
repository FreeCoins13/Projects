package com.techelevator.npgeek;

import org.springframework.beans.factory.annotation.Autowired;

import com.techelevator.npgeek.model.ParkDAO;
import com.techelevator.npgeek.model.Survey;
import com.techelevator.npgeek.model.SurveyDAO;
import com.techelevator.npgeek.model.SurveyResult;
import com.techelevator.npgeek.model.WeatherDAO;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.techelevator.npgeek.model.JDBCParkDAO;


@Controller
public class SurveyController {
	
	ParkDAO parkDAO;
	WeatherDAO weatherDAO;
	SurveyDAO surveyDAO;
	
	@Autowired
	public SurveyController(ParkDAO parkDAO, WeatherDAO weatherDAO, SurveyDAO surveyDAO) {
		this.parkDAO = parkDAO;
		this.weatherDAO = weatherDAO;
		this.surveyDAO = surveyDAO;
	}
	
	@RequestMapping(path="/surveyInput", method=RequestMethod.GET)
	public String displaySurveyInput (ModelMap modelMap, Model modelHolder) {
		if (! modelHolder.containsAttribute("survey")) {
			modelHolder.addAttribute("survey", new Survey());
		}
		
		modelMap.addAttribute("parks", parkDAO.getAllParks());
		List<String> states = getStates();
		modelMap.addAttribute("states", states);	
		
		return "surveyInput";
	}
	
	@RequestMapping(path="/surveyInput", method=RequestMethod.POST)
	public String submitSurvey (@Valid @ModelAttribute("survey") Survey survey, 
								BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			return "surveyInput";
		}
	 //do we need to do anything here with survey?
		
		surveyDAO.saveSurvey(survey.getParkCode(), survey.getEmail(), survey.getState(), survey.getActivityLevel());
		
		
		return "redirect:/surveyOutput";
	}
	
	@RequestMapping(path="surveyOutput", method=RequestMethod.GET)
	public String displaySurveyOutput(ModelMap map) {
		List <SurveyResult> surveyResult = new ArrayList<SurveyResult>();
		surveyResult = surveyDAO.getAllSurveyResults();
		
		map.addAttribute("surveyResultList", surveyResult);
		return "surveyOutput";
	}
	
	public List<String> getStates() {
		List<String> states = new ArrayList<String>();
		states.add("");
		states.add("AL");
		states.add("AK");
		states.add("AZ");
		states.add("AR");
		states.add("CA");
		states.add("CO");
		states.add("CT");
		states.add("DE");		
		states.add("FL");
		states.add("GA");
		states.add("HI");
		states.add("ID");
		states.add("IL");
		states.add("IN");
		states.add("IA");
		states.add("KS");				
		states.add("KY");
		states.add("LA");
		states.add("ME");
		states.add("MD");
		states.add("MA");
		states.add("MI");
		states.add("MN");
		states.add("MS");		
		states.add("MO");
		states.add("MT");
		states.add("NE");
		states.add("NV");
		states.add("NH");
		states.add("NJ");
		states.add("NM");
		states.add("NY");			
		states.add("NC");
		states.add("ND");
		states.add("OH");
		states.add("OK");
		states.add("OR");		
		states.add("PA");
		states.add("RI");
		states.add("SC");
		states.add("SD");
		states.add("TN");
		states.add("TX");
		states.add("UT");
		states.add("VT");				
		states.add("VA");
		states.add("WA");
		states.add("WV");
		states.add("WI");
		states.add("WY");
		states.add("other");
		return states;
	}

}
