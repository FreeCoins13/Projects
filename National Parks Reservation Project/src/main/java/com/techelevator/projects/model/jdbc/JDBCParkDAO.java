package com.techelevator.projects.model.jdbc;

import java.util.List;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Park> getAllParks() {
		List<Park> parks = new ArrayList<Park>();
		String getAllParks = "SELECT * FROM park ORDER BY name";
		Park thePark;
		SqlRowSet results = jdbcTemplate.queryForRowSet(getAllParks);
		while(results.next()) {
			thePark = mapRowToPark(results);
			parks.add(thePark);
		}
		return parks;
	}

	@Override
	public List<Park> getParkById(long id) {
		List<Park> parks = new ArrayList<Park>();
		String getAllParks = "SELECT * FROM park WHERE park_id = ?";
		Park thePark;
		SqlRowSet results = jdbcTemplate.queryForRowSet(getAllParks, id);
		while(results.next()) {
			thePark = mapRowToPark(results);
			parks.add(thePark);
		}
		return parks;
	}
	
	
	private Park mapRowToPark(SqlRowSet results) {
		Park thePark;
		thePark = new Park();
		thePark.setParkId(results.getLong("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		thePark.setArea(results.getLong("area"));
		thePark.setVisitors(results.getLong("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}
	
}
