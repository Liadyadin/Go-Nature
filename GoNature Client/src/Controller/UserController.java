/**
-----------------------------Class explanation------------------------------------------------------------------------------------------- 
 * This class is a controller
 * this class will contain an instance of person which can be traveller, pre order traveller or any of GoNature's members
 * this class will produce more functionality to the client side
 *
 * @author Bar Elhanati 
 * @version January 2021
 *
------------------------------------------------------------------------------------------------------------------------------------
 */
package Controller;
import java.io.IOException;

import Client.ClientUI;
import Entities.Person;





public class UserController {
	public Person traveller = null;
	private boolean changeScreen = false;
	private boolean alreadyLoggedIn = false;

	
	/**
	-----------------------------Class variables----------------------------------------------------------------------------------------------------------- 
		 * @param traveller will be an instance of person and will contain data for the person who is now logged
		 * in to GoNature system
		 * @param changeScreen will indicate if we can change screen (to any employee screen or any traveller)
		 * indicate refers to 2 situations:
		 * 1. employee log in == correct userName and correct password
		 * 2. traveller log in == correct ID (9 digits only)
		 * @param alreadyLogged will demonstrate if this traveller is already logged in to GoNature
		 * 
	-----------------------------Class methods-----------------------------------------------------------------------------------------*/
		
		/**
		 * Description of identify method:
	 	 * @param str is a string that contains all of our data that the DB needs to identify a traveller in it 
	 	 * s will contain the id of the traveller that desires to log into GoNature system and by sending it to the 
	 	 * server, we will be able to collect traveller's data (if exists in DB)
	 	 * if it is not, we will creat a defaul person instance (explained in gotMessage method)
	 	 * accept is the method that sends the information to the server
	 	 * @throws IOException - io
		 */
	
	
	public void identify(String str) throws IOException {
		ClientUI.chat.accept(str);
	}
	
	/**
	 * Description of goToDbForDepManagerRequest method:
 	 * @param str is a string that contains all of our data that the DB needs to pull a park settings changes requests 
 	 * str will contain no data, but only method name for echo server navigation 
 	 * we will be able to pull all available requests from DB (only requests that department manager did not refer to yet)
 	 * accept is the method that sends the information to the server
	 */
	
	public void goToDbForDepManagerRequest(String str) {
		ClientUI.chat.accept(str);
	}

	public void setChangeScreen(boolean toChange) {
		this.changeScreen = toChange;
	}

	public boolean getChangeScreen() {
		return this.changeScreen;
	}
	
	/**
	 * Description of gotMessage method:
	 * gotMessage method will let GoNature system to know if traveller can enter to it or not
	 * also, this method will assist us to know if this traveller exists in DB or not
	 * @param action will help us to navigate to the case that we desire (by the information that returned from server)
	 * @param info is an array of strings that will consist all of the information that the server returned us 
	 * @param info[0] - ID (DB PK)
	 * @param info[1] - firstName
	 * @param info[2] - lastName
	 * @param info[3] - phoneNumber
	 * @param info[4] - Email
	 * @param info[5] - AmountOfVisitors
	 * @param info[6] - creditCardNumber
	 * @param info[7] - Type
	 * @param info[8] - memberID 
	 * entry cases will be as the following:
	 * A. if traveller already logged in to GoNature system then alreadyLoggedIn will set as false (won't be able to enter)
	 * B. traveller exists in DB -- will create an instance of person with traveller info from DB
	 * C. traveller does not exists in DB -- will create a default traveller instance with no data (only id)
	 */

	public void gotMessage(String action, String[] info) {
		switch (action) {
		case "IdentifyTraveller": // Traveller exist in our DB
			this.alreadyLoggedIn = false;
			System.out.println(info[7]);
			ClientUI.userController.traveller = new Person(info[1], info[2], info[4], info[7], info[3]);
			ClientUI.userController.traveller.setId(info[0]);
			ClientUI.userController.traveller.setCreditCardNumber(info[6]);
			ClientUI.userController.traveller.setNumberOfVisitors(Integer.parseInt(info[5]));
			ClientUI.userController.traveller.setMemberID(info[8]);
			ClientUI.orderController.setPerson();
			// for (int i=0;i<info.length;i++)
			// System.out.println(info[i]);
			break;
		case "IdentifyNotExistingTraveller": // Traveller does not exist on our DB, making a default one
			this.alreadyLoggedIn = false;
			ClientUI.userController.traveller = new Person("Traveller", "", null, "Traveller", null);
			ClientUI.userController.traveller.setId(info[0]);
			ClientUI.userController.traveller.setNumberOfVisitors(1);
			ClientUI.orderController.setPerson();
			break;
		case "AlreadyLoggedIn":
			this.alreadyLoggedIn = true;
			break;
		case "UpdateFieldofLoggedInTraveller":
			this.alreadyLoggedIn = false;
			break;
		default:
			System.out.print("Don't know what to do");
		}

	}

	public boolean isAlreadyLoggedIn() {
		return alreadyLoggedIn;
	}

	public void setAlreadyLoggedIn(boolean alreadyLoggedIn) {
		this.alreadyLoggedIn = alreadyLoggedIn;
	}

}
