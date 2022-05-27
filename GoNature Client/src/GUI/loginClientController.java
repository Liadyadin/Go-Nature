package GUI;

import com.sun.javafx.css.StyleManager;

import Client.ClientUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class loginClientController {

	@FXML
	private Button BtnConnect;

	@FXML
	private TextField IpLbl;

	@FXML
	private TextField portLbl;

	private double xoffset;
	private double yoffset;

	public void start(Stage primaryStage) throws Exception {
		primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent root = FXMLLoader.load(getClass().getResource("loginClient.fxml"));

//		root.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//            	xoffset = primaryStage.getX() - event.getScreenX();
//            	yoffset = primaryStage.getY() - event.getScreenY();
//            }
//        });
//		
//		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                primaryStage.setX(event.getScreenX() + xoffset);
//                primaryStage.setY(event.getScreenY() + yoffset);
//            }
//        });

		Image icon = new Image(getClass().getResourceAsStream("titleIcon.png"));
		primaryStage.getIcons().add(icon);
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xoffset = primaryStage.getX() - event.getScreenX();
				yoffset = primaryStage.getY() - event.getScreenY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() + xoffset);
				primaryStage.setY(event.getScreenY() + yoffset);
			}
		});
		Scene scene = new Scene(root);
		primaryStage.sizeToScene();
		Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
		StyleManager.getInstance().addUserAgentStylesheet("GUI/css/WelcomeAll.css");
		primaryStage.setTitle("Prototype");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/*
	 * !!!!!!!! If you want to check your screen !!!!!!!! change line 55 to what
	 * screen you want (what fxml file you need) ip="localhost" port= what port you
	 * put in the server
	 */
	@FXML
	void WhenClickConnectBtn(ActionEvent event) throws Exception {
		String s = IpLbl.getText();
		int port = Integer.parseInt(portLbl.getText());
		ClientUI.set(s, port);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("WelcomeAndLoginScreen.fxml"));
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xoffset = stage.getX() - event.getScreenX();
				yoffset = stage.getY() - event.getScreenY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() + xoffset);
				stage.setY(event.getScreenY() + yoffset);
			}
		});

		Scene scene = new Scene(root);
		stage.getScene().getWindow().sizeToScene();
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	void WhenClickExitBtn(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.hide();
	}

}
