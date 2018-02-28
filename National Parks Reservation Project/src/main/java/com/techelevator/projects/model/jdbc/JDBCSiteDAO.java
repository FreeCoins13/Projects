package com.techelevator.projects.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.Site;
import com.techelevator.projects.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getAvailableSitesByReservationDate(long campgroundId, LocalDate startDate, LocalDate endDate) {
		List<Site> availableSites = new ArrayList<Site>();
		String sqlFindTopFiveAvailableSites = "SELECT distinct * FROM site " + 
				"join campground on site.campground_id = campground.campground_id " + 
				"where site.campground_id = ? " + 
				"and site_id not in " + 
				"(select site.site_id from site " + 
				"JOIN reservation ON reservation.site_id = site.site_id " + 
				"WHERE ? > reservation.from_date and ? < reservation.to_date) " + 
				"order by daily_fee " + 
				"LIMIT 5";
		Site theSite;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindTopFiveAvailableSites, campgroundId, startDate, endDate);
		while(results.next()) {
			theSite = mapRowToSite(results);
			availableSites.add(theSite);
		}
		return  availableSites;
	}
	
	private Site mapRowToSite(SqlRowSet results) {
		Site theSite;
		theSite = new Site();
		theSite.setSiteId(results.getLong("site_id"));
		theSite.setCampgroundId(results.getLong("campground_id"));
		theSite.setSiteNumber(results.getInt("site_number"));
		theSite.setMax(results.getInt("max_occupancy"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMaxRvLength(results.getInt("max_rv_length"));
		theSite.setHasUtilities(results.getBoolean("utilities"));
		theSite.setCampgroundId(results.getLong("campground_id"));
		theSite.setParkId(results.getLong("park_id"));
		theSite.setName(results.getString("name"));
		theSite.setOpenFrom(results.getInt("open_from_mm"));
		theSite.setOpenTo(results.getInt("open_to_mm"));
		theSite.setDailyFee(results.getBigDecimal("daily_fee"));
		return theSite;
	}
	
	
}
