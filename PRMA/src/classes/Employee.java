package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Employee {
	private boolean Workaholism;
	private ArrayList<Ability> abilities;
	private double DepreciationLevel;
	//private Schedule mySchedule;
	private String name;
	private String lastName;
	private int id;
	public ArrayList<Task> mySchedule;
	
	public Employee(ArrayList<Ability> abilities, boolean Workhaolism, double DepreciationLevel, String name, String lastname, int id){
		this.setWorkaholism(Workhaolism);
		this.name = name;
		Collections.copy(this.abilities, abilities);
		this.DepreciationLevel = DepreciationLevel;
		this.lastName = lastname;
		this.id = id;
	}
	/*public Employee(ArrayList<Ability> abilities, boolean Workhaolism){
		this.setWorkaholism(Workhaolism);
		Collections.copy(this.abilities, abilities);
	}*/
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
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
	
	public void addTask(Task task){		//Yeniden task olu�turmaya gerek yok
		double increaseInAbilities = task.getTaskDuration()/1000;//Buradaki 1000 katsayısı değiştirilebilir
		for(int i=0;i<task.getNeededAbilities().size();i++){
			this.increaseAbility(task.getNeededAbilities().get(i).name, increaseInAbilities);
		}
		for(int i=0;i<abilities.size();i++){
			this.decreaseAbility(abilities.get(i).name, task.getTaskDuration()/100000);//Bu katsayı değişebilir
		}
		//return mySchedule.addTask(task);
		mySchedule.add(task);
	}
	
	public void removeTask(){
		double decreaseInAbilities = mySchedule.get(mySchedule.size()-1).getTaskDuration()/1000;//Buradaki 1000 katsayısı değiştirilebilir
		for(int i=0;i<mySchedule.get(mySchedule.size()-1).getNeededAbilities().size();i++){
			this.decreaseAbility(mySchedule.get(mySchedule.size()-1).getNeededAbilities().get(i).name, decreaseInAbilities);
		}
		for(int i=0;i<abilities.size();i++){
			this.increaseAbility(abilities.get(i).name, mySchedule.get(mySchedule.size()-1).getTaskDuration()/100000);//Bu katsayı değişebilir
		}
	}
	/*public Task iterateOverSchedule(){
		return mySchedule.iterateOverSchedule();
	}*/
	/*public void resetIterator(){
		mySchedule.resetIterator();
	}*/
	public boolean isWorkaholism() {
		return Workaholism;
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
