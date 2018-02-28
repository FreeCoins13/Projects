package com.techelevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;

import com.techelevator.projects.model.*;
import com.techelevator.projects.model.jdbc.*;
import com.techelevator.projects.view.Menu;

public class CampgroundCLI {
	
	//create the static final stuff here e.g. private DepartmentDAO departmentDAO
	private static final String PARK_MENU_DISPLAY_PARKS = "Select a Park for Further Details";
	private static final String[] PARK_MENU_OPTIONS = new String[] { PARK_MENU_DISPLAY_PARKS };
	
	private static final String MAIN_MENU_OPTIONS_PARKS = "View Parks Interface";
	private static final String MAIN_MENU_OPTIONS_QUIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] {MAIN_MENU_OPTIONS_PARKS, MAIN_MENU_OPTIONS_QUIT};
	
	private static final String CAMP_MENU_OPTION_ALL_CAMPGROUNDS = "View Campgrounds";
	private static final String CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS = "Search for Reservation";
	private static final String CAMP_MENU_RETURN_TO_PREVIOUS = "Return to Previous Screen";
	private static final String[] CAMP_MENU_OPTIONS = new String[] {CAMP_MENU_OPTION_ALL_CAMPGROUNDS, CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS,CAMP_MENU_RETURN_TO_PREVIOUS};
	
	private static final String RESERVATION_MENU_SEARCH_AVAILABLE = "Search for Available Reservation";
	private static final String RESERVATION_MENU_RETURN_TO_PREVIOUS = "Return to Previous Screen";
	private static final String[] RESERVATION_MENU_OPTIONS = new String[] {RESERVATION_MENU_SEARCH_AVAILABLE,RESERVATION_MENU_RETURN_TO_PREVIOUS};
	
	private Menu menu;
	public ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;
	private long numChoice;
	private Park selectedPark;
	private List<Campground> campsOfParkId;
	private long selectedCampgroundId = 0;
	private long selectedSiteId = 0;
	private LocalDate arrival;
	private LocalDate departure;
	private List<Site> availableSites = null;

  	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource dataSource) {
		this.menu = new Menu(System.in, System.out);
		
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
	}
	
	public void run() {
		while(true) {
			displayApplicationBanner();
			printHeading("National Park Campsite Reservation System");
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(choice.equals(MAIN_MENU_OPTIONS_PARKS)) {
				handleParks();
			} else if (choice.equals(MAIN_MENU_OPTIONS_QUIT)) {
				break;
			}
			
		}
	}
	
	private void handleParks() {
		System.out.println("\nSelect a Park for Further Details");
		List<Park> results = parkDAO.getAllParks();
		int count = 1;
		for (Park park : results) {
			System.out.println(count++ + ") " + park.getName());
		}
			System.out.println("Q) " + "quit");
	
		System.out.print("\nPlease choose an option >>> ");
		
		String preChoice = menu.getUserInput();
		numChoice = handleParkChoice(preChoice, results);
		
		for (Park park : results) {
			if (numChoice == park.getParkId()) {
				selectedPark = park;
			} 
		}
		displayParkInfo(numChoice, selectedPark);
		
		switchToCampMenu();
	}
	
	private void switchToCampMenu () {
		String choice = (String)menu.getChoiceFromOptions(CAMP_MENU_OPTIONS);
		if(choice.equals(CAMP_MENU_OPTION_ALL_CAMPGROUNDS)) {
			System.out.println("\n\n" + selectedPark.getName() + " National Park Campgrounds\n");
			viewCampgroundsByParkId(numChoice, selectedPark);
			handleReservations();
		} else if (choice.equals(CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS)) {
			System.out.println("\nSearch for Campground Reservation\n");
			viewCampgroundsByParkId(numChoice, selectedPark);
			catchAllDateExceptionsBeforeUsingThemForSearch();
		} else if (choice.equals(CAMP_MENU_RETURN_TO_PREVIOUS)) {
			handleParks();
		}
	}
	
	private void handleReservations() {
		System.out.println("Select a Command");
		
		String choice = (String)menu.getChoiceFromOptions(RESERVATION_MENU_OPTIONS);
		if(choice.equals(RESERVATION_MENU_SEARCH_AVAILABLE)) {
			catchAllDateExceptionsBeforeUsingThemForSearch();
		} else if (choice.equals(RESERVATION_MENU_RETURN_TO_PREVIOUS)) {
			switchToCampMenu();
		}
	}
	
	private void catchAllDateExceptionsBeforeUsingThemForSearch() {
		
		//HANDLE THE CAMPGROUND
		System.out.print("Which Campground (enter 0 to cancel)? >>> ");
		String stringCampId = menu.getUserInput();
		selectedCampgroundId = handleCampgroundExceptions (stringCampId);
		
		//HANDLE THE ARRIVAL DATE
		System.out.print("What is the arrival date? (format: MM/dd/yyyy) >>> ");
		String strArrival = menu.getUserInput();
		arrival = handleDateExceptions(strArrival);
		
		//HANDLE THE DEPARTURE DATE
		System.out.print("What is the departure date? (format: MM/dd/yyyy) >>> ");
		String strDeparture = menu.getUserInput();
		departure = handleDateExceptions(strDeparture);
		
		if (departure.compareTo(arrival) <= 0) {
			System.out.println("We don't allow timetravel (or same day departure) at our distinguished establishment.\n");
			catchAllDateExceptionsBeforeUsingThemForSearch();
		}
		
		handleGetAvailableSites();
	}
	
	private LocalDate handleDateExceptions(String dateEnteredByUser) {
		LocalDate resultDate = null;
		
		if (dateEnteredByUser.length() != 10) {
			System.out.println("Invalid date format, please try again!");
			catchAllDateExceptionsBeforeUsingThemForSearch();
		} else {
			String[] dateArray = dateEnteredByUser.split("/");
			
			for (String s : dateArray) {
				try {
					Integer.parseInt(s);
				} catch (NumberFormatException e) {
					System.out.println("Invalid date format, please try again.");
					catchAllDateExceptionsBeforeUsingThemForSearch();
				}
			}
			if (dateArray.length != 3) {
				System.out.println("Invalid date format, please try again.");
				catchAllDateExceptionsBeforeUsingThemForSearch();
			}
			
			int Month = Integer.parseInt(dateArray[0]);
			int Day = Integer.parseInt(dateArray[1]);
			int Year = Integer.parseInt(dateArray[2]);
			
			if (Month < 1 || Month > 12) {
				System.out.println("Invalid Month, please try again.");
				catchAllDateExceptionsBeforeUsingThemForSearch();
			}
			if (Day < 1 || Day > 31) {
				System.out.println("Invalid Day, please try again.");
				catchAllDateExceptionsBeforeUsingThemForSearch();
			}
			if (Day > 30 && (Month == 2 || Month == 4 || Month == 6 || Month == 9 || Month == 11)) {
				System.out.println("Invalid Day, please try again.");
				catchAllDateExceptionsBeforeUsingThemForSearch();
			} else if (Day > 28 && (Month == 2)) {
				System.out.println("Invalid Day, please try again.");
				catchAllDateExceptionsBeforeUsingThemForSearch();
			}
			if (Year != 2018) {
				System.out.println("Currently we are only taking reservations for the year 2018. Please try again.");
				catchAllDateExceptionsBeforeUsingThemForSearch();
			}
			
			resultDate = LocalDate.of(Year, Month, Day);
		}
			return resultDate;
	}
	
	private long handleCampgroundExceptions (String stringCampId) {
		int selectedCampground = 0;
		long result = 0;
		try {
			selectedCampground = Integer.parseInt(stringCampId);
		} catch (NumberFormatException e) {
			System.out.println("Invalid entry, please try again.\n");
			catchAllDateExceptionsBeforeUsingThemForSearch();
		}
		if (selectedCampground == 0) {
			System.out.println("Cancelling operation, returning to Camp Menu\n");
			switchToCampMenu();
		} 
		selectedCampground--; //campground has to be decremented in order to get the correct index
		if (selectedCampground < 0 || selectedCampground > campsOfParkId.size() - 1) {
			System.out.println("Please select a different value\n");
			catchAllDateExceptionsBeforeUsingThemForSearch();
		} else { //if it passes through all the exception cases, we'll get the ACTUAL campgroundID from our List and assign it
			result = campsOfParkId.get(selectedCampground).getCampgroundId();
		}
		return result;
	}
	
	private long handleSiteExceptionsAndGetSelectedSiteId (String stringSiteNumber) {
		int selectedSite = 0;
		long result = 0;
		try {
			selectedSite = Integer.parseInt(stringSiteNumber);
		} catch (NumberFormatException e) {
			System.out.println("Invalid entry, please try again.\n");
			handleMakeReservation();
		}
		if (selectedSite == 0) {
			System.out.println("Cancelling operation, returning to Camp Menu\n");
			switchToCampMenu();
		} 
		selectedSite--; //site has to be decremented in order to get the correct index
		if (selectedSite < 0 || selectedSite > availableSites.size() - 1) {
			System.out.println("Please select a different value\n");
			handleMakeReservation();
		} else { //if it passes through all the exception cases, we'll get the ACTUAL campgroundID from our List and assign it
			result = availableSites.get(selectedSite).getSiteId();
		}
		return result;
	}
	
	private void handleGetAvailableSites() {
		System.out.println("\nResults Matching Your Search Criteria");
		availableSites = siteDAO.getAvailableSitesByReservationDate(selectedCampgroundId, arrival, departure);
		
		BigDecimal days = new BigDecimal((int)ChronoUnit.DAYS.between(arrival,departure));
		
		System.out.println(String.format("%-12s%-14s%-12s%15s%14s%10s", "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost"));
		
		String trueOrFalse = "";
		String rvLength = "";
		String utility = "";
		String sumCost = "";
		int count = 1;
		
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
					count++, site.getMax(), trueOrFalse, 
					rvLength, utility, sumCost));
		}
		if (availableSites.size() == 0) {
			handleEnterAlternateDateRange();
		}
		handleMakeReservation();
	}
	
	private void handleEnterAlternateDateRange() {
		System.out.println("There are no available sites for the specified date range.\nWould you like to enter an alternate date range? (Y)es, (N)o");
		String yesOrNo = menu.getUserInput();
		if (yesOrNo.toUpperCase().equals("Y")) {
			catchAllDateExceptionsBeforeUsingThemForSearch();
		} else if (yesOrNo.toUpperCase().equals("N")) {
			System.out.println("Cancelling operation, returning to Camp Menu\n");
			switchToCampMenu();
		} else {
			System.out.println("Invalid entry, please try again.\n");
			handleEnterAlternateDateRange();
		}
	}
	
	private void handleMakeReservation() {
		System.out.print("\nWhich site should be reserved (enter 0 to cancel)? >>> ");
		String stringSiteNumber = menu.getUserInput();
		selectedSiteId = handleSiteExceptionsAndGetSelectedSiteId (stringSiteNumber);
		
		System.out.print("What name should the reservation be made under? >>> ");
		String reservationName = menu.getUserInput();
		
		long customerReservationId = reservationDAO.makeReservation(selectedSiteId, reservationName, arrival, departure);
		
		System.out.println("\nThe reservation has been made and the confirmation id is: " + customerReservationId + "\n\n");
		run();
	}
	
	private void viewCampgroundsByParkId(long park_id, Park selectedPark) {
		
		campsOfParkId = campgroundDAO.getCampgroundByParkId(park_id);
		System.out.print("     ");
		System.out.println(String.format("%-35s%-9s%10s%20s", "Name", "Open", "Close", "Daily Fee"));
		
		int count = 1;
		
		for (Campground campground : campsOfParkId) {
			String price = "$" + String.format("%.2f", campground.getDailyFee());
			String counttoString = "#" + count++;
			System.out.println(String.format("%-5s%-35s%-12s%10s%15s",
					counttoString, campground.getName(), getMonth(campground.getOpenFrom()), 
					getMonth(campground.getOpenTo()), price));
		}
		System.out.println("");
		
	}
	
	public String getMonth (int month) {
		return new DateFormatSymbols().getMonths()[month-1];
	}
	
	private long handleParkChoice(String preChoice, List<Park> results) {
		int numChoice = 0;
		String strChoice = "";
		long parkInfoSearch = 0;
		try {
			numChoice = Integer.parseInt(preChoice);
			
			for (Park park : results) {
				if ((long)numChoice == park.getParkId()) {
					parkInfoSearch = park.getParkId();
				} 
			}
			
			if (parkInfoSearch == 0) {
				System.out.println("\n*** " + preChoice + " is not a valid option ***");
				handleParks();
			}
			
		} catch (NumberFormatException e) {
			strChoice = preChoice.toUpperCase();
			
			if (strChoice.equals("Q")) {
				System.exit(0);
			} else {
				System.out.println("\n*** " + strChoice + " is not a valid option ***");
				handleParks();
			}
		}
		return numChoice;
	}
	
	private void displayParkInfo(long park_id, Park thePark) {
		
		System.out.println("\nPark Information Screen");
		System.out.println(thePark.getName() + " National Park");
		System.out.println(String.format("%-10s%12s", "Location:", thePark.getLocation()));
		System.out.println(String.format("%-10s%15s", "Established:", thePark.getEstablishDate()));
		System.out.println(String.format("%-10s%12d", "Area:", thePark.getArea()) + " sq km");
		System.out.println(String.format("%-10s%8d", "Annual Visitors:", thePark.getVisitors()));
		System.out.println("");
		
		List<String> breakItUp = stringBreak(thePark.getDescription(), 80);
		
		for (String s : breakItUp) {
			System.out.println(s);
		}
	}
	
	private void printHeading(String headingText) {
		System.out.println("\n"+headingText);
		for(int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	private static void displayApplicationBanner() {
		
		System.out.println("__________  _____ __________ ____  __.  _________");
		System.out.println("\\______   \\/  _  \\\\______   \\    |/ _| /   _____/");
		System.out.println("  |    ___/  /_\\  \\|       _/      <   \\_____  \\ ");
		System.out.println("  |   |  /    |    \\    |   \\    |  \\  /        \\");
		System.out.println("  |___|  \\____|__  /____|_  /____|__ \\/_______  /");
		System.out.println("                 \\/       \\/        \\/        \\/");
	}
	//stole this from the INTERNET
	public static List<String> stringBreak(String string, int maxChar) {

	    List<String> subLines = new ArrayList<String>();

	    int length = string.length();
	    int start = 0;
	    int end = maxChar;
	    if (length > maxChar) {

	        int noOfLines = (length / maxChar) + 1;

	        int endOfStr[] = new int[noOfLines];

	        for (int f = 0; f < noOfLines - 1; f++) {

	            int end1 = maxChar;

	            endOfStr[f] = end;

	            if (string.charAt(end - 1) != ' ') {

	                if (string.charAt(end - 2) == ' ') {

	                    subLines.add(string.substring(start, end - 1));
	                    start = end - 1;
	                    end = end - 1 + end1;

	                } else if (string.charAt(end - 2) != ' '
	                        && string.charAt(end) == ' ') {

	                    subLines.add(string.substring(start, end));
	                    start = end;
	                    end = end + end1;

	                } else if (string.charAt(end - 2) != ' ') {

	                    subLines.add(string.substring(start, end) + "-");
	                    start = end;
	                    end = end + end1;

	                } else if (string.charAt(end + 2) == ' ') {
	                    System.out.println("m here ............");
	                    int lastSpaceIndex = string.substring(start, end)
	                            .lastIndexOf("");
	                    subLines.add(string.substring(start, lastSpaceIndex));

	                    start = lastSpaceIndex;
	                    end = lastSpaceIndex + end1;
	                }

	            } else {

	                subLines.add(string.substring(start, end));
	                start = end;
	                end = end + end1;
	            }
	        }
	        subLines.add(string.substring(endOfStr[noOfLines - 2], length));
	    }
	    return subLines;
	}
}
