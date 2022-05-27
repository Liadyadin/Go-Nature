/**
 *  Description of SignUpScreenController 
* @author Omri Cohen
* 

*/
package GUI;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import Client.ClientUI;
import clientLogic.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class ReportEntranceAndStayGraphScreen implements Initializable {
	/**
	 * This is a controller for report of entrance hours and stay duration graphs.
	 * 
	 * @implNote implements Initializable - initialize all predefined data
	 */
	@FXML
	private BarChart<String, Number> chartBarNumbers;

	@FXML
	private CategoryAxis DateX;

	@FXML
	private NumberAxis VisitY;
	@FXML
	private ScatterChart<String, Float> chartBarEntrance;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private PieChart pieChartTraveler;
	@FXML
	private PieChart pieChartMember;
	@FXML
	private PieChart pieChartGroups;

	/*
	 * cnt[1 - 8]: counters for various stay time with 30min gaps cnt1 =0 up to 30
	 * minutes stay cnt8 =210 up to 240 minutes stay
	 */
	int cnt1, cnt2, cnt3, cnt4, cnt5, cnt6, cnt7, cnt8, i;
	boolean barFlag;

	/**
	 * Description of initialize() this function initializes the bar and scatter
	 * charts. at the end initialization for pie chart is activated.
	 * 
	* @param arg0 -argi
	 * @param arg1 - argi2
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		XYChart.Series<String, Number> visitTravel;
		XYChart.Series<String, Number> visitMember;
		XYChart.Series<String, Number> visitGroup;

		XYChart.Series<String, Float> enterTravel;
		XYChart.Series<String, Float> enterMember;
		XYChart.Series<String, Float> enterGroup;
		XYChart.Series<String, Float> spaceholder;
		// how many entered the park
		visitTravel = new XYChart.Series<String, Number>();
		visitMember = new XYChart.Series<String, Number>();
		visitGroup = new XYChart.Series<String, Number>();

		// when did they enter the park
		enterTravel = new XYChart.Series<String, Float>();
		enterMember = new XYChart.Series<String, Float>();
		enterGroup = new XYChart.Series<String, Float>();
		spaceholder = new XYChart.Series<String, Float>();
//initialize legends
		visitTravel.setName("Travellers");
		enterTravel.setName("Travellers");
		visitMember.setName("Members");
		enterMember.setName("Members");
		visitGroup.setName("Groups");
		enterGroup.setName("Groups");
		spaceholder.setName("Travellers");
		// Initialize graphs
		int[] dayzVisitor = new int[32];
		int[] dayzMember = new int[32];
		int[] dayzGroup = new int[32];

		for (i = 1; i <= 31; i++) {
			dayzVisitor[i] = 0;
			dayzMember[i] = 0;
			dayzGroup[i] = 0;

		}

		for (Reports r : ClientUI.reportsController.totalArray) {
			r.syso();
			if (r.getType().equals("traveller")) {
				dayzVisitor[r.getDate()] += r.getNumOfVisit();
				for (i = 0; i < r.getNumOfVisit(); i++) {
					enterTravel.getData().add(new XYChart.Data<>(String.valueOf(r.getDate()), r.getEntranceTime()));
				}
			}
			if (r.getType().equals("Member") || r.getType().equals("Family")) {
				dayzMember[r.getDate()] += r.getNumOfVisit();
				for (i = 0; i < r.getNumOfVisit(); i++) {
					enterMember.getData().add(new XYChart.Data<>(String.valueOf(r.getDate()), r.getEntranceTime()));
				}
			}
			if (r.getType().equals("Group")) {
				dayzGroup[r.getDate()] += r.getNumOfVisit();
				for (i = 0; i < r.getNumOfVisit(); i++) {
					enterGroup.getData().add(new XYChart.Data<>(String.valueOf(r.getDate()), r.getEntranceTime()));
				}
			}
		}

		// Add collected data to the graph
		for (i = 1; i <= 31; i++) {
			if (dayzVisitor[i] > 0 || dayzMember[i] > 0 || dayzGroup[i] > 0) {
				visitTravel.getData().add(new XYChart.Data<>(String.valueOf(i), dayzVisitor[i]));
				visitMember.getData().add(new XYChart.Data<>(String.valueOf(i), dayzMember[i]));
				visitTravel.getData().add(new XYChart.Data<>(String.valueOf(i), dayzGroup[i]));
				spaceholder.getData().add(new XYChart.Data<>(String.valueOf(i), (float) 8));
			}
		}
		spaceholder.getData().addAll(enterTravel.getData());
		chartBarNumbers.getData().addAll(visitTravel, visitMember, visitGroup);
		chartBarEntrance.getData().addAll(spaceholder, enterMember, enterGroup);

		// Initialize pie chart
		countinit();
	}

	/**
	 * Description of countinit() this function initializes the pie charts.
	 * 
	 * @param pieChartTraveler is pie chart for Travellers.
	 * @param pieChartMember   is pie chart for members.
	 * @param pieChartGroups   is pie chart for groups.
	 * @param cnt[1            - 8]: counters for various stay time with 30min gaps
	 * 
	 * @return void - this function initialize the graphs..
	 */
	private void countinit() {
		resetcnts();
		pieChartTraveler.setClockwise(true);
		pieChartMember.setClockwise(true);
		pieChartGroups.setClockwise(true);

		// build pie cart for visitors
		for (Reports r : ClientUI.reportsController.visitors) {
			if (r.getStay() > 0) {
				if (r.getStay() > 30) {
					if (r.getStay() > 60) {
						if (r.getStay() > 90) {
							if (r.getStay() > 120) {
								if (r.getStay() > 150) {
									if (r.getStay() > 180) {
										if (r.getStay() > 210) {
											cnt8++;
										}
										cnt7++;
									}
									cnt6++;
								}
								cnt5++;
							}
							cnt4++;
						}
						cnt3++;
					}
					cnt2++;
				}
				cnt1++;
			}

		}
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("0-0.5h", cnt1), new PieChart.Data("0.5-1h", cnt2), new PieChart.Data("1-1.5h", cnt3),
				new PieChart.Data("1.5-2h", cnt4), new PieChart.Data("2-2.5h", cnt5), new PieChart.Data("1.5-3h", cnt6),
				new PieChart.Data("3-3.5h", cnt7), new PieChart.Data("3.5-4h", cnt8));
		pieChartTraveler.getData().addAll(pieChartData);
		resetcnts();

		// build pie cart for members
		for (Reports r : ClientUI.reportsController.members) {
			if (r.getStay() > 0) {
				if (r.getStay() > 30) {
					if (r.getStay() > 60) {
						if (r.getStay() > 90) {
							if (r.getStay() > 120) {
								if (r.getStay() > 150) {
									if (r.getStay() > 180) {
										if (r.getStay() > 210) {
											cnt8++;
										}
										cnt7++;
									}
									cnt6++;
								}
								cnt5++;
							}
							cnt4++;
						}
						cnt3++;
					}
					cnt2++;
				}
				cnt1++;
			}

		}
		ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList(
				new PieChart.Data("0-0.5h", cnt1), new PieChart.Data("0.5-1h", cnt2), new PieChart.Data("1-1.5h", cnt3),
				new PieChart.Data("1.5-2h", cnt4), new PieChart.Data("2-2.5h", cnt5), new PieChart.Data("1.5-3h", cnt6),
				new PieChart.Data("3-3.5h", cnt7), new PieChart.Data("3.5-4h", cnt8));
		pieChartMember.getData().addAll(pieChartData2);
		resetcnts();

		// build pie cart for groups
		for (Reports r : ClientUI.reportsController.groups) {
			if (r.getStay() > 0) {
				if (r.getStay() > 30) {
					if (r.getStay() > 60) {
						if (r.getStay() > 90) {
							if (r.getStay() > 120) {
								if (r.getStay() > 150) {
									if (r.getStay() > 180) {
										if (r.getStay() > 210) {
											cnt8++;
										}
										cnt7++;
									}
									cnt6++;
								}
								cnt5++;
							}
							cnt4++;
						}
						cnt3++;
					}
					cnt2++;
				}
				cnt1++;
			}

		}
		ObservableList<PieChart.Data> pieChartData3 = FXCollections.observableArrayList(
				new PieChart.Data("0-0.5h", cnt1), new PieChart.Data("0.5-1h", cnt2), new PieChart.Data("1-1.5h", cnt3),
				new PieChart.Data("1.5-2h", cnt4), new PieChart.Data("2-2.5h", cnt5), new PieChart.Data("1.5-3h", cnt6),
				new PieChart.Data("3-3.5h", cnt7), new PieChart.Data("3.5-4h", cnt8));
		pieChartGroups.getData().addAll(pieChartData3);

	}

	/**
	 * Description of resetcnts - aid function for pie charts, this function shall
	 * reset all counters used to build the pie charts.
	 * 
	 * @return void - no returns.
	 */
	private void resetcnts() {
		cnt1 = 0;
		cnt2 = 0;
		cnt3 = 0;
		cnt4 = 0;
		cnt5 = 0;
		cnt6 = 0;
		cnt7 = 0;
		cnt8 = 0;
	}

	@FXML
	/**
	 * Description of stage upon clicking the Exit button the window shall be
	 * closed.
	 * 
	 * @return void - no returns.
	 */
	void whenClickExit(ActionEvent event) {
		ClientUI.reportsController.visitors.clear();
		ClientUI.reportsController.members.clear();
		ClientUI.reportsController.groups.clear();
		ClientUI.reportsController.totalArray.clear();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

}
