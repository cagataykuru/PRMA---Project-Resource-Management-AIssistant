package classes;

import java.util.ArrayList;
import java.util.Date;

public class Task {
	private double taskDuration;
	private Date taskStart;
	private ArrayList<Task> prerequisites;
	private ArrayList<Ability> neededAbilities;
	private Project belongsTo;
	private Double minKnowledge;
	
	public Task(double taskDuration, Date taskStart, Project belongsTo){
		this.taskDuration = taskDuration;
		this.taskStart = taskStart;
		this.setBelongsTo(belongsTo);
	}
	
	public void calculateMinKnowledge(){
		//buraya da bir hesaplama metodu düþünelim, gereken abilityler ile olur.
		
		//neededAbilities listinden eleman seçip katsayýlarla çarpmaca.
	}
	
	void setTaskDuration(double duration){
		taskDuration = duration;
	}
	void setTaskStart(Date start){
		taskStart = start;
	}
	
	double getTaskDuration(){
		return taskDuration;
	}
	Date getTaskStart(){
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

	public void setMinKnowledge(Double minKnowledge) {
		this.minKnowledge = minKnowledge;
	}

	public ArrayList<Ability> getNeededAbilities() {
		return neededAbilities;
	}

	public void setNeededAbilities(ArrayList<Ability> neededAbilities) {
		this.neededAbilities = neededAbilities;
	}
}
