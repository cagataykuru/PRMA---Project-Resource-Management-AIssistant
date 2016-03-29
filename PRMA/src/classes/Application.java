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
	
	public ArrayList<EmployeeSortingObject> findBestMatches (int C, Task currentTask, Date inputTime){
		
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
	
	public ArrayList<EmployeeSortingObject> findBestMatchesWithWorkhaolism (int C, Task currentTask, Date inputTime){
		
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
