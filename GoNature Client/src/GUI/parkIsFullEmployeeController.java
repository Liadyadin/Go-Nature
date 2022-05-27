/**
 * This GUI will present to the employee if park is full and the system will
 * return him to the request screen
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


public class parkIsFullEmployeeController {
	/** button for back */
	@FXML
	private Button backBtn;
	/** button for close window */
	@FXML
	private Button closeBtn;

	/**
	 * this method will return the previous screen
	 * 
	 * @param event- when click back button
	 * @throws IOException
	 */
	@FXML
	void whenClickedonBackBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) backBtn.getScene().getWindow();
		stage.close();
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
	 * this method will close the window
	 * 
	 * @param event-when click close button
	 */
	@FXML
	void whenClickedonClosebtn(ActionEvent event) {
		Stage stage = (Stage) closeBtn.getScene().getWindow();
		stage.close();
	}

}
