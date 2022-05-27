/**
-----------------------------Class explanation------------------------------------------------------------------------------------------- 
 * This class is a controller
 * this class will not contain any instance of departmentEmployee since GoNature does not consist of it's employees
 * this class will produce more functionality to the client side
 *
 * @author Bar Elhanati 
 * @version January 2021
 *
------------------------------------------------------------------------------------------------------------------------------------
 */
package Controller;

import Client.ClientUI;
import GUI.parkPendingRData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
-----------------------------Class explanation------------------------------------------------------------------------------------------- 
 * This class is a controller
 * this class will not contain any instance of departmentEmployee since GoNature does not consist of it's employees
 * this class will produce more functionality to the client side
 *
 * @author Bar Elhanati 
 * @version January 2021
 *
------------------------------------------------------------------------------------------------------------------------------------
 */

public class EmployeeController {
	// !!!! WHAT NEED TO BE !!!!
	// departmentEmployee emp = ClientUI.userController.departmentEmployee
	String firstName, lastName, type, parkName, userName;
	private boolean isValidUserName;
	private boolean isPasswordsMatch;
	private boolean alreadyLogged;
	public ObservableList<parkPendingRData> parkSettingChangeRequests = FXCollections.observableArrayList();
	// private departmentEmployee employee;

	/**
	-----------------------------Class variables----------------------------------------------------------------------------------------------------------- 
		 * @param firstName will carry employee's first name
		 * @param lastName will carry employee's last name
		 * @param alreadyLogged will demonstrate if this employee is already logged in to GoNature
		 * @param isValidUserName for the use of the returned data from server (true means exists on DB)
		 * @param isPasswordMatch for the use of returned data from server (true means password matches to DB)
		 * @param parkSettingChangeRequests is an observable list which contains all data of park setting changes requests
		 * from park manager to department manager. this list will update with every click on any button(approve \ disapprove)
		 * in PenidngRequest.fxml screen and with every entry to this screen. 
		 * 
	-----------------------------Class methods-----------------------------------------------------------------------------------------*/
		
		/**
		 * Description of logOutEmployee method:
		 * @param employeeUN is the primary key that we would like to send to DB as parameter for logging out 
		 * from this employee user in GoNature system
		 * @param sb is a stringBuffer that contains all data for DB
		 * 
		 */
	
	
	public void logOutEmployee(String employeeUN) {
		StringBuffer sb = new StringBuffer();
		sb.append("logOutEmployee ");
		sb.append(employeeUN);
		String s = sb.toString();
		ClientUI.chat.accept(s);
	}

	public boolean isValidEmployee() {
		return isValidUserName;
	}

	public boolean isMatchingPasswords() {
		return isPasswordsMatch;
	}

	/**
	 * Description of identify method:
 	 * @param s is a string that contains all of our data that the DB needs to identify an employee in it 
 	 * s will contain the userName of employee that desires to log into GoNature system and by sending it to the 
 	 * server, we will be able to collect employee's data
 	 * accept is the method that sends the information to the server
	 */
	
	public void identify(String s) {
		ClientUI.chat.accept(s);
	}

	/**
	 * Description of SendChangesToDepartmentManager method:
 	 * @param s is a string that contains all of our data that the DB needs to set a request for changing 
 	 * any of park (the park that this employee is the manager of) settings  
 	 * s will contain the date and time of request as well as settings that park manager would like to change 
 	 * server will be able to insert the data to the appropriate table,
 	 * and when department manager wants to see park settings requests, DB will be able to provide
 	 * the information from the table that we had insert this information into
 	 * accept is the method that sends the information to the server
	 */
	
	public void sendChangesToDepartmentManager(String s) {
		ClientUI.chat.accept(s);
	}
	
