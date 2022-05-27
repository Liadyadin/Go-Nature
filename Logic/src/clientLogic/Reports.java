/** Description of SignUpScreenController 
* @author Omri Cohen
* 
* @version final Jan 2, 2021.
*/

package clientLogic;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

/**
 * This is a class for reports.
 * 
 * this class will store data required to produce reports
 */
public class Reports {

	int numOfVisitors;
	String type;
	LocalTime entrance;
	LocalTime exit;
	long Stay;
	LocalDate date;
	

	/**
	 * Description of Reports.
	 * 
	 * @param num      is numOfVisitors.
	 * @param type     is the type.
	 * @param entrance is entrance time.
	 * @param exit     is exit time
	 * @param date     is visit date
	 * 
	 * 
	 */

	public Reports(String num, String type, String entrance, String exit, String date) {
		this.numOfVisitors = Integer.valueOf(num);
		this.type = type;
		this.entrance = LocalTime.parse(entrance);
		this.exit = LocalTime.parse(exit);
		this.Stay = Duration.between(this.entrance, this.exit).toMinutes();
		this.date = LocalDate.parse(date);
	}

	/**
	 * Description of getEntranceTime()
	 * 
	 * @return float - this function returns entrance time to the park as a float.
	 */
	public Float getEntranceTime() {
		int hours, minutes;
		float res;
		String[] hourMin = entrance.toString().split(":");
		hours = Integer.parseInt(hourMin[0]);
		minutes = Integer.parseInt(hourMin[1]);
		res = (minutes > 0 ? (float) (hours + minutes / 100.0) : (float) hours);
		return res;

	}

	/**
	 * Description of getNumOfVisit()
	 * 
	 * @return int - this function returns how many visitors has entered the park.
	 */
	public int getNumOfVisit() {
		return numOfVisitors;
	}

	/**
	 * Description of getStay()
	 * 
	 * @return long - this function returns how much time visitor was in the park.
	 */
	public long getStay() {
		return Stay;
	}

	/**
	 * Description of getDate()
	 * 
	 * @return int - this function returns the day the visit happened.
	 */
	public int getDate() {
		return this.date.getDayOfMonth();
	}

	/**
	 * Description of getType()
	 * 
	 * @return String - this function returns the type of the visitor.
	 */
	public String getType() {
		return this.type;
	}

	// Create Comparator to sort charts by day
	public static final Comparator<Reports> dayOfMonth = new Comparator<Reports>() {
		public int compare(Reports r1, Reports r2) {
			return Integer.compare(r1.getDate(), r2.getDate());
		}
	};

	public void syso() {
		System.out.println("numOfVisitors: "+ numOfVisitors+" type: " + type+" enterTime: " +entrance + " exitTime: " + exit +" DateOfVisit: "+ date);
	}

}
