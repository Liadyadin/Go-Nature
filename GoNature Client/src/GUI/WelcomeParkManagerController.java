/**
-----------------------------Class explanation------------------------------------------------------------------------------------------- 
 * This class is a screen controller. This class also implements Initializable interface
 * which means that this class must have initialize method.
 * this method implements Initializable because we need to initialize screen with data before 
 * it is displayed to the user
 * this class will implement the behaviour of WelcomeParkManager screen with every data that is in it
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
import java.time.LocalDate;
import java.time.LocalTime;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WelcomeParkManagerController implements Initializable {
	@FXML
	private Button informationBtn;
	@FXML
	private Label ParkMAnagerName;

	@FXML
	private Label ParkName;

	@FXML
	private TextField MaxVisitorsField;

	@FXML
	private TextField GapField;

	@FXML
	private TextField durationField;

	@FXML
	private Button duraionbtn;

	@FXML
	private Button duraionbtn1;

	@FXML
	private Label numberOFVisitorsLabel;

	@FXML
	private Button SpecialDiscountBTN;

	@FXML
	private Button overallVisitorsReportBTN;

	@FXML
	private Button CreateUsageReportBTN;

	@FXML
	private Button SpecialDiscountBTN111;

	
	/**
	-----------------------------Class variables----------------------------------------------------------------------------------------------------------- 
		 * @param ParkMAnagerName will display park manager's name when he logs in to GoNature system == his full name 
		 * @param ParkName will display park manager's park name
		 * @param MaxVisitorsField is a field that park manager can set and send for approval of d. manager (sent as park settings request)
		 * @param GapField is a field that park manager can set and send for approval of d. manager (sent as park settings request)
		 * @param durationField is a field that park manager can set and send for approval of d. manager (sent as park settings request)
		 * @param numberOFVisitorsLabel is a label that would be able to display park manager how many visitors (orders and unexpected) are in park now
		 * @param SpecialDiscountBTN is a button that will navigate park manager to a screen which there he would be able to request for new discount request for d. manager approval
		 * @param overallVisitorsReportBTN with this button, park manager would be allowed to produce overall visitors report
		 * @param CreateUsageReportBTN with this button, park manager would be able to produce a usage report
		 * @param SpecialDiscountBTN111 will allow park manager to log out of GoNature system 
		 * 
	-----------------------------Class methods----------------------------------------------------------------------------------------------------------------------*/

		/**
		 * Description of initialize method:
		 * initialize method will set WelcomeParkManager screen with appropriate information.
		 * info will be provided by ClientUI.employeeController.x where x stands for the info that we need (firstname, lastname, and so and so)
		 * also, WelcomeParkManager will display amount of visitors in his park now and park current settings (maxVisitors,gap and maxDuration)
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
		ParkMAnagerName.setText(tName);
		String park = ClientUI.employeeController.getParkName();
		ParkName.setText(park);
		int current = 0, currentVisitors = 0, unExpected = 0;
		currentVisitors = ClientUI.parkController.getCurrentVisitors(park);
		unExpected = ClientUI.parkController.getCurrentUnexpectedVisitors(park);
		current = currentVisitors + unExpected;
		numberOFVisitorsLabel.setText("" + current);
		MaxVisitorsField.setText("" + ClientUI.parkController.getMaxVisitors(park));
		GapField.setText("" + (ClientUI.parkController.getMaxVisitors(park)
				- ClientUI.parkController.getMaxAvailableVisitors(park))); // --> need to know what the gap is
		durationField.setText("" + ClientUI.parkController.getMaxDuration(park));

	}

	/**
	 * Description of WhenClickExitBtn method:
	 * WhenClickExitBtn method will navigate by pressing X icon on the top right side to WelcomeAndLoginScreen screen
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
	 * Description of ClickOnSetSpecialDiscount method:
	 * ClickOnSetSpecialDiscount will let park manager to navigate to SetDiscount screen.
	 * there, park manager will be able to ask for d. manager approval for new discount in his park
	 */
	
	
	@FXML
	void ClickOnSetSpecialDiscount(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("SetDiscount.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Set new discount");
		stage.setScene(scene);
		stage.show();
	}

	

	@FXML
	void WhenOverGapInPArkForHelp(ActionEvent event) {

	}
	
	/**
	 * Description of WhenClickCreateMonthlyIncomeReportBtn method:
	 * WhenClickCreateMonthlyIncomeReportBtn will let park manager to navigate to MonthlyReport screen.
	 * there, park manager will be able to produce monthly income report
	 */

	@FXML
	void WhenClickCreateMonthlyIncomeReportBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("incomeReportScreen.fxml").openStream());

		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();

		Scene scene = new Scene(root);
		stage.setTitle("Create monthly report");
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Description of WhenClickCreateUsageReportBtn method:
	 * WhenClickCreateUsageReportBtn will let park manager to navigate to usageReport screen.
	 * there, park manager will be able to produce usage report 
	 */

	@FXML
	void WhenClickCreateUsageReportBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("usageReport.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Create usage report");
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Description of WhenPressLogOutBTN method:
	 * WhenPressLogOutBTN method will navigate by pressing logOutBTN to WelcomeAndLoginScreen screen
	 * it will navigate only after updating DB that the employee is no longer connected to GoNature system
	 * and also, will set employee values to be null, so other users will be able to connect (from this session)
	 * @throws IOException -io
	 */
	

	@FXML
	void WhenClickLogOutBtn(ActionEvent event) throws IOException {
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
	 * Description of WhenClickSendChangesBtn method:
	 * WhenClickSendChangesBtn method will update DB with new park settings change request
	 * request will be sent to DB only if it is appropriate (new maximum visitors can not be higher then the current amount)
	 * gap could not be greater then the max amount of visitors
	 * all fields must not be empty while sending to d. manager approval
	 * a request will have following properties: ID,date(now),time(now),park settings changes,status(department manager touched request or not)
	 * @throws IOException -io
	 */
	

	@FXML
	void WhenClickSendChangesBtn(ActionEvent event) {
		if (MaxVisitorsField.getText().isEmpty() || GapField.getText().isEmpty() || durationField.getText().isEmpty()) {
			Alert a = new Alert(AlertType.NONE, "All fields are required!\nPlease fill every field correctly");
			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		}
		int gap = Integer.parseInt(GapField.getText());
		int maxVisit = Integer.parseInt(MaxVisitorsField.getText());
		float duration = Float.parseFloat(durationField.getText());
		if (gap >= maxVisit) {
			Alert a = new Alert(AlertType.NONE,
					"Gap can not be greater then maximum visitors amount!\nPlease fill fields correctly");
			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		}
		if (duration <= 0 || gap <= 0 || maxVisit <= 0) {
			Alert a = new Alert(AlertType.NONE, "Field can not be negative!");
			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		}
		if (duration >= 8) {
			Alert a = new Alert(AlertType.NONE, "Duration must be less or equal to 8.00(H)");
			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		}
		LocalTime timeNow = LocalTime.now();
		LocalDate dateNow = LocalDate.now();
		StringBuffer s = new StringBuffer();
		s.append("SendParkChangesToDepartmentManager ");
		s.append("x ");
		s.append(dateNow);
		s.append(" ");
		s.append(timeNow);
		s.append(" ");
		s.append(ClientUI.employeeController.getParkName());
		s.append(" ");// untill now --> s = [SendParkChanges..,date,time,parkName]
		s.append(MaxVisitorsField.getText() + " " + GapField.getText() + " " + durationField.getText() + " " + "0");
		ClientUI.employeeController.sendChangesToDepartmentManager(s.toString());
		if (ClientUI.requestsController.isCanSendParkSettingsChangesToDm()) {
			Alert a = new Alert(AlertType.INFORMATION, "Changes sent to D.manager approval!");
			a.setAlertType(AlertType.INFORMATION);
			a.setHeaderText("Sent to D.manager");
			a.setTitle("Sent to D.manager");
			a.show();
			ClientUI.requestsController.setCanSendParkSettingsChangesToDm(false);
			return;
		} else {
			Alert a = new Alert(AlertType.NONE, "An error has occured\nCould not send to D.manager");
			a.setAlertType(AlertType.ERROR);
			a.show();
		}
		return;
	}

	@FXML
	void whenClickInformationBtn(ActionEvent event) {
		Alert a = new Alert(AlertType.INFORMATION,
				"Perform logout only by pressing Logout button\nIf you press the 'X' on the top right side you will remain connected\nIf so, you will need to call to IT for help\nPlease avoid it");
		a.setAlertType(AlertType.INFORMATION);
		a.setHeaderText("Logout instructions");
		a.show();
		return;
	}
	
	/**
	 * Description of ClickCreateOverallVisitorsReport method:
	 * ClickCreateOverallVisitorsReport will let park manager to navigate to MonthlyStayAndEnterReport screen.
	 * there, park manager will be able to produce monthly park being report 
	 */
	

	@FXML
	void ClickCreateOverallVisitorsReport(ActionEvent event) throws IOException {

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("MonthlyReport.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getP();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}


}
