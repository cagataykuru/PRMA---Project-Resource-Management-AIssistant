package classes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class ApplicationGreedy {
	
	public static ArrayList<Employee> employees;
	public static ArrayList<Project> projects;
	public static ArrayList<Task> tasks;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//AI olmadığı için XML okuma işlemi de olmayacak
		//ama sadece melihin inputları okumak için xml gerekecek
		
		tasks = new ArrayList<Task>();
		for(int i=0;i<projects.size(); i++){
			tasks.addAll(projects.get(i).getTasks());
		}
		//burada xmlden direk de doldurulabilir buna gerek kalmayabilir.
		
		DateFormat format = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa", Locale.ENGLISH);
		Date now = new Date();
		try {
			now = format.parse(args[0]);//Burada parametre alacak date'i yukarÄ±daki formattaki gibi.
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		while(!tasks.isEmpty()){
				Task currentTask = tasks.get(0);
				tasks.remove(0);
				
		}
		
		
	}
	
	//There is no AI so it just basically finds matches
	public static ArrayList<Employee> findMatches (int C, Task currentTask, Date inputTime){
				
		ArrayList<Employee> list = new ArrayList<Employee>();
		ArrayList<Ability> neededAbilities = currentTask.getNeededAbilities();
		
		for(int i = 0; i<employees.size();i++){
			Employee currentEmployee = employees.get(i);
			if(!currentEmployee.isFullAt(inputTime)){
				
				//currentEmployee.getAbility(currentTask.getNeededAbilities().get(0).name);
				for(int j = 0; j<neededAbilities.size(); j++){
					if(currentEmployee.getAbility(currentTask.getNeededAbilities().get(j).name)>0){
						//Burda kaldım
					}
				}
			}
		}
		return list;
	}
}
