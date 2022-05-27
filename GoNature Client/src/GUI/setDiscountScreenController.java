/**
 * Description of  setDiscountScreenController
 * @author Ilan
 *
 */
package GUI;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import Client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class setDiscountScreenController {
	long diff_days;
	Date from, to;
	float precentage;
	String parkName;
	@FXML
	private Button calculate_duration_btn;

	@FXML
	private DatePicker FromDateField;

	@FXML
	private DatePicker ToDateField;

	@FXML
	private TextField DiscountPrecantageLBL;

	@FXML
	private Label DaysOFDiscountLBL;

	@FXML
	private Button submitDiscountBTN;

	@FXML
	private Button CancelBTN;

	/**
	 * calculate duration of discount and show it to park manager
	 * 
	 * @param event
	 */
	@FXML
	void WhenClickCalculate(ActionEvent event) {
		// calc days between/
		LocalDate localFrom = FromDateField.getValue();
		LocalDate localTo = ToDateField.getValue();
		if (localFrom == null || localTo == null) {
			Alert a = new Alert(AlertType.NONE, "You must enter 'From' date and 'To' date!");
			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		}
		from = java.sql.Date.valueOf(localFrom);
		to = java.sql.Date.valueOf(localTo);
		long diff = to.getTime() - from.getTime();
		diff_days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		// set message to label/
		if (diff_days > 0) {
			String daysBetween = String.valueOf(diff_days);
			DaysOFDiscountLBL.setText(daysBetween + " Days");
		} else {
			Alert a = new Alert(AlertType.NONE, "'To' date must be after 'From' date!!!");
			a.setAlertType(AlertType.ERROR);
			a.show();
		}

	}

	/**
	 * send manager discount updates to DB
	 * 
	 * @param event
	 */
	@FXML
	void whenClickSubmitDiscount(ActionEvent event) {
		// call WhenClickCalculate to calculate dates and daysBetween/
		WhenClickCalculate(event);
		String PrecantageLBL = DiscountPrecantageLBL.getText();

		// check valid daysBetween && PrecantageLBL/
		if (diff_days < 0 || PrecantageLBL.equals("")) { // show popUp message
			Alert a = new Alert(AlertType.NONE, "All fields are required\n Or Duration time is invalid");
			a.setAlertType(AlertType.ERROR);
			a.show();
			return;
		} else {
			precentage = Float.valueOf(PrecantageLBL);
			if (precentage <= 0 || precentage >= 1) {
				Alert a = new Alert(AlertType.NONE, "Discount precentage must be between 0.01 - 0.99");
				a.setAlertType(AlertType.ERROR);
				a.show();
				return;
			}
			parkName = ClientUI.employeeController.getParkName();
			ClientUI.discountController.setManagerDiscount(from, to, precentage, parkName);
		}

		/* change screen if success */
		if (ClientUI.discountController.setManagerDiscount_flag) {
			Alert a = new Alert(AlertType.NONE, "Discount was sent to D.M");
			a.setAlertType(AlertType.CONFIRMATION);
			a.show();

			/* go back to ParkManger.fxml screen */
			WhenClickCancel(event);

		} else {
			Alert a = new Alert(AlertType.NONE, "Sql error!!!");
			a.setAlertType(AlertType.ERROR);
			a.show();
		}
	}

	/**
	 * go back to manager main menu
	 * 
	 * @param event
	 */
	@FXML
	void WhenClickCancel(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("WelcomeParkManager.fxml"));
			ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
			stage = ClientUI.LogOutUtility.getStage();
			root = ClientUI.LogOutUtility.getP();
			Scene scene = new Scene(root);
			stage.setTitle("Park Manger Menu");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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
			ClientUI.LogOutUtility.logOutEmployee();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		} else if (result.get() == ButtonType.CANCEL)
			alert.close();
	}

}