/**
 * confirmationScreenController class,
 * Responsible to show to the traveler all of 
 * his order details, this message is what need to be
 * presented at the email and SMS.
 * making an option to get back to the creating order 
 * (already filled with the previus details)
 * 
 * @author Ilan Alexandrov
 */

package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientUI;
import Entities.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class confirmationScreenController implements Initializable {
	/** IdOvTravelerLb - Label for IdOvTravelerLb */
	@FXML
	private Label IdOvTravelerLb;
	/** WantedParkLbl - Label for WantedParkLbl */
	@FXML
	private Label WantedParkLbl;
	/** DateLbl - Label for DateLbl */

	@FXML
	private Label DateLbl;
	/** TimeLbl - Label for TimeLbl */
	@FXML
	private Label TimeLbl;
	/** EmailLbl - Label for EmailLbl */
	@FXML
	private Label EmailLbl;
	/** PhoneLbl - Label for PhoneLbl */
	@FXML
	private Label PhoneLbl;
	/** NumVisitLlbl - Label for NumVisitLlbl */
	@FXML
	private Label NumVisitLlbl;

	/** PriceLbl - Label for PriceLbl */
	@FXML
	private Label PriceLbl;

	/**
	 * Description of setValues() Setting the values of the confirmation based on
	 * what was set in the order and what set for the traveler who made the order
	 * 
	 
	 */
	public void setValues() {
		Order o = ClientUI.orderController.order;
		IdOvTravelerLb.setText(ClientUI.userController.traveller.getId());
		WantedParkLbl.setText(o.getWantedPark());
		DateLbl.setText(o.getDateOfVisit().toString());
		TimeLbl.setText(o.getTimeInPark().toString());
		EmailLbl.setText(ClientUI.orderController.getEmail());
		PhoneLbl.setText(ClientUI.orderController.getPhone());
		NumVisitLlbl.setText(Integer.toString(o.getNumberOfVisitors()));
		Float finalPric = ClientUI.discountController.calculateFinalPrice(o);
		PriceLbl.setText(Float.toString(finalPric));
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
	 * Description initialize(URL location, ResourceBundle resources) Showing the
	 * information for the user the information includes all the data of the order
	 * 
	
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setValues();
	}

	/**
	 * Description initialize(URL location, ResourceBundle resources) when the user
	 * will click on done, the system will: will insert the order into DB will
	 * delete the current order from the "o" value
	 * 
	 * @throws IOException
	 * @return void
	 */

	@FXML
	void WhenClickDoneBtn(ActionEvent event) throws IOException {
		ClientUI.orderController.confirmOrder();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("WelcomeTraveller.fxml"));
		ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getP();
		Scene scene = new Scene(root);
		stage.setTitle("Prototyp");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Description of whenClickBackBtn(ActionEvent event) throws IOException if the
	 * user click back, he will need to fill the form again with the details from
	 * here
	 * 
	 * @return void
	 */
	@FXML
	void whenClickBackBtn(ActionEvent event) throws IOException {
		ClientUI.orderController.isConfirm = false;
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("NewOrder.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Prototyp");
		stage.setScene(scene);

		stage.show();
	}
}
