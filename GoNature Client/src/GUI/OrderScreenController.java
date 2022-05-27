/**
 * This class responsible for the comunication
 * between the client and creating new order
 * all of the datat transefring directly to the Order controller
 * The data being checked before to see for Input errors.
 * Transfering the user to another screens based on the type of order
 * Calculating price of the order, and comunicating with 
 * the Discount controller
 * 
 * @author Ilan Alexandrov

 */

package GUI;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientUI;
import Entities.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OrderScreenController implements Initializable {
	/** WantedParkCB - ComboBox for start */
	@FXML
	private ComboBox WantedParkCB;
	/** DateLbl - DatePicker for Date of visit */
	@FXML
	private DatePicker DateLbl;
	/** NumOfVisotrsLbl - TextField for Number of visitors */
	@FXML
	private TextField NumOfVisotrsLbl;
	/** TimeOfVisitCB - ComboBox for Time */
	@FXML
	private ComboBox TimeOfVisitCB;
	/** EmailLbl - TextField for Email */
	@FXML
	private TextField EmailLbl;
	/** PhoneNumberLbl - TextField for Phone */
	@FXML
	private TextField PhoneNumberLbl;
	/** IdOfViditorLbl - Label for Visitor ID */
	@FXML
	private Label IdOfViditorLbl;
	/** PriceLbl - Label for Price */
	@FXML
	private Label PriceLbl;

	/** listForParks - ObservableList for Park names */
	ObservableList<String> listForParks;
	/** listForTimes - ObservableList for Times of the park */
	ObservableList<String> listForTimes;

	/**
	 * Description of SetTimeParkCm() enter to the combo box the times from 8 Am to
	 * 12 Pm
	 * 
	 * @return void
	 */
	private void SetTimeParkCm() {
		String half = ":30";
		String whole = ":00";
		String t;
		int flag = 0;
		ArrayList<String> Times = new ArrayList<String>();

		for (int i = 8; i < 20; i++) {
			if (flag == 0) {
				t = Integer.toString(i) + whole;
				Times.add(t);
				flag++;
				i--;
			} else {
				t = Integer.toString(i) + half;
				Times.add(t);
				flag--;
			}
		}
		listForTimes = FXCollections.observableArrayList(Times);
		TimeOfVisitCB.setItems(listForTimes);
	}

	// !!!!!!! NEED TO CHANGE ID BY THE USER CONTROLLER!!!!!
	public void setIdOfMakingOrder() {
		IdOfViditorLbl.setText(ClientUI.userController.traveller.getId());
	}

	/**
	 * Description of initialize(URL arg0, ResourceBundle arg1) in Order screen
	 * initialize park times and dates, if the user came back from the confirmation
	 * page, it will show on the screen all of his ditails that he already filled
	 * 
	 * @return void
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		listForParks = clientLogic.inits.setWantedParkCB();
		WantedParkCB.setItems(listForParks);
		SetTimeParkCm();
		setIdOfMakingOrder();
		if (!(ClientUI.orderController.isConfirm)) {
			Order o = ClientUI.orderController.order;
			EmailLbl.setText(ClientUI.orderController.getEmail());
			PhoneNumberLbl.setText(ClientUI.orderController.getPhone());
			DateLbl.setValue(o.getDateOfVisit());
			NumOfVisotrsLbl.setText(Integer.toString(o.getNumberOfVisitors()));
			ClientUI.orderController.isConfirm = true;
		}
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
	 * Description of initialize(URL arg0, ResourceBundle arg1) in Order screen
	 * initialize park times and dates, if the user came back from the confirmation
	 * page, it will show on the screen all of his ditails that he already filled
	 * 
	 * @return void
	 */
	@FXML
	void WhenClickCalculatePriceBtn(ActionEvent event) {
		int numOfVisitors = Integer.parseInt(NumOfVisotrsLbl.getText());
		String type = ClientUI.userController.traveller.getType();
		if (type.equals("Family") || type.equals("Group"))
			ClientUI.discountController.getTotalPrice(type, numOfVisitors, "FutreOrder", "True");
		else
			ClientUI.discountController.getTotalPrice(type, numOfVisitors, "FutreOrder", "False");

		PriceLbl.setText(new DecimalFormat("##.##").format(ClientUI.discountController.getFinalPriceWithoutDM()));
		ClientUI.discountController.setFinalPrice(ClientUI.discountController.getFinalPriceWithoutDM());
	}

	/**
	 * Description of WhenClickNextBtn(ActionEvent event) in Order screen 1. Check
	 * if the date is valid date (not before today) 2. check if email and phone
	 * entered are valid 3. check if can make order from the order controller method
	 * 4. transfer the user to another stage based of if he can do an order or not
	 * 
	 * @throws IOExcepton
	 * @return void
	 */
	@FXML
	void WhenClickNextBtn(ActionEvent event) throws IOException {
		String typeOfUser = ClientUI.userController.traveller.getType();
		int numOfVisitors = Integer.parseInt(NumOfVisotrsLbl.getText());
		System.out.println(typeOfUser);
		if(typeOfUser.equals("Traveller")) {
			if(numOfVisitors>1) {
				Alert a = new Alert(AlertType.NONE,
						"Sorry but this  amount of visitors is more than allowed 4 you,\nYou can enter with maximum of 1");
				a.setAlertType(AlertType.ERROR);
				a.show();
				return;
			}
		}
		if(typeOfUser.equals("Group")||typeOfUser.equals("Family")) {
			if(numOfVisitors>ClientUI.userController.traveller.getNumberOfVisitors()) {
				Alert a = new Alert(AlertType.NONE,
						"Sorry but this  amount of visitors is more than allowed 4 you,\nYou can enter with maximum of "+ClientUI.userController.traveller.getNumberOfVisitors());
				a.setAlertType(AlertType.ERROR);
				a.show();
				return;
			}
		}
		String wanted = (String) WantedParkCB.getValue();
		
		int numOfAvailable = ClientUI.parkController.getMaxAvailableVisitors(wanted);
		if(numOfVisitors>numOfAvailable) {
			Alert a = new Alert(AlertType.NONE,
					"Sorry but this amount of visitors is not allowed in our park.\nPlease contact the employees or try again with  smaller  amount of visitors.\nIn our park we allowing only "+numOfAvailable);
			a.setAlertType(AlertType.ERROR);
			a.show();
		}
		else if (ClientUI.orderController.checkValidValues(PhoneNumberLbl.getText(), EmailLbl.getText())) {
			String temp = (String) TimeOfVisitCB.getValue();
			String[] res = temp.split(":");
			LocalDate date = DateLbl.getValue();
			LocalDate dateNow = LocalDate.now();
			if (date.compareTo(dateNow) < 0) {
				Alert a = new Alert(AlertType.NONE,
						"The date already passed.\nYou can not pick a date that is already been.\nPlease try again!");
				a.setAlertType(AlertType.ERROR);
				a.show();
			} else {
				
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				LocalDate d = DateLbl.getValue();
				LocalTime time = LocalTime.of(Integer.parseInt(res[0]), Integer.parseInt(res[1]));

				// here we will send the data we got from the page, we need to use "the type"
				// !!!!!!! TYPE NEED TO BE CHANGED !!!!!!!!
				ClientUI.orderController.setEmailAndPhone(EmailLbl.getText(), PhoneNumberLbl.getText());
				ClientUI.orderController.n_order = true;
				ClientUI.orderController.canMakeOrder(time, date, wanted, ClientUI.userController.traveller.getType(),
						numOfVisitors);

				/*
				 * after knowing if the order is possible or not, showing the right screen/
				 */
				if (ClientUI.orderController.valid) {
					ClientUI.orderController.isConfirm = false;
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/GUI/Confirmation.fxml").openStream());
					ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
					stage = ClientUI.LogOutUtility.getStage();
					root = ClientUI.LogOutUtility.getParent();
					Scene scene = new Scene(root);
					stage.setTitle("Confirm Order");
					stage.setScene(scene);
					stage.show();
				} else {
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/GUI/CancellOrder.fxml").openStream());
					ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
					stage = ClientUI.LogOutUtility.getStage();
					root = ClientUI.LogOutUtility.getParent();
					Scene scene = new Scene(root);
					stage.setTitle("Unapproved Order");
					stage.setScene(scene);
					stage.show();
				}
			}

		} else {
			Alert a = new Alert(AlertType.NONE, "Email or phone is incorrect!");
			a.setAlertType(AlertType.ERROR);
			a.show();
		}
		// System.out.println("Time: "+timeofVisit+" date: "+date+" wantedPark:
		// "+wanted+" number of visit: "+numOfVisitors);
	}

	/**
	 * Description of WhenClickPreviusBtn(ActionEvent event) in Order screen
	 * responislbe to get the traveller back to the welcome Traveller screen
	 * 
	 * @throws IOException
	 * @return void
	 */
	@FXML
	void WhenClickPreviusBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("WelcomeTraveller.fxml"));
		ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getP();
		Scene scene = new Scene(root);
		stage.setTitle("Welcome traveller");
		stage.setScene(scene);

		stage.show();
	}
}
