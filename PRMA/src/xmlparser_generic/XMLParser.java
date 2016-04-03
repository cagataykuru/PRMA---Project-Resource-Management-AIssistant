package xmlparser_generic;


import classes.*;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import org.w3c.dom.Attr; IF YOU WANT ATTRIBUTES............
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLParser {
	
	private static String directory,fullPathProject;
	
	
	public XMLParser(String directory){
		XMLParser.directory = directory;
	}
	
	public void writeToXML(String projectFileName, Project project, String EmployeeFileName){
		try {
			writeToProjectXML(projectFileName, project);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeToProjectXML(String fileName, Project prj) throws TransformerException{
		fullPathProject = directory.concat("/");
		fullPathProject = directory.concat(fileName);
		
		ArrayList<Task> allTasks = new ArrayList<Task>();
		allTasks = prj.getTasks();
		
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			//initiate task attributes.
			Element taskDuration = doc.createElement("duration");
			Element taskStartDate = doc.createElement("startDate");
			Element abilitiesNeeded = doc.createElement("abilities");
			Element ability = doc.createElement("ability");
			// needed prereq Attr taskDuration = doc.createAttribute("id");
			Element minKnowledge = doc.createElement("knowledge");
			Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			// root elements
			Element project = doc.createElement("project");
			doc.appendChild(project);

			// all the tasks
			Element tasks = doc.createElement("tasks");
			project.appendChild(tasks);
			
			for(Task eachTask: allTasks){
				//task elements
				Element task = doc.createElement("task");
				
				taskDuration.appendChild(doc.createTextNode(String.valueOf(eachTask.getTaskDuration())));
				task.appendChild(taskDuration);
				
				minKnowledge.appendChild(doc.createTextNode(String.valueOf(eachTask.getMinKnowledge())));
				task.appendChild(minKnowledge);
				
				taskStartDate.appendChild(doc.createTextNode(formatter.format(eachTask.getTaskStart())));
				task.appendChild(taskStartDate);
				
				task.appendChild(abilitiesNeeded);
				ArrayList<Ability> abilities = eachTask.getNeededAbilities();
				for(Ability eachAbility: abilities){
					//ability.appendChild(doc.createTextNode(eachAbility.name));
					String s = "";
					abilitiesNeeded.appendChild(doc.createTextNode(s));
				}
				tasks.appendChild(task);
			}
			
			Element projectPriority = doc.createElement("priority");
			projectPriority.appendChild(doc.createTextNode(String.valueOf(prj.getPriority())));
			project.appendChild(projectPriority);
			
			Element projectStartDate = doc.createElement("startDate");
			projectStartDate.appendChild(doc.createTextNode(formatter.format(prj.getProjectStart())));
			project.appendChild(projectStartDate);
			
			Element projectDueDate = doc.createElement("dueDate");
			projectDueDate.appendChild(doc.createTextNode(formatter.format(prj.getProjectDueDate())));
			project.appendChild(projectDueDate);
			

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("src/xmlparser_generic/fileToWrite.xml"));


			transformer.transform(source, result);

			System.out.println("Project file is saved!");
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDirectory() {
		return directory;
	}

	public String getFullPath() {
		return fullPathProject;
	}

	
	
	

}
