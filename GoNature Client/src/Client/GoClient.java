// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package Client;

import java.io.IOException;

import common.goIF;
import src.ocsf.client.AbstractClient;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class GoClient extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	goIF clientUI;
	public static boolean awaitResponse = false;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public GoClient(String host, int port, goIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		// openConnection();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {
		// System.out.println("--> handleMessageFromServer");
		awaitResponse = false;
		String st = msg.toString();
		String whatController = getAction(st);
		String[] res = DecrypteMassege(st);
		int len = res.length;
		len -= 1;
		String[] toSend = new String[len];

		for (int i = 0; i < len; i++) {
			toSend[i] = res[i + 1];
		}

		/*
		 * in place 0 of res will be the name of the method need to deal with in place
		 * greater then 0 will be all the data for this method in every controller will
		 * be a method that will communicate with the GoClient and transfer the data to
		 * her methods.
		 */
		switch (whatController) {

		case "OrderController":
			try {
				
				
				ClientUI.orderController.gotMessage(res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "EmployeeController":
			ClientUI.employeeController.gotMessage(res[0], toSend);
			break;
		case "UserController":
			ClientUI.userController.gotMessage(res[0], toSend);
			break;
		case "ParkController":
			ClientUI.parkController.gotMessage(res);
			break;
		case "EntranceParkController":
			ClientUI.entranceParkController.gotMesage(res);
			break;
		case "RequestsController":

			ClientUI.requestsController.gotMessage(res);
			break;

		case "DiscountController":
			try {
				ClientUI.discountController.gotMessage(res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "WaitingListController":
			try {
				ClientUI.waitingListController.gotMessage(res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "ChangeIsSababa":
			return;
		case "parkSettingsAreUpdated":
			return;
		case "LoggedOfSuccess":
			return;
		case "SignUpController":
			try {
				ClientUI.signUpController.gotMessage(res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "ReportsController":
			try {
				ClientUI.reportsController.gotMessage(res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "Done":

			break;
		}

	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(String message) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(message);
			if (message.equals("exit"))
				quit();
			// wait for response
			else
				while (awaitResponse) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			/*
			 * 
			 * There may be an exception trhown because there is no connection to the server
			 * This can happen if clicking conectivity when there is no connection This can
			 * happen when click exit whene there is no connecton to the server This can
			 * happen when trying to submit data to the server when there is no connection
			 * to the server
			 * 
			 * 
			 */
		} catch (IOException e) {
			if (!(message.equals("exit"))) {
				ClientUI.aFrame.thePortIsLBL.setText("No connection");
				ClientUI.aFrame.thePortIsLBL.setVisible(true);
				clientUI.display("Could not send message to server: Terminating client." + e);

			}
		}
	}

	public String[] DecrypteMassege(String msg) {
		String[] gotFromServer = msg.split(" ");
		String[] res = new String[gotFromServer.length - 1];
		for (int i = 1; i < gotFromServer.length; i++) {
			res[i - 1] = gotFromServer[i];
		}
		return res;

	}

	public String getAction(String msg) {
		String[] result = msg.split(" ");
		return result[0];
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
// End of ChatClient class
