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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
	
	private static String directory,fullPathProject;
	private static int taskIdCount = 0;
	
	
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
			Element taskid = doc.createElement("id");
			Element taskName = doc.createElement("name");
			Element taskDuration = doc.createElement("duration");
			Element taskStartDate = doc.createElement("startDate");
			Element abilitiesNeeded = doc.createElement("abilities");
			Element ability = doc.createElement("ability");
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
				
				taskid.appendChild(doc.createTextNode(String.valueOf(eachTask.getTaskName())));
				task.appendChild(taskid);
				
				taskName.appendChild(doc.createTextNode(String.valueOf(getTaskIdCount())));
				task.appendChild(taskName);
				
				taskDuration.appendChild(doc.createTextNode(String.valueOf(eachTask.getTaskDuration())));
				task.appendChild(taskDuration);
				
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
				setTaskIdCount(getTaskIdCount()+1);
			}
			
			Element projectPriority = doc.createElement("priority");
			projectPriority.appendChild(doc.createTextNode(String.valueOf(prj.getPriority())));
			project.appendChild(projectPriority);
			
			Element projectStartDate = doc.createElement("projectStartDate");
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
	
	public static void ReadProjectXml(String fileName, Project prj) throws TransformerException{
		fullPathProject = directory.concat("/");
		fullPathProject = directory.concat(fileName);
		
		try {

			File fXmlFile = new File(fullPathProject);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
								
			doc.getDocumentElement().normalize();
					
			NodeList nList = doc.getElementsByTagName("project");
					
			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node projectNode = nList.item(temp);
				Element element = (Element) projectNode;
				String prjid = element.getElementsByTagName("project-id").item(0).getTextContent();
				int projectId = Integer.parseInt(prjid);
				String prjPriority = element.getElementsByTagName("duration").item(0).getTextContent();
				double projectPriority = Double.parseDouble(prjPriority);
				ArrayList<Task> tasks = new ArrayList<Task>();
				
				Project project = new Project(projectId, tasks, projectPriority);
						
				System.out.println("\nCurrent Element " + temp + " :" + projectNode.getNodeName());
						
				if (projectNode.getNodeType() == Node.ELEMENT_NODE) {

					NodeList tasklist = doc.getElementsByTagName("task");
					
					for(int i = 0; i < tasklist.getLength(); i++){
						
						Node task = tasklist.item(i);
						Element eElement = (Element) task;
						System.out.println("\nCurrent Sub Element " + i +  " of Element " + temp + task.getNodeName());
						
						if(eElement.getElementsByTagName("belongsToProjectWithId").item(0).getTextContent().equalsIgnoreCase(String.valueOf(temp))){
							String taskid = eElement.getElementsByTagName("id").item(0).getTextContent();
							String taskDuration = eElement.getElementsByTagName("duration").item(0).getTextContent();
							String taskName = eElement.getElementsByTagName("name").item(0).getTextContent();
							Task newTask = new Task(Integer.parseInt(taskid), Double.parseDouble(taskDuration), project, taskName);
							tasks.add(newTask);
						}
					}
					
				}
				project.setTasks(tasks);
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
	}

	public String getDirectory() {
		return directory;
	}

	public String getFullPath() {
		return fullPathProject;
	}

	public static int getTaskIdCount() {
		return taskIdCount;
	}

	public static void setTaskIdCount(int taskIdCount) {
		XMLParser.taskIdCount = taskIdCount;
	}

	
	
	

}
