package Entities;

import java.sql.Date;

public class ManagerDiscount {

	private String parkName, status;
	private Date startDate, lastDate;
	private Float precentage;

	public ManagerDiscount(String parkName, Date startDate, Date lastDate, Float precentage, String status) {
		this.parkName = parkName;
		this.startDate = startDate;
		this.lastDate = lastDate;
		this.precentage = precentage;
		this.status = status;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Float getPrecentage() {
		return precentage;
	}

	public void setPrecentage(Float precentage) {
		this.precentage = precentage;
	}

}
