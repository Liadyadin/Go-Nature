/**
 * Controller for logic of enter - exit park. include creating a casual traveler.
 * updates by enter time and exit time of traveler(including with order 
 * without order). updates the numbers of travelers in the park.
 * 
 * @author Liad Yadin
 * @version 2.0 Build December, 2020
 */
package Controller;

import java.time.LocalDate;
import java.time.LocalTime;

import Client.ClientUI;
import Entities.Order;
import Entities.TravellerInPark;
import GUI.EnterParkNowController;

/**
 * Controller for logic of enter - exit park. include creating a casual traveler.
 * updates by enter time and exit time of traveler(including with order 
 * without order). updates the numbers of travelers in the park.
 * 
 * @author Liad Yadin
 * @version 2.0 Build December, 2020
 */
public class EntranceParkController {
	/** casual traveler object for save the data */
	public TravellerInPark travellerinpark;
	/** boolean variable if this traveler exist in travellerInPark table on DB */
	public boolean travellerExistsInDB = false;
	/** boolean variable if this traveler exist in order table on DB */
	public boolean OrderExistsInDB = false;
	/** for save data between EnterParkNow screen and Implemention */
	public GUI.EnterParkNowController enterpark = new EnterParkNowController();

	/**
	 * this method sends to server details of a casual traveler for insert this
	 * traveler to travellerInPark table in DB using by TravellerInPark entity in
	 * Logic source folder for helping
	 * 
	 * @param timeOfVisit   - current time - a casual traveler come at this moment
	 *                      to the park
	 * @param dateOfVisit   - current date
	 * @param wantedPark    - the park that this traveler chose to enter
	 * @param numOfVisitors - the number of visitors that came with this traveler to
	 *                      the park
	 * @param price         - total price
	 */
	public void enterWithoutOrder(LocalTime timeOfVisit, LocalDate dateOfVisit, String wantedPark, int numOfVisitors,
			float price) {
		travellerinpark = new TravellerInPark(timeOfVisit, dateOfVisit, wantedPark, numOfVisitors, price);

		StringBuffer sb = new StringBuffer();
		sb.append("enterWithoutOrder");
		sb.append(" ");
		sb.append(ClientUI.userController.traveller.getId());
		sb.append(" ");
		sb.append(Integer.toString(travellerinpark.getnumOfVisitors()));
		sb.append(" ");
		sb.append(travellerinpark.getdateOfVisit().toString());
		sb.append(" ");
		sb.append(travellerinpark.gettimeInPark().withNano(0).toString());
		// sb.append(":00");
		sb.append(" ");
		sb.append(Float.toString(travellerinpark.gettotalPrice()));
		sb.append(" ");
		sb.append(travellerinpark.getwantedPark());
		sb.append(" ");
		ClientUI.chat.accept(sb.toString());

	}

