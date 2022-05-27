/**
 * UnapprovedOrderController responsible for the unapproved
 * screen, giving the option to enter to waiting list
 * show alternative dates
 * or cancel order.
 * Every one of the option refer to another screen.
 * when initialized, presenting the information about the wanted 
 * order, and alerting about unable to complete the mission
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UnapprovedOrderController implements Initializable {
	/** enterWaitingList - Button */
	@FXML
	private Button enterWaitingList;
	/** ShowAlternativeDates - Button */
	@FXML
	private Button ShowAlternativeDates;
	/** cancelOrder - Button */
	@FXML
	private Button cancelOrder;
	/** ParkNameLbl - Lable */
	@FXML
	private Label ParkNameLbl;
	/** DateVisitLbl - Lable */
	@FXML
	private Label DateVisitLbl;
	/** NumberVisitorsLbl - Lable */
	@FXML
	private Label NumberVisitorsLbl;
	/** IdNumberLbl - Lable */
	@FXML
	private Label IdNumberLbl;

	/**
	 * Description of WhenClickCancellBtn(ActionEvent event) throws IOException
	 * 
	 * Method referring to the cancelation screen transfer in the OrderController
	 * 
	 * @throws IOException -io
	 */
	@FXML
	void WhenClickCancellBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ClientUI.orderController.wantToCancel(stage);
	}

	/**
	 * Description of WhenClickEnterWaitingListBtn(ActionEvent event)
	 * 
	 * when click enter waiting list : 1. need to enter the order into the Order
	 * table in the Db 2. need to change the status of the order to waiting from
	 * confirmed 3. need to put the waiting request into the table of the waiting
	 * list with the current timeStamp.
	 * 
	 */
	@FXML
	void WhenClickEnterWaitingListBtn(ActionEvent event) {
		Order tmp = ClientUI.orderController.order;
		ClientUI.orderController.confirmOrder();
		ClientUI.orderController.ChangeToWaitOrder(tmp);
		ClientUI.waitingListController.enterWaitingList(tmp.getOrderNum());
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Success");
		alert.setHeaderText("You successfuly entered into the waiting list");
		alert.setResizable(false);
		alert.setContentText("Please wait for SMS to confirm it");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root;
		try {
			root = loader.load(getClass().getResource("WelcomeTraveller.fxml").openStream());
			ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
			stage = ClientUI.LogOutUtility.getStage();
			root= ClientUI.LogOutUtility.getParent();
			Scene scene = new Scene(root);
			stage.setTitle("Cancel order");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Description of WhenClickShowBtn(ActionEvent event) throws IOException This
	 * method will transfer the client into new stage of alternative dates
	 * 
	 * @param event - event 
	 * @throws IOException - io
	 */
	@FXML
	void WhenClickShowBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("AlternativeDates.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Cancel order");
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * This method responislbe of showing an alert when want to close the
	 * application.
	 * 
	 * @param event - event
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
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		} else if (result.get() == ButtonType.CANCEL)
			alert.close();
	}

	/**
	 * Description of setValues() This method will insert the values into the labels
	 * 
	 */

	public void setValues() {
		Order o = ClientUI.orderController.order;
		IdNumberLbl.setText("31198");
		ParkNameLbl.setText(o.getWantedPark());
		DateVisitLbl.setText(o.getDateOfVisit().toString());
		String num = Integer.toString(o.getNumberOfVisitors());
		NumberVisitorsLbl.setText(num);

	}

	/**
	 * Description of initialize This method will initialize the values of the order
	 * wanted to be complete
	 * 

	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setValues();
	}

}
