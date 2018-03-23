package com.techelevator;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.jdbc.JDBCParkDAO;

import static org.junit.Assert.assertEquals;
import org.junit.Assert;

public class JDBCParkDAOIntegrationTest extends DAOIntegrationTest {
	
	private static final long PARK_ID = (long)4; //yes, I hardcoded this and I really shouldn't have
	private static final String PARK_NAME = "Yosemite Test Park";
	private static final String PARK_LOCATION = "Test State";
	private static final LocalDate ESTABLISH_DATE = LocalDate.of(1980,06,06);
	private static final long PARK_AREA = 1000000;
	private static final long PARK_VISITORS = 9999999;
	private static final String PARK_DESCRIPTION = "This is a really interesting and compelling Park Description that will make you want to give us all your money";
	private JDBCParkDAO dao;
	
	@Before
	public void setup() {
		String sqlInsertTheTestPark = " INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) "
				+ "VALUES (?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		jdbcTemplate.update(sqlInsertTheTestPark,PARK_ID,PARK_NAME,PARK_LOCATION,ESTABLISH_DATE,PARK_AREA,PARK_VISITORS,PARK_DESCRIPTION);
		dao = new JDBCParkDAO(getDataSource());
	}
	
	@Test
	public void get_all_parks () {
		List<Park> results = dao.getAllParks();
//		for (Park park : results) {
//			System.out.println(park.getParkId() + " " +
//			park.getParkId() + " " +
//			park.getName() + " " +
//			park.getLocation() + " " +
//			park.getEstablishDate() + " " +
//			park.getArea() + " " +
//			park.getVisitors() + " " +
//			park.getDescription());
//		}
	 Assert.assertEquals("There should be 4 parks now", 4, results.size());
	}
	
	@Test
	public void get_park_by_park_id() {
		List<Park> results = dao.getAllParks();
		Assert.assertNotNull(results);
		Park testPark = results.get(results.size() - 1);
		
		List<Park> results2 = dao.getParkById(testPark.getParkId());
		Park actualPark = results2.get(0);
		assertParksAreEqual(testPark, actualPark);
	}
	
//	@Test
//	public void display_all_parks() {
//		List<Park> results = dao.getAllParks();
//		System.out.println("Select a Park for Further Details");
//		int count = 1;
//		for (Park park : results) {
//			System.out.println(count++ + ") " + park.getName());
//			
//		}
//			System.out.println("Q) " + "quit");
//	}
	
	@Test
	public void display_park_information_screen() {
		List<Park> results = dao.getAllParks();
		Park testPark = results.get(results.size() - 1);
		System.out.println("Park Information Screen");
		System.out.println(testPark.getName());
		System.out.println(String.format("%-10s%17s", "Location:", testPark.getLocation()));
		System.out.println(String.format("%-10s%15s", "Established:", testPark.getEstablishDate()));
		System.out.println(String.format("%-10s%14d", "Area:", testPark.getArea()) + " sq km");
		System.out.println(String.format("%-10s%8d", "Annual Visitors:", testPark.getVisitors()));
		System.out.println("");
	}
	
	
	
	
	private void assertParksAreEqual(Park expected, Park actual) {
		assertEquals(expected.getParkId(), actual.getParkId());
		assertEquals(expected.getName(), actual.getName());
	}
}
