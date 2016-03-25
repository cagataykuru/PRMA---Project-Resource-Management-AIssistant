package aiProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class Employee {
	private boolean Workhaolism;
	private ArrayList<Ability> abilities;
	private double DepreciationLevel;
	private Schedule mySchedule;
	
	public Employee(ArrayList<Ability> abilities, boolean Workhaolism, double DepreciationLevel){
		this.Workhaolism = Workhaolism;
		Collections.copy(this.abilities, abilities);
		this.DepreciationLevel = DepreciationLevel;
	}
	public Employee(ArrayList<Ability> abilities, boolean Workhaolism){
		this.Workhaolism = Workhaolism;
		Collections.copy(this.abilities, abilities);
	}
	
	public boolean decreaseAbility(String name, double amount){
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
	public boolean increaseAbility(String name, double amount){
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
	
	public double getAbility(String name){
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
	
	public boolean addTask(Date startDate, int realTaskTime, String taskName){
		return mySchedule.addTask(startDate, realTaskTime, taskName);
	}
	public Task iterateOverSchedule(){
		return mySchedule.iterateOverSchedule();
	}
	public void resetIterator(){
		mySchedule.resetIterator();
	}
}
