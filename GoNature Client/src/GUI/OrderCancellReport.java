/** 
 * Description of OrderCancellReport
 * OrderCancellReport class responsible of the
 * connection to the client, ask him to fill 
 * the year and month, to get the report to.
 * 
 * @author Ilan Alexandrov
 */

package GUI;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OrderCancellReport implements Initializable {
	@FXML
	private ComboBox MonthCb;

	@FXML
	private ComboBox YearCb;

	@FXML
	private BarChart<String, Number> chartBar;

	@FXML
	private CategoryAxis DateX;

	@FXML
	private NumberAxis VisitY;
	int flag = 0;
	XYChart.Series<String, Number> series1;
	XYChart.Series<String, Number> series2;
	ObservableList<String> listForYears;
	ObservableList<String> listForMonth;

	/**
	 * Description of initialize(URL arg0, ResourceBundle arg1) in
	 * OrderCancellReport This method responsible to initialize the years and the
	 * month to give the user the option to choose from
	 * 
	 * @return void
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setYearCb();
		setMonthCb();

	}

	/**
	 * Description of setYearCb() in OrderCancellReport This method responsible to
	 * initialize the years to give the user the option to choose from
	 * 
	 * @return void
	 */
	private void setYearCb() {
		ArrayList<String> years = new ArrayList<String>();
		LocalDate lY = LocalDate.now();
		int base = 2018;
		for (int i = 2017; i < lY.getYear(); i++) {
			base++;
			years.add(Integer.toString(base));
		}
		listForYears = FXCollections.observableArrayList(years);
		YearCb.setItems(listForYears);
	}

	/**
	 * Description of setMonthCb() in OrderCancellReport This method responsible to
	 * initialize the month to give the user the option to choose from
	 * 
	 * @return void
	 */

	private void setMonthCb() {
		ArrayList<String> month = new ArrayList<String>();

		for (int i = 1; i <= 12; i++) {
			month.add(Integer.toString(i));

		}
		listForMonth = FXCollections.observableArrayList(month);
		MonthCb.setItems(listForMonth);
	}

	@FXML
	void WhenClickBackBtn(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("WelcomeDepartmentEmployee.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getP();
		Scene scene = new Scene(root);
		stage.setTitle("Success Sign Up");
		stage.setScene(scene);

		stage.show();
	}

	/**
	 * Description of WhenClickClearBtn(ActionEvent event) in OrderCancellReport
	 * This method is responsible to clear all the data that is in the graphs so
	 * far.
	 * 
	 * @return void
	 */
	@FXML
	void WhenClickClearBtn(ActionEvent event) {
		ClientUI.orderController.oR.clear();
		Alert a = new Alert(AlertType.INFORMATION, "All the previous data deleted");
		a.setTitle("Cleared data!");
		a.setHeaderText("Successfuly cleared all the data");
		a.show();
	}

	/**
	 * Description of WhenClickInformationBtn(ActionEvent event) in
	 * OrderCancellReport This method responsible of showing some rough data to the
	 * user
	 * 
	 * @return void
	 */
	@FXML
	void WhenClickInformationBtn(MouseEvent event) {
		Alert a = new Alert(AlertType.INFORMATION,
				"Please enter the wanted month, and wanted date to make the report.\nYou can't choose month that is not yet been.\nThe report will show the amount of orderes that has canceled, and will show the amount of reports that has been made but the visitors didn't came");
		a.setTitle("Order reports");
		a.setHeaderText("Information about Order report");

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
	 * Description of whenClickCalculateBtn(ActionEvent event) in OrderCancellReport
	 * This method will reach to the db, and get the data about the all amont of
	 * people that: 1.canceled there order 2. confirmed but didn't enter the park.
	 * 
	 * @return void
	 * @throws IOException
	 */
	@FXML
	void whenClickCalculateBtn(ActionEvent event) throws IOException {
		StringBuffer sb = new StringBuffer();

		String monthOfReport = (String) MonthCb.getValue();
		String yearOfReport = (String) YearCb.getValue();

		sb.append(yearOfReport);
		sb.append("-");
		if (Integer.parseInt(monthOfReport) < 10)
			sb.append("0");
		sb.append(monthOfReport);
		sb.append("-01");
		LocalDate fromDate = LocalDate.parse(sb.toString());
		LocalDate toDate = fromDate.plusMonths(1);
		System.out.println("The from is:: " + fromDate.toString() + "AND " + toDate.toString());
		ClientUI.orderController.getDataForReport(fromDate, toDate);
		CancelReportData tmp = ClientUI.orderController.oR.get(0);
		// ClientUI.orderController.oR.remove(0);
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("cancelGraph.fxml"));
		ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getP();
		Scene scene = new Scene(root);
		stage.setTitle("Prototype");
		stage.setScene(scene);
		stage.show();

		// XYChart.Series series2=new XYChart.Series();

		System.out.println("GOT HERE " + tmp.getMonth());
		String s = tmp.getMonth() + "/" + tmp.getYear();

		// series2.getData().add(new XYChart.Data<>(s, 3));
		flag++;

		// chartBar.getData().addAll(series1,series2);

		// chartBar.setTitle("EHR");

	}

}
