package com.techelevator;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.text.DateFormatSymbols;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.projects.model.jdbc.JDBCParkDAO;
import com.techelevator.projects.model.Campground;
import com.techelevator.projects.model.Park;

import static org.junit.Assert.assertEquals;
import org.junit.Assert;

public class JDBCCampgroundDAOIntegrationTest extends DAOIntegrationTest {
	
	private static final long PARK_ID = (long)4; //yes, I hardcoded this and I really shouldn't have
	private static final String PARK_NAME = "Yosemite Test Park";
	private static final String PARK_LOCATION = "Test State";
	private static final LocalDate ESTABLISH_DATE = LocalDate.of(1980,06,06);
	private static final long PARK_AREA = 1000000;
	private static final long PARK_VISITORS = 9999999;
	private static final String PARK_DESCRIPTION = "This is a really interesting and compelling Park Description that will make you want to give us all your money";
	private JDBCParkDAO parkDao;
	
	private static final long CAMPGROUND_ID = (long)8; //yup, did it here too
	private static final String CAMP_NAME = "Grenada";
	private static final int CAMP_OPEN = 01;
	private static final int CAMP_CLOSE = 05;
	private static final BigDecimal CAMP_DAILY_FEE = new BigDecimal(150);
	private JDBCCampgroundDAO campDao;
	
	@Before
	public void setup() {
		//first add a Park so that it can be associated with a campground
		String sqlInsertTheTestPark = " INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) "
				+ "VALUES (?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		jdbcTemplate.update(sqlInsertTheTestPark,PARK_ID,PARK_NAME,PARK_LOCATION,ESTABLISH_DATE,PARK_AREA,PARK_VISITORS,PARK_DESCRIPTION);
		parkDao = new JDBCParkDAO(getDataSource());
		
		//now add a campground and associate it with the entered park ID
		String sqlInsertTheTestCampground = " INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) "
				+ "VALUES (?,?,?,?,?,?)";
		jdbcTemplate.update(sqlInsertTheTestCampground, CAMPGROUND_ID,PARK_ID,CAMP_NAME,CAMP_OPEN,CAMP_CLOSE,CAMP_DAILY_FEE);
		campDao = new JDBCCampgroundDAO(getDataSource());
	}
	
	@Test
	public void test_get_campground_by_park_id() {
		List<Campground> results = campDao.getCampgroundByParkId(PARK_ID);
		Assert.assertNotNull(results);
		Campground test = results.get(0);
		Assert.assertEquals("names weren't the same", "Grenada", test.getName());
	}
	
	@Test
	public void test_display_all_campgrounds() {
		System.out.println("");
		List<Park> allParks = parkDao.getAllParks();
		Park testPark = allParks.get(allParks.size() - 1);
		
		List<Campground> campsOfParkId = campDao.getCampgroundByParkId(PARK_ID);
		System.out.println(testPark.getName() + " Campgrounds");
		System.out.print("     ");
		System.out.println(String.format("%-20s%-9s%10s%15s", "Name", "Open", "Close", "Daily Fee"));
		
		int count = 1;
		
		for (Campground campground : campsOfParkId) {
			String price = "$" + String.format("%.2f", campground.getDailyFee());
			String counttoString = "#" + count++;
			System.out.print(String.format("%-5s%-20s%8s%10s%15s",
					counttoString, campground.getName(), getMonth(campground.getOpenFrom()), 
					getMonth(campground.getOpenTo()), price));
		}
	}
	public String getMonth (int month) {
		return new DateFormatSymbols().getMonths()[month-1];
	}
	

	private void assertCampgroundsAreEqual(Campground expected, Campground actual) {
		assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
		assertEquals(expected.getName(), actual.getName());
	}
	
}
