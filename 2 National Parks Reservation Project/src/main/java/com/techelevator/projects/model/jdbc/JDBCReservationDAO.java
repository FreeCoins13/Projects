package com.techelevator.projects.model.jdbc;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.Reservation;
import com.techelevator.projects.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long makeReservation(long site_id, String name, LocalDate startDate, LocalDate endDate) {
		long yourResId = getNextReservationId();
		String sqlAddReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date) "
				+ "VALUES (?,?,?,?,?)";
		jdbcTemplate.update(sqlAddReservation, yourResId, site_id, name, startDate, endDate);
		return yourResId;
	}


	
	public long getNextReservationId() { 
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new Employee");
		}
	}
	
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation theReservation;
		theReservation = new Reservation();
		theReservation.setResId(results.getLong("reservation_id"));
		theReservation.setSiteId(results.getLong("site_id"));
		theReservation.setName(results.getString("name"));
		theReservation.setFromDate(results.getDate("from_date").toLocalDate());
		theReservation.setToDate(results.getDate("to_date").toLocalDate());
		theReservation.setCreateDate(results.getDate("create_date").toLocalDate());
		return theReservation;
	}
}
