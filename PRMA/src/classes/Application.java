package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Application {
	
	public ArrayList<Employee> employees;
	public ArrayList<Project> projects;
	
	public static void main(String [] args){
		System.out.println("hello world");
	}
	
	public ArrayList<TaskSortingObject> sortTasks (ArrayList<Task> tasks, int a, Date now){//Creates task queue
		
		ArrayList<TaskSortingObject> queue = new ArrayList<TaskSortingObject>();
		for(int i = 0; i<tasks.size();i++){
			Task currentTask = tasks.get(i);
				
			double taskImportance = a*(currentTask.getImportance())+(1-a)*(48*(60 * 60 * 1000)/(currentTask.getDueDate().getTime()-now.getTime()));
				
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
	
	public ArrayList<EmployeeSortingObject> findBestMatches (int C, Task currentTask, Date inputTime){//Creates best match queue
		
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
	
	public ArrayList<EmployeeSortingObject> findBestMatchesWithWorkhaolism (int C, Task currentTask, Date inputTime){//Creates best match queue with workhaolism
		
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
