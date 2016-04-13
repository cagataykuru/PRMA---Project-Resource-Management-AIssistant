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
	
	public boolean addTask(Task newTask){
		mySchedule.add(newTask);
		/*Collections.sort(mySchedule, new Comparator<Task>() {
	        @Override
	        public int compare(Task first, Task second)
	        {
	            return  first.getTaskStart().compareTo(second.getTaskStart());
	        }
	    });*///Çünkü zaten en son eklenen task en sonda olacak listenin
		return true;
	}
	
	public void removeTask(){
		mySchedule.remove(mySchedule.size()-1);
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
	public boolean isFullAt(Date check){//surayı değiştiricez
		for(int i=0; i<mySchedule.size();i++){
			if(mySchedule.get(i).getTaskStart().compareTo(check) == 0)
				return true;
			else if(mySchedule.get(i).getTaskStart().compareTo(check) == -1){
				if(mySchedule.get(i).getTaskEndDate().compareTo(check)>0&&mySchedule.get(i).getTaskStart().compareTo(check)<0)
					return true;
			}
		}
		return false;
	}
}
