/**
 * Description  WaitingListController
 * @author 123ta
 *
 */
package Controller;

import java.io.IOException;

import Client.ClientUI;


public class WaitingListController {

	public boolean enterWaitingList_Flag;
	public int cnt_OrdersToConfirm;

	/**
	 * enter order to waitingList
	 * 
	 * @param orderNumber
	 */
	public void enterWaitingList(int orderNumber) {
		StringBuffer sb = new StringBuffer();
		sb.append("enterWaitingList");// method name
		sb.append(" ");
		sb.append(orderNumber);
		ClientUI.chat.accept(sb.toString());
	}

	/**
	 * This method will be the connector from the data came from the server to this
	 * controller
	 * 
	 * @param msg from server
	 * @throws IOException
	 */
	public void gotMessage(String[] msg) throws IOException {
		String cases = msg[0];
		switch (cases) {
		case "enterWaitingList":
			set_enterWaitingList_Flag(msg[1]);
			break;

		}
	}

	/**
	 * set enterWaitingList Flag
	 * 
	 * @param str from server
	 */
	private void set_enterWaitingList_Flag(String str) {
		if (str.equals("false"))
			enterWaitingList_Flag = false;
		else
			enterWaitingList_Flag = true;
	}

	/**
	 * change Order Status in orders table DB
	 * 
	 * @param orderNum
	 * @param status
	 * @param comment
	 */
	public void changeOrderStatus(String orderNum, String status, String comment) {
		StringBuffer sb = new StringBuffer();
		sb.append("changeOrderStatus");// method name
		sb.append(" ");
		sb.append(orderNum);
		sb.append(" ");
		sb.append(status);
		sb.append(" ");
		sb.append(comment);
		ClientUI.chat.accept(sb.toString());

	}

	/**
	 * delete order From WaitingList
	 * 
	 * @param orderNum
	 */
	public void deleteFromWaitingList(String orderNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("deleteFromWaitingList");// method name
		sb.append(" ");
		sb.append(orderNum);
		ClientUI.chat.accept(sb.toString());

	}
}