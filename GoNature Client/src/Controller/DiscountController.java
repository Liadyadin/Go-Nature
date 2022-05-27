/** Description DiscountController
 * @author Tal
 *
 */
package Controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import Client.ClientUI;
import Entities.Order;
import GUI.parkPendingRData;


public class DiscountController {
	public boolean setManagerDiscount_flag;
	public boolean checkDiscount_flag;// update by ValidDiscount > gotMessage > checkDiscount
	private float discountPrecentage;

	private float finalPriceWithoutDM;
	public final int NumOfParks = 3;
	public boolean WaitingForApprove;
	public parkPendingRData discountWaitingForApproveRow;

	/**
	 * use this method to create a new manager discount in DB //(called by
	 * setDiscountScreenController.whenClickSubmitDiscount())
	 * 
	 * @param startDate  : Start date of discount
	 * @param lastDate   : End date of discount
	 * @param precentage : Discount precentage (0.01 - 0.99)
	 * @param parkName   : The name of the park
	 */
	public void setManagerDiscount(Date startDate, Date lastDate, float precentage, String parkName) {
		/* record discount to db */
		StringBuffer sb = new StringBuffer();
		sb.append("setManagerDiscount");// method name
		sb.append(" ");
		sb.append(startDate.toString());
		sb.append(" ");
		sb.append(lastDate.toString());
		sb.append(" ");
		sb.append(precentage);
		sb.append(" ");
		sb.append(parkName);

		ClientUI.chat.accept(sb.toString());

	}

	/**
	 * check if manager discount is valid
	 * 
	 * @param dateOfVisit
	 * @param parkName
	 */
	public void ValidDiscount(LocalDate dateOfVisit, String parkName) {
		/* check in db for a valid Discount */
		StringBuffer sb = new StringBuffer();
		sb.append("ValidDiscount");// methode name
		sb.append(" ");
		sb.append(parkName);
		sb.append(" ");
		sb.append(dateOfVisit.toString());

		ClientUI.chat.accept(sb.toString());

	}

	/**
	 * use this method to calc finalPrice with managerDiscount/ does not include
	 * Ministry of Tourism discount! ( only manager Discount )
	 * 
	 * @param order
	 * @return Float totalPrice after discount
	 */
	public float calculateFinalPrice(Order order) {
		ValidDiscount(order.getDateOfVisit(), order.getWantedPark());
		/* check for valid parkManager discount */
		if (checkDiscount_flag)// there is a valid manager discount for this order
			return (order.getTotalPrice() * (1 - discountPrecentage)); // return price after manager discount
		else
			return order.getTotalPrice();// return original price without manager discount
	}

	public float getFinalPriceWithoutDM() {
		return finalPriceWithoutDM;
	}

	public void setFinalPrice(float fp) {
		this.finalPriceWithoutDM = fp;

	}

	/**
	 * update checkDiscount_flag && discountPrecentage
	 * 
	 * @param precentage_str
	 */
	public void checkDiscount(String precentage_str) {
		discountPrecentage = Float.valueOf(precentage_str);
		if (discountPrecentage == -1) // invalid
			checkDiscount_flag = false;
		else
			checkDiscount_flag = true;
	}

