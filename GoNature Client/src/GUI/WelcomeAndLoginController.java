package GUI;

import java.io.IOException;

import java.util.Optional;

import Client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
-----------------------------Class explanation------------------------------------------------------------------------------------------- 
 * This class is a screen controller. 
 * this class will implement the behaviour of WelcomeAndLoginScreen screen with every data that is in it
 * also, this class will be able to navigate from this screen to every of the employees or traveller options in our system
 * this class will produce more functionality to the client side
 * this is the very first interaction of the client with our system
 *
 * @author Bar Elhanati
 * @version January 2021
 *
------------------------------------------------------------------------------------------------------------------------------------
 */

public class WelcomeAndLoginController {

	@FXML
	private TextField UserNameLBL;

	@FXML
	private TextField PasswordLBL;

	@FXML
	private Button EmployeeloginBTN;

	@FXML
	private ImageView imageOfLogin;

	@FXML
	private TextField IdLBL;

	@FXML
	private Button TravellerLoginBtn;

	public static String id;

	/**
	-----------------------------Class variables----------------------------------------------------------------------------------------------------------- 
		 * @param UserNameLBL will tell us which user is trying to log into GoNature system (employee login)
		 * employee user name is the PK to employees DB
		 * @param PasswordLBL is the password that employee entered (will be checked against the DB for his login)
		 * @param EmployeeloginBTN is the button that employee press on when he desires to log into GoNature
		 * only after appropriate data is inserted to appropriate fields (userName and password)
		 * imageOfLogin is the image of GoNature that you see on screen's center
		 * @param IdLBL will assist us to have traveller's ID for identifying from DB
		 * @param TravellerLoginBtn is the button that employee press on when he desires to log into GoNature
		 * only after appropriate data (9 digits ID or memberID)
		 * this info will be checked against DB so we would be informed if traveller exists on DB or not
		 * @param id is a String that would assist us to know id of traveller that wants to log into GoNature system
	-----------------------------Class methods----------------------------------------------------------------------------------------------------------------------*/

