/**
 * Controller for logic of requests in GoNature
 * 
 * @author Liad Yadin
 * @version 1.0 Build December, 2020
 */
package Controller;

import java.time.LocalDate;
import java.time.LocalTime;

import Client.ClientUI;
import GUI.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class RequestsController {
	/** status of request in real time */
	private int statusOfRequest = -1;
	/** observableList for data in table of requests */
	public ObservableList<Data> ob = FXCollections.observableArrayList();
	/** data for save the parameters of the table */
	public Data d;
	private boolean canSendParkSettingsChangesToDm;
	/** the name of park we want to handle the requests */
	private String park;

	/**
	 * this method sends to server parameters for insert to request table in DB
	 * 
	 * @param id            - of the asks
	 * @param date          - current date of request
	 * @param time          - current time of request
	 * @param park          - the name of the park we want to request something
	 * @param numOfVisitors - number of visitors of casual traveler
	 * @param type          - type of request
	 * @param status        - some options: -1 the request waiting for handle, 0 -
	 *                      the request was denied 1-the request was approved
	 */
	public void insertRequestToDB(String id, LocalDate date, LocalTime time, String park, int numOfVisitors,
			String type, int status) {
		StringBuffer sb = new StringBuffer();
		sb.append("insertRequestToDB");
		sb.append(" ");
		sb.append(id);
		sb.append(" ");
		sb.append(date);
		sb.append(" ");
		sb.append(time.withNano(0));
		sb.append(" ");
		sb.append(park);
		sb.append(" ");
		sb.append(numOfVisitors);
		sb.append(" ");
		sb.append(type);
		sb.append(" ");
		sb.append(status);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * this method sends to server id and type of request and check if the request
	 * was approved or not
	 * 
	 * @param id   -of the ask of request
	 * @param type - of request
	 * @return 0 if the request wasn't approved and 1 if the request was approved
	 */
	public int checkIfApproveRequest(String id, String type) {
		StringBuffer sb = new StringBuffer();
		sb.append("checkIfApproveRequest");
		sb.append(" ");
		sb.append(id);
		sb.append(" ");
		sb.append(type);
		String res = sb.toString();
		ClientUI.chat.accept(res);
		return statusOfRequest; // ?
	}

	/**
	 * this method sends to server the name of park and get the parameters of
	 * request table
	 * 
	 * @param wantedpark - for presenting in request table to department employee
	 */
	public void getRequestsTravellerOfEnterPark(String wantedpark) {
		this.park = wantedpark;
		StringBuffer sb = new StringBuffer();
		sb.append("getRequestsTravellerOfEnterPark");
		sb.append(" ");
		sb.append(wantedpark);
		ClientUI.chat.accept(sb.toString());

	}

	/**
	 * this method sends to server parameters for update in "enter park" request of
	 * casual traveller
	 * 
	 * @param status -the right status we want to update (0/1)
	 * @param id     - of asks of request
	 * @param park-  relevant park
	 */
	public void changeStatusForCasualTraveller(int status, String id, String park) {
		StringBuffer sb = new StringBuffer();
		sb.append("changeStatusForCasualTraveller");
		sb.append(" ");
		sb.append(status);
		sb.append(" ");
		sb.append(id);
		sb.append(" ");
		sb.append(park);
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
	public void gotMessage(String[] msg) {
		String cases = msg[0];
		switch (cases) {
		case "checkIfApproveRequest":
			statusOfRequest = Integer.parseInt(msg[1]);
			break;
		case "getRequestsTravellerOfEnterPark":
			fillRequestsTraveller(msg);
			break;
		case "parkSettingsChangesSent":
			if (msg[1].equals("true")) {
				ClientUI.requestsController.setCanSendParkSettingsChangesToDm(true);
			} else if (msg[1].equals("false"))
				ClientUI.requestsController.setCanSendParkSettingsChangesToDm(false);
		default:
			System.out.print("Nothing to do");
		}
	}

	/**
	 * this method fill the table in requests screen of department employee
	 * 
	 * @param msg - data from server (DB) that we want to present in request table
	 *            in the screen of employee
	 */
	private void fillRequestsTraveller(String[] msg) {
		int cnt = 1;
		if (!(msg[1].equals("Done"))) {
			while (!(msg[cnt].equals("Done"))) {
				d = new Data(msg[cnt], msg[cnt + 1], msg[cnt + 2]);
				d.setPark(park);
				ob.add(d);
				cnt += 3;
			}
		}
	}

	public boolean isCanSendParkSettingsChangesToDm() {
		return canSendParkSettingsChangesToDm;
	}

	public void setCanSendParkSettingsChangesToDm(boolean canSendParkSettingsChangesToDm) {
		this.canSendParkSettingsChangesToDm = canSendParkSettingsChangesToDm;
	}
}
