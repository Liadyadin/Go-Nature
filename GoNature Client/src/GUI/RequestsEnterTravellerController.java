/**
 * This GUI is presenting for employee department the casual traveler requests
 * of enter park the employee can see all the requests this day and to choose
 * who can enter park by checking if the park is full if the park is full - he
 * will get a message and if the park isn't full - he can choose if he wants to
 * approve the request or to refused it.
 * 
 * @author Liad Yadin
 *
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class RequestsEnterTravellerController implements Initializable {
	/** TableView for the requests */
	@FXML
	private TableView<Data> requestsTravellerTable;
	/** column for ID */
	@FXML
	private TableColumn<Data, String> IDlbl;
	/** column for request time */
	@FXML
	private TableColumn<Data, String> timeLbl;
	/** column for number of visitors */
	@FXML
	private TableColumn<Data, String> numOfVisitLbl;
	/** Lable for date */
	@FXML
	private Label DateLbl;
	/**
	 * boolean variable for check if park is full false- park isn't full true-park
	 * is full
	 */
	private boolean isFull = false;

	/**
	 * initialize all the data in the screen and presenting the table of requests
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientUI.requestsController.ob.clear();
		IDlbl.setCellValueFactory(new PropertyValueFactory<>("ID"));
		timeLbl.setCellValueFactory(new PropertyValueFactory<>("Time"));
		numOfVisitLbl.setCellValueFactory(new PropertyValueFactory<>("NumOfVisit"));
		LocalDate myDate = LocalDate.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
		this.DateLbl.setText(myDate.toString());
		ClientUI.requestsController.getRequestsTravellerOfEnterPark(ClientUI.employeeController.getParkName());
		requestsTravellerTable.setItems(ClientUI.requestsController.ob);
		addButtonToTable();

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

	/**
	 * this method will add button for all row in the table (Action name) for check
	 * availability enter park this button will do number of actions: 1. check if
	 * there is a place in park by "parkisfull" method 2. update full capacity table
	 * for usage report 3. present the right window when the employee check
	 * availability
	 */
	private void addButtonToTable() {
		TableColumn<Data, Void> colBtn = new TableColumn("Action");
		LocalDate myDate = LocalDate.now();
		Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<TableColumn<Data, Void>, TableCell<Data, Void>>() {
			@Override
			public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
				final TableCell<Data, Void> cell = new TableCell<Data, Void>() {

					private final Button btn = new Button("Check availability");

					{

						btn.setOnAction((ActionEvent event) -> {
							Data data = getTableView().getItems().get(getIndex());
							try {
								String ID = data.getID();
								String park = data.getPark();
								int numOfvisit = Integer.parseInt(data.getNumOfVisit());
								int maxUpdateCurrentVisitors;
								maxUpdateCurrentVisitors = ClientUI.parkController.getCurrentVisitors(park)
										+ ClientUI.parkController.getCurrentUnexpectedVisitors(park) + numOfvisit;
								if (!ClientUI.parkController.parkIsFull(park, numOfvisit)) {
									if (!ClientUI.parkController.IfgetDateExistInDB(park)) {
										ClientUI.parkController.enterDateofFullCapcityPark(park, myDate, 0,
												ClientUI.parkController.getMaxVisitors(park), maxUpdateCurrentVisitors);
									} else {
										if (ClientUI.parkController
												.getMaxcurrentVisitorsPerDay(park) < maxUpdateCurrentVisitors)
											ClientUI.parkController.changeMaxcurrentAmountOfVisitorsForCapacityPark(
													park, maxUpdateCurrentVisitors);
									}
									Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
									FXMLLoader loader = new FXMLLoader();
									Pane root;
									root = loader.load(
											getClass().getResource("ApprovingRequestTraveller.fxml").openStream());
									ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
									stage = ClientUI.LogOutUtility.getStage();
									root = ClientUI.LogOutUtility.getParent();
									Scene scene = new Scene(root);
									stage.setTitle("Approve request");
									stage.setScene(scene);
									stage.show();
								} else {
									ClientUI.requestsController.changeStatusForCasualTraveller(0, ID, park);

									if (ClientUI.parkController.getCurrentVisitors(park) // park is full
											+ ClientUI.parkController.getCurrentUnexpectedVisitors(
													park) == ClientUI.parkController.getMaxVisitors(park)) {
										ClientUI.parkController.updateStatusForCapacityParkToFull(park);
									}
									Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
									FXMLLoader loader = new FXMLLoader();
									Pane root;
									root = loader.load(getClass().getResource("parkIsFullEmployee.fxml").openStream());
									ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
									stage = ClientUI.LogOutUtility.getStage();
									root = ClientUI.LogOutUtility.getParent();
									Scene scene = new Scene(root);
									stage.setTitle("park is full");
									stage.setScene(scene);
									stage.show();
								}

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						);
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
		requestsTravellerTable.getColumns().add(colBtn);

	}

	/**
	 * this method will return the previous screen
	 * 
	 * @param event- click previous button
	 * @throws IOException
	 */
	@FXML
	void WhenClickedOnPreviousBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("WelcomeEmployee.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("New order");
		stage.setScene(scene);
		stage.show();
	}
}
