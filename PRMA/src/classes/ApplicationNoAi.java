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

public class ApplicationNoAi {
	
	public static ArrayList<Employee> employees;
	public static ArrayList<Project> projects;
	public static ArrayList<Task> tasks;
	public static XMLParser parser;


	public static void main(String[] args) throws TransformerException {
		// TODO Auto-generated method stub
		
		//XML classsı çağır
		parser = new XMLParser("src/xmlparser_generic/");
		projects = parser.ReadProjectXml("tasks.xml");
				
		//XML classsı çağır
		employees = parser.ReadEmployeeXml("employee-ability.xml");
		
		//AI olmadığı için XML okuma işlemi de olmayacak
		//ama sadece melihin inputları okumak için xml gerekecek
		
		tasks = new ArrayList<Task>();
		for(int i=0;i<projects.size(); i++){
			tasks.addAll(projects.get(i).getTasks());
		}
		//burada xmlden direk de doldurulabilir buna gerek kalmayabilir.
		
		DateFormat format = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa", Locale.ENGLISH);
		Date now = new Date();
		/*try {
			now = format.parse(args[0]);//Burada parametre alacak date'i yukarÄ±daki formattaki gibi.
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		ArrayList<Task> assignedTasks = new ArrayList<Task>();
			
		while(!tasks.isEmpty()){
			Task currentTask = tasks.get(0);
			tasks.remove(0);
					
			ArrayList<Employee> matchList = findMatches(currentTask, now);
					
			if(matchList.size()==0){
				now = getNextWorkHour(now);
				tasks.add(0, currentTask);
			}else{
				//Modify et
				double taskTime = currentTask.getTaskDuration();
				for(int i = 0;i<matchList.size(); i++){					//Assign task to the employee
					Employee currentEmployee = matchList.get(0);
					matchList.remove(0);
					int index = -1;
					for(int j = 0;j<employees.size();j++){
						if(employees.get(j).getId() == currentEmployee.getId()){
							index = j;
							j = employees.size();
						}
					}
					//Task taskToAdd = new Task(taskTime, now, currentTask.getBelongsTo(), currentTask.getTaskName());
					double realTaskTime = 0;
					double abilityDivided = 0;
					
					for(int k=0; k<currentTask.getNeededAbilities().size(); k++){
						double abilityUnder = currentEmployee.getAbility(currentTask.getNeededAbilities().get(k).name);
						double abilityOver = currentTask.getNeededAbilities().get(k).level;
						abilityDivided += Math.sqrt(abilityOver)/Math.sqrt(abilityUnder);
					}
					abilityDivided /= currentTask.getNeededAbilities().size();
					realTaskTime += abilityDivided*currentTask.getTaskDuration()*Math.sqrt((17.0/currentEmployee.getDepreciationLevel()));
				
					Task taskToAdd = new Task(realTaskTime, now, currentTask.getBelongsTo(), currentTask.getTaskName());

					employees.get(index).addTask(taskToAdd);
					assignedTasks.add(taskToAdd);
				}
			}		
		}
		for(int j = 0; j<employees.size();j++){
			employees.get(j).printSchedule();
		}
		
		for(int i=0; i<projects.size();i++){
			Project currentProject = projects.get(i);
			boolean projectCompletedInTime = true;
			Date projectEndDate = new Date();
			for(int k=0;k<currentProject.getTasks().size();k++){
				
				for(int e = 0; e<assignedTasks.size(); e++){
					if(currentProject.getTasks().get(k).getTaskName() == assignedTasks.get(e).getTaskName()){
						if(projectEndDate.compareTo(assignedTasks.get(e).getTaskEndDate())<0){
							projectEndDate = assignedTasks.get(e).getTaskEndDate();
						}
						//System.out.println("assignedTasks.get(e).getTaskEndDate(): "+assignedTasks.get(e).getTaskEndDate()+"currentProject.getProjectDueDate()"+currentProject.getProjectDueDate());
						if(assignedTasks.get(e).getTaskEndDate().compareTo(currentProject.getProjectDueDate())>0){
							projectCompletedInTime = false;
							e = assignedTasks.size();
							k = currentProject.getTasks().size();
						}
					}
				}
			}
			System.out.println("projectEndDate of "+currentProject.getId()+" is: "+projectEndDate);
		}
		CalendarDisplay calll = new CalendarDisplay(employees);
	}		
	
	//There is no AI so it just basically finds matches with first matching abilities.
	public static ArrayList<Employee> findMatches (Task currentTask, Date inputTime){
				
		ArrayList<Employee> list = new ArrayList<Employee>();
		ArrayList<Ability> neededAbilities = currentTask.getNeededAbilities();
		
		for(int i = 0; i<employees.size();i++){
			Employee currentEmployee = employees.get(i);
			if(!currentEmployee.isFullAt(inputTime)){
				//currentEmployee.getAbility(currentTask.getNeededAbilities().get(0).name);
				boolean employeeOk = true;
				for(int j = 0; j<neededAbilities.size(); j++){
					if(currentEmployee.getAbility(currentTask.getNeededAbilities().get(j).name)<=1){
						//neededAbilities.remove(j);
						//list.add(currentEmployee);
						employeeOk=false;
						j=neededAbilities.size();
					}
				}
				if(employeeOk){
					list.add(currentEmployee);
				}
			}
		}
		return list;
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
}
