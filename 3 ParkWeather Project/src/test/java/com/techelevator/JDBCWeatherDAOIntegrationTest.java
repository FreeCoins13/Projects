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
import com.techelevator.npgeek.model.SurveyResult;
import com.techelevator.npgeek.model.Weather;

import org.junit.Assert;

public class JDBCWeatherDAOIntegrationTest extends DAOIntegrationTest {

	private JDBCWeatherDAO dao;
	private JDBCParkDAO parkDao;
	
	@Before
	public void setup() {
	
	this.dao = new JDBCWeatherDAO(getDataSource());
	this.parkDao = new JDBCParkDAO(getDataSource());
	//insert park code to avoid foreign key constraints
	String sqlInsertParkTest1 = "INSERT INTO park VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	String sqlInsertWeatherTest1 = "INSERT INTO weather VALUES (?,?,?,?,?)";
	String sqlInsertWeatherTest2 = "INSERT INTO weather VALUES (?,?,?,?,?)";
	String sqlInsertWeatherTest3 = "INSERT INTO weather VALUES (?,?,?,?,?)";
	String sqlInsertWeatherTest4 = "INSERT INTO weather VALUES (?,?,?,?,?)";
	String sqlInsertWeatherTest5 = "INSERT INTO weather VALUES (?,?,?,?,?)";


	JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
	jdbcTemplate.update(sqlInsertParkTest1, "TEST", "Test Park Name", "Test State", 
			400, 8000, 2.2, 5, "test climate", 1949, 1000, "inspirational quote test", 
			"the one and only me", "awesome park description test", 5, 100);
	jdbcTemplate.update(sqlInsertWeatherTest1, "TEST", 1, 22, 60, "sunny");
	jdbcTemplate.update(sqlInsertWeatherTest2, "TEST", 2, 22, 60, "rainy");
	jdbcTemplate.update(sqlInsertWeatherTest3, "TEST", 3, 22, 60, "partly cloudy");
	jdbcTemplate.update(sqlInsertWeatherTest4, "TEST", 4, 22, 60, "thunderstorms");
	jdbcTemplate.update(sqlInsertWeatherTest5, "TEST", 5, 22, 60, "sunny");
	}
	
	@Test
	public void test_get_weather_by_park_code () {
		List <Weather> weatherResultList = new ArrayList<Weather>();
	
		weatherResultList = dao.getWeatherByParkCode("TEST");
		
		Assert.assertNotNull(weatherResultList);
		Assert.assertEquals("sunny", weatherResultList.get(0).getForecast());
		Assert.assertEquals("rainy", weatherResultList.get(1).getForecast());
		Assert.assertEquals("partly cloudy", weatherResultList.get(2).getForecast());
		Assert.assertEquals("thunderstorms", weatherResultList.get(3).getForecast());
		Assert.assertEquals("sunny", weatherResultList.get(4).getForecast());
	}
}