	/**
	 * set_ManagerDiscount_Flag set true if valid
	 * 
	 * @param str
	 */
	public void set_ManagerDiscount_Flag(String str) {
		if (str.equals("false"))
			setManagerDiscount_flag = false;
		else
			setManagerDiscount_flag = true;
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
		case "setManagerDiscount":
			set_ManagerDiscount_Flag(msg[1]);
			break;

		case "ValidDiscount":
			checkDiscount(msg[1]);
			break;

		case "getTotalPrice":
			updateTotalPrice(msg);
			break;

		case "isDiscountWaitingForApprove":
			setWaitingForApprove(msg[1]);
			break;

		case "getDiscountWaitingForApprove":
			set_discountWaitingForApprove(msg[1], msg[2], msg[3], msg[4], msg[5], msg[6]);

		}
	}

	/**
	 * create discountWaitingForApproveRow for DM
	 * 
	 * @param parkName
	 * @param sentDate
	 * @param sentTime
	 * @param startDate
	 * @param lastDate
	 * @param precentage
	 */
	private void set_discountWaitingForApprove(String parkName, String sentDate, String sentTime, String startDate,
			String lastDate, String precentage) {
		discountWaitingForApproveRow = new parkPendingRData("RequestNum", parkName, sentDate, sentTime, startDate,
				lastDate, precentage, "unimplement");
	}

	/**
	 * setWaitingForApprove - set flag if the discount is waiting for approve
	 * 
	 * @param str
	 */
	private void setWaitingForApprove(String str) {
		if (str.equals("false"))
			WaitingForApprove = false;
		else
			WaitingForApprove = true;
	}

	/*
	 * msg[1] = numOfVisitors msg[2]= ifMember msg[3] = price,
	 * msg[4]=valueDiscount,msg[5]=MemberDisc
	 * 
	 * 1. get the data from the db to a local string 2. check how many visitors are
	 * 3. check the precentage of discount he can get based of his type 4. check if
	 * he a member or not, if yes, add extra discount precantage
	 */

	private void updateTotalPrice(String[] msg) {

		// d[0]=depPrice , d[1] = valueDiscount, d[2] = MemberDiscount
		float totalDiscount1 = 0;
		float toatlDiscount2 = 0;
		float finalPrice;
		if (!(msg[4].equals("-")))
			totalDiscount1 = Float.parseFloat(msg[4]) / 100;
		if (msg[2].equals("True"))
			if (!(msg[5].equals("-")))
				toatlDiscount2= Float.parseFloat(msg[5]) / 100;
		if (totalDiscount1 == 0 && toatlDiscount2 == 0)
			finalPrice = Float.parseFloat(msg[3]) * Integer.parseInt(msg[1])  ;
		else if (totalDiscount1 != 0 && toatlDiscount2 == 0)
			finalPrice = (Float.parseFloat(msg[3]) * Integer.parseInt(msg[1])) * (1 - totalDiscount1);
		else if (totalDiscount1 == 0 && toatlDiscount2 != 0)
			finalPrice = (Float.parseFloat(msg[3]) * Integer.parseInt(msg[1]))  * (1 - toatlDiscount2);
		else 
			finalPrice = (Float.parseFloat(msg[3]) * Integer.parseInt(msg[1])) * (1 - toatlDiscount2) * (1-totalDiscount1);

		finalPriceWithoutDM = finalPrice;
	}

	/*
	 * Method that will calculate the total price for the travveler based on the
	 * stats that are in the Db This method need the type of person, how many
	 * visitors, and if he ordering a futre order or came into the park and ordering
	 * there
	 */
	public void getTotalPrice(String typeOfMember, int numberOfVisitors, String orderKind, String isMember) {

		StringBuffer sb = new StringBuffer();
		sb.append("getTotalPrice");
		sb.append(" ");
		switch (typeOfMember) {

		case "Traveller":
			if (orderKind.equals("FutreOrder"))
				sb.append("OrderdIOF");
			else
				sb.append("PreOrderdIOF");
			break;
		case "PreOrderedTraveller":
			if (orderKind.equals("FutreOrder"))
				sb.append("OrderdIOF");
			else
				sb.append("PreOrderdIOF");
			break;

		case "Family":
			if (orderKind.equals("FutreOrder"))
				sb.append("OrderdIOF");
			else
				sb.append("PreOrderdIOF");
			break;

		case "Group":
			if (orderKind.equals("FutreOrder"))
				sb.append("OrderdG");

			else
				sb.append("PreOrderdG");
			break;
		}
		sb.append(" ");
		sb.append(numberOfVisitors);
		sb.append(" ");
		sb.append(isMember);
		sb.append(" ");

		ClientUI.chat.accept(sb.toString());

	}

	/**
	 * setDiscountStatus - change the discount status in DB according to DM choice
	 * 
	 * @param parkName
	 * @param status
	 */
	public void setDiscountStatus(String parkName, String status) {
		StringBuffer sb = new StringBuffer();
		sb.append("setDiscountStatus");// methode name
		sb.append(" ");
		sb.append(parkName);
		sb.append(" ");
		sb.append(status);
		ClientUI.chat.accept(sb.toString());

	}

}