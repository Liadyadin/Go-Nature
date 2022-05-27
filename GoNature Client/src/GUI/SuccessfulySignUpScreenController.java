/** Description of SignUpScreenController 
* @author Omri Cohen
* 
*/
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * This is a controller for sign up confirmation screen.
 * 

 */
public class SuccessfulySignUpScreenController implements Initializable {

	@FXML
	private Label SubscriberNumberLbl;

	@FXML
	private Label SignedUpType;

	/**
	 * Description of initialize() this function initializes the combo boxes for the
	 * window..
	 * @param arg0- arg0
	 * @param arg1- arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		SubscriberNumberLbl.setText(ClientUI.signUpController.getID());
		SignedUpType.setText(ClientUI.signUpController.getType());
	}

	/**
	 * Description of WhenClickMainMenuBtn() Upon clicking "main menu" button the
	 * employee shall be returned to the employees main window.
	 * 
	 * @return void.
	 */
	@FXML
	void WhenClickMainMenuBtn(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("welcomeEmployee.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getP();
		Scene scene = new Scene(root);
		stage.setTitle("Welcome Employee");
		stage.setScene(scene);

		stage.show();
	}

}
