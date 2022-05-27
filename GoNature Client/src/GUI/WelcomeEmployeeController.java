/**
-----------------------------Class explanation------------------------------------------------------------------------------------------- 
 * This class is a screen controller. This method also implements Initializable interface
 * which means that this class must have initialize method.
 * this method implements Initializable because we need to initialize screen with data before 
 * it is displayed to the user
 * this class will implement the behaviour of WelcomeEmployee screen with every data that is in it
 * also, this class will be able to navigate from this screen to every of the department employee's options in our system
 * this class will produce more functionality to the client side
 *
 * @author Bar Elhanati
 * @author Omri Cohen
 * @version January 2021
 *
------------------------------------------------------------------------------------------------------------------------------------
 */

package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;




public class WelcomeEmployeeController implements Initializable {
	@FXML
	private Button informationBtn;
	@FXML
	private Button logOutButton;
	@FXML
	private Label EmployeeNameLbl;

	@FXML
	private Label CurrentPeopleLbl;

	@FXML
	private Label HowManyEnterLbl;

	@FXML
	private Label ParkNameLbl;

	/**
	-----------------------------Class variables----------------------------------------------------------------------------------------------------------- 
		 * @param EmployeeNameLbl will display employee's name when he logs in to GoNature system his full name (if WelcomeAndLoginScreen approved)
		 * if employee does not exist in DB we won't be able to get to this screen
		 * it means that if we got to this screen it means that it is an employee for sure (DE,PM,DM)
		 * @param logOutButton by clicking this button, employee will be out of GoNature system 
		 * @param CurrentPeopleLbl will be for the use of initialize method and will let employee know how many
		 * visitors are in the park that employee works in
		 * @param HowManyEnterLbl will let employee know how many visitors may enter park 
		 * @param ParkNameLbl will display employee the park name (that he works in)
		 * 
	-----------------------------Class methods-----------------------------------------------------------------------------------------*/
		
		/**
		 * Description of initialize method:
		 * initialize method will set WelcomeEmployee screen with appropriate information.
		 * an example for this is if we got to this screen, it means that employee does exist on DB. the screen will welcome him with his first name, last name 
		 * the park that he works at and screen will display how many visitors are in park. also, this screen will display how many more
		 * visitors can enter park
		 * info will be provided by ClientUI.employeeController.x where x stands for the info that we need (firstname, lastname,park and so and so)
		 */
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		String first = ClientUI.employeeController.getFirstName();
		String last = ClientUI.employeeController.getLastName();
		StringBuffer name = new StringBuffer();
		name.append(first);
		name.append(" ");
		name.append(last);
		String tName = name.toString();
		EmployeeNameLbl.setText(tName);
		String park = ClientUI.employeeController.getParkName();
		ParkNameLbl.setText(park);
		int current = 0, currentVisitors = 0, unExpected = 0;
		currentVisitors = ClientUI.parkController.getCurrentVisitors(park);
		unExpected = ClientUI.parkController.getCurrentUnexpectedVisitors(park);
		current = currentVisitors + unExpected;
		CurrentPeopleLbl.setText("" + current);
		int howMany = 0;
		howMany = ClientUI.parkController.getMaxVisitors(park) - current;
		HowManyEnterLbl.setText("" + howMany);
	}

	/**
	 * Description of WhenClickExitBtn method:
	 * WhenClickExitBtn method will navigate by pressing X icon to the top right side to WelcomeAndLoginScreen screen
	 * it will navigate only after updating DB that the employee is no longer connected to GoNature system
	 * and also, will set employee values to be null, so other users will be able to connect (from this session)
	 * @throws IOException -io
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
			ClientUI.LogOutUtility.logOutEmployee();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		} else if (result.get() == ButtonType.CANCEL)
			alert.close();
	}

	/**
	 * Description of WhenClickLogOutBtn method:
	 * WhenClickLogOutBtn method will navigate by pressing logOutButton to WelcomeAndLoginScreen screen
	 * it will navigate only after updating DB that the employee is no longer connected to GoNature system
	 * and also, will set employee values to be null, so other users will be able to connect (from this session)
	 * @throws IOException - io
	 */
	
	
	@FXML
	void WhenClickLogOutBtn(ActionEvent event) throws IOException {
		ClientUI.LogOutUtility.logOutEmployee();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("WelcomeAndLoginScreen.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getP();
		Scene scene = new Scene(root);
		stage.setTitle("Welcome to GoNature!");
		stage.setScene(scene);
		stage.show();

	}


	/**
	 * Description of WhenClickSignUpNewMemberBtn method:
	 * WhenClickSignUpNewMemberBtn method will navigate by pressing signUpMemberButton to SignUpNewMember screen
	 * @throws IOException - io
	 */
	
	
	@FXML
	void WhenClickSignUpNewMemberBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("SignUpNewMember.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Signup new member");
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	void WhenClickSRequestsOfCasualTravellerBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("requestsEnterTraveller.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Requests enter traveller");
		stage.setScene(scene);
		stage.show();
	}

}