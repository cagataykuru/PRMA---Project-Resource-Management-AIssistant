package classes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Application {
	
	public static ArrayList<Employee> employees;
	public static ArrayList<Project> projects;
	
	public static void main(String [] args){
		System.out.println("hello world");
		//Buraya xmlden okumalar gelcek
		
		//Xml okumalar sonu
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		for(int i=0;i<projects.size(); i++){
			tasks.addAll(projects.get(i).getTasks());
		}
		
		DateFormat format = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa", Locale.ENGLISH);
		Date now = new Date();
		try {
			now = format.parse(args[0]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<TaskSortingObject> sortedTasks = sortTasks(tasks, 0.5, now);
		int treshHold = 10;
		int iteration = 0;
		while(!sortedTasks.isEmpty()){
			if(iteration<treshHold){//Without workhaolism
				TaskSortingObject currentSortingObject = sortedTasks.get(0);
				Task currentTask = currentSortingObject.task;//Get the first one from the list
				
				sortedTasks.remove(0);//Delete first one
				
				ArrayList<EmployeeSortingObject> bestMatchList = findBestMatches(0, currentTask, now);//Get best match list with respect to current task
				
				if(bestMatchList.size()==0){
					Calendar cal = Calendar.getInstance(); // creates calendar
				    cal.setTime(now); // sets calendar time/date
				    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
				    now = cal.getTime(); //now++
				    
				    sortedTasks.add(0, currentSortingObject);
				    continue;
				}else{
					iteration++;
					//Burdan sonrasına devam edilecek
				}
			}
		}
		
	}
	
	public static ArrayList<TaskSortingObject> sortTasks (ArrayList<Task> tasks, double a, Date now){//Creates task queue
		
		ArrayList<TaskSortingObject> queue = new ArrayList<TaskSortingObject>();
		for(int i = 0; i<tasks.size();i++){
			Task currentTask = tasks.get(i);
				
			double taskImportance = a*(currentTask.getImportance())+(1.0-a)*(48.0*(60.0 * 60.0 * 1000.0)/(currentTask.getDueDate().getTime()-now.getTime()));
				
			TaskSortingObject newSortingObject = new TaskSortingObject();
			newSortingObject.importance = taskImportance;
			newSortingObject.task = currentTask;
			
			queue.add(newSortingObject);
		}
		Collections.sort(queue, new Comparator<TaskSortingObject>() {
	        @Override
	        public int compare(TaskSortingObject first, TaskSortingObject second)
	        {
	            return  Double.compare(first.importance, second.importance);
	        }
	    });
		return queue;
	}
	
	public static ArrayList<EmployeeSortingObject> findBestMatches (int C, Task currentTask, Date inputTime){//Creates best match queue
		
		int abilityOver = (int) Math.pow(10,currentTask.getNeededAbilities().size());
		
		ArrayList<EmployeeSortingObject> queue = new ArrayList<EmployeeSortingObject>();
		for(int i = 0; i<employees.size();i++){
			Employee currentEmployee = employees.get(i);
			if(!currentEmployee.getSchedule().isFullAt(inputTime)){
				int abilityUnder = 0;
				for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
					abilityUnder += currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
				}
				
				double employeeRealTaskTime = (abilityOver/abilityUnder)*currentTask.getTaskDuration()*(currentEmployee.getDepreciationLevel()/10.0);
				
				EmployeeSortingObject newSortingObject = new EmployeeSortingObject();
				newSortingObject.score = employeeRealTaskTime;
				newSortingObject.employee = currentEmployee;
				
				queue.add(newSortingObject);
			}
		}
		Collections.sort(queue, new Comparator<EmployeeSortingObject>() {
	        @Override
	        public int compare(EmployeeSortingObject first, EmployeeSortingObject second)
	        {
	            return  Double.compare(first.score, second.score);
	        }
	    });
		return queue;
	}
	
	public static ArrayList<EmployeeSortingObject> findBestMatchesWithWorkhaolism (int C, Task currentTask, Date inputTime){//Creates best match queue with workhaolism
		
		int abilityOver = (int) Math.pow(10,currentTask.getNeededAbilities().size());
		
		ArrayList<EmployeeSortingObject> queue = new ArrayList<EmployeeSortingObject>();
		for(int i = 0; i<employees.size();i++){
			Employee currentEmployee = employees.get(i);
			if(currentEmployee.isWorkaholism()&&!currentEmployee.getSchedule().isFullAt(inputTime)){
				int abilityUnder = 0;
				for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
					abilityUnder += currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
				}
				
				double employeeRealTaskTime = (abilityOver/abilityUnder)*currentTask.getTaskDuration()*(currentEmployee.getDepreciationLevel()/10.0);
				
				EmployeeSortingObject newSortingObject = new EmployeeSortingObject();
				newSortingObject.score = employeeRealTaskTime;
				newSortingObject.employee = currentEmployee;
				
				queue.add(newSortingObject);
			}
		}
		Collections.sort(queue, new Comparator<EmployeeSortingObject>() {
	        @Override
	        public int compare(EmployeeSortingObject first, EmployeeSortingObject second)
	        {
	            return  Double.compare(first.score, second.score);
	        }
	    });
		return queue;
	}
}
