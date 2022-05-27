/**
 * this GUI will show the details of casual entrance park. the traveler will
 * choose the park he wants to enter inside and the number of visitors he came
 * with. there is an option to calculate price for this enter. by click on
 * 'next' we will send the request enter for the employee
 * 
 * @author Liad Yadin
 *
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.jws.soap.SOAPBinding.Use;

import Client.ClientUI;
import Entities.Order;
import Entities.Park;
import Entities.TravellerInPark;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class EnterParkNowController implements Initializable {
	/** for send request enter to employee of the park */
	@FXML
	private Button btnNext;
	/** return to the previous page */
	@FXML
	private Button btnPrevious;
	/** for the ID of traveller */
	@FXML
	private Label IDlbl;
	/** for the total price */
	@FXML
	private Label PriceLbl;
	/** for the wanted park */
	@FXML
	private ComboBox WantedParkCB;
	/** the current date of request */
	@FXML
	private Label DateLbl;
	/** the current time of request */
	@FXML
	private Label TimeLbl;
	/** for the number of visitors */
	@FXML
	private ComboBox NumOfVisitorsCB;
	/** button for calculate total price */
	@FXML
	private Button btnCalculatePrice;
	/** list for the wanted park CB */
	ObservableList<String> listForParks;
	/** list for the numberofvisitors CB */
	ObservableList<String> listForNumOfVisitors;
	/** variable for the wanted park */
	private String wantedpark;
	/** variable for the num of visitors */
	private int numOfVisitors;
	/** variable for the total price */
	private float Totalprice;
	/** variable for the current date */
	private LocalDate myDate;
	/** variable for the current time */
	private LocalTime myTime;

	/**
	 * this methos will initialize the Combo Box of NumOfVisitors
	 */
	private void setNumOfVisitorsCm() {
		ArrayList<String> numbersOfVisitors = new ArrayList<String>();
		for (int i = 1; i <= 15; i++)
			numbersOfVisitors.add(String.valueOf(i));

		listForNumOfVisitors = FXCollections.observableArrayList(numbersOfVisitors);
		NumOfVisitorsCB.setItems(listForNumOfVisitors);
	}

	/**
	 * this method will initialize all th component of this GUI: 1.wanted park CB
	 * 2.num of visitors CB 3.time 4.date 5.ID
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listForParks = clientLogic.inits.setWantedParkCB();
		WantedParkCB.setItems(listForParks);

		myDate = LocalDate.now();
		myTime = LocalTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
		String formattedTime = myTime.format(myFormatObj);
		this.DateLbl.setText(myDate.toString());
		this.TimeLbl.setText(formattedTime.toString());

		IDlbl.setText(ClientUI.userController.traveller.getId());
		setNumOfVisitorsCm();

	}

	/**
	 * this method call to getprice method for calculate total price
	 * 
	 * @param event when we click the button
	 * @throws IOException
	 */
	@FXML
	void WhenClickCalculatePriceBtn(ActionEvent event) throws IOException {
		this.PriceLbl.setText((String.valueOf(this.getPrice())));
	}

	/**
	 * this method calculate total price of casual traveler the price determined by:
	 * number of visitors, if he has a member and the discount
	 * 
	 * @return total price of the traveler enter park
	 */
	public float getPrice() {
		float priceBeforParkManager;
		float priceAftarDiscountManager;
		int numOfVisit = Integer.parseInt((String) NumOfVisitorsCB.getValue());
		String type = ClientUI.userController.traveller.getType();
		if (ClientUI.userController.traveller.getMemberID() != null) // member - 20% discount
			ClientUI.discountController.getTotalPrice("PreOrderedTraveller", numOfVisit, "Casual", "True");
		else if (numOfVisit == 1) // regular price
			ClientUI.discountController.getTotalPrice("PreOrderedTraveller", numOfVisit, "Casual", "False");
		else // group- 10% discounts
			ClientUI.discountController.getTotalPrice("Group", numOfVisit, "Casual", "False");

		priceBeforParkManager = (ClientUI.discountController.getFinalPriceWithoutDM());
		Order orderForPrice = new Order(0, null, myDate, (String)WantedParkCB.getValue(), numOfVisit, priceBeforParkManager);
		priceAftarDiscountManager = ClientUI.discountController.calculateFinalPrice(orderForPrice);
		ClientUI.entranceParkController.enterpark.Totalprice = priceAftarDiscountManager;
		return priceAftarDiscountManager;
	}

	/**
	 * get number of visitor
	 * 
	 * @return numOfVisitors
	 */
	public int getNumOfVisitors() {
		return numOfVisitors;
	}

	/**
	 * get price
	 * 
	 * @return price
	 */
	public float getTotalprice() {
		return Totalprice;
	}

	/**
	 * get wanted park
	 * 
	 * @return wantedpark
	 */
	public String getWantedPark() {
		return wantedpark;
	}

	/**
	 * this button send request of enter park to the employee department and add to
	 * DB of requests table when he push the button he moves to the waiting screen
	 * 
	 * @param event - when click next button
	 * @throws IOException
	 */
	@FXML
	void WhenClickNextBtn(ActionEvent event) throws IOException {
		Totalprice = this.getPrice();
		wantedpark = (String) WantedParkCB.getValue();
		ClientUI.entranceParkController.enterpark.wantedpark = wantedpark;
		String numString = (String) NumOfVisitorsCB.getValue();
		numOfVisitors = Integer.parseInt(numString);
		ClientUI.entranceParkController.enterpark.numOfVisitors = numOfVisitors;

		ClientUI.requestsController.insertRequestToDB(ClientUI.userController.traveller.getId(), myDate, myTime,
				wantedpark, numOfVisitors, "EnterPark", -1);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("waiting.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Waiting for enter");
		stage.setScene(scene);
		stage.show();
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

	/**
	 * this action will return to the back screen
	 * 
	 * @param event- when click previous button
	 * @throws IOException
	 */
	@FXML
	void WhenClickPreviusBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnPrevious.getScene().getWindow();
		stage.close();
		FXMLLoader loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/GUI/WelcomeTraveller.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Welcome Traveller");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}