package com.techelevator.projects.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	public List<Site> getAvailableSitesByReservationDate(long id, LocalDate startDate, LocalDate endDate);

}
