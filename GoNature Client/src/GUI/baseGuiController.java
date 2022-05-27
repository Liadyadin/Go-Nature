package GUI;

import Client.ClientController;
import Client.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class baseGuiController {
	private ClientController cl;
	public boolean waitresponse = false;
	@FXML
	private Button SubButton;

	@FXML
	private Button connectivity;

	@FXML
	private TextField newEmail;

	@FXML
	private Button updateVisitor;

	@FXML
	private TextField findID;
	@FXML
	private Label WelcomeLBL;

	@FXML
	private TextField firstDISP;

	@FXML
	private TextField phoneDISP;

	@FXML
	private TextField lastDISP;

	@FXML
	private TextField emailDISP;

	@FXML
	public Label thePortIsLBL = null;

	@FXML
	private Label theIPISLBL = null;
	@FXML
	private Button ExitBtn;

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("baseGUI.fxml"));
		Image icon = new Image(getClass().getResourceAsStream("titleIcon.png"));
		primaryStage.getIcons().add(icon);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("baseGui.css").toExternalForm());
		primaryStage.setTitle("Prototyeeee");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/*
	 * This method will return the host and the ip. If there is no connection there
	 * will be printed a "no connection error"
	 * 
	 * 
	 */
	@FXML
	void connectivity(ActionEvent event) {
		String s = "connectivity";

		ClientUI.aFrame = this;
		ClientUI.chat.accept(s);
	}

	@FXML
	void submitVisitor(ActionEvent event) {
		StringBuffer sb = new StringBuffer();
		/*
		 * Appending to the massege that will be send in the first line what type of
		 * action will need to commit in server
		 */
		sb.append("submitVisitor");
		ClientUI.aFrame = this;
		String id = findID.getText();
		if (id.equals(""))
			this.findID.setText("EnterID");
		else {
			sb.append(" ");
			sb.append(id);
			String res = sb.toString();
			ClientUI.chat.accept(res);
		}

	}

	public void GetRepondId(String[] msg) {
		// this methods called after getting the data back from the server.
		if (msg == null) {
			this.findID.setText("InvalidID");

		} else {
			this.firstDISP.setText(msg[0]);
			this.lastDISP.setText(msg[1]);
			this.emailDISP.setText(msg[3]);
			this.phoneDISP.setText(msg[4]);
			waitresponse = true;
		}

	}

	@FXML
	void updateVisitor(ActionEvent event) {
		if (!(waitresponse))
			newEmail.setText("First enter id");
		else {
			StringBuffer sb = new StringBuffer();

			String email = newEmail.getText();
			String[] check = email.split("@");
			if (check.length == 1) {
				Alert a = new Alert(AlertType.NONE, "You must enter valid email");
				a.setAlertType(AlertType.ERROR);
				a.show();
			} else {
				sb.append("updateVisitor");
				sb.append(" ");
				sb.append(findID.getText());
				sb.append(" ");
				sb.append(email);
				String res = sb.toString();
				ClientUI.chat.accept(res);
			}

		}
	}

	public void displayConnection(String[] result) {
		System.out.println(result[0]);
		String s = "The Port Is : " + result[0];
		System.out.println(s);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				thePortIsLBL.setVisible(true);
				theIPISLBL.setVisible(true);
				thePortIsLBL.setText("The port is: " + result[0]);
				System.out.println(result[1]);
				theIPISLBL.setText("The host is: " + result[1]);

			}
		});

	}

	@FXML
	void WhenClickExitBtn(ActionEvent event) {

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.hide();
		String s = "exit";
		ClientUI.chat.accept(s);
	}

}
