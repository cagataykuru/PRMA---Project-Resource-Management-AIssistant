package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Project {
	
	private Date projectStart;
	private Date projectDueDate;
	private ArrayList<Task> tasks;
	private Double priority;	//emin de�ilim type�ndan
	
	//new variables will be added, just a draft
	
	public Project(ArrayList<Task> tasks, Date projectDueDate, Date projectStart, Double priority){
		this.setProjectDueDate(projectDueDate);
		this.setProjectStart(projectStart);
		this.priority = priority;
		Collections.copy(this.tasks, tasks);
	}

	public Date getProjectStart() {
		return projectStart;
	}

	public void setProjectStart(Date projectStart) {
		this.projectStart = projectStart;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public Date getProjectDueDate() {
		return projectDueDate;
	}

	public void setProjectDueDate(Date projectDueDate) {
		this.projectDueDate = projectDueDate;
	}

	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}
}
