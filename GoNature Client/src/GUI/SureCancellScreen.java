/**
 *  Descripiton of  SureCancell Screen
 * SureCancellScreen class responsible of showing, 
 * a screen with aditional option to not cancel order
 * 
 * @author Ilan Alexandrov	

 */

package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientUI;
import Entities.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SureCancellScreen implements Initializable {
	/** DateLbl - Label for DAte */
	@FXML
	private Label DateLbl;
	/** ParkLbl - Label for Park */
	@FXML
	private Label ParkLbl;
	/** TimeLbl - Label for Time */
	@FXML
	private Label TimeLbl;

	/**
	 * Description of WhenClickDontWantToCancelBtn(ActionEvent event) in SureCancell
	 * Screen if the traveler don't want to cancel the order, he will return to the
	 * previous page (the UnapprovedOrder sceen)
	 * 
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void WhenClickDontWantToCancelBtn(ActionEvent event) throws IOException {

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/GUI/WelcomeTraveller.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Cancel order");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Description of WhenClickWantToCancel(ActionEvent event) in SureCancell Screen
	 * if the traveler want to cancel the order he will return to the main traveller
	 * page
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void WhenClickWantToCancel(ActionEvent event) throws IOException {
		ClientUI.orderController.cancelOrder(DateLbl.getText(), ParkLbl.getText(), TimeLbl.getText());

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/GUI/WelcomeTraveller.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Cancel order");
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Description of setVals() in SureCancell Screen Setting the values of the Date
	 * park and time for the traveller to see what type of order he will be
	 * canceling now
	 * 

	 */
	public void setVals() {
		Order o = ClientUI.orderController.order;
		DateLbl.setText(o.getDateOfVisit().toString());
		ParkLbl.setText(o.getWantedPark());
		TimeLbl.setText(o.getTimeInPark().toString());
	}

	/**
	 * Description of setVals() in SureCancell Screen calling to the setVals that
	 * will be initializing the datat into the labels
	 * 
	
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setVals();

	}

}
