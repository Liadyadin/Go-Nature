package Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Order {

	private int orderNum;
	private LocalTime timeInPark;
	private LocalDate dateOfVisit;
	private String wantedPark;
	private float totalPrice;
	private int numberOfVisitors;

	public Order(int orderNum, LocalTime time, LocalDate dateOfVisit2, String wantedPark, int numberOfVisitors,
			float totalPrice) {
		this.orderNum = orderNum;
		this.timeInPark = time;
		this.dateOfVisit = dateOfVisit2;
		this.wantedPark = wantedPark;
		this.totalPrice = totalPrice;
		this.numberOfVisitors = numberOfVisitors;
	}

	public Order(int numberOfVisitors, String wantedPark) {
		this.wantedPark = wantedPark;
		this.numberOfVisitors = numberOfVisitors;
	}

	public int getNumberOfVisitors() {
		return numberOfVisitors;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public LocalTime getTimeInPark() {
		return timeInPark;
	}

	public void setTimeInPark(LocalTime timeInPark) {
		this.timeInPark = timeInPark;
	}

	public LocalDate getDateOfVisit() {
		return dateOfVisit;
	}

	public void setDateOfVisit(LocalDate dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}

	public String getWantedPark() {
		return wantedPark;
	}

	public void setWantedPark(String wantedPark) {
		this.wantedPark = wantedPark;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String toString() {
		return ("" + orderNum + "," + timeInPark + "," + dateOfVisit + "," + wantedPark + "," + totalPrice + ","
				+ numberOfVisitors);
	}

}
