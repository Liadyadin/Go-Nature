/**
 *  Description of AlternativDatesScreenController
 * This class responisble for the table showing
 * the alternative dates for picking a visit
 * Creating button to choose a date
 * Creating the option to choose from another time
 * 
 * @author Ilan Alexandrov	

 */

package GUI;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AlternativDatesScreenController implements Initializable {
	/** StartDateLbl - DatePicker for start */
	@FXML
	private DatePicker StartDateLbl;
	/** EndDateLbl - DatePicker for end */
	@FXML
	private DatePicker EndDateLbl;
	/** FromLbl - Combo box of the From Time */
	@FXML
	private ComboBox FromLbl;
	/** UserIdLbl - The Id of the user */
	@FXML
	private Label UserIdLbl;
	/** AlternativeTable - Table */
	@FXML
	private TableView<Data> AlternativeTable;
	/** Date - TableColumn */
	@FXML
	private TableColumn<Data, String> Date;
	/** Park - TableColumn */
	@FXML
	private TableColumn<Data, String> Park;
	/** Time - TableColumn */
	@FXML
	private TableColumn<Data, String> Time;
	/** numOfVisit - TableColumn */
	@FXML
	private TableColumn<Data, String> numOfVisit;
	/** Price - TableColumn */
	@FXML
	private TableColumn<Data, String> Price;
	/** choseOrder - TableColumn */
	@FXML
	private TableColumn<Data, String> choseOrder;
	/** TimeOfVisitCB - CB for the Time */
	@FXML
	private ComboBox TimeOfVisitCB;

	ObservableList<String> listForTimes;

	@FXML
	void WhenClickBack(ActionEvent event) {

	}

	/**
	 * Description of initialize(URL arg0, ResourceBundle arg1) The method will
	 * initialize all of the table rows, this method will insert into the CB all the
	 * values.
	 * 
	 * @return void
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
		Park.setCellValueFactory(new PropertyValueFactory<>("Park"));
		Time.setCellValueFactory(new PropertyValueFactory<>("Time"));
		numOfVisit.setCellValueFactory(new PropertyValueFactory<>("NumOfVisit"));
		Price.setCellValueFactory(new PropertyValueFactory<>("Price"));

		try {
			ClientUI.orderController.getAlternativeDates(ClientUI.orderController.order.getTimeInPark());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SetTimeParkCm();
		AlternativeTable.setItems(ClientUI.orderController.aD);
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
			ClientUI.LogOutUtility.logOutTraveller();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		} else if (result.get() == ButtonType.CANCEL)
			alert.close();
	}

	/**
	 * Description addButtonToTable() This method will add button to every row that
	 * will be in the table this button will be dynamic and will create new order
	 * based on what row you clicked
	 * 
	 * @return void
	 */
	private void addButtonToTable() {
		TableColumn<Data, Void> colBtn = new TableColumn("Choose Order");

		Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<TableColumn<Data, Void>, TableCell<Data, Void>>() {
			@Override
			public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
				final TableCell<Data, Void> cell = new TableCell<Data, Void>() {

					private final Button btn = new Button("Choose");

					{
						btn.setOnAction((ActionEvent event) -> {

							Data data = getTableView().getItems().get(getIndex());
							int orderNum = Integer.parseInt(data.getID());
							System.out.println("The time is:" + data.getTime());
							LocalTime lt = LocalTime.parse(data.getTime());
							LocalDate ld = LocalDate.parse(data.getDate());
							ClientUI.orderController.isInDb = false;
							ClientUI.orderController.order = new Order(orderNum, lt, ld, data.getPark(),
									Integer.parseInt(data.getNumOfVisit()), Float.parseFloat(data.getPrice()));
							Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
							FXMLLoader loader = new FXMLLoader();
							Pane root;
							try {
								root = loader.load(getClass().getResource("/GUI/Confirmation.fxml").openStream());
								ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
								stage = ClientUI.LogOutUtility.getStage();
								root = ClientUI.LogOutUtility.getParent();
								Scene scene = new Scene(root);
								stage.setTitle("Unapproved Order");
								stage.setScene(scene);
								stage.show();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

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

		AlternativeTable.getColumns().add(colBtn);

	}

	/**
	 * Description SetTimeParkCm() This method will create the combo Box and insert
	 * into it the values of the opening of the park
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

	/**
	 * Description of WhenClickBackBtn(ActionEvent event) if don't want to enter the
	 * waiting list, clicking back and redirect to the unapproed order screen
	 * 
	 * @param event
	 * @throws IOException
	 * @return void
	 */

	@FXML
	void WhenClickBackBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/GUI/CencellOrder.fxml").openStream());
		ClientUI.LogOutUtility.makeTheStageDynamic(stage, root);
		stage = ClientUI.LogOutUtility.getStage();
		root = ClientUI.LogOutUtility.getParent();
		Scene scene = new Scene(root);
		stage.setTitle("Cancel order");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Description of whenClickSubmitBtn(ActionEvent event) 1. get the date and time
	 * from the input 2. move the data to a method that in the order controller 3.
	 * send the data to the db and get back all the dates and times in range
	 * entered. 4. initialize counter that will reset every "maxDurationVisit" 5.
	 * counter will count the amount of visitors in park in every "maxDurationVisit"
	 * 6. when reaching new "maxDurationVisit" check if adding the current order
	 * possible 7. if so, create new Data object with the time and date possible 8.
	 * Save it into an ArrayList 9. Initialize this screen again, and feed the Table
	 * with the array.
	 * 
	 * @param event - the event that triggered
	 * @return void
	 */

	@FXML
	void whenClickSubmitBtn(ActionEvent event) {
		String temp = (String) TimeOfVisitCB.getValue();

		String[] res = temp.split(":");
		LocalTime time = LocalTime.of(Integer.parseInt(res[0]), Integer.parseInt(res[1]));
		try {
			ClientUI.orderController.getAlternativeDates(time);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AlternativeTable.getColumns().removeAll(ClientUI.orderController.aD);
		AlternativeTable.refresh();

		AlternativeTable.setItems(ClientUI.orderController.aD);

	}

}
