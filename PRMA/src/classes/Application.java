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
import java.util.Scanner;

import javax.xml.transform.TransformerException;

import Gui.CalendarDisplay;
import xmlparser_generic.XMLParser;

public class Application {
	
	public static ArrayList<Employee> resources;
	public static ArrayList<Project> projects;
	public static XMLParser parser;
	
	public static int iteration = 0;
	
	public static int startingTime;
	public static int endingTime;
	
	public static void main(String [] args) throws TransformerException{
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter the starting time of the scheduling in a day: ");
		startingTime = in.nextInt();//Get the starting hour of scheduling
		
		System.out.println("Enter the finish time of the scheduling in a day: ");
		endingTime = in.nextInt();//Get the ending hour of scheduling
		
		//Read projects from xml and parse
		parser = new XMLParser("src/xmlparser_generic/");
		projects = parser.ReadProjectXml("tasks.xml");
		
		//Read resources from xml and parse
		resources = parser.ReadEmployeeXml("employee-ability.xml");
		
		ArrayList<Task> tasks = new ArrayList<Task>();//Get all tasks into one ArrayList
		for(int i=0;i<projects.size(); i++){
			tasks.addAll(projects.get(i).getTasks());
		}
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH);//Create date format for parsing
		
		double a = 1.1;
		ArrayList<Task> assignedTasks = new ArrayList<Task>();
		int treshHold = 1000;
		int iteration = 1;
		ArrayList<Integer> unfinishedProjects = new ArrayList<Integer>();
		boolean continued = true;
		while(continued){
			a-=0.1;//Change a value in each iteration
			if(a<0)
				a=0;
			Date now = new Date();
			String dateToRead = "";
			for(int z =0;z<args.length;z++){
				dateToRead += args[z];
				dateToRead += " ";
			}
			
			try {
				now = format.parse(dateToRead);//Read date from arguments
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(unfinishedProjects.size()!=0)//Print iteration number
				System.out.println("iteration: "+(iteration+1));
			else
				System.out.println("iteration: "+iteration);
			ArrayList<TaskSortingObject> sortedTasks = sortTasks(tasks, a, now);//Sorts tasks

			while(!sortedTasks.isEmpty()){//Scheduling starts here
				
				if(iteration<treshHold){//Stops if treshold is exceeded
					TaskSortingObject currentSortingObject = sortedTasks.get(0);
					Task currentTask = currentSortingObject.task;//Get the first one from the list
						
					sortedTasks.remove(0);//Delete first one
					
					ArrayList<EmployeeSortingObject> bestMatchList = findBestMatches(0, currentTask, now);//Get best match list with respect to current task
					
					if(bestMatchList.size()==0){//If there are no available resource schedule for the next hour
						
						now = getNextWorkHour(now);
						sortedTasks.add(0, currentSortingObject);
							
					}else{//If there is at least one resource
						double realTaskTime = 0;
						
						int indexx = -1;
						for(int ll=0;ll<unfinishedProjects.size();ll++){
							if(unfinishedProjects.get(ll)==currentTask.getBelongsTo().getId()){
								indexx = ll;
							}
						}
						
						int iterator = iteration;//Calculate iteration number
						if(indexx!=-1)
							iterator = iteration+1;
						int iterated = 0;

						for(int i = 0;i<iterator&&i<bestMatchList.size(); i++){//Calculate real task time
							iterated++;
							Employee currentEmployee = bestMatchList.get(i).employee;
							double abilityDivided = 0;

							for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
								double abilityUnder = currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
								double abilityOver = currentTask.getNeededAbilities().get(k).level;
								abilityDivided += Math.sqrt(abilityOver)/Math.sqrt(abilityUnder);
							}
							abilityDivided /= currentTask.getNeededAbilities().size();
							
							realTaskTime += abilityDivided*currentTask.getTaskDuration()*Math.sqrt((17.0/currentEmployee.getDepreciationLevel()));
						}
						
						realTaskTime /= iterated;
						realTaskTime /= iterated;
						Task taskToAdd = new Task(realTaskTime, now, currentTask.getBelongsTo(), currentTask.getTaskName());//Create the new task to add to the schedule
						
						
						for(int i = 0;i<iterator&&i<bestMatchList.size(); i++){//Assign task to resource
							Employee currentEmployee = bestMatchList.get(0).employee;
							bestMatchList.remove(0);

							int index = -1;
							for(int j = 0;j<resources.size();j++){
								if(resources.get(j).getId() == currentEmployee.getId()){
									index = j;
									j = resources.size();
								}
							}
							resources.get(index).addTask(taskToAdd);	
							
						}
						
						assignedTasks.add(taskToAdd);//Add assigned task to assigned task list
					}
				}
			}
			//Check the completion time of all the projects
			if(unfinishedProjects.size()!=0){
				unfinishedProjects.clear();
				iteration++;
			}
			continued = false;
			for(int i=0; i<projects.size();i++){
				Project currentProject = projects.get(i);
				boolean projectCompletedInTime = true;
				Date projectEndDate = new Date();
				try {
					projectEndDate = format.parse(dateToRead);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(int k=0;k<currentProject.getTasks().size();k++){//Check each task of each project
					for(int e = 0; e<assignedTasks.size(); e++){
						if(currentProject.getTasks().get(k).getTaskName().compareTo(assignedTasks.get(e).getTaskName())==0){
							
							if(projectEndDate.compareTo(assignedTasks.get(e).getTaskEndDate())<0){
								projectEndDate = assignedTasks.get(e).getTaskEndDate();
							}
							
							if(projectEndDate.compareTo(currentProject.getProjectDueDate())>0){

								projectCompletedInTime = false;
								e = assignedTasks.size();
								k = currentProject.getTasks().size();
								unfinishedProjects.add(currentProject.getId());
							}
						}
					}
				}
				//Print completion time of projects and whether they are completed or not
				System.out.println("projectEndDate of "+currentProject.getId()+" is: "+projectEndDate + " projectDueDate: "+currentProject.getProjectDueDate());
				System.out.println("projectCompletedInTime: "+projectCompletedInTime);

			}
			if(unfinishedProjects.size()!=0){//If all the projects are not completed

				if(iteration<=resources.size()&&iteration<=11)//Generate another schedule
					continued = true;
				else{//If the scheduling cannot be any better stop application
					continued = false;
					System.out.println("All the projects cannot be completed in time. Application stopped.");
				}
				
				for(int e = 0; e<resources.size();e++){//Clear schedules of resources
					resources.get(e).mySchedule.clear();
				}
				assignedTasks.clear();
				
			}
			
		}
		CalendarDisplay calll = new CalendarDisplay(resources);
	}
		
		
	
