package com.techelevator;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.npgeek.model.JDBCParkDAO;
import com.techelevator.npgeek.model.JDBCSurveyDAO;
import com.techelevator.npgeek.model.JDBCWeatherDAO;
import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.SurveyResult;
import com.techelevator.npgeek.model.Weather;

import org.junit.Assert;

public class JDBCParkDAOIntegrationTest extends DAOIntegrationTest {

private JDBCParkDAO dao;
	
	@Before
	public void setup() {
	
	this.dao = new JDBCParkDAO(getDataSource());
	String sqlInsertTest1 = "INSERT INTO park VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
	jdbcTemplate.update(sqlInsertTest1, "TEST", "Test Park Name", "Test State", 
			400, 8000, 2.2, 5, "test climate", 1949, 1000, "inspirational quote test", 
			"the one and only me", "awesome park description test", 5, 100);
	}
	
	@Test
	public void test_get_all_parks() {
		List<Park> parkList = new ArrayList<Park>();
		parkList = dao.getAllParks();
		
		Park thePark = new Park();
		
		for (Park park: parkList) {
			if (park.getParkCode().equals("TEST")) {
				thePark = park;
			}
		}
		
		Assert.assertNotNull(parkList);
		Assert.assertNotNull(thePark);
	}
	
	@Test
	public void test_get_park_by_park_code() {
		Park thePark = new Park();
		thePark = dao.getParkByCode("TEST");
		Assert.assertNotNull(thePark);
		Assert.assertEquals("Test Park Name", thePark.getParkName());
		Assert.assertEquals("Test State", thePark.getState());
		Assert.assertEquals( "awesome park description test", thePark.getDescription());
		Assert.assertEquals(100, thePark.getAnimals());
	}
	
}
