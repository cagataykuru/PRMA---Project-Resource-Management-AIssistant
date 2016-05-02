package classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Task {
	private int id;
	private double taskDuration;
	private Date taskStart;
	private ArrayList<Task> prerequisites;
	private ArrayList<Ability> neededAbilities = new ArrayList<Ability>();
	private Project belongsTo;
	private String taskName;
	private boolean workhaolism;
	private int priority;
	
	public Task(double taskDuration, Date taskStart, Project belongsTo, String taskName){
		this.taskDuration = taskDuration;
		this.taskStart = taskStart;
		this.setBelongsTo(belongsTo);
		this.taskName = taskName;
		this.workhaolism = false;
	}
	public Task(int id, double taskDuration,Project belongsTo, String taskName, int priority){
		this.id = id;
		this.taskDuration = taskDuration;
		this.setBelongsTo(belongsTo);
		this.taskName = taskName;
		this.workhaolism = false;
		this.priority = priority;
	}
	
	public void setWorkhaolism(boolean workhaolism){
		this.workhaolism = workhaolism;
	}
	
	public String getTaskName(){
		return taskName;
	}
	
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

	public Date getDueDate(){
		return belongsTo.getProjectDueDate();
	}
	
	void setTaskDuration(double duration){
		taskDuration = duration;
	}
	void setTaskStart(Date start){
		taskStart = start;
	}
	
	public double getTaskDuration(){
		return taskDuration;
	}
	public Date getTaskStart(){
		return taskStart;
	}

	public ArrayList<Task> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(ArrayList<Task> prerequisites) {
		this.prerequisites = prerequisites;
	}

	public Project getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(Project belongsTo) {
		this.belongsTo = belongsTo;
	}

	
	public Project getProject() {
		return belongsTo;
	}
	
	public double getImportance() {
		return belongsTo.getPriority();
	}

	public ArrayList<Ability> getNeededAbilities() {
		return neededAbilities;
	}

	public void setNeededAbilities(ArrayList<Ability> neededAbilities) {
		this.neededAbilities = neededAbilities;
	}
	public Date getTaskEndDate(){
		Date taskEnding = this.taskStart;
		for(int i = 0; i<Math.ceil(this.taskDuration); i++){
				taskEnding = getNextWorkHour(taskEnding);
		}
		return taskEnding;
	}
	/*public static Date getNextWorkHour(Date now){
		Calendar cal = Calendar.getInstance(); // creates calendar
		//System.out.println(now);
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
	}*/
	public static Date getNextWorkHour(Date now){
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(now);
		if(conventionalResourceMatching.startingTime != 0){
			//System.out.println("hereeee");
			if(now.getHours()>=conventionalResourceMatching.endingTime){//Jump to next day
				cal.add(Calendar.DAY_OF_MONTH, 1); // jump to next day
			    cal.set(Calendar.HOUR_OF_DAY, conventionalResourceMatching.startingTime); //set starting hour
			    return cal.getTime(); //now
			}else{//Add one hour
			    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
			    return cal.getTime(); //now++
			}
		}else{
			if(now.getHours()>=Application.endingTime){//Jump to next day
				cal.add(Calendar.DAY_OF_MONTH, 1); // jump to next day
			    cal.set(Calendar.HOUR_OF_DAY, Application.startingTime); //set starting hour
			    return cal.getTime(); //now
			}else{//Add one hour
			    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
			    return cal.getTime(); //now++
			}
		}
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
