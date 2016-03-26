package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Project {
	
	private Date projectStart;
	private double projectDuration;
	private ArrayList<Task> tasks;
	
	//new variables will be added, just a draft
	
	public Project(ArrayList<Task> tasks, double projectDuration, Date projectStart){
		this.setProjectDuration(projectDuration);
		this.setProjectStart(projectStart);
		Collections.copy(this.tasks, tasks);
	}

	public Date getProjectStart() {
		return projectStart;
	}

	public void setProjectStart(Date projectStart) {
		this.projectStart = projectStart;
	}

	public double getProjectDuration() {
		return projectDuration;
	}

	public void setProjectDuration(double projectDuration) {
		this.projectDuration = projectDuration;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}
	
	
}
