/**
 *  Description of SignUpScreenController 
 *  This is sign up controller
* @author Omri Cohen

*/

package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientUI;
import clientLogic.inits;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ReportEntreisScreenController implements Initializable {
	/**
	 * This is a controller for report of How many entered by type.
	 * 
	 * @implNote implements Initializable - initialize all predefined data
	 */
	@FXML
	private ComboBox<String> monthCB;

	@FXML
	private ComboBox<String> YearCB;

	@FXML
	private Button GetBTN;

	@FXML
	private TableView<ReportData> ReportTable;
	@FXML
	private TableColumn<ReportData, String> IndiLbl;

	@FXML
	private TableColumn<ReportData, String> MemberLbl;

	@FXML
	private TableColumn<ReportData, String> GroupsLbl;

	@FXML
	private TableColumn<ReportData, String> TotalLbl;

	@FXML
	private Button SetReportBTN;

	@FXML
	private Button ClickCancelBTN;

	@FXML
	private Label MonthLBL;

	@FXML
	private Label YearLBL;

	@FXML
	private Label ParkLBL;

	@FXML
	private Label NameLBL;

	@FXML
	private PieChart visitorsPie;

	/**
	 * Description of initialize - When this window is opened, this is the first
	 * function to be activated. initializing all the data needed for this window.
	 * 
	 * @return void - no returns.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		monthCB.setItems(inits.setMonthCB());
		YearCB.setItems(inits.setYearCB());
		IndiLbl.setCellValueFactory(new PropertyValueFactory<>("Individuals"));
		MemberLbl.setCellValueFactory(new PropertyValueFactory<>("Members"));
		GroupsLbl.setCellValueFactory(new PropertyValueFactory<>("Groups"));
		TotalLbl.setCellValueFactory(new PropertyValueFactory<>("Total"));
	}

	/**
	 * Description of WhenClickOnCancel() this function returns the user to welcome
	 * employee screen
	 * 
	 * @return void.
	 */
	@FXML
	void WhenClickOnCancel(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("WelcomeParkManager.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getP();
		Scene scene = new Scene(root);
		stage.setTitle("Welcome " + ClientUI.employeeController.getFirstName());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Description of WhenClickOnGetBtn() this function will show the report results
	 * both in a table and in graph form.
	 * 
	 * @param month - wanted month, chosen by employee.
	 * @param year  - wanted year, chosen by employee.
	 * @param park  - data taken from employees park
	 * 
	 * @return void - lie display of graph and table with requested data..
	 */
	@FXML
	void WhenClickOnGetBtn(ActionEvent event) {
		String month = monthCB.getValue().toString();
		String year = YearCB.getValue().toString();
		String park = ClientUI.employeeController.getParkName();
		MonthLBL.setText(month);
		YearLBL.setText(year);
		ParkLBL.setText(ClientUI.employeeController.getParkName());
		NameLBL.setText(ClientUI.employeeController.getFirstName() + " " + ClientUI.employeeController.getLastName());
		ClientUI.reportsController.getData(month, year, park);
		int sumSolo = ClientUI.reportsController.getSolo();
		int sumMembers = ClientUI.reportsController.getMembers();
		int sumGroups = ClientUI.reportsController.getGroups();
		int sumTotal = sumSolo + sumMembers + sumGroups;
		ObservableList<ReportData> counters = FXCollections.observableArrayList();
		ObservableList<PieChart.Data> pieChart = FXCollections.observableArrayList(
				new PieChart.Data("Travellers", sumSolo), new PieChart.Data("Members", sumMembers),
				new PieChart.Data("Groups", sumGroups));
		ReportData rd = new ReportData(String.valueOf(sumSolo), String.valueOf(sumMembers), String.valueOf(sumGroups),
				String.valueOf(sumTotal));
		counters.add(rd);
		visitorsPie.setData(pieChart);
		ReportTable.setItems(counters);

	}

	/**
	 * Description of WhenClickExitBtn() this function will exit.
	 * 
	 * @param alert - pop up message asking the user if he is sure he wants to exit.
	 * 
	 * @return void - lie display of graph and table with requested data..
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

}