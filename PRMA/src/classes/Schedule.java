package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Schedule {
	private ArrayList<Task> mySchedule;
	private int index;
	
	public Schedule(){
		mySchedule = new ArrayList<Task>();
		index = 0;
	}
	
	public boolean addTask(Date startDate, int realTaskTime, String taskName){
		Task newTask = new Task(realTaskTime, startDate);
		mySchedule.add(newTask);
		Collections.sort(mySchedule, new Comparator<Task>() {
	        @Override
	        public int compare(Task first, Task second)
	        {
	            return  first.getTaskStart().compareTo(second.getTaskStart());
	        }
	    });
		return true;
	}
	
	public Task iterateOverSchedule(){
		if(index<mySchedule.size())
			return mySchedule.get(index++);
		else{
			index = 0;
			return null;
		}
	}
	
	public void resetIterator(){
		index = 0;
	}
}
