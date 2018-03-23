package com.techelevator.npgeek.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> parkList = new ArrayList<Park>();
		String getAllParks = "SELECT * FROM park ORDER BY parkname";
		Park thePark;
		SqlRowSet results = jdbcTemplate.queryForRowSet(getAllParks);
		while (results.next()) {
			thePark = mapRowToPark(results);
			parkList.add(thePark);
		}
		return parkList;
	}

	@Override
	public Park getParkByCode(String parkCode) {
		String getParkByCode = "SELECT * FROM park WHERE parkcode = ?";
		Park thePark = null;
		SqlRowSet results = jdbcTemplate.queryForRowSet(getParkByCode, parkCode);
		while (results.next()) {
			thePark = mapRowToPark(results);
		}
		return thePark;
	}
	
	private Park mapRowToPark(SqlRowSet results) {
		Park thePark = new Park();
		thePark.setParkCode(results.getString("parkcode"));
		thePark.setParkName(results.getString("parkname"));
		thePark.setState(results.getString("state"));
		thePark.setAcreage(results.getInt("acreage"));
		thePark.setElevation(results.getInt("elevationinfeet"));
		thePark.setMiles(results.getDouble("milesoftrail"));
		thePark.setCampNum(results.getInt("numberofcampsites"));
		thePark.setClimate(results.getString("climate"));
		thePark.setYear(results.getInt("yearfounded"));
		thePark.setVisitors(results.getInt("annualvisitorcount"));
		thePark.setQuote(results.getString("inspirationalquote"));
		thePark.setQuoteSource(results.getString("inspirationalquotesource"));
		thePark.setDescription(results.getString("parkdescription"));
		thePark.setFee(results.getInt("entryfee"));
		thePark.setAnimals(results.getInt("numberofanimalspecies"));
		
		return thePark;
	}

}
