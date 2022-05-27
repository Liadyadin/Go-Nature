package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Description of pendingParkSettingsRequestDepartmentManagerScreen
 * @author Ilan
 * @author Bar
 *
 */
public class pendingParkSettingsRequestDepartmentManagerScreen implements Initializable {
	@FXML
	private TableView<parkPendingRData> RequestTable;

	@FXML
	private TableColumn<parkPendingRData, String> ParkNameLbl;

	@FXML
	private TableColumn<parkPendingRData, String> RequestDateLbl;

	@FXML
	private TableColumn<parkPendingRData, String> RequestTimeLbl;

	@FXML
	private TableColumn<parkPendingRData, String> MaxVisitorsLbl;

	@FXML
	private TableColumn<parkPendingRData, String> GapLbl;

	@FXML
	private TableColumn<parkPendingRData, String> MaxDurationLbl;

	@FXML
	private TableColumn<parkPendingRData, String> IdLbl;

	@FXML
	void whenClickBackBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("WelcomeDepartmentEmployee.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Welcome" + " " + ClientUI.employeeController.getType() + "!");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void WhenClickInformationBtn(MouseEvent event) {
		Alert a = new Alert(AlertType.INFORMATION,
				"This table will show how many requests are pending from ParkManagers\nYou may approve or dissaprove.\nOnce you approve, the park settings  will be set.\nIf you disapprove, nothing will happen.");
		a.setTitle("Park settings requests");
		a.setHeaderText("Information about requests table");

		a.show();
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
			ClientUI.LogOutUtility.logOutEmployee();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		} else if (result.get() == ButtonType.CANCEL)
			alert.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ParkNameLbl.setCellValueFactory(new PropertyValueFactory<>("ParkName"));
		RequestDateLbl.setCellValueFactory(new PropertyValueFactory<>("RequestDate"));
		RequestTimeLbl.setCellValueFactory(new PropertyValueFactory<>("RequestTime"));
		MaxVisitorsLbl.setCellValueFactory(new PropertyValueFactory<>("MaxVisitors"));
		GapLbl.setCellValueFactory(new PropertyValueFactory<>("Gap"));
		MaxDurationLbl.setCellValueFactory(new PropertyValueFactory<>("MaxDuration"));
		IdLbl.setCellValueFactory(new PropertyValueFactory<>("RequestID"));

		addButtonToTableToApprove();
		addButtonToTableToDisApprove();
		RequestTable.setItems(ClientUI.employeeController.parkSettingChangeRequests);
	}

	private void addButtonToTableToApprove() {
		TableColumn<parkPendingRData, Void> colBtn = new TableColumn("Approve");

		Callback<TableColumn<parkPendingRData, Void>, TableCell<parkPendingRData, Void>> cellFactory = new Callback<TableColumn<parkPendingRData, Void>, TableCell<parkPendingRData, Void>>() {
			@Override
			public TableCell<parkPendingRData, Void> call(final TableColumn<parkPendingRData, Void> param) {
				final TableCell<parkPendingRData, Void> cell = new TableCell<parkPendingRData, Void>() {

					private final Button btn = new Button("Approve");

					{
						btn.setOnAction((ActionEvent event) -> {
							/*
							 * When pressing approve first we need to update the DB with status 1 After this
							 * we need to update park table with the new data After this I need to go to DB
							 * and pull the current requests And then finish.
							 * 
							 */
							parkPendingRData data = getTableView().getItems().get(getIndex());
							StringBuffer sb2 = new StringBuffer();
							sb2.append("updateParkChangesWhenPressedApprove ");
							sb2.append("" + data.getParkName());
							sb2.append(" ");
							sb2.append(data.getMaxVisitors());
							sb2.append(" ");
							sb2.append(data.getGap());
							sb2.append(" ");
							sb2.append(data.getMaxDuration());
							String s2 = sb2.toString();
							ClientUI.employeeController.goAndChangeParkSettingsInDB(s2);

							StringBuffer sb1 = new StringBuffer();
							sb1.append("updateParkChangeRequestStatus ");
							sb1.append("" + data.getRequestID());
							String s = sb1.toString();
							ClientUI.employeeController.goAndUpdateRequestStatusInDB(s);
							// after this commands request status with RequestID is updated to be 1

							StringBuffer sb = new StringBuffer();
							sb.append("getParkSettingsRequestsFromDB ");
							String s1 = sb.toString();
							ClientUI.userController.goToDbForDepManagerRequest(s1);

							RequestTable.getColumns().removeAll(ClientUI.employeeController.parkSettingChangeRequests);
							RequestTable.refresh();
							RequestTable.setItems(ClientUI.employeeController.parkSettingChangeRequests);
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
							parkPendingRData data = getTableView().getItems().get(getIndex());
							StringBuffer sb1 = new StringBuffer();
							sb1.append("updateParkChangeRequestStatus ");
							sb1.append("" + data.getRequestID());
							String s = sb1.toString();
							ClientUI.employeeController.goAndUpdateRequestStatusInDB(s);
							// After this command the DB will update (status of request id will be 1)

							StringBuffer sb = new StringBuffer();
							sb.append("getParkSettingsRequestsFromDB ");
							String s1 = sb.toString();
							ClientUI.userController.goToDbForDepManagerRequest(s1);
							// After this command the observable array in employeeController will be updated

							RequestTable.getColumns().removeAll(ClientUI.employeeController.parkSettingChangeRequests);
							RequestTable.refresh();
							RequestTable.setItems(ClientUI.employeeController.parkSettingChangeRequests);
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
}
