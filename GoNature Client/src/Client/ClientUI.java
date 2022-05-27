package Client;

import Controller.DiscountController;
import Controller.EmployeeController;
import Controller.EntranceParkController;
import Controller.OrderController;
import Controller.ParkController;
import Controller.ReportsController;
import Controller.RequestsController;
import Controller.SignUpController;
import Controller.UserController;
import Controller.WaitingListController;
import Controller.logOutUtility;
import GUI.EnterParkNowController;
import GUI.OrderScreenController;
import GUI.ReportEntreisScreenController;
import GUI.SignUpScreenController;
import GUI.WelcomeAndLoginController;
import GUI.WelcomeTravellerController;
import GUI.baseGuiController;
import GUI.loginClientController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {

	public static ClientController chat; // only one instance
	public static baseGuiController aFrame;
	public static EntranceParkController entranceParkController;
	public static loginClientController cp;
	public static OrderScreenController oc;
	public static EnterParkNowController ep;
	public static WelcomeTravellerController wt;
	public static WelcomeAndLoginController welcomeController;
	public static WelcomeTravellerController welcomeTraveller;
	public static loginClientController LoginClientController;
	public static OrderScreenController orderScreenController;
	public static OrderController orderController;
	public static ParkController parkController;
	public static EmployeeController employeeController;
	public static UserController userController;
	public static DiscountController discountController;
	public static WaitingListController waitingListController;
	// public static UserController userController;
	public static logOutUtility LogOutUtility;
	// public static UserController userController;
	public static SignUpController signUpController;
	public static SignUpScreenController signUpScreenController;

	public static RequestsController requestsController;
	public static ReportsController reportsController;
	public static ReportEntreisScreenController reportsScreenController;

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	public static void set(String ip, int port) {
		chat = new ClientController(ip, port);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		cp = new loginClientController();
		discountController = new DiscountController();
		waitingListController = new WaitingListController();
		employeeController = new EmployeeController();
		parkController = new ParkController();
		entranceParkController = new EntranceParkController();
		LoginClientController = new loginClientController();
		orderScreenController = new OrderScreenController();
		wt = new WelcomeTravellerController();
		orderController = new OrderController();
		signUpScreenController = new SignUpScreenController();
		signUpController = new SignUpController();
		welcomeController = new WelcomeAndLoginController();
		userController = new UserController();
		LogOutUtility = new logOutUtility();
		requestsController = new RequestsController();

		reportsController = new ReportsController();
		reportsScreenController = new ReportEntreisScreenController();

		cp.start(primaryStage);
	}

}
