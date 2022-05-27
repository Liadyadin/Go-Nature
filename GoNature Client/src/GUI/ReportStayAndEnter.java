/** 
 *Description of SignUpScreenController 
 *Desc of reports
* @author Omri Cohen
* 
* @version final Jan 2, 2021.
*/
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientUI;
import clientLogic.inits;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This is a controller for report of entrance hours and stay duration graphs.
 * 
 * @implNote implements Initializable - initialize all predefined data
 */
public class ReportStayAndEnter implements Initializable {

	@FXML
	/** button to show graph */
	private Button calculateBtn;
	@FXML
	/** Combo box for choosing wanted month */
	private ComboBox<String> MonthCB;

	@FXML
	/** Combo box for choosing wanted year */
	private ComboBox<String> YearCB;

	@FXML
	/** display label "park" */
	private Label parkLbl;

	@FXML
	/** Combo box for choosing wanted park */
	private ComboBox<String> parkCB;

	/** button to get back */
	@FXML
	private Button backBTN;

	/**
	 * Description of initialize() this function initializes the combo boxes for the
	 * window..
	 * 
	 * @param MonthCB combo box for wanted month.
	 * @param parkCB  combo box for wanted park.
	 * @param YearCB  combo box for wanted year.
	 * @return void.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		MonthCB.setItems(inits.setMonthCB());
		parkCB.setItems(inits.setWantedParkCB());
		YearCB.setItems(inits.setYearCB());
	}

	/**
	 * Description of WhenClickBackBtn Upon clicking back the department manager
	 * shall be sent back to department managers main screen.
	 * 
	 * @return void - no returns.
	 * @throws IOException
	 */
	@FXML
	void WhenClickBackBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("WelcomeDepartmentEmployee.fxml").openStream());

		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();

		Scene scene = new Scene(root);
		stage.setTitle("Welcome" + " " + ClientUI.employeeController.getType() + "!");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Description of WhenClickClearBtn Upon clicking the button all data shall be
	 * cleared in all arrays and all graphs.
	 * 
	 * @return void - no returns.
	 */
	@FXML
	void WhenClickClearBtn(ActionEvent event) {
		ClientUI.reportsController.visitors.clear();
		ClientUI.reportsController.members.clear();
		ClientUI.reportsController.groups.clear();
		ClientUI.reportsController.totalArray.clear();
		Alert a = new Alert(AlertType.INFORMATION, "All the previous data deleted");
		a.setTitle("Cleared data!");
		a.setHeaderText("Successfuly cleared all the data");
		a.show();
	}

	/**
	 * Description of WhenClickInformationBtn Upon clicking the button an alert with
	 * explanation for the screen.
	 * 
	 * @return void - no returns.
	 */
	@FXML
	void WhenClickInformationBtn(ActionEvent event) {
		Alert a = new Alert(AlertType.INFORMATION,
				"Please enter the wanted Park Month and Year to make the report.\nYou can't choose month that is not yet been.");
		a.setTitle("Stay and Entrance reports");
		a.setHeaderText("Information about Stay and Entrance report");
		a.show();
	}

	/**
	 * This method responislbe of showing an alert when want to close the
	 * application.
	 * 
	 * @param event
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
	 * Description of whenClickCalculateBtn Upon clicking the button a window will
	 * pop up with wanted graphs.
	 * 
	 * @throws IOException -From inner methods (getDataEntranceTimesAndStay)
	 * 
	 * @return void - no returns.
	 */
	@FXML
	void whenClickCalculateBtn(ActionEvent event) throws IOException {
		String monthOfReport = (String) MonthCB.getValue();
		String yearOfReport = (String) YearCB.getValue();
		String parkOfreport = (String) parkCB.getValue();
		ClientUI.reportsController.getDataEntranceTimesAndStay(monthOfReport, yearOfReport, parkOfreport);
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("MonthlyStayAndEnterReportGraph.fxml"));
		ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getP();
		Scene scene = new Scene(root);
		stage.setTitle("View Graph");
		stage.setScene(scene);
		stage.show();

	}

}
