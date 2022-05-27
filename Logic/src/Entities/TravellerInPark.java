
package Entities;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class for save data from TravellerInpark table
 * 
 * @author Liad Yadin
 *
 */
public class TravellerInPark {
	private String Id;
	private int numOfVisitors;
	private LocalTime timeInPark;
	private LocalDate dateOfVisit;
	private String wantedPark;
	private float totalPrice;

	public TravellerInPark(LocalTime time, LocalDate dateOfVisit, String wantedPark, int numberOfVisitors,
			float totalPrice) {
		this.numOfVisitors = numberOfVisitors;
		this.timeInPark = time;
		this.dateOfVisit = dateOfVisit;
		this.wantedPark = wantedPark;
		this.totalPrice = totalPrice;
	}

	public TravellerInPark(int numberOfVisitors, String wantedPark) {
		this.numOfVisitors = numberOfVisitors;
		this.wantedPark = wantedPark;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public int getnumOfVisitors() {
		return numOfVisitors;
	}

	public void setnumOfVisitors(int numberOfVisitors) {
		numOfVisitors = numberOfVisitors;
	}

	public LocalTime gettimeInPark() {
		return timeInPark;
	}

	public void settimeInPark(LocalTime time) {
		timeInPark = time;
	}

	public LocalDate getdateOfVisit() {
		return dateOfVisit;
	}

	public void setdateOfVisit(LocalDate date) {
		dateOfVisit = date;
	}

	public String getwantedPark() {
		return wantedPark;
	}

	public void setwantedPark(String park) {
		wantedPark = park;
	}

	public float gettotalPrice() {
		return totalPrice;
	}

	public void settotalPrice(Float price) {
		totalPrice = price;
	}
}