	public static ArrayList<TaskSortingObject> sortTasks (ArrayList<Task> tasks, double a, Date now){//Creates task queue
		
		ArrayList<TaskSortingObject> queue = new ArrayList<TaskSortingObject>();
		for(int i = 0; i<tasks.size();i++){
			Task currentTask = tasks.get(i);
				
			double taskImportance = a*(currentTask.getImportance()+currentTask.getPriority())+(1.0-a)*(48.0*(60.0 * 60.0 * 1000.0)/(currentTask.getDueDate().getTime()-now.getTime()));
				
			TaskSortingObject newSortingObject = new TaskSortingObject();
			newSortingObject.importance = taskImportance;
			newSortingObject.task = currentTask;
			
			queue.add(newSortingObject);
		}
		Collections.sort(queue, new Comparator<TaskSortingObject>() {
	        @Override
	        public int compare(TaskSortingObject first, TaskSortingObject second)
	        {
	            return  Double.compare(second.importance, first.importance);
	        }
	    });
		return queue;
	}
	
	public static ArrayList<EmployeeSortingObject> findBestMatches (int C, Task currentTask, Date inputTime){//Creates best match queue
		
		
		ArrayList<EmployeeSortingObject> queue = new ArrayList<EmployeeSortingObject>();
		for(int i = 0; i<resources.size();i++){
			Employee currentEmployee = resources.get(i);
			if(!currentEmployee.isFullAt(inputTime)){
				
				double abilityDivided = 0;
				
				for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
					double abilityUnder = currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
					double abilityOver = currentTask.getNeededAbilities().get(k).level;
					abilityDivided += abilityOver/abilityUnder;
				}
				abilityDivided /= currentTask.getNeededAbilities().size();
				
				double employeeRealTaskTime = abilityDivided*currentTask.getTaskDuration();
				
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
	
	public static Date getNextWorkHour(Date now){
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(now);

		if(now.getHours()>=endingTime){//Jump to next day
			cal.add(Calendar.DAY_OF_MONTH, 1); // jump to next day
		    cal.set(Calendar.HOUR_OF_DAY, startingTime); //set starting hour
		    return cal.getTime(); //now
		}else{//Add one hour
		    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		    return cal.getTime(); //now++
		}

		
	}
}
