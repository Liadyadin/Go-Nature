/** 
*Description of SignUpController 
* @author Omri Cohen
* @version final Jan 2, 2021.
 */
package Controller;

import java.io.IOException;

import Client.ClientUI;

/**
 * This Class store functions for Sign Up form. firstName - persons first name,
 * lastName - persons last name, phoneNum - persons phone number, email -
 * persons email address, paymentMethod - persons chosen payment method Cash or
 * credit card, memberType - persons type of membership, numOfVisitors - number
 * of visitor on the subscription, checker - flag for marking if an id in
 * already on the DB or not
 */

public class SignUpController {
	public String memberID, id, firstName, lastName, phoneNum, email, paymentMethod, memberType;
	int numOfVisitors;
	public Boolean checker;

	/**
	 * Description of checkExist(String id)
	 *
	 * @param id - person id
	 
	 */
	public void checkExist(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("isMemberExists");
		sb.append(" ");
		sb.append(id);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * Description of init(String id, String firstName, String lastName, String
	 * phoneNum, String email, String paymentMethod, String memberType, int
	 * numOfVisitors)
	 *
	 * @param id            - persons id
	 * @param firstName     - persons first name
	 * @param lastName      - persons last name
	 * @param phoneNum      - persons phone number
	 * @param email         - persons email address
	 * @param paymentMethod - persons chosen payment method Cash or credit card
	 * @param memberType    - persons type of membership
	 * @param numOfVisitors - number of visitor on the subscription
	 * 
	 
	 */
	public void init(String id, String firstName, String lastName, String phoneNum, String email, String paymentMethod,
			String memberType, int numOfVisitors) {
		StringBuffer sb = new StringBuffer();
		sb.append("addMember");
		sb.append(" ");
		sb.append(id);
		sb.append(" ");
		sb.append(firstName);
		sb.append(" ");
		sb.append(lastName);
		sb.append(" ");
		sb.append(phoneNum);
		sb.append(" ");
		sb.append(email);
		sb.append(" ");
		sb.append(paymentMethod);
		sb.append(" ");
		if (memberType == "Traveller") {
			sb.append(memberType);
		}
		if (memberType == "Family Member") {
			sb.append("Family");
		}
		if (memberType == "Group Guide") {
			sb.append("Group");
		}
		sb.append(" ");
		sb.append(numOfVisitors);
		String res = sb.toString();
		ClientUI.chat.accept(res);
	}

	/**
	 * Description of gotMessage(String[] msg)
	 *
	 * @param msg    - String containing information, return from server side with
	 *               function result or additional data.
	 * @param msg[0] - return destination functions name.
	 * 
	 * @throws IOException -From inner methods
	
	 */
	public void gotMessage(String[] msg) throws IOException {
		String cases = msg[0];
		switch (cases) {
		case "isMemberExists":
			if (msg[1] == "false") {
				checker = false;
			} else
				checker = true;
			;
			break;
		case "addMember":
			if (msg[1] == "false") {
				System.out.println("Member was not added");
			} else {
				this.memberID = msg[1];
			}
			break;
		default:
			break;
		}

	}

	/**
	 * Description of getID()
	 *
	 * @return String with members ID number
	 */
	public String getID() {
		return this.memberID;
	}

	/**
	 * Description of getType()
	 *
	 * @return String with members membership type
	 */
	public String getType() {
		return this.memberType;
	}
}