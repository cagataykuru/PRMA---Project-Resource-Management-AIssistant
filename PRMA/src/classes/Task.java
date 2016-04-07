package classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Task {
	private double taskDuration;
	private Date taskStart;
	private ArrayList<Task> prerequisites;
	private ArrayList<Ability> neededAbilities;
	private Project belongsTo;
	private Double minKnowledge;
	private String taskName;
	private boolean workhaolism;
	
	public Task(double taskDuration, Date taskStart, Project belongsTo, String taskName){
		this.taskDuration = taskDuration;
		this.taskStart = taskStart;
		this.setBelongsTo(belongsTo);
		this.taskName = taskName;
		this.workhaolism = false;
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
	
	public void calculateMinKnowledge(){
		//buraya da bir hesaplama metodu d���nelim, gereken abilityler ile olur.
		
		//neededAbilities listinden eleman se�ip katsay�larla �arpmaca.
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

	public Double getMinKnowledge() {
		return minKnowledge;
	}
	
	public Project getProject() {
		return belongsTo;
	}
	
	public double getImportance() {
		return belongsTo.getPriority();
	}

	public void setMinKnowledge(Double minKnowledge) {
		this.minKnowledge = minKnowledge;
	}

	public ArrayList<Ability> getNeededAbilities() {
		return neededAbilities;
	}

	public void setNeededAbilities(ArrayList<Ability> neededAbilities) {
		this.neededAbilities = neededAbilities;
	}
	public Date getTaskEndDate(){
		Date taskEnding = this.taskStart;
		if(!this.workhaolism){
			for(int i = 0; i<this.taskDuration; i++){
				taskEnding = getNextWorkHour(taskEnding);
			}
		}
		return taskEnding;
	}
	public static Date getNextWorkHour(Date now){
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(now);
		if(now.getDay()==5){//Jump to monday
		    cal.add(Calendar.DAY_OF_MONTH, 2); // jumps to monday
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
}
