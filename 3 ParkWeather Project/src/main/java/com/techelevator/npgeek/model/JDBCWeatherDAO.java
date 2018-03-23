package com.techelevator.npgeek.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCWeatherDAO implements WeatherDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCWeatherDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Weather> getWeatherByParkCode(String parkCode) {
		List<Weather> forecastList = new ArrayList<Weather>();
		String getWeatherByParkCode = "SELECT * FROM weather WHERE parkcode = ? ORDER BY fivedayforecastvalue ASC";
		Weather theWeather = null;
		SqlRowSet results = jdbcTemplate.queryForRowSet(getWeatherByParkCode, parkCode);
		while (results.next()) {
			theWeather = mapRowToWeather(results);
			forecastList.add(theWeather);
		}
		
		return forecastList;
	}
	
	private Weather mapRowToWeather(SqlRowSet results) {
		Weather theWeather = new Weather();
		theWeather.setParkCode(results.getString("parkcode"));
		theWeather.setDayNum(results.getInt("fivedayforecastvalue"));
		theWeather.setLow(results.getInt("low"));
		theWeather.setHigh(results.getInt("high"));
		theWeather.setForecast(results.getString("forecast"));
		
		return theWeather;
	}
	
	

}
