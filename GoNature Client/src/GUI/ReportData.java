package GUI;

import javafx.beans.property.SimpleStringProperty;

public class ReportData {
	private SimpleStringProperty Individuals;
	private SimpleStringProperty Members;
	private SimpleStringProperty Groups;
	private SimpleStringProperty Total;

	public ReportData(String Individuals, String Members, String Groups, String Total) {
		this.Individuals = new SimpleStringProperty(Individuals);
		this.Members = new SimpleStringProperty(Members);
		this.Groups = new SimpleStringProperty(Groups);
		this.Total = new SimpleStringProperty(Total);
	}

	public String getIndividuals() {
		return Individuals.get();
	}

	public void setIndividuals(String individuals) {
		Individuals = new SimpleStringProperty(individuals);
	}

	public String getMembers() {
		return Members.get();
	}

	public void setMembers(String members) {
		Members = new SimpleStringProperty(members);
	}

	public String getGroups() {
		return Groups.get();
	}

	public void setGroups(String groups) {
		Groups = new SimpleStringProperty(groups);
	}

	public String getTotal() {
		return Total.get();
	}

	public void setTotal(String total) {
		Total = new SimpleStringProperty(total);
	}
}