	/**
	 * Description of gotMessage method:
	 * gotMessage is the method that controls the data that returns from server	 
	 * @param action will help us to navigate to the case that we desire (by the information that returned from server)
	 * @param info is an array of strings that will consist all of the information that the server returned us 
	 * @param info[0] - firstName
	 * @param info[1] - lastName
	 * @param info[2] - email
	 * @param info[3] - type
	 * @param info[4] - phoneNumber
	 * @param info[5] - id
	 * @param info[6] - department
	 * @param info[7] - parkName 
	 * @param info[8] - userName (DB PK)
	 * Cases are as the following:
	 * - employee can enter GoNature only if - isValidUsaerName,alreadyLogged and isPasswordsMatch are all true
	 * - employee will not be able to park otherwise and alerts will be thrown as expected with the appropriate 
	 * 	 text for client's convenience and knowledge 
	 * gotMessage will also recognize if department manager desires to pull his requests from park manager
	 * for park settings changes and would fill it with appropriate info from DB (via returned string from server)	
	 */
	

	public void gotMessage(String action, String[] info) {
		switch (action) {
		case "IdentifyEmployee": // Employee exist in our DB
			isValidUserName = true;
			isPasswordsMatch = true;
			alreadyLogged = true;
			firstName = info[0];
			lastName = info[1];
			type = info[3];
			parkName = info[7];
			userName = info[8];
			break;
		case "IdentifyNotExistingEmployee": // UserName does not exist on our DB
			isValidUserName = false;
			break;
		case "IdentifyPasswordDoesNotMatch": // Means that passwords do not match
			isPasswordsMatch = false;
			isValidUserName = true;
			break;
		case "employeeAlreadyLoggedIn":
			isPasswordsMatch = true;
			isValidUserName = true;
			alreadyLogged = false;
			break;
		case "displayParkSettingsRequestsToDepartmentManager":
			fillParkSettingRequests(info);
			break;
		default:
			System.out.print("Don't know what to do");
		}
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParkName() {
		return this.parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	/**
	 * Description of fillParkSettingRequests method:
	 * fillParkSettingRequests will be activated only if department manager pressed button related with 
	 * displaying park settings requests from DB
	 * this method gets an array of strings (long array) that contains all the data returned from our DB
	 * with all the requests from department manager 
	 * this information will fill the table in departmentManager's screen and update with every press of him on any
	 * of the related buttons
	 */
	
	
	private void fillParkSettingRequests(String[] ordersArray) {
		int counter = 0;
		parkSettingChangeRequests.removeAll(parkSettingChangeRequests);
		parkPendingRData pprd;
		if (!(ordersArray[1].equals("Done"))) {
			while (!(ordersArray[counter].equals("Done"))) {
				pprd = new parkPendingRData(ordersArray[counter], ordersArray[counter + 1], ordersArray[counter + 2],
						ordersArray[counter + 3], ordersArray[counter + 4], ordersArray[counter + 5],
						ordersArray[counter + 6]);
				parkSettingChangeRequests.add(pprd);
				counter += 7;
			}
		}

	}
	
	/**
	 * Description of goAndUpdateRequestStatusInDB method:
	 *  As well as other methods that goes from client to server, this method will update	
	 *  DB with new request for department manager from park manager for changing any of park settings
	 *  @param s will contain the data for the request --> date(now),time(now),wanted park,and the changes.
	 */
	

	public void goAndUpdateRequestStatusInDB(String s) {
		ClientUI.chat.accept(s);
	}
	
	/**
	 * Description of goAndChangeParkSettingsInDB method:
	 *  As well as other methods that goes from client to server, this method will update park settings	
	 *  this method will happen only when departmenr manager clicked on approve button for a request
	 *  @param s will contain the data for changing park settings - which park, new settings
	 */
	

	public void goAndChangeParkSettingsInDB(String s) {
		ClientUI.chat.accept(s);
	}

	public boolean isAlreadyLogged() {
		return alreadyLogged;
	}

	public void setAlreadyLogged(boolean alreadyLogged) {
		this.alreadyLogged = alreadyLogged;
	}

	public String getUserName() {
		return this.userName;
	}
}
