
/**
 * This GUI will give to the park manager an option to do report about usage in
 * the park. the park manager need to choose month and year. the system will
 * present in the table the date in this month and this year that the park
 * wasn't full. in addition, the system will present in the line chart
 * Precentage of max capacity per day in this month
 * 
 * @author Liad Yadin
 *
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientUI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class usageReportController implements Initializable {
	/** Combo box for month */
	@FXML
	private ComboBox monthCB;
	/** Combo box for year */
	@FXML
	private ComboBox yearCB;
	/** Button for return back screen */
	@FXML
	private Button PreviousBtn;
	/** Button for calculate report */
	@FXML
	private Button calculateBtn;
	/** Line chart for presenting the data */
	@FXML
	private LineChart<String, Number> lineChartimePercent;
	/** The vertical axis of the line chart */
	@FXML
	private CategoryAxis days;
	/** The horizontal axis of the line chart */
	@FXML
	private NumberAxis precent;
	/** Table for presenting the data */
	@FXML
	private TableView<Data> dateofNotfullCapacityTable;
	/** Column in the table for dates */
	@FXML
	private TableColumn<Data, String> DateLbl;
	/** List for years */
	ObservableList<String> listForYears;
	/** List for months */
	ObservableList<String> listForMonth;

	/**
	 * this method for initialize the Combo box of years and months
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listForYears = clientLogic.inits.setYearCB();
		listForMonth = clientLogic.inits.setMonthCB();
		monthCB.setItems(listForMonth);
		yearCB.setItems(listForYears);
		dateofNotfullCapacityTable.setVisible(false);
		lineChartimePercent.setVisible(false);
	}

	/**
	 * this method will calculate by year and month the data of usage report to the
	 * table view and line chart
	 * 
	 * @param event - when click calculate button
	 */
	@FXML
	void WhenClickCralculateBtn(ActionEvent event) {
		lineChartimePercent.getData().clear();
		dateofNotfullCapacityTable.setVisible(true);
		lineChartimePercent.setVisible(true);
		String year = (String) yearCB.getValue();
		String month = (String) monthCB.getValue();
		DateLbl.setCellValueFactory(new PropertyValueFactory<>("Date"));
		ClientUI.reportsController.ob.clear();
		ClientUI.reportsController.getTableOfUnFullCapacityInDates(month, year, "parkA"); // ClientUI.employeeController.getParkName()
		dateofNotfullCapacityTable.setItems(ClientUI.reportsController.ob);
		String date;
		int maxVisitors;
		int maxCurrent;
		float ratio;
		ClientUI.reportsController.ob2.clear();
		ClientUI.reportsController.getUnFullCapacityTableInDatesAndNumbers(month, year, "parkA");
		XYChart.Series series = new XYChart.Series();
		series.setName("Precentage of max capacity per day");
		for (Data d : ClientUI.reportsController.ob2) {
			date = d.getDate();
			maxVisitors = Integer.parseInt(d.getMaxVisitors());
			maxCurrent = Integer.parseInt(d.getMaxCurrent());
			ratio = (float) ((float) maxCurrent / (float) maxVisitors);
			series.getData().add(new XYChart.Data(date, (ratio * 100)));
		}
		lineChartimePercent.setTitle("Unfull days");

		lineChartimePercent.getData().addAll(series);

	}

	/**
	 * 
	 * This method responislbe of showing an alert when want to close the
	 * application.
	 * 
	 * @param event -event
	 */
	@FXML
	void WhenClickExitBtn(MouseEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Are you sure you want to exit the application?");
		alert.setResizable(false);
		alert.setContentText("Select yes if you want, or not if you want to get back!");
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
	 * this method will return to the back screen
	 * 
	 * @param event - when click previous button
	 */
	@FXML
	void WhenClickPreviousBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("WelcomeParkManager.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
