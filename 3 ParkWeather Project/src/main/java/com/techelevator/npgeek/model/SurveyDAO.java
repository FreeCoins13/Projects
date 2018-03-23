package com.techelevator.npgeek.model;

import java.util.List;

public interface SurveyDAO {
	
	public void saveSurvey(String parkCode, String email, String state, String activityLevel);
	
	public List<SurveyResult> getAllSurveyResults();

}
