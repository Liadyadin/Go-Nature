package Entities;

public class Person {
	private String firstName;
	private String LastName;
	private String email;
	private String type;
	private String phoneNumber;
	private String id = null;
	private String creditCardNumber = null;
	private int numberOfVisitors = 0;
	private String memberID = null;

	public Person(String firstName, String LastName, String email, String type, String phoneNumber) {
		this.firstName = firstName;
		this.LastName = LastName;
		this.email = email;
		this.type = type;
		this.phoneNumber = phoneNumber;

	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public int getNumberOfVisitors() {
		return numberOfVisitors;
	}

	public void setNumberOfVisitors(int numberOfVisitors) {
		this.numberOfVisitors = numberOfVisitors;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getPhone() {
		return phoneNumber;
	}

	public void setPhone(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getType() {
		return type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setType(String string) {
		this.type = string;

	}

}
