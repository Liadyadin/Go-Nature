/**
 * The cancelGraphScreen class is responsible
 * for the vizual image of the graph
 * showing the Canceled orders by month.
 * added option of Exit to close stage.
 * @author Ilan Alexandrov
 
 */
package GUI;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class cancelGraphScreen implements Initializable {
	/** chartBar - BarChart for the graph */
	@FXML
	private BarChart<String, Number> chartBar;
	/** DateX - CategoryAxis for Graph */
	@FXML
	private CategoryAxis DateX;
	/** VisitY - NumberAxis for Graph */
	@FXML
	private NumberAxis VisitY;

	/**
	 * Description of initialize(URL arg0, ResourceBundle arg1) The initialize
	 * method responislb eto show the daya of the graph to the stage
	 * 
	
	 * 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		XYChart.Series<String, Number> series1;
		XYChart.Series<String, Number> series2;
		series1 = new XYChart.Series<String, Number>();
		series2 = new XYChart.Series<String, Number>();
		System.out.println("Here im trying!");
		for (CancelReportData k : ClientUI.orderController.oR) {
			System.out.println(k.getMonth());
		}
		series1.setName("Canceled");
		series2.setName("Not entered");
		for (CancelReportData k : ClientUI.orderController.oR) {
			series1.getData().add(new XYChart.Data<>(k.getMonth() + "/" + k.getYear(), k.getCancelledOrders()));
			series2.getData().add(new XYChart.Data<>(k.getMonth() + "/" + k.getYear(), k.getUncompleteOrders()));
		}
		chartBar.getData().addAll(series1, series2);
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
	 * Description of whenClickExit(ActionEvent event) Closing the stage of this
	 * graph
	 * 
	 * @return void
	 * 
	 */
	@FXML
	void whenClickExit(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

}
