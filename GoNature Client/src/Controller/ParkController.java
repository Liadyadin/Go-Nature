/**
 * Controller for logic of data in park table. get&set methods checking if the
 * park is full by the data and checking when the park in full capacity
 * 
 * @author Liad Yadin
 * @version 2.0 Build December, 2020
 */
package Controller;

import java.time.LocalDate;

import Client.ClientUI;
import Entities.Park;


public class ParkController {
	/** park object for save the data of this park */
	public Park park;
	/** the name of the park we want to do the actions */
	private String parkname;
	/** for check if date of full capacity exits in DB of fullcapacity table */
	private boolean dateExistInDB = false;
	/**
	 * The maximum between current amount of traveler in park this moment(total) and
	 * the amount in full capacity table
	 */
	private int maxCurrent;

	/**
	 * this method sends to server parkName for get data from the table of park
	 * 
	 * @param parkName- the name of park we want details
	 */
	public void DetailsPark(String parkName) {
		this.parkname = parkName;
		StringBuffer sb = new StringBuffer();
		sb.append("DetailsPark");
		sb.append(" ");
		sb.append(parkName);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * get for current visitors in park(only travelers with order)
	 * 
	 * @param parkName- the name of park we want details
	 * @return the current visitors in the park(only travelers with order)
	 */
	public int getCurrentVisitors(String parkName) {
		ClientUI.parkController.DetailsPark(parkName);
		return park.getCurrentVisitors();
	}

	/**
	 * get for current unexpected visitors in park(only casual travelers )
	 * 
	 * @param parkName- the name of park we want details
	 * @return the current unexpected visitors in the park(only casual travelers)
	 */
	public int getCurrentUnexpectedVisitors(String parkName) {
		ClientUI.parkController.DetailsPark(parkName);
		return park.getAmountOfUnExpectedTravellers();
	}

	/**
	 * get for max available visitors in park- the number of visitors that the park
	 * manager approved enter for travelers with order
	 * 
	 * @param parkName- the name of park we want details
	 * @return amount of max available visitors in the park(only travelers with
	 *         order)
	 */
	public int getMaxAvailableVisitors(String parkName) {
		ClientUI.parkController.DetailsPark(parkName);
		return park.getMaxAvailableVisitors();
	}

	/**
	 * get for max visitors in park(determined by the park manager)
	 * 
	 * @param parkName- the name of park we want details
	 * @return amount of max visitors in the park
	 */
	public int getMaxVisitors(String parkName) {
		ClientUI.parkController.DetailsPark(parkName);
		return park.getMaxVisitors();
	}

	/**
	 * get max duration for travelers (determined by park manager)
	 * 
	 * @param parkName- the name of park we want details
	 * @return max duration of visit in the park
	 */
	public float getMaxDuration(String parkName) {
		ClientUI.parkController.DetailsPark(parkName);
		return park.getMaxDurationVisit();
	}

	/**
	 * this method check if the park is full for casual travelers(this mean that
	 * casuals traveler cannot enter the park)
	 * 
	 * @param parkName-      the name of park we want to check
	 * @param numOfVisitors- number of visitors we want to check
	 * @return true - if park is full and false- if park is not full
	 */
	public boolean parkIsFull(String parkName, int numOfVisitors) {
		int unExpectedVisitors = getMaxVisitors(parkName) - getMaxAvailableVisitors(parkName);
		if (numOfVisitors + getCurrentUnexpectedVisitors(parkName) >= unExpectedVisitors)
			return true;
		return false;
	}

	/**
	 * this method sends to server park name, date and full - and enter this date to
	 * the table with the correct value of full- if the park is full
	 * 
	 * @param park- the name of the park
	 * @param date- current date
	 * @param full- 0 if the park is not full and 1 if the park is full
	 * @param maxVisitors - maxVisitors
	 * @param maxCurrentPerDay - maxCurrentPerDay
	 */
	public void enterDateofFullCapcityPark(String park, LocalDate date, int full, int maxVisitors,
			int maxCurrentPerDay) {
		StringBuffer sb = new StringBuffer();
		sb.append("enterDateofFullCapcityPark");
		sb.append(" ");
		sb.append(park);
		sb.append(" ");
		sb.append(date.toString());
		sb.append(" ");
		sb.append(full);
		sb.append(" ");
		sb.append(maxVisitors);
		sb.append(" ");
		sb.append(maxCurrentPerDay);
		String res = sb.toString();
		ClientUI.chat.accept(res);

	}

	/**
	 * this method sends to server park name and check if the current date exists in
	 * DB thid date can found in the table only once
	 * 
	 * @param park- the name of the park we want to check
	 */
	public void checkIfThisDateInFullCapacityTable(String park) {
		StringBuffer sb = new StringBuffer();
		sb.append("checkIfThisDateInFullCapacityTable");
		sb.append(" ");
		sb.append(park);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * this method call to checkIfThisDateInFullCapacityTable
	 * @param park -park
	 * @return boolean-return true if this date exists, and false-else
	 */
	public boolean IfgetDateExistInDB(String park) {
		checkIfThisDateInFullCapacityTable(park);
		return dateExistInDB;
	}

	/**
	 * this method sends to server park name and update the status of capacityfull
	 * table
	 * 
	 * @param park- the name of the park we want to update
	 */
	public void updateStatusForCapacityParkToFull(String park) {
		StringBuffer sb = new StringBuffer();
		sb.append("updateStatusForCapacityParkToFull");
		sb.append(" ");
		sb.append(park);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * this method sends to server park name and maxCurrent visitors per day and
	 * update the value
	 * 
	 * @param park               - the name of park we want to change in
	 *                           fullcapacity table
	 * @param maxCurrentVisitors - the value we want to change
	 */
	public void changeMaxcurrentAmountOfVisitorsForCapacityPark(String park, int maxCurrentVisitors) {
		StringBuffer sb = new StringBuffer();
		sb.append("changeMaxcurrentAmountOfVisitorsForCapacityPark");
		sb.append(" ");
		sb.append(park);
		sb.append(" ");
		sb.append(maxCurrentVisitors);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * this method sends to server park name and get the max current from full
	 * capacity table
	 * 
	 * @param park- the name of the park we want get max current visitors
	 * @return max current amount visitors today in full capacity table
	 */
	public int getMaxcurrentVisitorsPerDay(String park) {
		StringBuffer sb = new StringBuffer();
		sb.append("getMaxcurrentVisitorsPerDay");
		sb.append(" ");
		sb.append(park);
		String res = sb.toString();
		ClientUI.chat.accept(res);
		return maxCurrent;
	}

	/**
	 * gotMesage from server - for handle all messages that sent there is cases of
	 * got messages - and handle by switch case and updates variables there is case
	 * of "done" that we don't need to do something - the action accrued in DB
	 * 
	 * @param msg - the massage that we got from server
	 */
	public void gotMessage(String[] msg) {
		String cases = msg[0];
		switch (cases) {
		case "DetailsPark":
			park = null;
			park = new Park(parkname, Integer.parseInt(msg[1]), Integer.parseInt(msg[2]), Integer.parseInt(msg[3]),
					Integer.parseInt(msg[4]), Float.parseFloat(msg[5]));
			break;
		case "checkIfThisDateInFullCapacityTable":
			if ((msg[1].toString()).equals("true"))
				dateExistInDB = true;
			else
				dateExistInDB = false;
			break;
		case "getMaxcurrentVisitorsPerDay":
			maxCurrent = (Integer.parseInt(msg[1]));

		}

	}
}