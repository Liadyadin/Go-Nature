/**
-----------------------------Class explanation------------------------------------------------------------------------------------------- 
 * This class is a screen controller. This class also implements Initializable interface
 * which means that this class must have initialize method.
 * this method implements Initializable because we need to initialize screen with data before 
 * it is displayed to the user
 * this class will implement the behaviour of WelcomeDepartmentManager screen with every data that is in it
 * also, this class will be able to navigate from this screen to every of the department manager's options in our system
 * this class will produce more functionality to the client side
 *
 * @author Bar Elhanati
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



public class welcomeDepartmentManagerController implements Initializable {
	@FXML
	private Button informationBtn;
	@FXML
	private Label DepartmentManagerNameLBL;

	@FXML
	private Label parkNameLBL;

	@FXML
	private Label VisitorsInParkLBL;

	@FXML
	private Button WaitingDiscountsBTN;

	@FXML
	private Button WaitingParkChangesBTN;

	@FXML
	private Button logOutBTN;

	@FXML
	private Button produceCancelledOrdersBtn;

	@FXML
	private Button produceVisitReportBtn;

	/**---------------------------------Class variables----------------------------------------------------------------------------------------------------------- 
	 * @param DepartmentManagerNameLBL will display department manager's name when he logs in to GoNature system == his full name 
	 * @param WaitingDiscountsBTN this button will allow department manager to watch every park manager request for setting a new discount
	 * @param WaitingParkChangesBTN this button will allow department manager to watch every park manager request for setting park changes
	 * @param btnNewOrder by clicking this button, traveller will be able to create a new order
	 * @param logOutBTN by clicking this button, department manger will be out of GoNature system 
	 * @param produceCancelledOrdersBtn will allow department manager to produce cancelled orders report
	 * @param produceVisitReportBtn will allow department manager to produce any park visits reports
	 * 
-----------------------------Class methods----------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Description of WhenPressLogOutBTN method:
	 * WhenPressLogOutBTN method will navigate by pressing logOutBTN to WelcomeAndLoginScreen screen
	 * it will navigate only after updating DB that the employee is no longer connected to GoNature system
	 * and also, will set employee values to be null, so other users will be able to connect (from this session)
	 * @throws IOException - io
	 */
	
	@FXML
	void WhenPressLogOutBTN(ActionEvent event) throws IOException {
		ClientUI.employeeController.logOutEmployee(ClientUI.employeeController.getUserName());
		ClientUI.employeeController.setFirstName(null);
		ClientUI.employeeController.setLastName(null);
		ClientUI.employeeController.setType(null);
		ClientUI.employeeController.setParkName(null);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("WelcomeAndLoginScreen.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getP();
		Scene scene = new Scene(root);
		stage.setTitle("Welcome" + " " + ClientUI.employeeController.getType() + "!");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Description of WhenPressNotYetApprovedDiscountsBTN method:
	 * WhenPressNotYetApprovedDiscountsBTN method will navigate by pressing WaitingDiscountsBTN to discountForDM.fxml screen
	 * it will navigate only after updating the table in discountForDM screen with the park discount requests from park managers
	 * @throws IOException - io
	 */
	
	
	@FXML
	void WhenPressNotYetApprovedDiscountsBTN(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("discountForDM.fxml").openStream()); // screen name here
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Description of WhenClickExitBtn method:
	 * WhenClickExitBtn method will navigate by pressing X icon on the top right side to WelcomeAndLoginScreen screen
	 * it will navigate only after updating DB that the employee is no longer connected to GoNature system
	 * and also, will set employee values to be null, so other users will be able to connect (from this session)
	 * @throws IOException - io
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
	 * Description of WhenPressNotYetApprovedParkChanges method:
	 * WhenPressNotYetApprovedParkChanges method will navigate by pressing WaitingParkChangesBTN to penidngRequest.fxml screen
	 * it will navigate only after updating the table in penidngRequest screen with the park settings request for change by park managers
	 * @throws IOException - io
	 */
	

	@FXML
	void WhenPressNotYetApprovedParkChanges(ActionEvent event) throws IOException {
		// means its Bar's method
		StringBuffer sb = new StringBuffer();
		sb.append("getParkSettingsRequestsFromDB ");
		String s = sb.toString();
		ClientUI.userController.goToDbForDepManagerRequest(s);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("penidngRequest.fxml").openStream()); // screen name here
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Welcome" + " " + ClientUI.employeeController.getType() + "!");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void WhenPressproduceCancelledOrdersBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("cancellationReport.fxml").openStream()); // screen name here
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Description of WhenPressproduceVisitReportBtn method:
	 * WhenPressproduceVisitReportBtn method will navigate by pressing produceVisitReportBtn to ..... 
	 */
	
	
	@FXML
	void WhenPressproduceVisitReportBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("MonthlyStayAndEnterReport.fxml").openStream());

			ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
			stage = ClientUI.LogOutUtility.getStage();
			root = ClientUI.LogOutUtility.getP();
			Scene scene = new Scene(root);
			stage.setTitle("Welcome to GoNature!");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * Description of initialize method:
	 * initialize method will set WelcomeDepartmentManager screen with appropriate information.
	 * info will be provided by ClientUI.employeeController.x where x stands for the info that we need (firstname, lastname, and so and so)
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		String first = ClientUI.employeeController.getFirstName();
		String last = ClientUI.employeeController.getLastName();
		StringBuffer name = new StringBuffer();
		name.append(first);
		name.append(" ");
		name.append(last);
		String tName = name.toString();
		DepartmentManagerNameLBL.setText(tName);

	}

}
