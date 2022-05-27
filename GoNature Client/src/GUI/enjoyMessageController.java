/**
 * This GUI will show 'enjoy message' for casual traveler in the entrance park
 * and presnting him how many time he has in the park
 * 
 * @author Liad Yadin
 *
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class enjoyMessageController implements Initializable {
	/** for presenting the time the traveler has in park */
	@FXML
	private Label Lbltime;
	/** when the traveler click close button */
	@FXML
	private Button closeBtn;

	/**
	 * close window
	 * 
	 * @param event- when click on close button
	 */
	@FXML
	void whenClickOnCloseBtn(ActionEvent event) {
		Stage stage = (Stage) closeBtn.getScene().getWindow();
		ClientUI.LogOutUtility.logOutTraveller();
		stage.close();
	}

	/**
	 * this method will show the time that the traveler could be in the park
	 */
	public void setTime() {
		String st = String.valueOf(ClientUI.parkController.park.getMaxDurationVisit());
		Lbltime.setText(st);
	}

	/**
	 * for present the time
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setTime();
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
		alert.setContentText("Select yes if you want, or not if you want to get back!");
		((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
		((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent())
			alert.close();
		else if (result.get() == ButtonType.OK) {
			ClientUI.LogOutUtility.logOutTraveller();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		} else if (result.get() == ButtonType.CANCEL)
			alert.close();
	}

}
