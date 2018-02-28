package com.techelevator.projects.model;

import java.time.LocalDate;

public interface ReservationDAO {
	public long makeReservation (long site_id, String name, LocalDate startDate, LocalDate endDate);

}
