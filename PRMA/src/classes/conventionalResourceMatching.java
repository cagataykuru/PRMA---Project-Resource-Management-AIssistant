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

import javax.xml.transform.TransformerException;

import Gui.CalendarDisplay;
import xmlparser_generic.XMLParser;

public class conventionalResourceMatching {
	
	public static ArrayList<Employee> employees;
	public static ArrayList<Project> projects;
	public static XMLParser parser;
	
	public static int iteration = 0;
	
	public static void main(String [] args) throws TransformerException{
		
		//XML classsı çağır
		parser = new XMLParser("src/xmlparser_generic/");
		projects = parser.ReadProjectXml("tasks.xml");
		
		//XML classsı çağır
		employees = parser.ReadEmployeeXml("employee-ability.xml");
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		//System.out.println("Number of projects: "+projects.size());
		for(int i=0;i<projects.size(); i++){
			//System.out.println(projects.get(i).getId());
			tasks.addAll(projects.get(i).getTasks());
		}
		//for(int i = 0; i<tasks.size();i++){
			//System.out.println("taskName: "+tasks.get(i).getTaskName()+"taskDuration: "+tasks.get(i).getTaskDuration());
		//}
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH);
		
		double a = 1.1;
		ArrayList<Task> assignedTasks = new ArrayList<Task>();
		int treshHold = 1000;
		int iteration = 1;
		ArrayList<Integer> unfinishedProjects = new ArrayList<Integer>();
		boolean continued = true;
		while(continued){
			a-=0.1;
			if(a<0)
				a=0;
			Date now = new Date();
			String dateToRead = "";
			for(int z =0;z<args.length;z++){//Get current date from command line arguments
				dateToRead += args[z];
				dateToRead += " ";
			}
			
			try {
				now = format.parse(dateToRead);//Burada parametre alacak date'i yukarÄ±daki formattaki gibi.
			} catch (ParseException e) {//Å�u anki tarih girilecek
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("iteration: "+iteration);
			//ArrayList<TaskSortingObject> sortedTasks = sortTasks(tasks, a, now);//Sorts tasks
			ArrayList<Task>tasksToAssign = new ArrayList<Task>();
			tasksToAssign.addAll(tasks);

			while(!tasksToAssign.isEmpty()){//Scheduling starts here
				
				if(iteration<treshHold){//Without workhaolism
					//TaskSortingObject currentSortingObject = sortedTasks.get(0);
					//Task currentTask = currentSortingObject.task;//Get the first one from the list
					
					Task currentTask = tasksToAssign.get(0);//Get the first one from the list
						
					tasksToAssign.remove(0);//Delete first one
					
					ArrayList<EmployeeSortingObject> bestMatchList = findBestMatches(0, currentTask, now);//Get best match list with respect to current task
					
					if(bestMatchList.size()==0){//Time'Ä± arttÄ±rma kÄ±smÄ±
						
						now = getNextWorkHour(now);
						tasksToAssign.add(0, currentTask);
							
					}else{
						double realTaskTime = 0;
						
						int indexx = -1;
						for(int ll=0;ll<unfinishedProjects.size();ll++){
							if(unfinishedProjects.get(ll)==currentTask.getBelongsTo().getId()){
								indexx = ll;
							}
						}
						
						int iterator = iteration;
						if(indexx!=-1)
							iterator = iteration+1;
						int iterated = 0;

						for(int i = 0;i<iterator&&i<bestMatchList.size(); i++){//Real task time i hesapla
							iterated++;
							Employee currentEmployee = bestMatchList.get(i).employee;
							double abilityDivided = 0;
							//double abilityUnder = 0;
							for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
								double abilityUnder = currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
								double abilityOver = currentTask.getNeededAbilities().get(k).level;
								abilityDivided += Math.sqrt(abilityOver)/Math.sqrt(abilityUnder);
							}
							abilityDivided /= currentTask.getNeededAbilities().size();
							
							realTaskTime += abilityDivided*currentTask.getTaskDuration()*Math.sqrt((17.0/currentEmployee.getDepreciationLevel()));
						}
						/*if(iterated>1){
							System.out.println("iterated: "+iterated+"belongsTo: "+currentTask.getBelongsTo().getId());
						}*/
						
						realTaskTime /= iterated;
						realTaskTime /= Math.sqrt(iterated);
						if(currentTask.getTaskName()=="Task31")
							System.out.println("task: "+currentTask.getTaskName()+" realTaskTime: "+realTaskTime);
						Task taskToAdd = new Task(realTaskTime, now, currentTask.getBelongsTo(), currentTask.getTaskName());
						
						
						for(int i = 0;i<iterator&&i<bestMatchList.size(); i++){//TaskÄ± employeeye ata
							Employee currentEmployee = bestMatchList.get(0).employee;
							bestMatchList.remove(0);

							int index = -1;
							for(int j = 0;j<employees.size();j++){
								if(employees.get(j).getId() == currentEmployee.getId()){
									index = j;
									j = employees.size();
								}
							}
							employees.get(index).addTask(taskToAdd);	
							
						}
						
						assignedTasks.add(taskToAdd);//EklenmiÅŸ taskÄ± assignedTasks'a koy
					}
				}else{//With workhaolism
					//TaskSortingObject currentSortingObject = sortedTasks.get(0);
					//Task currentTask = currentSortingObject.task;//Get the first one from the list
					
					Task currentTask = tasksToAssign.get(0);//Get the first one from the list
						
					tasksToAssign.remove(0);//Delete first one
					ArrayList<EmployeeSortingObject> bestMatchList;
					boolean workhaolismNow = workhaolismTime(now);
					if(workhaolismNow)
						bestMatchList = findBestMatchesWithWorkhaolism(0, currentTask, now);//Get best match list with respect to current task
					else
						bestMatchList = findBestMatches(0, currentTask, now);//Get best match list with respect to current task
					
					if(bestMatchList.size()==0){//Time'Ä± arttÄ±rma kÄ±smÄ±
						
						now = getNextHour(now);
						tasksToAssign.add(0, currentTask);
						continue;
							
					}else{
						double realTaskTime = 0;

						int indexx = -1;
						for(int ll=0;ll<unfinishedProjects.size();ll++){
							if(unfinishedProjects.get(ll)==currentTask.getBelongsTo().getId()){
								indexx = ll;
							}
						}
						
						int iterator = iteration;
						if(indexx!=-1)
							iterator = iteration+1;
						int iterated = 0;
						for(int i = 0;i<iterator&&i<bestMatchList.size(); i++){//Real task time i hesapla
							if(iterator>bestMatchList.size()){
								System.out.println("here!!!!");
							}
							iterated++;
							Employee currentEmployee = bestMatchList.get(i).employee;
							double abilityDivided = 0;
							for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
								double abilityUnder = currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
								double abilityOver = currentTask.getNeededAbilities().get(k).level;
								abilityDivided += Math.sqrt(abilityOver)/Math.sqrt(abilityUnder);
							}
							abilityDivided /= currentTask.getNeededAbilities().size();
							
							//realTaskTime += abilityDivided*currentTask.getTaskDuration()*Math.sqrt((17.0/currentEmployee.getDepreciationLevel()));
							realTaskTime += abilityDivided*currentTask.getTaskDuration();
						}
						realTaskTime /= iterated;
						realTaskTime /= iterated;
						Task taskToAdd = new Task(realTaskTime, now, currentTask.getBelongsTo(), currentTask.getTaskName());
						taskToAdd.setWorkhaolism(workhaolismNow);
						
						for(int i = 0;i<iterator&&i<bestMatchList.size(); i++){//Add task to employee(s)
							Employee currentEmployee = bestMatchList.get(0).employee;
							bestMatchList.remove(0);
							
							int index = -1;
							for(int j = 0;j<employees.size();j++){
								if(employees.get(j).getId() == currentEmployee.getId()){
									index = j;
									j = employees.size();
								}
							}
							employees.get(index).addTask(taskToAdd);	
							
						}
						//System.out.println("taskToAdd: "+taskToAdd.getTaskStart());
						assignedTasks.add(taskToAdd);
					}
				}
			}
			//Burada tÃ¼m projeler zamanÄ±nda bitiyor mu onu kontrol et
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
				for(int k=0;k<currentProject.getTasks().size();k++){
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
				System.out.println("projectEndDate of "+currentProject.getId()+" is: "+projectEndDate + "projectDueDate: "+currentProject.getProjectDueDate());
				System.out.println("projectCompletedInTime: "+projectCompletedInTime);

			}
			if(unfinishedProjects.size()!=0){//TÃ¼m projeler bitmiyorsa
				if(iteration<=employees.size())
					continued = true;
				else{
					continued = false;
					System.out.println("All the projects cannot be completed in time. Application stopped.");
				}
					
				
				for(int e = 0; e<employees.size();e++){
					employees.get(e).mySchedule.clear();
				}
				assignedTasks.clear();
				
			}
			
		}
		CalendarDisplay calll = new CalendarDisplay(employees);
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
		for(int i = 0; i<employees.size();i++){
			Employee currentEmployee = employees.get(i);
			if(!currentEmployee.isFullAt(inputTime)){
				
				double abilityDivided = 0;
				
				for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
					double abilityUnder = currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
					double abilityOver = currentTask.getNeededAbilities().get(k).level;
					abilityDivided += Math.sqrt(abilityOver)/Math.sqrt(abilityUnder);
				}
				abilityDivided /= currentTask.getNeededAbilities().size();
				
				
				double differenceFromExpectance = Math.abs(abilityDivided*currentTask.getTaskDuration()*Math.sqrt((17.0/currentEmployee.getDepreciationLevel()))-currentTask.getTaskDuration());
				
				EmployeeSortingObject newSortingObject = new EmployeeSortingObject();
				newSortingObject.score = differenceFromExpectance;
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
		
		//int abilityOver = (int) Math.pow(10,currentTask.getNeededAbilities().size());
		
		ArrayList<EmployeeSortingObject> queue = new ArrayList<EmployeeSortingObject>();
		for(int i = 0; i<employees.size();i++){
			Employee currentEmployee = employees.get(i);
			if(currentEmployee.isWorkaholism()&&!currentEmployee.isFullAt(inputTime)){
				
				double abilityDivided = 0;
				//double abilityUnder = 0;
				for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
					double abilityUnder = currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
					double abilityOver = currentTask.getNeededAbilities().get(k).level;
					abilityDivided += Math.sqrt(abilityOver)/Math.sqrt(abilityUnder);
				}
				abilityDivided /= currentTask.getNeededAbilities().size();
				
				int abilityUnder = 0;
				for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
					abilityUnder *= currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
				}
				
				//double employeeRealTaskTime = abilityDivided*currentTask.getTaskDuration()*Math.sqrt((17.0/currentEmployee.getDepreciationLevel()));
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