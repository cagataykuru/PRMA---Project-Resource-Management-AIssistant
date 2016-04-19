package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Project {
	private int id;
	private Date projectStart;
	private Date projectDueDate;
	private ArrayList<Task> tasks;
	private Double priority;	
		
	public Project(int id, ArrayList<Task> tasks, Date projectDueDate, Date projectStart, Double priority){
		this.id = id;
		this.setProjectDueDate(projectDueDate);
		this.setProjectStart(projectStart);
		this.priority = priority;
		Collections.copy(this.tasks, tasks);
	}
	
	public Project(int id, ArrayList<Task> tasks,Double priority){
		this.id = id;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
