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
	
	public static int iteration = 0;
	
	public static void main(String [] args){
		System.out.println("hello world");
		//Buraya xmlden okumalar gelcek
		ArrayList<Task> tasks = new ArrayList<Task>();
		//Xml okumalar sonu
		
		
		
		for(int i=0;i<projects.size(); i++){
			tasks.addAll(projects.get(i).getTasks());
		}
		
		DateFormat format = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa", Locale.ENGLISH);
		Date now = new Date();
		try {
			now = format.parse(args[0]);//Burada parametre alacak date'i yukarıdaki formattaki gibi.
		} catch (ParseException e) {//Şu anki tarih girilecek
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<TaskSortingObject> sortedTasks = sortTasks(tasks, 0.5, now);//Sorts tasks
		ArrayList<Task> assignedTasks = new ArrayList<Task>();
		int treshHold = 10;
		int iteration = 0;
		
		boolean continued = true;
		while(continued){
			iteration++;
			while(!sortedTasks.isEmpty()){//Scheduling starts here
				if(iteration<treshHold){//Without workhaolism
					TaskSortingObject currentSortingObject = sortedTasks.get(0);
					Task currentTask = currentSortingObject.task;//Get the first one from the list
						
					sortedTasks.remove(0);//Delete first one
					
					ArrayList<EmployeeSortingObject> bestMatchList = findBestMatches(0, currentTask, now);//Get best match list with respect to current task
					
					if(bestMatchList.size()==0){//Time'ı arttırma kısmı
						
						now = getNextWorkHour(now);
						    sortedTasks.add(0, currentSortingObject);
						    continue;
							
					}else{
							
						double realTaskTime = 0;
						for(int i = 0;i<iteration&&i<bestMatchList.size(); i++){
							Employee currentEmployee = bestMatchList.get(0).employee;
							double abilityUnder = 0;
							for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
								abilityUnder += currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
							}
							int abilityOver = (int) Math.pow(10,currentTask.getNeededAbilities().size());							
							realTaskTime += (abilityOver/abilityUnder)*currentTask.getTaskDuration()*(10.0/currentEmployee.getDepreciationLevel());
						}
						realTaskTime /= iteration;
						Task taskToAdd = new Task(realTaskTime, now, currentTask.getBelongsTo(), currentTask.getTaskName());
						//taskToAdd.setTaskStart(now);
						for(int i = 0;i<iteration&&i<bestMatchList.size(); i++){
							Employee currentEmployee = bestMatchList.get(0).employee;
							bestMatchList.remove(0);
							//int taskTime = (int) Math.ceil(realTaskTime);
							currentEmployee.addTask(taskToAdd);	
							
						}
						//currentTask.setTaskStart(now);
						//currentTask.setTaskDuration(realTaskTime);
						assignedTasks.add(currentTask);
					}
				}else{//With workhaolism
					TaskSortingObject currentSortingObject = sortedTasks.get(0);
					Task currentTask = currentSortingObject.task;//Get the first one from the list
						
					sortedTasks.remove(0);//Delete first one
					ArrayList<EmployeeSortingObject> bestMatchList;
					if(workhaolismTime(now))
						bestMatchList = findBestMatchesWithWorkhaolism(0, currentTask, now);//Get best match list with respect to current task
					else
						bestMatchList = findBestMatches(0, currentTask, now);//Get best match list with respect to current task
					
					if(bestMatchList.size()==0){//Time'ı arttırma kısmı
						
						now = getNextHour(now);
						sortedTasks.add(0, currentSortingObject);
						continue;
							
					}else{
						double realTaskTime = 0;
						for(int i = 0;i<iteration&&i<bestMatchList.size(); i++){
							Employee currentEmployee = bestMatchList.get(0).employee;
							double abilityUnder = 0;
							for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
								abilityUnder += currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
							}
							int abilityOver = (int) Math.pow(10,currentTask.getNeededAbilities().size());							
							realTaskTime += (abilityOver/abilityUnder)*currentTask.getTaskDuration()*(10.0/currentEmployee.getDepreciationLevel());
						}
						realTaskTime /= iteration;
						Task taskToAdd = new Task(realTaskTime, now, currentTask.getBelongsTo(), currentTask.getTaskName());
						//taskToAdd.setTaskStart(now);
						for(int i = 0;i<iteration&&i<bestMatchList.size(); i++){
							Employee currentEmployee = bestMatchList.get(0).employee;
							bestMatchList.remove(0);
							//int taskTime = (int) Math.ceil(realTaskTime);
							currentEmployee.addTask(taskToAdd);	
							
						}
						currentTask.setTaskStart(now);
						currentTask.setTaskDuration(realTaskTime);
						assignedTasks.add(currentTask);
					}
				}
			}
			//Burada tüm projeler zamanında bitiyor mu onu kontrol et
			continued = false;
			for(int i=0; i<projects.size();i++){
				Project currentProject = projects.get(i);
				boolean projectCompletedInTime = false;
				for(int k=0;k<currentProject.getTasks().size();k++){
					for(int e = 0; e<assignedTasks.size(); e++){
						if(currentProject.getTasks().get(k).getTaskName() == assignedTasks.get(e).getTaskName())
							if(assignedTasks.get(e).getTaskEndDate().compareTo(currentProject.getProjectDueDate())>0){
								projectCompletedInTime = false;
								e=assignedTasks.size();
								k = currentProject.getTasks().size();
							}
					}
				}
				if(!projectCompletedInTime){
					continued = true;
					i = projects.size();
				}
			}
			//Tüm projelerin zamanında bitişi kontrolü sonu
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
				
				double employeeRealTaskTime = (abilityOver/abilityUnder)*currentTask.getTaskDuration()*(10.0/currentEmployee.getDepreciationLevel());
				
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
	            return  Double.compare(second.score, first.score);
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
				
				double employeeRealTaskTime = (abilityOver/abilityUnder)*currentTask.getTaskDuration()*(10.0/currentEmployee.getDepreciationLevel());
				
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
	            return  Double.compare(second.score, first.score);
	        }
	    });
		return queue;
	}
	public static Date getNextWorkHour(Date now){
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(now);
		if(now.getDay()==5&&now.getHours()>=17){//Jump to monday
		    cal.add(Calendar.DAY_OF_MONTH, 3); // jumps to monday
		    cal.set(Calendar.HOUR_OF_DAY, 9); //set starting hour
		    return cal.getTime(); //now
		}else if(now.getHours()>=17){//Jump to next day
		    cal.add(Calendar.DAY_OF_MONTH, 1); // jump to next day
		    cal.set(Calendar.HOUR_OF_DAY, 9); //set starting hour
		    return cal.getTime(); //now
		}else{//Add one hour
		    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		    return cal.getTime(); //now++
		}
	}
	public static Date getNextHour(Date now){
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(now);
		if(now.getDay()==5){//Jump to monday
		    cal.add(Calendar.DAY_OF_MONTH, 3); // jumps to monday
		    cal.set(Calendar.HOUR_OF_DAY, 9); //set starting hour
		    return cal.getTime(); //now
		}else if(now.getHours()>=20){//Jump to next day
		    cal.add(Calendar.DAY_OF_MONTH, 1); // jump to next day
		    cal.set(Calendar.HOUR_OF_DAY, 9); //set starting hour
		    return cal.getTime(); //now
		}else{//Add one hour
		    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		    return cal.getTime(); //now++
		}
	}
	public static boolean workhaolismTime(Date now){
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(now);
		if(now.getHours()>=17){
		    return true;
		}else
			return false;
	}
}
