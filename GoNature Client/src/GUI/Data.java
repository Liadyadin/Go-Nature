/** Description of Data
 * This class is responislble for keeping the data
 * of the tables (alternative and exisitng orders)
 * 
 * 
 * @author Ilan Alexandrov	
 * @version 1.0 Build December, 2020
 */

package GUI;

import javafx.beans.property.SimpleStringProperty;

public class Data {
	private SimpleStringProperty Date;
	private SimpleStringProperty Park;
	private SimpleStringProperty Time;
	private SimpleStringProperty numOfVisit;
	private SimpleStringProperty Price;
	private SimpleStringProperty ID;
	private SimpleStringProperty Status;
	private SimpleStringProperty Comments;
	private SimpleStringProperty maxVisitors;
	private SimpleStringProperty maxCurrent;

	public Data(String ID, String date, String park, String time, String numOfVisit, String price) {
		this.ID = new SimpleStringProperty(ID);
		this.Date = new SimpleStringProperty(date);
		this.Park = new SimpleStringProperty(park);
		this.Time = new SimpleStringProperty(time);
		this.numOfVisit = new SimpleStringProperty(numOfVisit);
		this.Price = new SimpleStringProperty(price);
	}

	public Data(String ID, String date, String park, String time, String numOfVisit, String price, String Status,
			String Comments) {
		this.ID = new SimpleStringProperty(ID);
		this.Date = new SimpleStringProperty(date);
		this.Park = new SimpleStringProperty(park);
		this.Time = new SimpleStringProperty(time);
		this.numOfVisit = new SimpleStringProperty(numOfVisit);
		this.Price = new SimpleStringProperty(price);
		this.Status = new SimpleStringProperty(Status);
		this.Comments = new SimpleStringProperty(Comments);
	}

	public Data(String ID, String time, String numOfVisit) {
		this.ID = new SimpleStringProperty(ID);
		this.Time = new SimpleStringProperty(time);
		this.numOfVisit = new SimpleStringProperty(numOfVisit);
	}

	public Data(String date) {
		this.Date = new SimpleStringProperty(date);
	}

	public Data(String date, int maxVisitors, int maxCurrent) {
		this.Date = new SimpleStringProperty(date);
		this.maxVisitors = new SimpleStringProperty(String.valueOf(maxVisitors));
		this.maxCurrent = new SimpleStringProperty(String.valueOf(maxCurrent));
	}

	public void setID(String Id) {
		this.ID = new SimpleStringProperty(Id);
	}

	public String getID() {
		return ID.get();
	}

	public void setDate(String date) {
		Date = new SimpleStringProperty(date);
	}

	public String getStatus() {
		return Status.get();
	}

	public void setStatus(String StatusOfOrder) {
		Status = new SimpleStringProperty(StatusOfOrder);
	}

	public String getComments() {
		return Comments.get();
	}

	public void setComments(String CommentsForSt) {
		Comments = new SimpleStringProperty(CommentsForSt);
	}

	public void setPark(String park) {
		Park = new SimpleStringProperty(park);
	}

	public void setTime(String time) {
		Time = new SimpleStringProperty(time);
	}

	public void setNumOfVisit(String numOfVisit) {
		this.numOfVisit = new SimpleStringProperty(numOfVisit);
	}

	public void setPrice(String price) {
		new SimpleStringProperty(price);
	}

	public String getDate() {
		return Date.get();
	}

	public String getPark() {
		return Park.get();
	}

	public String getTime() {
		return Time.get();
	}

	public String getNumOfVisit() {
		return numOfVisit.get();
	}

	public String getPrice() {
		return Price.get();
	}

	public String getMaxCurrent() {
		return maxCurrent.get();
	}

	public String getMaxVisitors() {
		return maxVisitors.get();
	}
}
