
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientUI;
import Entities.TravellerInPark;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * this GUI will present to the traveler the implemantion of the request he send
 * to the employee department this GUI will show the details of the traveler and
 * the total price with option of payment for entrance park
 * 
 * @author Liad Yadin
 *
 */
public class ImplemantionEnterParkController implements Initializable {
	/** Label for present current date */
	@FXML
	private Label DateLbl;
	/** Label for present park name */
	@FXML
	private Label ParkLbl;
	/** Label for present current time of enter */
	@FXML
	private Label TimeLbl;
	/** Label for present number of visitors */
	@FXML
	private Label VisitNumberLbl;
	/** Label for present total price */
	@FXML
	private Label PriceLbl;
	/** button for payment */
	@FXML
	private Button payNowBtn;

	/**
	 * if the traveler choose in this option he go on to the payment(we dont care
	 * this logic- in external system) finally presenting enjoy message screen
	 * 
	 * @param event - when click for payment
	 * @throws IOException
	 */
	@FXML
	void WhenClickPayNowBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) payNowBtn.getScene().getWindow();
		stage.close();
		FXMLLoader loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/GUI/enjoyMessage.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		primaryStage.setTitle("bye bye");
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
		alert.setContentText("Select Yes if you want to exit Or No if you want to stay.");
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

	/**
	 * this method will initialize all the details of this traveler
	 */

	public void initialize(URL location, ResourceBundle resources) {
		TravellerInPark t = ClientUI.entranceParkController.travellerinpark;

		ParkLbl.setText(t.getwantedPark());
		DateLbl.setText(t.getdateOfVisit().toString());
		TimeLbl.setText(t.gettimeInPark().withSecond(0).withNano(0).toString());
		VisitNumberLbl.setText(Integer.toString(t.getnumOfVisitors()));
		PriceLbl.setText(Float.toString(t.gettotalPrice()));
		ClientUI.entranceParkController.travellerinpark = null;
	}

}