package com.techelevator.npgeek.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCSurveyDAO implements SurveyDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCSurveyDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	

	@Override
	public void saveSurvey(String parkCode, String email, String state, String activityLevel) {
		long	 surveyId = getNextSurveyId();
		String insertIntoSurvey = "INSERT INTO survey_result VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertIntoSurvey, surveyId, parkCode, email, state, activityLevel);
	}

	@Override
	public List<SurveyResult> getAllSurveyResults() {
		List<SurveyResult> surveyResultList = new ArrayList<SurveyResult>();
		String getAllSurveyResults = "SELECT survey_result.parkcode, parkname, "
				+ " count(survey_result.parkcode) AS surveycount FROM survey_result "
				+ " JOIN park ON park.parkcode = survey_result.parkcode " 
				+ " GROUP BY survey_result.parkcode, parkname ORDER BY surveycount DESC, parkname ASC";
		SurveyResult theSurveyResult = new SurveyResult();
		SqlRowSet results = jdbcTemplate.queryForRowSet(getAllSurveyResults);
		while (results.next()) {
			theSurveyResult = mapRowToSurveyResult(results);
			surveyResultList.add(theSurveyResult);
			
		}
		return surveyResultList;
	}
	
	public long getNextSurveyId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_surveyid')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		}
		else {
			throw new RuntimeException("Something went wrong with survey sequence");
		}
	}
	
	private SurveyResult mapRowToSurveyResult(SqlRowSet results) {
		SurveyResult theSurveyResult = new SurveyResult();
		theSurveyResult.setParkCode(results.getString("parkcode"));
		theSurveyResult.setParkName(results.getString("parkname"));
		theSurveyResult.setSurveySum(results.getInt("surveycount"));
		return theSurveyResult;
	}
	
	

}
