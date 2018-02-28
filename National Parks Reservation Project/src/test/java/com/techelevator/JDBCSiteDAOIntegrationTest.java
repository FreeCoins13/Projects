package com.techelevator;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.Site;
import com.techelevator.projects.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.projects.model.jdbc.JDBCParkDAO;
import com.techelevator.projects.model.jdbc.JDBCReservationDAO;
import com.techelevator.projects.model.jdbc.JDBCSiteDAO;

import static org.junit.Assert.assertEquals;
import org.junit.Assert;

public class JDBCSiteDAOIntegrationTest extends DAOIntegrationTest {

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
	
	private static final String RES_NAME = "Once upon a time";
	private static final LocalDate RES_FROM = LocalDate.of(2018, 03, 4);
	private static final LocalDate RES_TO = LocalDate.of(2018, 03, 8);
	private JDBCReservationDAO resDao;
	
	private JDBCSiteDAO siteDao;
	
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
		resDao = new JDBCReservationDAO(getDataSource());
		siteDao = new JDBCSiteDAO(getDataSource());
	}
	
	@Test
	public void test_get_available_sites_by_res_date_and_get_sum_daily_fee () {
		
		List<Site> availableSites = siteDao.getAvailableSitesByReservationDate(2, RES_FROM, RES_TO);
		Assert.assertEquals(5, availableSites.size());
		
		BigDecimal days = new BigDecimal((int)ChronoUnit.DAYS.between(RES_FROM,RES_TO));
		
		System.out.println(String.format("%-12s%-14s%-12s%15s%14s%10s", "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost"));
		
		String trueOrFalse = "";
		String rvLength = "";
		String utility = "";
		String sumCost = "";
		
		for (Site site : availableSites) {
			BigDecimal sumFee = site.getDailyFee().multiply(days);
			if (site.isAccessible()) {
				trueOrFalse = "Yes";
			} else {
				trueOrFalse = "No";
			}
			
			if (site.getMaxRvLength() == 0) {
				rvLength = "N/A";
			} else {
				rvLength = Integer.toString(site.getMaxRvLength());
			}
			if (site.isHasUtilities()) {
				utility = "Yes";
			} else {
				utility = "N/A";
			}
			sumCost = "$" + Double.toString(sumFee.doubleValue()) + "0";
			
			System.out.println(String.format("%-12d%-14s%-14s%-20s%-13s%1s",
					site.getSiteId(), site.getMax(), trueOrFalse, 
					rvLength, utility, sumCost));
		}
	}
	
}
