package com.techelevator;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.npgeek.model.JDBCParkDAO;
import com.techelevator.npgeek.model.JDBCSurveyDAO;
import com.techelevator.npgeek.model.SurveyResult;

import org.junit.Assert;

public class JDBCSurveyDAOIntegrationTest extends DAOIntegrationTest {

	private JDBCSurveyDAO dao;
	private JDBCParkDAO parkDao;
	
	@Before
	public void setup() {
	
	this.dao = new JDBCSurveyDAO(getDataSource());
	this.parkDao = new JDBCParkDAO(getDataSource());
	//insert park code to avoid foreign key constraints
	String sqlInsertParkTest1 = "INSERT INTO park VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	String sqlInsertParkTest2 = "INSERT INTO park VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	String sqlInsertTest1 = "INSERT INTO survey_result VALUES (?,?,?,?,?)";
	String sqlInsertTest2 = "INSERT INTO survey_result VALUES (?,?,?,?,?)";
	String sqlInsertTest3 = 	"INSERT INTO survey_result VALUES (?,?,?,?,?)";
	
	JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
	jdbcTemplate.update(sqlInsertParkTest1, "TEST", "Test Park Name", "Test State", 
			400, 8000, 2.2, 5, "test climate", 1949, 1000, "inspirational quote test", 
			"the one and only me", "awesome park description test", 5, 100);
	jdbcTemplate.update(sqlInsertParkTest2, "TEST2", "Test Park Name2", "Test State", 
			400, 8000, 2.2, 5, "test climate", 1949, 1000, "inspirational quote test", 
			"the one and only me", "awesome park description test", 5, 100);
	
	jdbcTemplate.update(sqlInsertTest1, dao.getNextSurveyId(), "TEST", "testEmail1", "AL", "CROSSFIT");
	jdbcTemplate.update(sqlInsertTest2, dao.getNextSurveyId(), "TEST", "testEmail2", "OH", "Couch Potato");
	jdbcTemplate.update(sqlInsertTest3, dao.getNextSurveyId(), "TEST2", "testEmail3", "MN", "Opportunist Wanderer");
	
	}
	
	@Test
	public void save_survey() {
		List <SurveyResult> surveyResultList = new ArrayList<SurveyResult>();
	
		dao.saveSurvey("TEST2", "testEmail4", "MN", "Couch Potato");
		dao.saveSurvey("TEST2", "testEmail4", "MN", "Couch Potato");
		surveyResultList = dao.getAllSurveyResults();
		
		String testCode = "";
		int count = 0;
		
		for (SurveyResult surveyResult : surveyResultList) {
			 //find the index where parkCode is TEST
			if (surveyResult.getParkCode().equals("TEST2")) {
				testCode = "TEST2";
				break;
			}
			count++;
		}
		
		Assert.assertEquals("TEST2", surveyResultList.get(count).getParkCode());
		Assert.assertEquals(3, surveyResultList.get(count).getSurveySum());

	}
	
	@Test
	public void test_get_all_survey_results() {
		List <SurveyResult> surveyResultList = new ArrayList<SurveyResult>();
		
		surveyResultList = dao.getAllSurveyResults();
		
		String testCode = "";
		int count = 0;
		
		for (SurveyResult surveyResult : surveyResultList) {
			 //find the index where parkCode is TEST
			if (surveyResult.getParkCode().equals("TEST")) {
				testCode = "TEST";
				break;
			}
			count++;
		}
		
		assertNotNull(surveyResultList);
		//Assert.assertEquals(2, surveyResultList.size()); this will keep changing
		Assert.assertEquals("two test surveys for testparkcode TEST were entered",2, surveyResultList.get(count).getSurveySum());
		Assert.assertEquals("TEST", surveyResultList.get(count).getParkCode());
	}

}
