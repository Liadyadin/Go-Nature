package Entities;

public class departmentEmployee extends Person {
	private String idEmployee;
	private String department;
	private String parkName;
	private String userName;
	private String password;

	public departmentEmployee(String firstName, String LastName, String email, String type, String phoneNumber,
			String id, String dep, String parkName, String un, String pass) {
		super(firstName, LastName, email, type, phoneNumber);
		idEmployee = id;
		department = dep;
		this.parkName = parkName;
		userName = un;
		password = pass;
	}

	public String getID() {
		return this.idEmployee;
	}

	public void setID(String id) {
		this.idEmployee = id;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartmenr(String department) {
		this.department = department;
	}

	public String getParkName() {
		return this.parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return this.getFirstName();
	}
}