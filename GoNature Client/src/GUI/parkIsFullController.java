/**
 * This GUI will present to the traveler if park is full and will give him 2
 * options: 1. make new future order 2. close end exit
 * 
 * @author Liad Yadin
 *
 */
package GUI;

import java.io.IOException;

import Client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class parkIsFullController {
	/** button for close window */
	@FXML
	private Button exitBtn;
	/** button for make new order */
	@FXML
	private Button newOrderBtn;

	/**
	 * this method will close the window
	 * 
	 * @param event - when click exit button
	 */
	@FXML
	void WhenClickExitBtn(ActionEvent event) {
		Stage stage = (Stage) exitBtn.getScene().getWindow();
		stage.close();
	}

	/**
	 * in this option - the park is full if the traveler will click on this option
	 * he could make a future order
	 * 
	 * @param event- when click on make new order
	 * @throws IOException
	 */
	@FXML
	void WhenClickMakeNewOrderBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) newOrderBtn.getScene().getWindow();
		stage.close();
		FXMLLoader loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/GUI/NewOrder.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		primaryStage.setTitle("New Order");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}