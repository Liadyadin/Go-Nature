package Controller;

import java.io.IOException;

import Client.ClientUI;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class logOutUtility {
	private Stage stage;
	private Pane root;
	private Parent p;
	private double xoffset;
	private double yoffset;

	public void logOutEmployee() {
		ClientUI.employeeController.logOutEmployee(ClientUI.employeeController.getUserName());
		ClientUI.employeeController.setFirstName(null);
		ClientUI.employeeController.setLastName(null);
		ClientUI.employeeController.setType(null);
		ClientUI.employeeController.setParkName(null);

	}

	public Pane getParent() {
		return root;
	}

	public Stage getStage() {
		return stage;
	}

	public Parent getP() {
		return p;
	}

	public void logOutTraveller() {
		ClientUI.userController.setAlreadyLoggedIn(false);
		System.out.println(ClientUI.userController.traveller.getId());
		System.out.println(ClientUI.userController.traveller.getMemberID());
		try {
			if (ClientUI.userController.traveller.getMemberID() != null)
			ClientUI.userController
					.identify("deleteFromDbWhenlogOutTraveller " + ClientUI.userController.traveller.getId() + " "
							+ ClientUI.userController.traveller.getMemberID());
			else
				ClientUI.userController
				.identify("deleteFromDbWhenlogOutTraveller " + ClientUI.userController.traveller.getId() + " "
						+ ClientUI.userController.traveller.getMemberID());

		} catch (IOException e) {
			e.printStackTrace();
		}
		ClientUI.userController.traveller = null;
	}

	public void makeTheStageDynamic(Stage stage, Pane r) {

		r.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xoffset = stage.getX() - event.getScreenX();
				yoffset = stage.getY() - event.getScreenY();
			}
		});
		r.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() + xoffset);
				stage.setY(event.getScreenY() + yoffset);
			}
		});
		this.stage = stage;
		this.root = r;

	}

	public void makeTheStageDynamicForParent(Stage stage, Parent r) {

		r.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xoffset = stage.getX() - event.getScreenX();
				yoffset = stage.getY() - event.getScreenY();
			}
		});
		r.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() + xoffset);
				stage.setY(event.getScreenY() + yoffset);
			}
		});
		this.stage = stage;
		this.p = r;

	}

}
