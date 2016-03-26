package classes;

import java.util.Date;

public class Task {
	private double taskDuration;
	private Date taskStart;
	
	public Task(double taskDuration, Date taskStart){
		this.taskDuration = taskDuration;
		this.taskStart = taskStart;
	}
	
	void setTaskDuration(double duration){
		taskDuration = duration;
	}
	void setTaskStart(Date start){
		taskStart = start;
	}
	
	double getTaskDuration(){
		return taskDuration;
	}
	Date getTaskStart(){
		return taskStart;
	}
}
