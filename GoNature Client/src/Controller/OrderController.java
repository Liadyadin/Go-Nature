/**
 * The order controller Class
 * containtes all of the routes from the client in the section of:
 * 	1. Creating orders (enter to Db, create local tmp order..)
 * 	2. Show existing orders (containing options to cancel/approve)
 * 	3. Enter to a waiting list (partly, secend part in WLcontroller)
 * 	4. Creating a Cancelation report by the department manager.
 * 	5. Show alternative dates if there are no dates for the traveller.
 * 	6. Checking about a day before alerts (for upcoming order)
 * 
 * 
 * 
 *  @author Ilan Alexandrov
 *  @version 7.0 Build December, 2020.
 * 
 */
package Controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import Client.ClientUI;
import Entities.Order;
import Entities.Person;
import GUI.CancelReportData;
import GUI.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OrderController {
	public Person t;
	private int orderNum;
	/** Instance of the current order of the traveller */

	public Order order;
	/**
	 * valid - Parameter that is responsible for telling if can make order or not
	 */
	public boolean valid = false;
	/***/
	ArrayList<String> mess = new ArrayList<String>();
	/** currentEmail - Parameter containing the email of the traveller */
	private String currentEmail;
	/** currentPhone - Parameter containing the phone of the traveller */
	private String currentPhone;
	/**
	 * n_order - flag indicates if the method can Make order need to creat new
	 * instance of Order or not
	 */
	public boolean n_order = false;
	/**
	 * isInDb - flag that responsible to cancel order from existing or from new
	 * order making
	 */
	public boolean isInDb = false;
	/** ReportMonth - Parameter to keep the month of the report needed */
	private String ReportMonth;
	/** ReportYear - Parameter to keep the year of the report needed */
	private String ReportYear;
	/** alternativeDates - ArrayList containing alternative dates for the table */
	private ArrayList<String> alternativeDates = new ArrayList<String>();
	/** ob - List that holds the Existing orders of the traveller */
	public ObservableList<Data> ob = FXCollections.observableArrayList();
	/** aD - List that holds the Alternative dates of the traveller */
	public ObservableList<Data> aD = FXCollections.observableArrayList();
	/** oR - List that holds the data of the canceled report */
	public ArrayList<CancelReportData> oR = new ArrayList<CancelReportData>();
	/***/
	public boolean isConfirm = true;
	/** need_alert - Flag indicates if the traveller got alerted for order or not */
	public boolean need_alert = false;

	/**
	 * Description canMakeOrder(LocalTime time, LocalDate dateOfVisit, String
	 * wantedPark, String type, int numOfVisitors) This method will check with the
	 * db if there is a place in the park for this time and date got if so, will
	 * create new order, and save it later in the DB. will check first if there is
	 * an order for the current time
	 * 
	 * @param time          - The time of the wanted visit
	 * @param dateOfVisit   - The date of the wanted visit
	 * @param wantedPark    - The park of the wanted visit
	 * @param type          - The type of the visitors who make the order
	 * @param numOfVisitors - The number of visitors who want to create an order
	 *         anything.
	 * 
	 */
	public void canMakeOrder(LocalTime time, LocalDate dateOfVisit, String wantedPark, String type, int numOfVisitors) {
		if (n_order) {
			if (type.equals("Family") || type.equals("Group"))
				ClientUI.discountController.getTotalPrice(type, numOfVisitors, "FutreOrder", "True");
			else
				ClientUI.discountController.getTotalPrice(type, numOfVisitors, "FutreOrder", "False");

			float finalPr = ClientUI.discountController.getFinalPriceWithoutDM();
			StringBuffer sb = new StringBuffer();
			sb.append("getOrderNnumber");
			ClientUI.chat.accept(sb.toString());
			order = new Order(orderNum, time, dateOfVisit, wantedPark, numOfVisitors, finalPr);

			System.out.println("The number of the order is : " + orderNum);
		}
		LocalTime openingTime = LocalTime.of(8, 0);
		LocalTime closingTime = LocalTime.of(23, 30);
		LocalTime turn = LocalTime.of(11, 00);
		/*
		 * check what are the boundary's this will numbers will help us to know how many
		 * park in this times.
		 */
		LocalTime from = null;
		LocalTime to = null;
		LocalTime tmp;
		tmp = time.minusMinutes(30);
		if (tmp.isBefore(turn))
			from = openingTime;
		else {
			for (int i = 3; i >= 0; i--) {
				tmp = time.minusHours(i);
				if (!(tmp.isBefore(openingTime))) {
					from = tmp;
					break;
				}
			}
		}
		tmp = time.minusHours(3);
		tmp = tmp.minusMinutes(30);
		if (!(tmp.isBefore(openingTime)))
			from = tmp;

		for (int i = 3; i >= 0; i--) {
			tmp = time.plusHours(i);
			if (tmp.isBefore(closingTime)) {
				to = tmp;
				break;
			}
		}
		tmp = time.plusHours(3);
		tmp = tmp.plusMinutes(30);
		if (tmp.isBefore(closingTime))
			to = tmp;

		System.out.println("The interval for " + time.toString());

		/*
		 * Send the date, time number of visitors and wanted park to the db in the db
		 * will return the amount of people in the park for the boudry time set above in
		 * new method in the OrderController will check if : Add new order for the
		 * current orders be possible if the amount of visitors in total will be greater
		 * then how many visitors can enter will show the unproved order screen
		 */
		String fromB = from.toString();
		String toB = to.toString();
		System.out.println(fromB);
		StringBuffer sb = new StringBuffer();
		sb.append("canMakeOrder");
		sb.append(" ");
		sb.append(from.toString());
		sb.append(":00");
		sb.append(" ");
		sb.append(to.toString());
		sb.append(":00");
		sb.append(" ");
		sb.append(wantedPark);
		sb.append(" ");
		sb.append(dateOfVisit.toString());
		ClientUI.chat.accept(sb.toString());
	}

	public void setPerson() {
		this.t = ClientUI.userController.traveller;
	}

	/**
	 * Description of checkValidValues(String phone, String email)
	 * 
	 * check if the phone and numbers are valid will check if the phone containing
	 * 10 digits. will check if all the string of the phone is not a char but a
	 * number if will find a char there will enter to the cache.
	 * 
	 * @param phone - the phone that gets from the screen
	 * @param email = the email that gets from screen
	 * @return boolean true or false, if all is valid or not
	 * 
	 */
	public boolean checkValidValues(String phone, String email) {
		if (phone.length() == 10) {
			String[] tmp = email.split("@");
			try {
				int areDigits = Integer.parseInt(phone);
			} catch (Exception e) {
				return false;
			}

			if (tmp.length == 2)
				return true;
		}
		return false;
	}

	public boolean getValid() {
		return valid;
	}

	public void setEmailAndPhone(String email, String phone) {
		setEmail(email);
		setPhone(phone);
	}

	/**
	 * Description gotMessage(String[] msg) This method will be the connector from
	 * the data came from the server to this controller
	 * 
	 * @param msg - String array that containing the msg that comes from the server.
	
	 * @throws IOException - io
	 */
	public void gotMessage(String[] msg) throws IOException {

		String cases = msg[0];
		switch (cases) {
		case "canMakeOrder":
			checkIfCanMakeOrder(msg);
			break;
		case "getExsistingOrders":
			fillExsistingOrders(msg);
			break;
		case "getOrderNumber":
			orderNum = Integer.parseInt(msg[1]);

			break;
		case "getDataForReport":
			fillReportTableData(msg);
			break;
		case "havingAlert":
			if (msg[1].equals(""))
				need_alert = false;
			else
				need_alert = true;
			break;
		}
	}

	/**
	 * Description fillReportTableData(String[] msg) This method will create a table
	 * row for the report.
	 * 
	 * @param msg[1] - Number of canceled orders in the current month and year
	 * @param msg[2] - Number of confirmed but not get to the park orders.
	 * @return void
	 */
	private void fillReportTableData(String[] msg) {
		System.out.println(msg[1] + "This is the cancel Amount");

		System.out.println(msg[2] + "this is confirmed");
		Number numOfCancel;
		Number numOfConfirm;
		CancelReportData crd = null;
		try {
			numOfCancel = NumberFormat.getInstance().parse(msg[1]);
			numOfConfirm = NumberFormat.getInstance().parse(msg[2]);
			crd = new CancelReportData(ReportYear, ReportMonth, numOfCancel, numOfConfirm);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		oR.add(crd);

	}

	/**
	 * Description fillExsistingOrders(String[] ordersArray) This method will get
	 * all the orderes from the DB and every order will be inserted into a Data
	 * class, later, all of this datas instances will be entered into a observable
	 * list array[0]=methodName, array[1]=data
	 * 
	 * @param ordersArray
	 */
	private void fillExsistingOrders(String[] ordersArray) {
		int counter = 1;
		int check = 1;
		Data d;
		if (!(ordersArray[1].equals("Done"))) {
			while (!(ordersArray[counter].equals("Done"))) {
				d = new Data(ordersArray[counter], ordersArray[counter + 1], ordersArray[counter + 2],
						ordersArray[counter + 3], ordersArray[counter + 4], ordersArray[counter + 5],
						ordersArray[counter + 6], ordersArray[counter + 7]);

				ob.add(d);
				counter += 8;
				check++;

			}
			System.out.println(check);

		}

	}

	/**
	 * Description checkIfCanMakeOrder(String[] msg)
	 * 
	 * This method get from the server all the numbers how many can enter how many
	 * visitors overall in the gap if adding will make the maxVisit bigger, move to
	 * unapproved order, if not, will put into the variable true, and make the order
	 * confirmed
	 * 
	 * @param msg[1]                      - currentVisitors that allows
	 * @param msg[2]                      - availableVisitors in the park
	 * @throws IOException - exe
	 */

	public void checkIfCanMakeOrder(String[] msg) throws IOException {

		int currentVisitorsAtBoundry = Integer.parseInt(msg[1]);

		int availableVisitors = Integer.parseInt(msg[2]);
		if (currentVisitorsAtBoundry + order.getNumberOfVisitors() > availableVisitors)
			valid = false;
		else
			valid = true;
	}

	public void setPhone(String phone) {
		this.currentPhone = phone;
	}

	public void setEmail(String email) {
		this.currentEmail = email;
	}

	public String getPhone() {
		return currentPhone;
	}

	public String getEmail() {
		return currentEmail;
	}

	/**
	 * Description wantToCancel(Stage stage) This method will transfer the traveller
	 * to a new screen that will ask the user if he want to cancel his order
	 * 
	 * @param stage - current stage that is in use
	
	 * @throws IOException - io
	 */
	public void wantToCancel(Stage stage) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/GUI/SureIfCancel.fxml").openStream());
		Scene scene = new Scene(root);
		stage.setTitle("Cancel order");
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Description getAlternativeDates(LocalTime timeForVisit)
	 * 
	 * This method will be responislbe for adding the lternative dates for the
	 * current order into the observable array list another method will take the
	 * data from the observable and show it into the table
	 * 
	 * @param timeForVisit - the hour of the wanted alternative dates
	
	 * @throws IOException - io
	 */
	public void getAlternativeDates(LocalTime timeForVisit) throws IOException {
		alternativeDates.clear();
		Data d;
		aD.removeAll(aD);
		n_order = false;
		LocalDate date = order.getDateOfVisit();
		LocalTime time = timeForVisit;
		String park = order.getWantedPark();
		int numOfVisit = order.getNumberOfVisitors();
		for (int i = 1; i <= 7; i++) {
			LocalDate toDate = date.plusDays(i);
			this.canMakeOrder(time, toDate, park, "f", numOfVisit);
			if (valid)
				alternativeDates.add(toDate.toString());

			for (String var : alternativeDates) {
				d = new Data("4", var, order.getWantedPark(), time.toString(),
						Integer.toString(order.getNumberOfVisitors()), Float.toString(order.getTotalPrice()));

				aD.add(d);

			}

		}

	}

	/**
	 * Description of confirmOrder()
	 * 
	 * This method will be responsible for saving the order into the db This method
	 * will not return anything worthy to the client
	 * 
	 
	 */
	public void confirmOrder() {
		t = ClientUI.userController.traveller;
		StringBuffer sb = new StringBuffer();
		sb.append("confirmOrder");
		sb.append(" ");
		sb.append(order.getTimeInPark().toString());
		sb.append(":00");
		sb.append(" ");
		sb.append(order.getDateOfVisit().toString());
		sb.append(" ");
		sb.append(order.getWantedPark());
		sb.append(" ");
		sb.append(Float.toString(order.getTotalPrice()));
		sb.append(" ");
		// !!!!!!! NEED TO BE !!!!!!
		sb.append(t.getId());
		// sb.append("4");
		sb.append(" ");
		// !!!!!!! NEED TO BE !!!!!!
		sb.append(t.getType());
		// sb.append("F");
		sb.append(" ");
		sb.append(Integer.toString(order.getNumberOfVisitors()));
		sb.append(" ");
		sb.append("Confirmed");
		order = null;
		isConfirm = true;
		ClientUI.chat.accept(sb.toString());

	}

	/**
	 * Description of cancelOrder(String dateOfVisit, String wantedPark, String
	 * timeOfVisit) This method will be responisble for canceling the order -
	 * chaning the order status to canceled in the DB this method will that the type
	 * of the person from the traveller instance his type and ID.
	 * 
	 * @param dateOfVisit - the date of the visit that need to be canceled
	 * @param wantedPark  - the wanted park of the visit that need to be canceled
	 * @param timeOfVisit - the time of the visit that need to be canceled.
	
	 */
	public void cancelOrder(String dateOfVisit, String wantedPark, String timeOfVisit) {

		if (isInDb) {
			String id = t.getId();
			StringBuffer sb = new StringBuffer();
			sb.append("cancelOrder");
			sb.append(" ");
			sb.append(timeOfVisit);
			sb.append(":00");
			sb.append(" ");
			sb.append(dateOfVisit);
			sb.append(" ");
			sb.append(wantedPark);
			sb.append(" ");
			sb.append(id);
			ClientUI.chat.accept(sb.toString());
		} else
			order = null;
		isInDb = false;

	}

	/**
	 * Description of etExsistingOrders()
	 * 
	 * This method will fill the observable value with all the exsisting orders of
	 * the current person
	 * 
	
	 */
	public void getExsistingOrders() {
		ob.clear();
		StringBuffer sb = new StringBuffer();
		sb.append("getExsistingOrders");
		sb.append(" ");
		System.out.println(ClientUI.userController.traveller.getId());
		sb.append(ClientUI.userController.traveller.getId());
		ClientUI.chat.accept(sb.toString());

	}

	/**
	 * Descritpion of setEnterOrder(String Id, String wantedPark, String
	 * dateOfVisit, String TimeInPark) Method that will change the status of the
	 * order of the traveller from confirmed to entered meaning the traveller is now
	 * in the park
	 * 
	 * @param Id          - the id of the Traveller
	 * @param wantedPark  - the Wanted park name
	 * @param dateOfVisit - the date of the visit of the order
	 * @param TimeInPark  - the Time of the visit of the order
	 * 
	
	 */
	public void setEnterOrder(String Id, String wantedPark, String dateOfVisit, String TimeInPark) {

		StringBuffer sb = new StringBuffer();
		sb.append("setEnterOrder");
		sb.append(" ");
		sb.append(TimeInPark);
		sb.append(" ");
		sb.append(dateOfVisit);
		sb.append(" ");
		sb.append(wantedPark);
		sb.append(" ");
		sb.append(Id);
		sb.append(" ");

		ClientUI.chat.accept(sb.toString());

	}

	/**
	 * Description ChangeToWaitOrder(Order getOr) This method will change the order
	 * that the user just made to wait, because he probably can't enter the park
	 * right now.
	 * 
	 * @param getOr - get
	
	 */
	public void ChangeToWaitOrder(Order getOr) {
		StringBuffer sb = new StringBuffer();
		sb.append("ChangeToWaitOrder");
		sb.append(" ");
		sb.append(getOr.getTimeInPark().toString());
		sb.append(" ");
		sb.append(getOr.getDateOfVisit().toString());
		sb.append(" ");
		sb.append(getOr.getWantedPark());
		sb.append(" ");
		sb.append(t.getId());
		// sb.append("4");
		sb.append(" ");
		ClientUI.chat.accept(sb.toString());

	}

	/**
	 * Description of getDataForReport(LocalDate fromDate, LocalDate toDate) This
	 * method will be responsible to get the data to the report based on the 2
	 * parameters that he get, that are the limits of the reports
	 * 
	 * @param fromDate - date that the report will start from
	 * @param toDate   - date that the report will end
	
	 */
	public void getDataForReport(LocalDate fromDate, LocalDate toDate) {
		ReportMonth = Integer.toString(fromDate.getMonthValue());
		ReportYear = Integer.toString(fromDate.getYear());
		StringBuffer sb = new StringBuffer();
		sb.append("getDataForReport");
		sb.append(" ");
		sb.append(fromDate.toString());
		sb.append(" ");
		sb.append(toDate.toString());
		ClientUI.chat.accept(sb.toString());
	}

	/**
	 * Description of havingAlert(LocalDate tomorrow, String ID) method that will
	 * check if tomorrow there will be any order this is not counting how many
	 * orders, its just check if there are some this method will be used in the
	 * welcome traveller initialize, and if it will be true, it will pop up a
	 * message saying he need to confirm his orders that are going to be tomorrow
	 * 
	 * @param tomorrow - the date of tomorrow
	 * @param ID       - the Id of the user
	 * @return boolean - true or false if the user already got the message of not
	 */

	public boolean havingAlert(LocalDate tomorrow, String ID) {
		StringBuffer sb = new StringBuffer();
		sb.append("havingAlert");
		sb.append(" ");
		sb.append(tomorrow.toString());
		sb.append(" ");
		sb.append(ID);
		ClientUI.chat.accept(sb.toString());
		if (need_alert)
			return true;
		return false;

	}

	/**
	 * confirmAlert(String id) This method will just send to the server that the
	 * alert message already appeard for him
	 * 
	 * @param id - the ID of the user that get into the system
	
	 */
	public void confirmAlert(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("confirmAlert");
		sb.append(" ");
		sb.append(id);
		ClientUI.chat.accept(sb.toString());

	}

}
