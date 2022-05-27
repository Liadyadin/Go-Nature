/**
 *  Description of discountForApproveDM
 * This class responisble for the table showing
 * the alternative dates for picking a visit
 * Creating button to choose a date
 * Creating the option to choose from another time
 * 
 * @author TalLanger	
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientUI;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class discountForApproveDM implements Initializable {

	@FXML
	public TableView<parkPendingRData> RequestTable;

	@FXML
	private TableColumn<parkPendingRData, String> IdLbl;

	@FXML
	private TableColumn<parkPendingRData, String> RequestDateLbl;

	@FXML
	private TableColumn<parkPendingRData, String> RequestTimeLbl;

	@FXML
	private TableColumn<parkPendingRData, String> ParkNameLbl;

	@FXML
	private TableColumn<parkPendingRData, String> StartDateLbl;

	@FXML
	private TableColumn<parkPendingRData, String> LastDateLbl;

	@FXML
	private TableColumn<parkPendingRData, String> PrecentageLbl;

	@FXML
	void whenClickBackBtn(ActionEvent event) {
		/* Go back to DM main menu */
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("WelcomeDepartmentEmployee.fxml"));
			ClientUI.LogOutUtility.makeTheStageDynamicForParent(stage, root);
			stage = ClientUI.LogOutUtility.getStage();
			root = ClientUI.LogOutUtility.getP();
			Scene scene = new Scene(root);
			stage.setTitle("Department Manger Menu");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void WhenClickInformationBtn(MouseEvent event) {
		Alert a = new Alert(AlertType.INFORMATION,
				"On this screen, you will see park discount requests waiting for confirmation.\r\n"
						+ "The discount will become valid only if you chose to approve it.\r\n"
						+ "*every time the park manager wants to set a discount the discount will be sent here.");
		a.setTitle("INFORMATION");
		a.setAlertType(AlertType.INFORMATION);
		a.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("D");

		ParkNameLbl.setCellValueFactory(new PropertyValueFactory<>("ParkName"));
		IdLbl.setCellValueFactory(new PropertyValueFactory<>("RequestID"));
		RequestDateLbl.setCellValueFactory(new PropertyValueFactory<>("RequestDate"));
		RequestTimeLbl.setCellValueFactory(new PropertyValueFactory<>("RequestTime"));
		StartDateLbl.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
		LastDateLbl.setCellValueFactory(new PropertyValueFactory<>("LastDate"));
		PrecentageLbl.setCellValueFactory(new PropertyValueFactory<>("Precentage"));

		ObservableList<parkPendingRData> rows = null;
		rows = FXCollections.observableArrayList();
		int CntDiscounts = ClientUI.discountController.NumOfParks;
		int RequestID = 0;
		for (int i = 0; i < CntDiscounts; i++) {
			/* check if discount i is waiting for approve */
			ClientUI.chat.accept("isDiscountWaitingForApprove " + i);
			if (ClientUI.discountController.WaitingForApprove) {
				/* generate row */
				ClientUI.chat.accept("getDiscountWaitingForApprove " + i);
				/* add row */
				parkPendingRData r = ClientUI.discountController.discountWaitingForApproveRow;
				r.setRequestID(String.valueOf(++RequestID));
				rows.add(r);
			}
		}
		/* create table */
		RequestTable.setItems(rows);
		addButtonToTableToApprove();
		addButtonToTableToDisApprove();
	}

	/**
	 * addButtonToTableToApprove
	 */
	private void addButtonToTableToApprove() {
		TableColumn<parkPendingRData, Void> colBtn = new TableColumn("Approve");

		Callback<TableColumn<parkPendingRData, Void>, TableCell<parkPendingRData, Void>> cellFactory = new Callback<TableColumn<parkPendingRData, Void>, TableCell<parkPendingRData, Void>>() {
			@Override
			public TableCell<parkPendingRData, Void> call(final TableColumn<parkPendingRData, Void> param) {
				final TableCell<parkPendingRData, Void> cell = new TableCell<parkPendingRData, Void>() {

					private final Button btn = new Button("Approve");

					{
						btn.setOnAction((ActionEvent event) -> {
							// SET ACTION FOR APPROVE
							parkPendingRData data = getTableView().getItems().get(getIndex());
							/* change Discount status to 'Approved' */
							ClientUI.discountController.setDiscountStatus(data.getParkName(), "Approved");
							/* PopUp message */
							Alert a = new Alert(AlertType.CONFIRMATION, "Discount has been Approved");
							a.setTitle("Approved");
							a.setAlertType(AlertType.CONFIRMATION);
							a.show();
							/* Refrash table */
							initialize(null, null);

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);

		RequestTable.getColumns().add(colBtn);

	}

	/**
	 * addButtonToTableToDisApprove
	 */
	private void addButtonToTableToDisApprove() {
		TableColumn<parkPendingRData, Void> colBtn = new TableColumn("Disapprove");

		Callback<TableColumn<parkPendingRData, Void>, TableCell<parkPendingRData, Void>> cellFactory = new Callback<TableColumn<parkPendingRData, Void>, TableCell<parkPendingRData, Void>>() {
			@Override
			public TableCell<parkPendingRData, Void> call(final TableColumn<parkPendingRData, Void> param) {
				final TableCell<parkPendingRData, Void> cell = new TableCell<parkPendingRData, Void>() {

					private final Button btn = new Button("Disapprove");

					{
						/*
						 * When pressing disapprove first we need to update the DB with status 1 After
						 * this I need to go to DB and pull the current requests And then finish.
						 * 
						 */
						btn.setOnAction((ActionEvent event) -> {
							// HERE SET ACTION FOR DISAPPROVE
							parkPendingRData data = getTableView().getItems().get(getIndex());
							/* change Discount status to 'Approved' */
							ClientUI.discountController.setDiscountStatus(data.getParkName(), "Disapproved");
							/* PopUp message */
							Alert a = new Alert(AlertType.CONFIRMATION, "Discount has been Approved");
							a.setTitle("Disapproved");
							a.setAlertType(AlertType.CONFIRMATION);
							a.show();
							/* Refrash table */
							initialize(null, null);

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);

		RequestTable.getColumns().add(colBtn);

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
