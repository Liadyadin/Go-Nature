/**
* Description of SignUpScreenController 
* @author Omri Cohen
* 
* @version final Jan 2, 2021.
*/

package Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import Client.ClientUI;
import GUI.Data;
import clientLogic.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReportsController {

	/**
	 * This is a controller for sign up window
	 * 
	 * @implNote implements Initializable - initialize all predefined data
	 */

	/** data object for save the data of the table on ObservableList ob */
	public Data d1;
	/** data object for save the data of the table on ObservableList ob2 */
	public Data d2;
	/** ObservableList for management data in usage table */

	public ObservableList<Data> ob = FXCollections.observableArrayList();
	/** ObservableList for management data in usage line chart */
	public ObservableList<Data> ob2 = FXCollections.observableArrayList();
	public ArrayList<Reports> visitors = new ArrayList<Reports>();
	public ArrayList<Reports> members = new ArrayList<Reports>();
	public ArrayList<Reports> groups = new ArrayList<Reports>();
	public ArrayList<Reports> totalArray = new ArrayList<Reports>();
	public String month, park, year;

	public int sumSolo = 0, sumMembers = 0, sumGroups = 0;

	/**
	 * sumSolo=individuals that entered the park, sumMembers=members and family
	 * members that entered the park, sumGroups=groups that entered to the park
	 */
	public Float Traveler_cnt, Traveler_income, Member_cnt, Member_income, Family_cnt, Family_income, Group_cnt,
			Group_income;

	/**
	 * Description of gotMessage(String[] msg) - handle response from server, switch
	 * control to manage all functions
	 *
	 * @param msg    - String containing information, return from server side with
	 *               function result or additional data.
	 * @param msg[0] - return destination functions name.
	 * 
	 * @throws IOException -From inner methods
	
	 */
	public void gotMessage(String[] msg) throws IOException {
		String cases = msg[0];
		switch (cases) {
		case "getData":
			this.sumSolo = Integer.parseInt(msg[1]);
			this.sumMembers = Integer.parseInt(msg[2]);
			this.sumGroups = Integer.parseInt(msg[3]);
			break;
		case "getTableOfUnFullCapacityInDates":
			filldateofNotfullCapacityTable(msg);
			break;
		case "getUnFullCapacityTableInDatesAndNumbers":
			filldateofNotfullCapacityLineChart(msg);
			break;
		case "getDataEntranceTimesAndStay":
			dealWithgetDataEntranceTimesAndStay(msg);
			break;

		case "makeMonthlyIncomeReport":
			createMonthlyIncomeReport(msg[1], msg[2], msg[3], msg[4], msg[5], msg[6], msg[7], msg[8], msg[9], msg[10]);
			break;
		}
	}

	public void makeMonthlyIncomeReport(String month, String Year, String park) {
		String date = Year + "-" + getMonth(month) + "-01";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		System.out.println(localDate);
		StringBuffer sb = new StringBuffer();
		sb.append("makeMonthlyIncomeReport");// method name
		sb.append(" ");
		sb.append(localDate.toString());
		sb.append(" ");
		sb.append(park);
		ClientUI.chat.accept(sb.toString());
	}

	/**
	 * Description of getMonth(String month)
	 *
	 * @param month - String containing month name.
	 * 
	 * @throws IOException -From inner methods
	 * @return String - month numeric representation as a string
	 */
	private String getMonth(String month) {
		switch (month) {
		case "Jan":
			return ("01");
		case "Fab":
			return ("02");
		case "Mar":
			return ("03");
		case "Apr":
			return ("04");
		case "May":
			return ("05");
		case "Jun":
			return ("06");
		case "Jul":
			return ("07");
		case "Aug":
			return ("08");
		case "Sep":
			return ("09");
		case "Oct":
			return ("10");
		case "Nov":
			return ("11");
		case "Dec":
			return ("12");
		default:
			System.out.println("Error validation month");
			return "Error validation month";
		}
	}

	private void createMonthlyIncomeReport(String Traveler_cnt, String Traveler_income, String Member_cnt,
			String Member_income, String Family_cnt, String Family_income, String Group_cnt, String Group_income,
			String TravelerInPark_cnt, String TravelerInPark_income) {
		/* set values */
		this.Traveler_cnt = Float.valueOf(Traveler_cnt) + Float.valueOf(TravelerInPark_cnt);
		this.Traveler_income = Float.valueOf(Traveler_income) + Float.valueOf(TravelerInPark_income);

		this.Member_cnt = Float.valueOf(Member_cnt);
		this.Member_income = Float.valueOf(Member_income);

		this.Family_cnt = Float.valueOf(Family_cnt);
		this.Family_income = Float.valueOf(Family_income);

		this.Group_cnt = Float.valueOf(Group_cnt);
		this.Group_income = Float.valueOf(Group_income);
	}

	/**
	 * Description of getData(String month, String year, String park)
	 *
	 * @param month - String containing wanted month.
	 * @param year  - String containing wanted year.
	 * @param park  - String containing wanted park.
	 * 

	 */
	public void getData(String month, String year, String park) {
		StringBuffer sb = new StringBuffer();
		sb.append("getData");
		sb.append(" ");
		sb.append(getMonth(month));
		sb.append(" ");
		sb.append(year);
		sb.append(" ");
		sb.append(park);

		ClientUI.chat.accept(sb.toString());
	}

	/**
	 * Description of getSolo()
	 * 
	 * @return int - this function returns how many solo visitors (travelers) has
	 *         entered the park.
	 */
	public int getSolo() {
		return this.sumSolo;
	}

	/**
	 * Description of getSolo()
	 * 
	 * @return int - this function returns how many members has entered the park.
	 */
	public int getMembers() {
		return this.sumMembers;
	}

	/**
	 * Description of getSolo()
	 * 
	 * @return int - this function returns how many groups has entered the park.
	 */
	public int getGroups() {
		return this.sumGroups;
	}

	/**
	 * this method sends to server month, year and name park and get from DB in
	 * fullcapacity table only dates that the park wasn't full in this month and
	 * year for using in table unuse
	 * 
	 * @param month-     the month we want to get the report
	 * @param year       - the year we want to get the report
	 * @param wantedpark - name park we want to make a report
	 */
	public void getTableOfUnFullCapacityInDates(String month, String year, String wantedpark) {
		StringBuffer sb = new StringBuffer();
		sb.append("getTableOfUnFullCapacityInDates");
		sb.append(" ");
		sb.append(getMonth(month));
		sb.append(" ");
		sb.append(year);
		sb.append(" ");
		sb.append(wantedpark);
		ClientUI.chat.accept(sb.toString());
	}

	/**
	 * this method sends to server month, year and name park and get from DB in
	 * fullcapacity table details that the park wasn't full in this month and year
	 * for use in line chart
	 * 
	 * @param month-     the month we want to get the report
	 * @param year       - the year we want to get the report
	 * @param wantedpark - name park we want to make a reoprt
	 */
	public void getUnFullCapacityTableInDatesAndNumbers(String month, String year, String wantedpark) {
		StringBuffer sb = new StringBuffer();
		sb.append("getUnFullCapacityTableInDatesAndNumbers");
		sb.append(" ");
		sb.append(getMonth(month));
		sb.append(" ");
		sb.append(year);
		sb.append(" ");
		sb.append(wantedpark);
		ClientUI.chat.accept(sb.toString());
	}

	/**
	 * this method fills the table for the table(by ObservableList<Data> )
	 * 
	 * @param msg- the details from DB
	 */
	private void filldateofNotfullCapacityTable(String[] msg) {
		int cnt = 1;
		if (!(msg[1].equals("Done"))) {
			while (!(msg[cnt].equals("Done"))) {
				d1 = new Data(msg[cnt]);
				ob.add(d1);
				cnt++;
			}
		}
	}

	/**
	 * <<<<<<< HEAD Description of getDataEntranceTimesAndStay(String month, String
	 * year, String park)
	 *
	 * @param month - String containing wanted month.
	 * @param year  - String containing wanted year.
	 * @param park  - String containing wanted park.
	 * 
	 * @return void - this function requests data from server, the response will be
	 *         catched in "gotMessage"
	 */

	/*
	 * this method fills the table for line chart (by ObservableList<Data> )
	 * 
	 * @param msg- the details from DB
	 */
	private void filldateofNotfullCapacityLineChart(String[] msg) {
		int cnt = 1;
		if (!(msg[1].equals("Done"))) {
			while (!(msg[cnt].equals("Done"))) {
				d2 = new Data(msg[cnt], Integer.parseInt(msg[cnt + 1]), Integer.parseInt(msg[cnt + 2]));
				ob2.add(d2);
				cnt += 3;
			}
		}
	}

	public void getDataEntranceTimesAndStay(String month, String year, String wantedpark) {
		StringBuffer sb = new StringBuffer();
		this.month = month;
		this.year = year;
		this.park = wantedpark;
		sb.append("getDataEntranceTimesAndStay");
		sb.append(" ");
		sb.append(getMonth(month));
		sb.append(" ");
		sb.append(year);
		sb.append(" ");
		sb.append(wantedpark);
		ClientUI.chat.accept(sb.toString());
	}

	/**
	 * Description of dealWithgetDataEntranceTimesAndStay(String[] msg) this
	 * function handles the received data from server after executing
	 * "getDataEntranceTimesAndStay".
	 * 
	 * @param msg[i]   is numOfVisitors.
	 * @param msg[i+1] is the type.
	 * @param msg[i+2] is entrance time.
	 * @param msg[i+3] is exit time
	 * @param msg[i+4] is visit date
	 * 
	 * @return void - this function updates 3 arrays: visitors, members and groups
	 *         in order to create detailed reports.
	 */
	private void dealWithgetDataEntranceTimesAndStay(String[] msg) {
		int i = 1;
System.out.println(msg);
		while (!(msg[i].equals("Done"))) {
			System.out.println("I: " + i + " msg[1]: " + msg[i] + "  msg[2]: " + msg[i + 1] + "  msg[3]: "
					+ msg[i + 2] + "  msg[4]: " + msg[i + 3] + " msg[5]: " + msg[i + 4]);
			String type = msg[i + 1];
			switch (type) {
			case "traveller":
				visitors.add(new Reports(msg[i], msg[i + 1], msg[i + 2], msg[i + 3], msg[i + 4]));
								break;
			case "Member":
			case "Family":
				members.add(new Reports(msg[i], msg[i + 1], msg[i + 2], msg[i + 3], msg[i + 4]));
				
				break;
			case "Group":
				groups.add(new Reports(msg[i], msg[i + 1], msg[i + 2], msg[i + 3], msg[i + 4]));
					break;
			default:
				System.out.println("Somthing is wrong with type");
				System.out.println("I: " + i + " msg[1]: " + msg[i] + "  msg[2]: " + msg[i + 1] + "  msg[3]: "
						+ msg[i + 2] + "  msg[4]: " + msg[i + 3] + " msg[5]: " + msg[i + 4]);
			}
			i += 5;
		}
		totalArray.addAll(visitors);
		totalArray.addAll(members);
		totalArray.addAll(groups);
		totalArray.sort(clientLogic.Reports.dayOfMonth);

	}
}
