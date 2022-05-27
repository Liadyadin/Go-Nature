package GUI;

import java.util.Optional;

import Client.ClientUI;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CancelReportData {
	public String getYear() {
		return Year;
	}

	public String getMonth() {
		return Month;
	}

	public Number getCancelledOrders() {
		return CancelledOrders;
	}

	public Number getUncompleteOrders() {
		return UncompleteOrders;
	}

	private String Year;
	private String Month;
	private Number CancelledOrders;
	private Number UncompleteOrders;

	public CancelReportData(String year, String Month, Number CancelledOrders, Number UncompleteOrders) {
		this.Year = year;
		this.Month = Month;
		this.CancelledOrders = CancelledOrders;
		this.UncompleteOrders = UncompleteOrders;
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

}
