package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Employee {
	private boolean Workaholism;
	private ArrayList<Ability> abilities;
	private double DepreciationLevel;
	private String name;
	private String lastName;
	private int id;
	public ArrayList<Task> mySchedule;
	
	public Employee(ArrayList<Ability> abilities, boolean Workhaolism, double DepreciationLevel, String name, String lastname, int id){
		this.setWorkaholism(Workhaolism);
		this.name = name;
		this.abilities = abilities;
		this.DepreciationLevel = DepreciationLevel;
		this.lastName = lastname;
		this.id = id;
		mySchedule = new ArrayList<Task>();
	}
	
	public void printSchedule(){
		for(int i=0;i<mySchedule.size();i++){
			System.out.println("emplooye "+this.id+" schedule "+i+"taskName: "+mySchedule.get(i).getTaskName()+": taskStart: " +mySchedule.get(i).getTaskStart()+" taskEnd: "+mySchedule.get(i).getTaskEndDate()+" taskDuration: "+mySchedule.get(i).getTaskDuration()+" taskBelongsTo: "+mySchedule.get(i).getBelongsTo().getId());
		}
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public void setAbilities(ArrayList<Ability> abilities){
		this.abilities = abilities;
	}
	
	public ArrayList<Ability> getAbilities(){
		return abilities;
	}
	
	public void setLastName(String lastname){
		this.lastName = lastname;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public boolean decreaseAbility(Ability.Type name, double amount){
		int indexx = -1;
		for(int i = 0; i<abilities.size(); i++){
			if(abilities.get(i).name==name)
				indexx = i;
		}
		if(indexx==-1)
			return false;
		else{
			abilities.get(indexx).level -= amount; 
		}
		return true;
	}
	public boolean increaseAbility(Ability.Type name, double amount){
		int indexx = -1;
		for(int i = 0; i<abilities.size(); i++){
			if(abilities.get(i).name==name)
				indexx = i;
		}
		if(indexx==-1)
			return false;
		else{
			abilities.get(indexx).level += amount; 
		}
		return true;
	}
	
	public void changeDepreciationLevel(double amount){
		DepreciationLevel += amount;
		if(DepreciationLevel<0)
			DepreciationLevel = 0;
	}
	
	public double getDepreciationLevel(){
		return DepreciationLevel;
	}
	
	public double getAbility(Ability.Type name){
		int indexx = -1;
		for(int i = 0; i<abilities.size(); i++){
			if(abilities.get(i).name==name)
				indexx = i;
		}
		if(indexx==-1)
			return -1;
		else{
			return abilities.get(indexx).level; 
		}
	}
	
	public void addTask(Task task){

		Date taskEnd = task.getTaskEndDate();
		int depreciationDecrease = taskEnd.getHours()%17;
		
		this.changeDepreciationLevel(17-depreciationDecrease);
		mySchedule.add(task);
	}
	
	public void removeTask(){

		Date taskEnd = mySchedule.get(mySchedule.size()-1).getTaskEndDate();
		int depreciationDecrease = taskEnd.getHours()%17;
		
		this.changeDepreciationLevel(17-depreciationDecrease);
	}

	public void setWorkaholism(boolean workaholism) {
		Workaholism = workaholism;
	}
	public ArrayList<Task> getSchedule(){
		return mySchedule;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public boolean isFullAt(Date check){
		for(int i=0; i<mySchedule.size();i++){
			if(mySchedule.get(i).getTaskStart().compareTo(check) == 0)
				return true;
			else if(mySchedule.get(i).getTaskStart().compareTo(check) < 0){
				if(mySchedule.get(i).getTaskEndDate().compareTo(check)>0&&mySchedule.get(i).getTaskStart().compareTo(check)<0)
					return true;
			}
		}
		return false;
	}
}
