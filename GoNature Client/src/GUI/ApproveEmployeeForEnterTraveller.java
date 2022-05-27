/**
 * This GUI will open when the employee choose to enter specific traveller in
 * park and to choose if approve or don't approve the request only provided that
 * there is a place in the park
 * 
 * @author Liad Yadin
 *
 */
package GUI;

import java.io.IOException;
import java.util.Optional;

import Client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ApproveEmployeeForEnterTraveller {

	/**
	 * variable for refusal enter park for casual traveler
	 */

	@FXML
	private Button notApproveBtn;

	/**
	 * variable for approve enter park for casual traveler
	 */

	@FXML
	private Button approveBtn;

	/**
	 * this method is enabled when the park employee approve the request of casual
	 * traveller, update status of request to 1 and back to the previous page
	 * 
	 * @param event - when clicked on Approve button
	 * @throws IOException
	 */
	@FXML
	void whenClickedApproveBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) approveBtn.getScene().getWindow();
		stage.close();
		ClientUI.requestsController.changeStatusForCasualTraveller(1, ClientUI.requestsController.d.getID(),
				ClientUI.requestsController.d.getPark());
		FXMLLoader loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("requestsEnterTraveller.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Requests enter traveller");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * this method is enabled when the park employee don't approve the request of
	 * casual traveller,update status of request to 0 and back to the previous page
	 * 
	 * @param event- when clicked on Approve button
	 * @throws IOException
	 */
	@FXML
	void whenClickedDontApprove(ActionEvent event) throws IOException {
		Stage stage = (Stage) notApproveBtn.getScene().getWindow();
		stage.close();
		ClientUI.requestsController.changeStatusForCasualTraveller(0, ClientUI.requestsController.d.getID(),
				ClientUI.requestsController.d.getPark());
		FXMLLoader loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("requestsEnterTraveller.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Requests enter traveller");
		primaryStage.setScene(scene);
		primaryStage.show();

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
			ClientUI.LogOutUtility.logOutEmployee();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		} else if (result.get() == ButtonType.CANCEL)
			alert.close();
	}

}
