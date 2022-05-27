/** Description of sqlConnector Class
 * 
 * This class is responsible to the comunication with the 
 * Db, giving back information
 * updating and inserting into the tables
 * 
 * @author Ilan Alexandrov
 * @author Bar Elhanati
 * @author Liad Yadin
 * @author Omri Cohen
 * @author Tal Langer
 */

package SqlConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import Entities.Order;

public class sqlConnector {

	private Connection conn;

	public sqlConnector(Connection conn) {
		this.conn = conn;
	}

	

	public String[] CheckForId(String msg) {
		Statement stm;
		String[] s = new String[5];
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM project.person WHERE ID=?");
			stm = conn.createStatement();
			ps.setString(1, msg);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				s[0] = rs.getString(1);
				s[1] = rs.getString(2);
				s[2] = rs.getString(3);
				s[3] = rs.getString(4);
				s[4] = rs.getString(5);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	/*
	 * 
	 * This method will get a string with the email and the id to be changed will
	 * return true or false
	 * 
	 * 
	 */

	public boolean updateEmail(String[] msg) {
		Statement stm;
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE project.person SET email=? WHERE ID=?");
			ps.setString(1, msg[1]);
			ps.setString(2, msg[0]);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

///////////////// Start Sign Up New Member /////////////////////////
	/**
	 * Description of isMemberExists(String[] msg) - this function check if a member
	 * is already in the DB
	 *
	 * @param msg - String containing visitors id.
	 * 
	 * @return boolean - true if member exists, false if not.
	 */
	public boolean isMemberExists(String[] msg) {
		Statement stm;
		try {

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM project.person WHERE ID=?");
			stm = conn.createStatement();
			ps.setString(1, msg[0]);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}
		return true;
	}

	/**
	 * Description of addMember(String[] msg) - this function ads a member to the DB
	 *
	 * @param msg[0]    - String containing visitors id.
	 * @param msg[1]    - String containing visitors first name.
	 * @param msg[2]    - String containing visitors last name.
	 * @param msg[3]    - String containing visitors phone number
	 * @param msg[4]    - String containing visitors email.
	 * @param msg[5]    - String containing visitors payment method
	 * @param msg[6]    - String containing visitors type
	 * @param msg[7]    - String containing visitors max visitors (or family
	 *                  members).
	 * @param msg - next available member id.
	 * 
	 * @return String - containing member id or false if process failed.
	 */
	public String addMember(String[] msg) {
		Statement stm;
		String memberCNT = String.valueOf(nextMember());
		try {
			PreparedStatement ps = conn.prepareStatement(

					"INSERT project.person SET ID=? ,firstName=?, lastName=?, phoneNumber=? ,Email=? ,creditCardNum=? ,Type=? ,maxFamilyMembers=?,memberId=?");

			ps.setString(1, msg[0]);
			ps.setString(2, msg[1]);
			ps.setString(3, msg[2]);
			ps.setString(4, msg[3]);
			ps.setString(5, msg[4]);
			ps.setString(6, msg[5]);
			ps.setString(7, msg[6]);
			ps.setString(8, msg[7]);
			ps.setString(9, memberCNT);
			ps.executeUpdate();
			return memberCNT.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return "false";
		}

	}

	/**
	 * Description of nextMember() - this function finds next available membership
	 * id.
	 * 
	 * @return int - next next available membership id. .
	 */
	public int nextMember() {
		Statement stm;
		int i = 0;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM project.person");
			stm = conn.createStatement();
			ResultSet rs = ps.executeQuery();

			rs.next();
			i = rs.getInt("rowcount");
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i += 1000000002;

	}

///////////////////// End Sign Up Member /////////////////////////////

	

	
	
	

	/* * ------------------------------- START ORDER * -------------------------------------/
	 * 
	 * /** Description of nextOrder()
	 * 
	 */
	public int nextOrder() {
		Statement stm;
		int i = 0;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT orderNum FROM project.order");
			stm = conn.createStatement();
			ResultSet rs = ps.executeQuery();
			
				
			while (rs.next()) {
				String tmp = rs.getString(1);
				i = Integer.parseInt(tmp);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return ++i;

	}

	/**
	 * Description of getOrderNum(String[] res) This method will return the order
	 * num based on the informaton it got
	 * 
	 * @param res[0] - The Time in the park
	 * @param res[1] - The Date of visit
	 * @param res[2] - The wantedPark
	 * @param res[3] - The Id
	 * @return orderNumber - the order number
	 */
	public int getOrderNum(String[] res) {
		Statement stm;
		int orderNumber = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT orderNum FROM project.order WHERE TimeInPark=? AND DateOfVisit=? AND wantedPark=? AND ID=?");
			stm = conn.createStatement();
			ps.setString(1, res[0]);
			ps.setString(2, res[1]);
			ps.setString(3, res[2]);
			ps.setString(4, res[3]);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				orderNumber = Integer.parseInt(rs.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return orderNumber;
	}

	/**
	 * Description of check_Confirmation(int orderNum) This method will heck if the
	 * 
	 * @param orderNum
	 * @return
	 */
//	public String check_Confirmation(int orderNum) {
//		try {
//			PreparedStatement ps = conn.prepareStatement("SELECT confirmed from project.order WHERE orderNum = ?");
//			ps.setInt(1, orderNum);
//			ResultSet rs = ps.executeQuery();
//			rs.next();
//			return rs.getString(1);// return confirmed
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//}

	/**
	 * Description of howManyForCurrentTimeAndDat1e(String[] msg)
	 * 
	 * check how many visitors in total there is for the desired time and date and
	 * return this value
	 * 
	 * @param msg[0] - The time in the park
	 * @param msg[1] - The date of the visit
	 * @param msg[2] - The wanted park
	 * @return the number fo visitors for the current time date and park
	 */
//	public int howManyForCurrentTimeAndDat1e(String[] msg) {
//		String res;
//		int counter = 0;
//		Statement stm;
//		try {
//			PreparedStatement ps = conn.prepareStatement(
//					"SELECT numOfVisitors FROM project.order WHERE TimeInPark=? AND DateOfVisit=? AND wantedPark=?");
//			ps.setString(1, msg[0]);
//			ps.setString(2, msg[1]);
//			ps.setString(3, msg[2]);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				res = rs.getString(1);
//				counter += Integer.parseInt(res);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//
//		}
//		return counter;
//	}

	/**
	 * Description of howManyForCurrentTimeAndDate(String[] result)
	 * 
	 * @param result[0] - The time in the park
	 * @param result[1] - The date of the visit
	 * @param result[2] - The wanted park
	 * @return int - i
	 * @throws ParseException - Parse
	 */
	public int howManyForCurrentTimeAndDate(String[] result) throws ParseException {
		Statement stm;

		int counter = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(

					"SELECT numOfVisitors,orderNum FROM project.order WHERE wantedPark=? AND DateOfVisit=? AND TimeInPark BETWEEN ? AND ? AND (status= 'waitForConfirm' OR (status='entered' OR status='confirmed'))");

			ps.setString(1, result[2]);
			ps.setString(3, result[0]);
			ps.setString(4, result[1]);
			ps.setString(2, result[3]);
			ResultSet rs = ps.executeQuery();
			stm = conn.createStatement();
			while (rs.next()) {
				counter += rs.getInt(1);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return counter;
	}

	/**
	 * Description of addOrder(int orderNum, String[] result) Method to insert new
	 * order to table
	 * 
	 * @param result[0] -time
	 * @param result[1] -date
	 * @param result[2] -parkname
	 * @param result[3] -price
	 * @param result[4] -id
	 * @param result[5] -type
	 * @param result[6] -numOfVisit
	 * @param orderNum -  orderNum
	 */

	public void addOrder(int orderNum, String[] result) {
		Statement stm;
		LocalDate wanted1 = LocalDate.parse(result[1]);
		Date wanted = java.sql.Date.valueOf(wanted1);

		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO project.order (orderNum, TimeInPark, DateOfVisit, wantedPark, TotalPrice, ID,type,numOfVisitors) VALUES (?,?,?,?,?,?,?,?)");
			ps.setInt(1, orderNum);
			ps.setString(2, result[0]);
			ps.setDate(3, wanted);
			ps.setString(4, result[2]);
			ps.setFloat(5, Float.parseFloat(result[3]));
			ps.setString(6, result[4]);
			ps.setString(7, result[5]);
			ps.setString(8, result[6]);

			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	/**
	 * Description of changeStatusOfOrder(String[] result,String status,String
	 * comment) This method will change status of order to cancel, confirm etc
	 * 
	 * @param result[0] - Time in park
	 * @param result[1] - Date of visit
	 * @param result[2] - Wanted park
	 * @param result[3] - Id of client
	 * @param status - staus
	 * @param comment - commet
	 
	 */
	public void changeStatusOfOrder(String[] result, String status, String comment) {
		Statement stm;
		

		try {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE project.order SET status=? ,comment=? WHERE TimeInPark=? AND DateOfVisit=? AND wantedPark=? AND ID=?");

			ps.setString(1, status);
			ps.setString(2, comment);
			ps.setString(3, result[0]);
			ps.setString(4, result[1]);
			ps.setString(5, result[2]);
			ps.setString(6, result[3]);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Description of getOrders(String iD) This method will return all the orders
	 * based on the id given from the client
	 * 
	 * @param iD - The id of the client
	 *@return String -r
	 */
	public String getOrders(String iD) {
		Statement stm;
		StringBuffer s = new StringBuffer();
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT orderNum,DateOfVisit,wantedPark,TimeInPark,numOfVisitors,TotalPrice,status,comment FROM project.order WHERE ID=? AND (status='waitForConfirm' OR status='confirmed' OR status='InWaitingList' OR status='waitForConfirm_WaitingList')");
			ps.setString(1, iD);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				for (int i = 1; i <= 8; i++) {
					s.append(rs.getString(i));
					s.append(" ");
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		s.append("Done");

		return s.toString();
	}
	/**
	 * Description of checkIfHavingTomorrow(String[] result) method that will check
	 * if some traveller have order for tomorrow, if so will check if its not been
	 * informed for him yet. if informed, will not do anything. if found some of
	 * them, will return to the client a massege that he have some orders need to be
	 * approved. also this method will change akk the Informed values to 't'
	 * 
	 * @param result[0] - The date of the visit
	 * @param result[1] - The Id of the client
	 * @return String - str
	 */
	public String checkIfHavingTomorrow(String[] result) {
		String s = "";
		
		int flag = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT orderNum from project.order WHERE DateOfVisit = ? AND ID=? AND Informed = 'f'");

			ps.setString(1, result[0]);
			ps.setString(2, result[1]);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getString(1);
				flag++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag > 0) {
			try {// try to get travellers with out order
				PreparedStatement ps = conn
						.prepareStatement("UPDATE project.order SET Informed='t' WHERE DateOfVisit=? AND ID=?");
				ps.setString(1, result[0]);
				ps.setString(2, result[1]);
				ps.executeUpdate();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return s;
	}

	/**
	 * Description of checkIfConfirmAlert(String dat, String timeOfVisit) This
	 * method if responsible of getting back all the orderes that passed two hours
	 * and still no one have approved them they will be canceled automaticly later
	 * 
	 * @param dat         - Date of the visit
	 * @param timeOfVisit - Time of the visit
	 * @return String - s
	 */
	public String checkIfConfirmAlert(String dat, String timeOfVisit) {
		StringBuffer sb = new StringBuffer();
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT orderNum,DateOfVisit from project.order WHERE DateOfVisit =? AND TimeInPark =? AND status='waitForConfirm'");
			ps.setString(1, dat);
			ps.setString(2, timeOfVisit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sb.append(rs.getString(1));
				sb.append(" ");
				sb.append(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Description of cancelOrderForWaiting(String orderNum) This method will make
	 * the order that no one have approved as canceled and say that the cancelation
	 * is automaticly
	 * 
	 * @param orderNum - The number of the order
	
	 */
	public void cancelOrderForWaiting(String orderNum) {
		try {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE project.order SET status='cancelled',comment='Automatic' WHERE orderNum=?");
			ps.setString(1, orderNum);
			ps.executeUpdate();
			// waitingList here

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description of conAlert(String orderNum) This method will be responsible to
	 * make the status as confirmed after the client approving it day before his
	 * visit
	 * 
	 * @param orderNum - ordere
	 */
	public void conAlert(String orderNum) {
		try {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE project.order SET status='confirmed',comment='OrderConfirmed' WHERE orderNum=?");
			ps.setString(1, orderNum);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Description of checkHowManyCancelled(String[] result, String status) This
	 * method will check how many of the orders has been canceled for the reports
	 * later
	 * 
	 * @param result[0] - Date from
	 * @param result[1] - Date to
	 * @param status -status
	 * @return number of canceled orders
	 */
	public int checkHowManyCancelled(String[] result, String status) {
		Statement stm;
		int counter = 0;

		try {
			PreparedStatement ps = conn.prepareStatement(

					"SELECT * from project.order WHERE status = ? AND DateOfVisit BETWEEN ? AND ?");

			ps.setString(1, status);
			ps.setString(2, result[0]);
			ps.setString(3, result[1]);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				counter++;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return counter;
	}

	
	/* ------------------------------- END ORDER ----------------------------------- */
	 

	
	
	
	

	public void changeStatusForTravellerInPark(String[] msg) {
		Statement stm;
		try {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE project.travellerinpark SET inpark=0 WHERE ID=? AND wantedpark=? AND Date=curdate()");
			ps.setString(1, msg[0]); // ID
			ps.setString(2, msg[1]); // park
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	

	

	

	

	

	

	////// Reports start/////
	
	public String getMonthlyIncomes(String date_month[], String type) {
		int cnt;
		int income = 0;
		LocalDate date = LocalDate.parse(date_month[0]);
		String park = date_month[1];

		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT sum(numOfVisitors) ,sum(TotalPrice) FROM project.order WHERE type = ? && (MONTH(DateOfVisit) = ? AND YEAR(DateOfVisit) = ? AND wantedPark=? AND status='done') && status = 'done'");
			ps.setString(1, type);
			ps.setInt(2, date.getMonthValue());// MONTH
			ps.setInt(3, date.getYear());// YEAR
			ps.setString(4, park);// park
			ResultSet rs = ps.executeQuery();
			rs.next();
			cnt = rs.getInt(1);// count(*)
			if (cnt > 0) {
				income = rs.getInt(2);
			}
			String res = "" + cnt + " " + income;
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}
	}

	public String getMonthlyIncomes_TravelerInPark(String[] date_month) {
		int cnt;
		int income = 0;
		LocalDate date = LocalDate.parse(date_month[0]);
		String park = date_month[1];
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT sum(numOfVisitors) ,sum(price) FROM project.travellerinpark WHERE (MONTH(Date) = ? AND YEAR(Date) = ? AND wantedPark = ?) && inPark = 0");
			ps.setInt(1, date.getMonthValue());// MONTH
			ps.setInt(2, date.getYear());// YEAR
			ps.setString(3, park);// park
			ResultSet rs = ps.executeQuery();
			rs.next();
			cnt = rs.getInt(1);// count(*)
			if (cnt > 0) {
				income = rs.getInt(2);
			}
			String res = "" + cnt + " " + income;

			return res;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}
	}
	
	
	
	public String getVisitorsDataReport(String[] monthYearPark) {
		String month = monthYearPark[0];
		String year = monthYearPark[1];
		String park = monthYearPark[2];
		StringBuffer sb = new StringBuffer();
		Statement stm;
		int sumSolo = 0, sumMembers = 0, sumGroups = 0;
		try {// try to get travellers with out order
			PreparedStatement ps = conn.prepareStatement(
					"SELECT numOfVisitors FROM project.travellerinpark WHERE wantedPark=? AND MONTH(Date)=? AND YEAR(Date)=?");
			ps.setString(1, park);
			ps.setString(2, month);
			ps.setString(3, year);
			stm = conn.createStatement();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sumSolo += rs.getInt("numOfVisitors");
			}
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {// try to get members and family members with order
			PreparedStatement ps = conn.prepareStatement(
					"SELECT numOfVisitors FROM project.order WHERE wantedPark=? AND MONTH(DateOfVisit)=? AND YEAR(DateOfVisit)=? AND status='done' AND (type='Member' OR type='Family') ");
			ps.setString(1, park);
			ps.setString(2, month);
			ps.setString(3, year);
			stm = conn.createStatement();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sumMembers += rs.getInt("numOfVisitors");
			}
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {// try to get Groups members with order
			PreparedStatement ps = conn.prepareStatement(
					"SELECT numOfVisitors FROM project.order WHERE wantedPark=? AND MONTH(DateOfVisit)=? AND YEAR(DateOfVisit)=? AND status='done' AND type='Group' ");
			ps.setString(1, park);
			ps.setString(2, month);
			ps.setString(3, year);
			stm = conn.createStatement();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sumGroups += rs.getInt("numOfVisitors");
			}
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		sb.append(sumSolo);
		sb.append(" ");
		sb.append(sumMembers);
		sb.append(" ");
		sb.append(sumGroups);
		return sb.toString();
	}

	////////////////// Entrance and Stay Report ////////////////////
	public String getEntranceAndStay(String[] monthYearPark) {
		String month = monthYearPark[0];
		String year = monthYearPark[1];
		String park = monthYearPark[2];
		StringBuffer sb = new StringBuffer();
		Statement stm;
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT numOfVisitors,enterTime,exitTime,Date FROM project.travellerinpark WHERE wantedPark=? AND MONTH(Date)=? AND YEAR(Date)=?");
			ps.setString(1, park);
			ps.setString(2, month);
			ps.setString(3, year);
			stm = conn.createStatement();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sb.append(rs.getInt("numOfVisitors") + " " + "traveller" + " " + rs.getString("enterTime") + " "
						+ rs.getString("exitTime") + " " + rs.getString("Date") + " ");

			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fail to get visitors data");
			e.printStackTrace();
		}
		try {// try to get Groups members with order
			PreparedStatement ps = conn.prepareStatement(
					"SELECT numOfVisitors, type, enterTime, exitTime,DateOfVisit FROM project.order WHERE wantedPark=? AND MONTH(DateOfVisit)=? AND YEAR(DateOfVisit)=? AND status='done'");
			ps.setString(1, park);
			ps.setString(2, month);
			ps.setString(3, year);
			stm = conn.createStatement();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sb.append(rs.getInt("numOfVisitors") + " " + rs.getString("type") + " " + rs.getString("enterTime")
						+ " " + rs.getString("exitTime") + " " + rs.getString("DateOfVisit") + " ");
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fail to get members and group guides data");
			e.printStackTrace();
		}

		sb.append("Done");
		return sb.toString();
	}

	////// Reports end/////





	
	
	//Park controller start	

		/**
		 * this method get max available visitors in park
		 * 
		 * @param parkName - parkName
		 * @return max available visitors in park
		 */
		public int howManyAllowedInPark(String parkName) {
			String res;
			int counter = 0;
			Statement stm;
			try {
				PreparedStatement ps = conn

						.prepareStatement("SELECT maxAvailableVisitors FROM project.park WHERE ParkName=?");

				ps.setString(1, parkName);

				ResultSet rs = ps.executeQuery();
				rs.next();

				res = rs.getString(1);
				counter += Integer.parseInt(res);

			} catch (SQLException e) {
				e.printStackTrace();

			}
			return counter;
		}

		/**
		 * this method get current unexpected visitors in park
		 * 
		 * @param parkName - parkName
		 * @return current unexpected visitors in park
		 */
		public int howManyUnexpectedVisitorsInPark(String parkName) {
			String res;
			int unexpectedVisitors = 0;
			Statement stm;
			try {
				PreparedStatement ps = conn
						.prepareStatement("SELECT AmountOfUnExpectedVisitors FROM project.park WHERE parkName=?");

				ps.setString(1, parkName);
				stm = conn.createStatement();
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					res = rs.getString(1);
					unexpectedVisitors = Integer.parseInt(res);
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
			return unexpectedVisitors;
		}

		/**
		 * this method get current visitors now in park
		 * 
		 * @param parkName - parkName
		 * @return current visitors in park
		 */
		public int howManyCurrentvisitorsForOrdersInPark(String parkName) {
			String res;
			int currentvisitors = 0;
			Statement stm;
			try {
				PreparedStatement ps = conn.prepareStatement("SELECT currentVisitors FROM project.park WHERE parkName=?");
				ps.setString(1, parkName);
				stm = conn.createStatement();
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					res = rs.getString(1);
					currentvisitors = Integer.parseInt(res);
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
			return currentvisitors;
		}

		/**
		 * this method getmax visitors now in park
		 * 
		 * @param parkName - parkName
		 * @return max visitors in park
		 */
		public int howManyMaxvisitorsAllowedInPark(String parkName) {
			String res;
			int Maxvisitors = 0;
			Statement stm;
			try {
				PreparedStatement ps = conn.prepareStatement("SELECT maxVisitors FROM project.park WHERE parkName=?");
				ps.setString(1, parkName);
				stm = conn.createStatement();
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					res = rs.getString(1);
					Maxvisitors = Integer.parseInt(res);
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
			return Maxvisitors;
		}

		/**
		 * this method get max duration in park
		 * 
		 * @param parkName - parkName
		 * @return max duration in park
		 */
		public float howmanyTimeEveryVisitorInPark(String parkName) {
			String res;
			float time = 0;
			Statement stm;
			try {
				PreparedStatement ps = conn.prepareStatement("SELECT maxDuration FROM project.park WHERE parkName=?");
				ps.setString(1, parkName);
				stm = conn.createStatement();
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					res = rs.getString(1);
					time = Float.parseFloat(res);
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
			return time;
		}

		/**
		 * this method insert data for full capacity table
		 * 
		 * @param result- data of full capacity table
		 */
		public void insertfullcapacityPark(String[] result) {
			Statement stm;
			LocalDate wanted1 = LocalDate.parse(result[1]);
			Date wanted = java.sql.Date.valueOf(wanted1);

			try {
				PreparedStatement ps = conn.prepareStatement(
						"INSERT INTO project.fullcapacity(park,date,full,maxVisitors,maxCurrentPerDay) VALUES (?,?,?,?,?)");
				ps.setString(1, result[0]); // park
				ps.setDate(2, wanted); // Date
				ps.setString(3, result[2]);
				ps.setInt(4, Integer.parseInt(result[3])); // maxVisitors
				ps.setInt(5, Integer.parseInt(result[4])); // maxCurrent
				ps.execute();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

		/**
		 * this method update max current amount of visitors to full capacity table
		 * 
		 * @param msg- max current per day and park
		 */
		public void changeMaxcurrentAmountOfVisitorsForCapacityPark(String[] msg) {
			Statement stm;
			try {
				PreparedStatement ps = conn.prepareStatement(
						"UPDATE project.fullcapacity SET maxCurrentPerDay=? WHERE park=? and date=curdate()");
				ps.setString(2, msg[0]); // park
				ps.setInt(1, Integer.parseInt(msg[1])); // maxCurrent
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		/**
		 * this method check if this date per park in DB
		 * 
		 * @param msg- park name
		 * @return true if succeed and false if not succeed
		 */
		public boolean isDateInfullcapacityExists(String[] msg) {
			Statement stm;
			try {

				PreparedStatement ps = conn
						.prepareStatement("SELECT * FROM project.fullcapacity WHERE park=? AND Date=curdate()");
				stm = conn.createStatement();
				ps.setString(1, msg[0]);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;

			}
			return false;
		}

		/**
		 * this method update status for capacity park to full
		 * 
		 * @param msg -park name
		 */
		public void changeStatusForCapacityParkToFull(String[] msg) {
			Statement stm;
			try {
				PreparedStatement ps = conn
						.prepareStatement("UPDATE project.fullcapacity SET full=1 WHERE park=? and date=curdate()");
				ps.setString(1, msg[0]); // park
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		/**
		 * this method get max current visitors per day
		 * 
		 * @param msg- park name
		 * @return max current visitors per day in specific park
		 */
		public int getMaxcurrentVisitorsPerDay(String[] msg) {
			String res;
			int max = 0;
			Statement stm;
			try {
				PreparedStatement ps = conn.prepareStatement(
						"SELECT maxCurrentPerDay FROM project.fullcapacity WHERE park=? AND date=curDate()");
				ps.setString(1, msg[0]);
				stm = conn.createStatement();
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					res = rs.getString(1);
					max = Integer.parseInt(res);
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
			return max;
		}

		// Park controller end

		// EntrancePark controller start
		/**
		 * this method update amount of unexpected visitors in park
		 * 
		 * @param msg- amount of unexpectd visitors and park name
		 */
		public void updateUnexpectedVisitors(String[] msg) {
			Statement stm;
			try {
				PreparedStatement ps = conn

						.prepareStatement("UPDATE project.park SET AmountOfUnExpectedVisitors=? WHERE parkName=?");

				ps.setInt(1, Integer.parseInt(msg[1]));
				ps.setString(2, msg[0]);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/**
		 * this method update current visitors in park
		 * 
		 * @param msg- amount of current visitors and park name
		 */
		public void updateCurrentVisitors(String[] msg) {
			Statement stm;
			try {
				PreparedStatement ps = conn.prepareStatement("UPDATE project.park SET currentVisitors=? WHERE ParkName=?");
				ps.setInt(1, Integer.parseInt(msg[1]));
				ps.setString(2, msg[0]);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/**
		 * update enter time of traveller with order in order table
		 * 
		 * @param msg -park name and ID
		 */
		public void enterEnterTimeForTravellerWithOrder(String[] msg) {
			Statement stm;
			try {
				PreparedStatement ps = conn.prepareStatement(
						"UPDATE project.order SET enterTime=TIME_FORMAT(curtime(), '%k:%i'), status=\"in park\" WHERE wantedPark=? AND DateOfVisit=curdate() AND ID=? AND status=\"Confirmed\" ");
				ps.setString(1, msg[0]);
				ps.setString(2, msg[1]);
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();

			}
		}

		/**
		 * update exit time of traveller with order in order table
		 * 
		 * @param msg -park name and ID
		 */
		public void enterExitTimeForTravellerWithOrder(String[] msg) {
			Statement stm;
			try {
				PreparedStatement ps = conn.prepareStatement(
						"UPDATE project.order SET ExitTime=TIME_FORMAT(curtime(), '%k:%i'),status=\"done\" WHERE wantedPark=? AND DateOfVisit=curdate() AND ID=? AND status=\"in park\" ");
				ps.setString(1, msg[0]);
				ps.setString(2, msg[1]);
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();

			}
		}

		/**
		 * update enter time of traveller without order in travellerinpark table
		 * 
		 * @param msg -park name and ID
		 */
		public void enterExitTimeForcasualTraveller(String[] msg) {
			Statement stm;
			try {
				PreparedStatement ps = conn.prepareStatement(
						"UPDATE project.travellerinpark SET exitTime=TIME_FORMAT(curtime(), '%k:%i'), inPark=0 WHERE wantedPark=? AND Date=curdate() AND ID=? ");
				ps.setString(1, msg[0]);
				ps.setString(2, msg[1]);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/**
		 * this method insert traveller to travellerinpark table
		 * 
		 * @param result - parameters for travellerinpark table
		 */
		public void insertTravellerInPark(String[] result) {
			Statement stm;
			LocalDate wanted1 = LocalDate.parse(result[2]);
			Date wanted = java.sql.Date.valueOf(wanted1);

			try {
				PreparedStatement ps = conn.prepareStatement(
						"INSERT INTO project.travellerInPark(ID,numOfVisitors,Date,enterTime,exitTime,price,wantedPark,inPark) VALUES (?,?,?,?,null,?,?,1)");
				ps.setString(1, result[0]); // ID
				ps.setString(2, result[1]); // numberOfVisitors
				ps.setDate(3, wanted); // Date
				ps.setString(4, result[3]); // time
				ps.setFloat(5, Float.parseFloat(result[4]));
				ps.setString(6, result[5]);
				ps.execute();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}
	/**
	 * this method check if traveller exists in DB
	 * @param msg- ID
	 * @return true if traveller exists in travellerinpark table and fale if not exists
	 */
		public boolean isTravellerExistsInPark(String[] msg) {
			Statement stm;
			try {

				PreparedStatement ps = conn
						.prepareStatement("SELECT * FROM project.travellerinpark WHERE ID=? AND inPark=1");
				stm = conn.createStatement();
				ps.setString(1, msg[0]);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;

			}
			return false;
		}
	/**
	 * this method get details of travellerinpark table
 	 * @param id - id
	 * @return traveller details
	 */
		public String getTravellerInParkDetails(String id) {
			Statement stm;
			int num;
			StringBuffer s = new StringBuffer();
			try {
				PreparedStatement ps = conn
						.prepareStatement("SELECT numOfVisitors, wantedPark FROM project.travellerinpark WHERE ID=?");
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					num = Integer.parseInt(rs.getString(1));
					s.append(num);
					s.append(" ");
					s.append(rs.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}

			return s.toString();
		}
	/**
	 * this method check if order exists in DB 
	 * @param msg - ID
	 * @return true if exists, else- not exists 
	 */
		public boolean isOrderExistsInPark(String[] msg) {
			Statement stm;
			try {

				PreparedStatement ps = conn
						.prepareStatement("SELECT * FROM project.order WHERE ID=? AND status=\"in park\" ");
				stm = conn.createStatement();
				ps.setString(1, msg[0]);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;

			}
			return false;
		}
	/**
	 * this methods get details for exit park
	 * @param id - id
	 * @return order details 
	 */
		public String getOrderDetailsForExitPark(String id) {
			Statement stm;
			int num;
			StringBuffer s = new StringBuffer();
			try {
				PreparedStatement ps = conn.prepareStatement(
						"SELECT numOfVisitors, wantedPark FROM project.order WHERE ID=? AND status=\"in park\" ");
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					num = Integer.parseInt(rs.getString(1));
					s.append(num);
					s.append(" ");
					s.append(rs.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}

			return s.toString();
		}
		// EntrancePark controller end

		// Report controller start
		/**
		 * this method gets details of full capacity table for usage report
		 * @param msg - month and year
		 * @return details of full capacity table
		 */
		public String getUnFullCapacityTableInDates(String[] msg) {
			Statement stm;
			StringBuffer s = new StringBuffer();
			try {
				PreparedStatement ps = conn.prepareStatement(
						"SELECT date FROM project.fullcapacity WHERE month(date)=? and year(date)=? and park=? and full=0");
				ps.setString(1, msg[0]);// month
				ps.setString(2, msg[1]);// year
				ps.setString(3, msg[2]); // park
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					s.append(rs.getString(1));
					s.append(" ");
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
			s.append("Done");

			return s.toString();
		}
		/**
		 * this method gets details of full capacity table for usage report
		 * @param msg - month  and year
		 * @return details of full capacity table
		 */
		public String getUnFullCapacityTableInDatesAndNumbers(String[] msg) {
			Statement stm;
			StringBuffer s = new StringBuffer();
			try {
				PreparedStatement ps = conn.prepareStatement(
						"SELECT date_format(date, \"%e/%c\" ),maxVisitors,maxCurrentPerDay FROM project.fullcapacity WHERE month(date)=? and year(date)=? and park=? and full=0 ORDER BY date ASC");
				ps.setString(1, msg[0]);// month
				ps.setString(2, msg[1]);// year
				ps.setString(3, msg[2]); // park
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					s.append(rs.getString(1));// date
					s.append(" ");
					s.append(rs.getString(2)); // maxVisitors
					s.append(" ");
					s.append(rs.getString(3)); // maxCurrent
					s.append(" ");
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
			s.append("Done");

			return s.toString();
		}
		// Report controller end

		// Requests controller start
		/**
		 * this method insert details to request table
		 * @param result - details for enter request details
		 */
		public void insertRequest(String[] result) {
			Statement stm;
			LocalDate wanted1 = LocalDate.parse(result[1]);
			Date wanted = java.sql.Date.valueOf(wanted1);

			try {
				PreparedStatement ps = conn.prepareStatement(
						"INSERT INTO project.requests(IdOfAsks,dateOfRequest,timeOfRequest,wantedpark,numberOfVisitors,type,status) VALUES (?,?,?,?,?,?,-1)");
				ps.setString(1, result[0]); // ID
				ps.setDate(2, wanted); // Date
				ps.setString(3, result[2]); // park
				ps.setString(4, result[3]); // numOfVisitors
				ps.setString(5, result[4]); // time
				ps.setString(6, result[5]); // type
				ps.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	/**
	 * this method check if the request is approve
	 * @param result- id annd type
	 * @return status of request- 0 for unapprove and 1 for approve
	 */
		public int IsApproveEnterParkForTraveller(String[] result) {
			String res;
			int status = -1;
			Statement stm;
			try {
				PreparedStatement ps = conn
						.prepareStatement("SELECT status FROM project.requests WHERE idOfAsks=? AND type=? ");
				ps.setString(1, result[0]); // ID
				ps.setString(2, result[1]); // type
				stm = conn.createStatement();
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					res = rs.getString(1);
					status = Integer.parseInt(res);
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
			return status;
		}
	/**
	 * this method get details from request table
	 * @param park - park
	 * @return details request table
	 */
		public String getRequestTableOfEnterPark(String park) {
			Statement stm;
			StringBuffer s = new StringBuffer();
			try {
				PreparedStatement ps = conn.prepareStatement(
						"SELECT IdOfAsks,timeOfRequest,numberOfVisitors FROM project.requests WHERE wantedpark=? and dateOfRequest=curdate() and type=\"EnterPark\" and ( status=\"-1\" OR status=\"0\" ) ORDER BY timeOfRequest ASC");
				ps.setString(1, park);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					for (int i = 1; i <= 3; i++) {
						s.append(rs.getString(i));
						s.append(" ");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
			s.append("Done");

			return s.toString();
		}
	/**
	 * this method change request status in request table
	 * @param msg- status, id and park
	 */
		public void changeRequestStatusForCasualTraveller(String[] msg) {
			Statement stm;
			try {
				PreparedStatement ps = conn.prepareStatement(
						"UPDATE project.requests SET status=? WHERE IdOfAsks=? AND wantedpark=? and dateOfRequest=curdate() and type=\"EnterPark\"");
				ps.setString(1, msg[0]); // status
				ps.setString(2, msg[1]); // ID
				ps.setString(3, msg[2]); // park
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		// Requests controller end
		
		/*---------------------------------------------- Discounts Start ---------------------------------------------------*/				
		public boolean isDiscountWaitingForApprove(String rowNumber) {
			int rowIndex = Integer.valueOf(rowNumber);
			try {
				PreparedStatement ps = conn.prepareStatement("SELECT status FROM project.managerdiscounts");
				ResultSet rs = ps.executeQuery();
				//go to rowIndex/
				for(int i = 0 ; i <= rowIndex; i++)
					rs.next();
				String status = rs.getString(1);
				if(status.equals("waitingForApprove"))
					return true;
				else 
					return false;
				  
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
		}


		public String getDiscountWaitingForApprove(String rowNumber) {
			int rowIndex = Integer.valueOf(rowNumber);
			try {
				PreparedStatement ps = conn.prepareStatement("SELECT parkName, sentDate , sentTime ,startDate , lastDate, precentage FROM project.managerdiscounts;");
				ResultSet rs = ps.executeQuery();
				//go to rowIndex/
				for(int i = 0 ; i <= rowIndex; i++)
					rs.next();
				
				String parkName = rs.getString(1);
				Date sentDate = rs.getDate(2);
				Time sentTime = rs.getTime(3);
				Date startDate = rs.getDate(4);
				Date lastDate = rs.getDate(5);
				Float precentage = rs.getFloat(6);
				
				
				StringBuffer sb = new StringBuffer();
				sb.append(parkName);
				sb.append(" ");
				sb.append(sentDate.toString());
				sb.append(" ");
				sb.append(sentTime.toString());
				sb.append(" ");
				sb.append(startDate.toString());
				sb.append(" ");
				sb.append(lastDate.toString());
				sb.append(" ");
				sb.append(precentage);
		
				return sb.toString();
				  
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			
		}


		/** set Discount Status
		 * @param parkName - park
		 * @param status -status
		 */
		public void setDiscountStatus(String parkName, String status) {
			try {
				PreparedStatement ps = conn.prepareStatement("UPDATE project.managerdiscounts SET status = ? Where parkName = ?");
				ps.setString(1, status);
				ps.setString(2, parkName);
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println("SQL ERROR - setDiscountStatus");
			}
			
		}

		/** getManagerDiscount if approved and valid for dateOfVisit - return precentage ,else return -1
		 * @param parkName - park
		 * @param dateOfVisit - date 
		 * @return precentage - precent
		 */ 
		public float getManagerDiscount(String parkName, String dateOfVisit) {
			LocalDate temp = LocalDate.parse(dateOfVisit);
			Date visitDate = java.sql.Date.valueOf(temp);
			try {
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM project.managerdiscounts WHERE parkName=? ");
				ps.setString(1, parkName);
				ResultSet rs = ps.executeQuery();
				rs.next();
				Date startDate = rs.getDate(2);
				Date endDate = rs.getDate(3);
				Float precentage = rs.getFloat(4);
				String status = rs.getString(5);

				if (status.equals("Approved")) // discount is confirmed by D_M
				{
					if (startDate.before(visitDate) && endDate.after(visitDate)) // valid
						return precentage;

					if (startDate.equals(visitDate) || endDate.equals(visitDate)) // valid
						return precentage;
				}
				return -1; // invalid

			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}

		}
		
		//this method will go into the db and check what the discount and the price need to be given for the current visitor
			public String getTotalPayload(String typeOfService) {
				StringBuffer sb = new StringBuffer();
				
				Statement stm;
				int counter=0;
			
				try {
					PreparedStatement ps = conn.prepareStatement(
							"SELECT departmentPrice,valueOfDiscount,Members from project.discounts WHERE typeOfService = ?");

					ps.setString(1, typeOfService); 
					
					
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						sb.append(rs.getString(1));
						sb.append(" ");
						sb.append(rs.getString(2));
						sb.append(" ");
						sb.append(rs.getString(3));
					}

				} catch (SQLException e) {
					e.printStackTrace();

				}
				return sb.toString();
				
			}
		
			
			/** update Manager Discount - always set status: 'waitingForApprove' after update
			 * @param startDate - start
			 * @param lastDate - last
			 * @param precentage - precent
			 * @param parkName - park
			 * @return boolean -  true if successed
			 */
			public boolean updateManagerDiscount(String startDate, String lastDate, String precentage, String parkName) {
				Statement stm;
				PreparedStatement ps;
				try {
					ps = conn.prepareStatement("UPDATE project.managerdiscounts SET startDate = ?, lastDate = ?, precentage = ?, status = ?, sentDate = ?, sentTime = ?  where parkName = ?");
					LocalDate start = LocalDate.parse(startDate);
					Date startDate1 = java.sql.Date.valueOf(start);

					LocalDate end = LocalDate.parse(lastDate);
					Date endDate = java.sql.Date.valueOf(end);
					
					//RequestDate/
					LocalDate request = LocalDate.now();
					Date requestDate = java.sql.Date.valueOf(request);
					//RequestTime/
					LocalTime requestTime = LocalTime.now();
					Time now = java.sql.Time.valueOf(requestTime);

					ps.setDate(1, startDate1);// set start Date
					ps.setDate(2, endDate);// set end Date
					ps.setFloat(3, Float.valueOf(precentage));// set precentage (casting)
					ps.setString(4,"waitingForApprove"); // set status , will be changed by D_M to 'T' if approved OR 'F' if not
					ps.setDate(5, requestDate);//
					ps.setTime(6, now);
					ps.setString(7, parkName);
					ps.executeUpdate();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("catch updateManagerDiscount");
					return false;
				}
			}
	/*---------------------------------------------- Discounts End ---------------------------------------------------*/				
		
			
	/*---------------------------------------------- WaitingList Start ---------------------------------------------------*/		
			/**change Status By OrderNum of order in orders table DB
			 * @param orderNum - oreder
			 * @param status - statius
			 * @param comment - comment
			 */		
			public void changeStatusByOrderNum(int orderNum ,String status, String comment) {
				Statement stm;

				try {
					PreparedStatement ps = conn.prepareStatement("UPDATE project.order SET status=? ,comment=? WHERE orderNum = ?");

					ps.setString(1, status);
					ps.setString(2, comment);
					ps.setInt(3,orderNum);
					
					ps.execute();
				} catch (SQLException e) {
					e.printStackTrace();

				}
			}
			
			public String getOrderStatus(int orderNum) {
				try {
					PreparedStatement ps = conn.prepareStatement("SELECT Status FROM project.order where orderNum = ?");
					ps.setInt(1,orderNum);
					ResultSet rs = ps.executeQuery();
					rs.next();
					return rs.getString(1); // return Status
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
		}
			
			/** add Order To Waiting List DB with timestamp of now
			 * @param orderNum - ordernum
			 * @return boolean- true if successed
			 */
			public boolean addToWaitingList(String orderNum) {
				System.out.println("This is to add to waiting list");
				
				LocalDateTime now = LocalDateTime.now();
				try {
					PreparedStatement ps = conn.prepareStatement(
							"INSERT INTO project.waitinglist SET waitingOrder=? ,timestamp=?");
					ps.setString(1, orderNum);
					ps.setString(2,now.toString());
					ps.executeUpdate();
					return true;

				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}

			}
			
			
			/** remove order From Waiting List
			 * @param orderToRemove - order
			 * @return boolean - true if successed
			 */
			public boolean removeFromWaitingList(String orderToRemove) {
				Statement stm;
				try {
					PreparedStatement ps = conn.prepareStatement("DELETE FROM project.waitinglist WHERE waitingOrder = ?");
					stm = conn.createStatement();
					ps.setString(1, orderToRemove);
					ps.executeUpdate();
					return true;

				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}

			/**
			 * @param numOfOrder -num
			 * @return boolean: true if successed
			 */
			public boolean IsOrderInWaitingList(int numOfOrder) {
				try {

					PreparedStatement ps = conn.prepareStatement("SELECT * from project.waitinglist WHERE waitingOrder = ?");
					ps.setInt(1, numOfOrder);
					ResultSet rs = ps.executeQuery();
					return rs.next(); // return: true if numOfOrder exists in project.waitinglist DB
				} catch (SQLException e) {
					e.printStackTrace();
		      return false;
				}
			}
			
			
			/**
			 * 
			 * @param canceledOrder_DateOfVisit - date
			 * @return ArrayList- a-  sorted by timestamp in DB
			 */ 
			public ArrayList<Order> getSortedWatingOrders(String canceledOrder_DateOfVisit) {
				Statement stm;
				try {
					ArrayList<Order> waitingOrders = new ArrayList<>();
					PreparedStatement ps = conn.prepareStatement(
							"SELECT * FROM project.waitinglist join project.order ON project.waitinglist.waitingOrder =  project.order.orderNum where DateOfVisit = ?  ORDER BY timestamp");
					ps.setString(1,canceledOrder_DateOfVisit);
					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						int orderNum = rs.getInt(3);
						LocalTime time = (rs.getTime(4)).toLocalTime(); // rs.getTime(7) = TimeInPark
						String dat= rs.getString(5);
						
						LocalDate dateOfVisit =LocalDate.parse(dat);
						String wantedPark = rs.getString(6);
						int numberOfVisitors = rs.getInt(10);
						float totalPrice = rs.getFloat(7);
						Order o = new Order(orderNum, time, dateOfVisit, wantedPark, numberOfVisitors, totalPrice);
						waitingOrders.add(o);
					}

					return waitingOrders;

				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}			
	/*---------------------------------------------- WaitingList End ---------------------------------------------------*/
			
			
			
			
			//---------------------------------  Start employeeController  ------------------------------------------------------------------------------------------------------------------------------
			
			/**
			 * Description of canGetEmployee:
			 * canGetEmployee will return true if employee exists on our DB and false otherwise
			 * @param userName will be employee userName which is PK for our employees DB
			 * @return boolean true or false
			 */
			
			public boolean canGetEmployee(String userName) { 
				Statement stm;
				try {
					String[] s = userName.split(" "); // new row

					PreparedStatement ps = conn.prepareStatement("SELECT *  FROM project.Employees WHERE userName = ?");

					stm = conn.createStatement();
					ps.setString(1, s[0]);// new row
					ps.executeQuery();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
			
			/**
			 * Description of sendNewRequestID:
			 * sendNewRequestID will return number of tuples in requests for park changes for the use of request id which is PK for DB
			 * @return int - i
			 */
			
			public int sendNewRequestID() { 
				Statement stm;
				int i = 0;
				try {
					PreparedStatement ps = conn
							.prepareStatement("SELECT COUNT(*) AS rowcount FROM project.newparksettingsrequest");
					stm = conn.createStatement();
					ResultSet rs = ps.executeQuery();
					rs.next();
					i = rs.getInt("rowcount");
					rs.close();
				} catch (SQLException e) {
					return -1;
				}
				return ++i;
			}
			
			/**
			 * Description of sendParkSettingsRequestToDepManager:
			 * sendParkSettingsRequestToDepManager will insert new request for park setting changes from park manager to department manager 
			 * request will have id,date(now),time(now), changes and status (approved \ disapproved)
			 * method returns a string
			 * "true" means successfully updated DB with this request
			 * "false" means that it could not insert new request
			 * @param s - s
			 * @return String - sq
			 */

			public String sendParkSettingsRequestToDepManager(String[] s) {
				Statement stm;
				int id = sendNewRequestID();
				try {
					PreparedStatement ps = conn
							.prepareStatement("insert into project.newparksettingsrequest values (?, ?, ?, ?, ?, ?, ?, ?)");
					ps.setInt(1, id);
					LocalDate start = LocalDate.parse(s[1]);
					Date startDate = java.sql.Date.valueOf(start);
					LocalTime time = LocalTime.parse(s[2]);
					Time hour = java.sql.Time.valueOf(time);
					ps.setString(2, startDate.toString());
					ps.setString(3, hour.toString());
					ps.setString(4, s[3]);
					ps.setInt(5, Integer.parseInt(s[4]));
					ps.setInt(6, Integer.parseInt(s[5]));
					ps.setFloat(7, Float.parseFloat(s[6]));
					ps.setInt(8, Integer.parseInt(s[7]));
					ps.executeUpdate();
				} catch (SQLException e) {
					return "false";
				}
				return "true";
			}
			
			/**
			 * Description of getEmployeeUN:
			 * getEmployeeUN will return to client the data related with this employee (identified by userName )
			 * @param empID is the data which comes from client
			
			 * from this method we will navigate to goClient which handles message from server and by the data returned from here 
			 * we will be able to alert the employee if userName exists on DB, if password is incorrect or if employee can enter GoNature system
			 * @return String[] - str 
			 */

			public String[] getEmployeeUN(String empID) // if employee exists, DB returns this employee as a tuple (String[])
			{
				Statement stm;
				String check[] = empID.split(" ");
				String[] s = new String[14];
				int isLogged = 0;
				try {

					PreparedStatement ps = conn.prepareStatement("SELECT * FROM project.Employees WHERE userName = ?");

					stm = conn.createStatement();
					ps.setString(1, check[0]);
					ResultSet rs = ps.executeQuery();
					s[0] = "EmployeeController"; // For GoClient.HandleMessageFromServer
					s[1] = "IdentifyEmployee"; // in this case we assume that employee is in our DB --> if it isn't it will
												// change
					while (rs.next()) {
						for (int i = 2; i < 12; i++)
							s[i] = rs.getString(i - 1);
						isLogged = rs.getInt(11);
					}
					if (s[10] == null)
						s[1] = "IdentifyNotExistingEmployee"; // Means that we didn't find an employee with this userName
					else if (!(s[11].equals(check[1]))) {// It means that passwords do not match and employee can not enter to
															// GoNature system
						s[1] = "IdentifyPasswordDoesNotMatch";
						s[11] = null;
					} else if (isLogged == 1) {
						s[1] = "employeeAlreadyLoggedIn";
					} else {
						PreparedStatement ps1 = conn.prepareStatement("UPDATE employees SET isLoggedIn = 1 Where userName = ?");
						Statement stm1 = conn.createStatement();
						ps1.setString(1, check[0]);
						ps1.executeUpdate();
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
				return s;
			}
			
			/**
			 * Description of logOutEmployee:
			 * logOutEmployee will update our DB that employee has logged out from GoNature system
			 * @param userName is PK for employee in DB
			 * from this method we will navigate to goClient which handles message from server and by the data returned from here 
			 * true means logout executed appropriately 
			 * false means SQL exception
			 * @return boolean - truie
			 */
			
			public boolean logOutEmployee(String userName) {
				try {
					PreparedStatement ps1 = conn.prepareStatement("UPDATE employees SET isLoggedIn = 0 Where userName = ?");
					Statement stm1 = conn.createStatement();
					ps1.setString(1, userName);
					ps1.executeUpdate();
				} catch (SQLException e) {
					return false;
				}
				return true;
			}
			
			/**
			 * Description of updateParkChangesInParkTable:
			 * updateParkChangesInParkTable will update park with settings as requested by park manager.
			 * if we got to this method it means that departmentManager did approved this specific request
			 * @param parkName is the requested park for request change by park manager
			 * @param maxVisitors is amount of max visitors requested to change by park manager
			 * @param gap is the gap between max visitors to max amount of people that ordered. (in other words its the amount of un expected travellers)
			 * @param maxDur is the desired maximum duration in park requested by park manager
			 * true means park setting are changed
			 * false means could not perform the change
			 * @return boolean true or
			 */
			
			public boolean updateParkChangesInParkTable(String parkName, String maxVisitors, String gap, String maxDur) { //Bar employee
				Statement stm;
				try {
					PreparedStatement ps = conn.prepareStatement(
							"UPDATE project.park SET maxVisitors=? , maxAvailableVisitors = ?, maxDuration = ?  WHERE parkName= ? ");
					ps.setInt(1, Integer.parseInt(maxVisitors));
					int maxAvailable = Integer.parseInt(maxVisitors) - Integer.parseInt(gap);
					ps.setInt(2, maxAvailable);
					ps.setFloat(3, Float.parseFloat(maxDur));
					ps.setString(4, parkName);
					ps.executeUpdate();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
			
			/**
			 * Description of getParkSettingsRequests:
			 * getParkSettingsRequests will 
			 * if we got to this method it means that departmentManager wants to see park settings changes requests from park managers
			 * this method will return a large string with every available request (department manager did not touch it yet)
			 * for the use of EmployeeController so we will be able to display the requests via table
			 * @return String - s 
			 */
			
			public String getParkSettingsRequests() {
				String s;
				StringBuffer sb = new StringBuffer();
				sb.append("EmployeeController ");
				sb.append("displayParkSettingsRequestsToDepartmentManager ");
				int i = 2;
				int idReq = 0;
				String date = "";
				String time = "";
				String wantedPark = "";
				int maxVisit = 0, gapBetween = 0, len = 0;
				float maxDur = 0;
				Statement stm;
				try {
					PreparedStatement ps = conn
							.prepareStatement("SELECT * FROM project.newparksettingsrequest WHERE status = ?");
					stm = conn.createStatement();
					ps.setInt(1, 0);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						idReq = rs.getInt(1);
						date += rs.getString(2);
						time += rs.getString(3);
						wantedPark = rs.getString(4);
						maxVisit = rs.getInt(5);
						gapBetween = rs.getInt(6);
						maxDur = rs.getFloat(7);
						sb.append("" + idReq);
						sb.append(" ");
						sb.append(wantedPark + " ");
						sb.append(date + " ");
						sb.append(time + " ");
						sb.append("" + maxVisit + " ");
						sb.append("" + gapBetween + " ");
						sb.append("" + maxDur + " ");
						idReq = 0;
						date = "";
						time = "";
						wantedPark = "";
						maxVisit = 0;
						gapBetween = 0;
						maxDur = 0;
					}
				} catch (SQLException e) {
					return null;
				}
				sb.append("Done");
				s = sb.toString();
				return s;
			}
			
			/**
			 * Description of updateParkChangeRequestStatus:
			 * updateParkChangeRequestStatus will update request status in DB so we will be able to display to department manager only 
			 * requests which he did not touch yet (and only them!)
			 * @param string will be the request id 
			 * if we got to this method it means that department manager had clicked on approve \ disapprove button on a specific request in penidngRequest screen
			 * @return boolean - true or
			 */
			
			public boolean updateParkChangeRequestStatus(String string) {

				Statement stm;
				try {
					PreparedStatement ps = conn
							.prepareStatement("UPDATE project.newparksettingsrequest SET status=? WHERE requestID=?");
					ps.setInt(1, 1);
					ps.setInt(2, Integer.parseInt(string));
					ps.executeUpdate();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
			
		//--------------------------------- End employeeController ---------------------------------------------------------------------------------------	
			

		//--------------------------------- Start userController ---------------------------------------------------------------------------------------	
			
			/**
			 * Description of getTravellerFromDB:
			 * getTravellerFromDB will return to client the data related with this traveller (identified by ID or memberID)
			 * @param travellerID is the ID that traveller signed in with (in this session)
			 * travellerID might be ID or memberID if he is a member (member,familyMember,groupGuide)
			 * from this method we will navigate to goClient which handles message from server and by the data returned from here 
			 * we will be able to alert the traveller if he exists on DB, if it is, we will pull traveller from DB with all of its data.
			 * if traveller does not exist, we will create a default traveller 
			 * @return String[] - the str
			 */
			public String[] getTravellerFromDB(String travellerID) // returns a String[] with this traveller info
			{
				int temp = 0;
				Statement stm;
				String[] s = new String[12]; // should be as number fields number in traveller class
				// I am working currently on a DB with 5 fields for traveller. It works
				try {
					PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM project.loggedintravellers WHERE id = ?");
					ps1.setString(1, travellerID);
					ResultSet rs1 = ps1.executeQuery();
					if (rs1.next()) {
						s[0] = "UserController";
						s[1] = "AlreadyLoggedIn";
						return s;
					} else {
						PreparedStatement ps2 = conn.prepareStatement("insert into loggedintravellers values (?,1)");
						ps2.setString(1, travellerID);
						ps2.execute();
					}
					PreparedStatement ps = conn.prepareStatement("SELECT * FROM project.person WHERE ID = ? OR memberId = ?");
					stm = conn.createStatement();
					ps.setString(1, travellerID);
					ps.setString(2, travellerID);
					ResultSet rs = ps.executeQuery();
					s[0] = "UserController"; // for use of GoClient.HandleMessageFromServer
					s[1] = "IdentifyTraveller";
					while (rs.next()) {
						s[2] = rs.getString(1);
						s[3] = rs.getString(2); // info[1]
						s[4] = rs.getString(3); // info[2]
						s[5] = rs.getString(4);
						s[6] = rs.getString(5);

						s[8] = rs.getString(7);
						s[9] = rs.getString(8);
						temp = rs.getInt(6);
						s[10] = rs.getString(9);
					}
					s[7] = ("" + temp);
					if (s[2] == null) {
						s[1] = "IdentifyNotExistingTraveller";
						s[2] = "" + travellerID;
					}
					// System.out.print(rs.getString(i).toString());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return s;
			}

			
			/**
			 * Description of canGetTraveller:
			 * canGetTraveller will return true if traveller exists on our DB and false otherwise
			 * @param travellerID will be traveller id which is PK for our travellers DB
			 * @return boolean - true or false
			 */
			
			public boolean canGetTraveller(String travellerID) // Checks if traveller exists in our DB (By ID)
			{
				Statement stm;
				try {
					PreparedStatement ps = conn.prepareStatement("SELECT *  FROM project.person WHERE ID = ? OR memberId = ?");
					// stm = conn.createStatement();
					ps.setString(1, travellerID);
					ps.setString(2, travellerID);
					ps.executeQuery();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}

			/**
			 * Description of deleteFromDbWhenTravellerLogOut:
			 * deleteFromDbWhenTravellerLogOut will delete traveller from DB
			 * we decided to have a relation with all travellers who are logged in to GoNature system.
			 * if an id is in this relation, it means that traveller is logged in. otherwise, it means that traveller can enter with his user
			 * @param travellerID is id of the traveller
			 * @param memberID is member id of the traveller (null or "" if he is not signed as any member)
			 */

			public void deleteFromDbWhenTravellerLogOut(String travellerID, String memberID) {
				try {
					PreparedStatement ps1 = conn.prepareStatement("delete from loggedintravellers where id = ?");
					Statement stm1 = conn.createStatement();
					ps1.setString(1, travellerID);
					ps1.execute();
					PreparedStatement ps2 = conn.prepareStatement("delete from loggedintravellers where id = ?");
					Statement stm2 = conn.createStatement();
					ps2.setString(1, memberID);
					ps2.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		//--------------------------------- End userController ---------------------------------------------------------------------------------------
			
			
			
			
}