		/**
		 * Description of WhenClickExitBtn method:
		 * WhenClickExitBtn method will navigate by pressing X icon on the top right side to disconnect from GoNature system
		 */
	
	
	@FXML
	void WhenClickExitBtn(MouseEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Are you sure you want to exit the application?");
		alert.setResizable(false);
		alert.setContentText("Select Yes if you want to exit Or No if you want to stay.");
		((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
		((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent())
			alert.close();
		else if (result.get() == ButtonType.OK) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		} else if (result.get() == ButtonType.CANCEL)
			alert.close();
	}

	/**
	 * Description of WhenPressTravellerLoginBtn method:
	 * WhenPressTravellerLoginBtn method will navigate by pressing TravellerLoginBtn to WelcomeTraveller screen only if:
	 * ID that traveller entered is 9 (precisely) digits and digits only
	 * traveller does not already connected to GoNature system
	 * This method, after checking the input propriety we will go out to DB and check if traveller exists on DB.
	 * if it is, UserController will have an instance of person will all data that came from DB (info for traveller that we need)
	 * and WelcomeTraveller screen will be displayed to the user with the appropriate data
	 * if traveller does not exist in DB, we will create a default traveller and display only "Welcome Traveller" and appropriate buttons
	 * on WelcomeTraveller screen to the user
	 */
	
	@FXML
	void WhenPressTravellerLoginBtn(ActionEvent event) throws Exception {
		id = IdLBL.getText();
		if (!(this.checkID(id))) {
			Alert a = new Alert(AlertType.NONE,
					"You must enter a valid id!\nId must consist the following:\nA. 9 digits\nB. numbers only");
			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		}
		// need to check if id consists chars or its length is less or greater than 9
		// if the answer to one of the questions above is true it means id is not
		// validated
		StringBuffer str = new StringBuffer();
		str.append("getTravellerDetails ");
		str.append(id);
		String s = str.toString(); // Got the necessary info for traveller
		ClientUI.userController.identify(s);
		if (ClientUI.userController.isAlreadyLoggedIn()) {
			Alert a = new Alert(AlertType.NONE,
					"User already logged in\nPlease make sure that you are connected to GoNature with 1 decive only");
			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		}
		// while (!(ClientUI.userController.getChangeScreen())) {};
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/GUI/WelcomeTraveller.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Welcome Traveller!");
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Description of WhenPressEmployeeLogInBtn method:
	 * WhenPressEmployeeLogInBtn method will navigate by pressing EmployeeloginBTN to any employee screen (DE,PM,DM):
	 * userName that employee entered must appear on DB, and if it isn't, user will get an alert that userName isn't valid
	 * password must be matching to the one that is on our DB and if it is not, user will get an alert that password does not match
	 * employee must not be connected to GoNature system. if he already is, an alert will be displayed and entrance will not be approved
	 * if every of the following does appear correctly as DB, employee would be able to move to the appropriate employee screen
	 * we will navigate to the appropriate screen via employee's type (DE,PM,DM)
	 * also, this method creates a park instance where employee works in. this action would assist us to display the employee 
	 * park (where employee belongs to) details  
	 */
	

	@FXML
	void WhenPressEmployeeLogInBtn(ActionEvent event) throws IOException {
		String userName = UserNameLBL.getText();
		String password = PasswordLBL.getText();
		if (password.isEmpty()) {
			Alert a = new Alert(AlertType.NONE, "Password field must not be empty!!");
			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		}
		StringBuffer str = new StringBuffer();
		str.append("getEmployeeDetails");
		str.append(" ");
		str.append(userName);
		str.append(" ");
		str.append(password);
		String s = str.toString();
		ClientUI.employeeController.identify(s);
		// Here I need to check if userName exists on DB
		// If it is --> check password entered equals to password in DB
		// If match --> can enter. If one of them fails reload scene and throw an alert
		if (!(ClientUI.employeeController.isValidEmployee())) {
			Alert a = new Alert(AlertType.NONE, "User name is not valid!\nYou must enter a valid user name");
			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		} else if (!(ClientUI.employeeController.isMatchingPasswords())) {
			Alert a = new Alert(AlertType.NONE,
					"Incorrect password!\nYour user name exists, password does not\nPlease try again");
			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		} else if (!(ClientUI.employeeController.isAlreadyLogged())) {
			Alert a = new Alert(AlertType.NONE,
					"User is already connected to GoNature system\nPlease make sure that you are logged only from 1 device");

			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		}
		// departmentEmployee t_emp =
		// (departmentEmployee)ClientUI.employeeController.getEmployee();
		ClientUI.parkController.DetailsPark(ClientUI.employeeController.getParkName());
		String whichScreen = ClientUI.employeeController.getType();
		switch (whichScreen) {
		case "departmentEmployee":
			changeScreen(event, "welcomeEmployee.fxml");
			break;
		case "parkManager":
			changeScreen(event, "WelcomeParkManager.fxml");
			break;
		case "departmentManager":
			changeScreen(event, "WelcomeDepartmentEmployee.fxml");
			break;
		default:
			System.out.print("Don't know what to do (2)");
		}
	}

	/**
	 * Description of CheckID method:
	 * CheckID method will assist us to check the propriety of ID that entered by traveller
	 * @param id is the ID that traveller entered when he asks to log into GoNature system
	 * @return boolean - true false
	 */
	
	
	public boolean checkID(String id) {
		if (id.length() != 9)
			return false;
		char ch;
		for (int i = 0; i < 9; i++) {
			ch = id.charAt(i);
			if (!(Character.isDigit(ch)))
				return false;
		}
		return true;
	}
	
	/**
	 * Description of changeScreen method:
	 * changeScreen method will navigate by pressing EmployeeloginBTN and after all the input propriety checks 
	 * will display employee the correct screen regarding to his type in DB (we can know type by ClientUI.employeeController.getType())
	 * @param screen is the name of the string that we need to display to the employee according to his type (DE,PM,DM)
	 * @param event - enent
	 * @throws IOException -io
	 */
	
	public void changeScreen(ActionEvent event, String screen) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(screen).openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Welcome" + " " + ClientUI.employeeController.getType() + "!");
		stage.setScene(scene);
		stage.show();
	}

}