	/**
	 * this method sends to server the right number of visitors that enter/exit from
	 * the park for updates the value of the current unexpected visitors in park
	 * 
	 * @param wantedpark    - the park that we want to change the amount of visitors
	 * @param numOfVisitors - amount we want to add or sub
	 */
	public void setNumOfVisitorEntringPark(String wantedpark, int numOfVisitors) {
		int unexpectedVisitorsToUpdate = ClientUI.parkController.getCurrentUnexpectedVisitors(wantedpark)
				+ numOfVisitors;
		StringBuffer sb = new StringBuffer();
		sb.append("setNumOfVisitorEntringPark");
		sb.append(" ");
		sb.append(wantedpark);
		sb.append(" ");
		sb.append(unexpectedVisitorsToUpdate);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * this method sends to server id and check if this traveler exists in DB
	 * 
	 * @param id of this traveler
	 */
	public void checkIfTravellerExistsInPark(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("checkIfTravellerExistsInPark");
		sb.append(" ");
		sb.append(id);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * 
	 * this method sends to server the right number of visitors that enter/exit from
	 * the park for updates the value of the current visitors with order in park
	 * 
	 * @param wantedpark    - the park that we want to change the amount of visitors
	 * @param numOfVisitors - amount we want to add or sub
	 */

	public void setCurrentVisitros(String wantedpark, int numOfVisitors) {
		int currentVisitorsToUpdate = ClientUI.parkController.getCurrentVisitors(wantedpark) + numOfVisitors;
		StringBuffer sb = new StringBuffer();
		sb.append("setCurrentVisitros");
		sb.append(" ");
		sb.append(wantedpark);
		sb.append(" ");
		sb.append(currentVisitorsToUpdate);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * this method sends to server id and park and its update the enter time for
	 * traveler with order in DB
	 * 
	 * @param wantedpark -the park that the traveler entered into it
	 * @param id         - ID of traveler who wants to enter the park
	 */
	public void updateEnterTimeForTravellerWithOrder(String wantedpark, String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("updateEnterTimeForTravellerWithOrder");
		sb.append(" ");
		sb.append(wantedpark);
		sb.append(" ");
		sb.append(id);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * this method sends to server id and park and its update the exit time for
	 * traveler with order in DB
	 * 
	 * @param wantedpark -the park that the traveler went out into it
	 * @param id         - ID of traveler who wants to go out the park
	 */

	public void updateExitTimeForTravellerWithOrder(String wantedpark, String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("updateExitTimeForTravellerWithOrder");
		sb.append(" ");
		sb.append(wantedpark);
		sb.append(" ");
		sb.append(id);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * this method sends to server id and park and its update the enter time for
	 * casual traveler in DB
	 * 
	 * @param wantedpark -the park that the traveler entered into it
	 * @param id         - ID of traveler who wants to enter the park
	 */
	public void updateExitTimeForcasualTraveller(String wantedpark, String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("updateExitTimeForcasualTraveller");
		sb.append(" ");
		sb.append(wantedpark);
		sb.append(" ");
		sb.append(id);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * this method sends to server id and check if this traveler exists in DB
	 * 
	 * @param id of this traveler
	 * @return if this traveler exists in DB -return true, else - return false
	 */
	public boolean IfgetTravellerInParkExistInDB(String id) {
		checkIfTravellerExistsInPark(id);
		return travellerExistsInDB;
	}

	/**
	 * this method sends to server id for get the data of this traveler from DB
	 * 
	 * @param id that we want his details
	 */
	public void getTravellerInParkDetails(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("getTravellerInParkDetails");
		sb.append(" ");
		sb.append(id);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * this method sends to server id and check if order exists in DB and confirmed
	 * by the employees
	 * 
	 * @param id of the owner of the order
	 */
	public void checkIfOrderExistsInParkAndConfirmed(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("checkIfOrderExistsInParkAndConfirmed");
		sb.append(" ");
		sb.append(id);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * this method sends to server id and check if this order exists in DB
	 * 
	 * @param id of this traveler that the order belong him
	 * @return if this order exists in DB -return true, else - return false
	 */
	public boolean IfgetOrderInParkExistInDB(String id) {
		checkIfOrderExistsInParkAndConfirmed(id);
		return OrderExistsInDB;
	}

	/**
	 * this method sends to server id for get the data of this order from DB
	 * 
	 * @param id that we want his order details today
	 */
	public void getOrderDetailsForExitPark(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("getOrderDetailsForExitPark");
		sb.append(" ");
		sb.append(id);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * gotMesage from server - for handle all messages that sent there is cases of
	 * got messages - and handle by switch case and updates variables there is case
	 * of "done" that we don't need to do something - the action accrued in DB
	 * 
	 * @param msg - the massage that we got from server
	 */
	public void gotMesage(String[] msg) {
		String cases = msg[0];
		switch (cases) {
		case "checkIfTravellerExistsInPark":
			if ((msg[1].toString()).equals("true"))
				travellerExistsInDB = true;
			else
				travellerExistsInDB = false;
			break;

		case "getTravellerInParkDetails":
			travellerinpark = null;
			travellerinpark = new TravellerInPark(Integer.parseInt(msg[1]), msg[2]);

		case "checkIfOrderExistsInParkAndConfirmed":
			if ((msg[1].toString()).equals("true"))
				OrderExistsInDB = true;
			else
				OrderExistsInDB = false;
			break;
		case "getOrderDetailsForExitPark":
			ClientUI.orderController.order = null;
			ClientUI.orderController.order = new Order(Integer.parseInt(msg[1]), msg[2]);

		}
	}

}