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
	
	public ArrayList<Project> ReadProjectXml(String fileName) throws TransformerException{
		fullPathProject = directory.concat("/");
		fullPathProject = directory.concat(fileName);
		
		ArrayList <Project> projects = new ArrayList<Project>();
		
		try {

			File fXmlFile = new File(fullPathProject);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
								
			doc.getDocumentElement().normalize();
					
			NodeList nList = doc.getElementsByTagName("project");
					
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node projectNode = nList.item(temp);
				Element element = (Element) projectNode;
				String prjid = element.getElementsByTagName("project-id").item(0).getTextContent();
				int projectId = Integer.parseInt(prjid);
				String prjPriority = element.getElementsByTagName("priority").item(0).getTextContent();
				double projectPriority = Double.parseDouble(prjPriority);
				ArrayList<Task> tasks = new ArrayList<Task>();
				
				Project project = new Project(projectId, tasks, projectPriority);
												
				if (projectNode.getNodeType() == Node.ELEMENT_NODE) {

					NodeList tasklist = doc.getElementsByTagName("task");
					
					for(int i = 0; i < tasklist.getLength(); i++){
						
						Node task = tasklist.item(i);
						Element eElement = (Element) task;
						
						if(eElement.getElementsByTagName("belongsToProjectWithId").item(0).getTextContent().equalsIgnoreCase(String.valueOf(temp))){
							String taskid = eElement.getElementsByTagName("task-id").item(0).getTextContent();
							String taskDuration = eElement.getElementsByTagName("duration").item(0).getTextContent();
							String taskName = eElement.getElementsByTagName("name").item(0).getTextContent();
							
							ArrayList<Ability> abilities = new ArrayList<Ability>();
							
							
							String As = element.getElementsByTagName("A").item(0).getTextContent();
							String Bs = element.getElementsByTagName("B").item(0).getTextContent();
							String Cs = element.getElementsByTagName("C").item(0).getTextContent();
							String Ds = element.getElementsByTagName("D").item(0).getTextContent();
							String Es = element.getElementsByTagName("E").item(0).getTextContent();
							String Fs = element.getElementsByTagName("F").item(0).getTextContent();
							String Gs = element.getElementsByTagName("G").item(0).getTextContent();
							String Hs = element.getElementsByTagName("H").item(0).getTextContent();
							
							double Ax = Double.parseDouble(As);
							double Bx = Double.parseDouble(Bs);
							double Cx = Double.parseDouble(Cs);
							double Dx = Double.parseDouble(Ds);
							double Ex = Double.parseDouble(Es);
							double Fx = Double.parseDouble(Fs);
							double Gx = Double.parseDouble(Gs);
							double Hx = Double.parseDouble(Hs);
							
							Ability A = new Ability("A", Ax);
							abilities.add(A);
							
							Ability B = new Ability("B", Bx);
							abilities.add(B);
							
							Ability C = new Ability("C", Cx);
							abilities.add(C);
							
							Ability D = new Ability("D", Dx);
							abilities.add(D);
							
							Ability E = new Ability("E", Ex);
							abilities.add(E);
							
							Ability F = new Ability("F", Fx);
							abilities.add(F);
							
							Ability G = new Ability("G", Gx);
							abilities.add(G);
							
							Ability H = new Ability("H", Hx);
							abilities.add(H);
							
							/*
							int counter = 0;
							for(Ability a: abilities){
								if(a.level <= 0.0){
									abilities.remove(counter);
								}
								counter++;
							}
							*/
							
							Task newTask = new Task(Integer.parseInt(taskid), Double.parseDouble(taskDuration), project, taskName);
							newTask.setNeededAbilities(abilities);
							tasks.add(newTask);
						}
					}
					
				}
				project.setTasks(tasks);
				projects.add(project);
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		return projects;
		
	}
	
	
	//Bu employeeler için modify edilecek
	public ArrayList<Employee> ReadEmployeeXml(String fileName) throws TransformerException{
		fullPathProject = directory.concat("/");
		fullPathProject = directory.concat(fileName);
		
		ArrayList <Employee> employees = new ArrayList<Employee>();
		
		try {

			File fXmlFile = new File(fullPathProject);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
								
			doc.getDocumentElement().normalize();
					
			NodeList nList = doc.getElementsByTagName("employee");
					
			System.out.println("----------------------------");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node employeeNode = nList.item(temp);
				
				Element element = (Element) employeeNode;
				
				String empid = element.getElementsByTagName("employee-id").item(0).getTextContent();
				int empId = Integer.parseInt(empid);
				
				String empWorkaholism = element.getElementsByTagName("workaholism").item(0).getTextContent();
				boolean workaholism = Boolean.getBoolean(empWorkaholism);
				
				String name = element.getElementsByTagName("firstname").item(0).getTextContent();
				String lname = element.getElementsByTagName("lastname").item(0).getTextContent();
				
				String empDepreciation = element.getElementsByTagName("depreciation").item(0).getTextContent();
				double depreciation = Double.parseDouble(empDepreciation);
				
				ArrayList<Ability> abilities = new ArrayList<Ability>();
				
				
				String As = element.getElementsByTagName("A").item(0).getTextContent();
				String Bs = element.getElementsByTagName("B").item(0).getTextContent();
				String Cs = element.getElementsByTagName("C").item(0).getTextContent();
				String Ds = element.getElementsByTagName("D").item(0).getTextContent();
				String Es = element.getElementsByTagName("E").item(0).getTextContent();
				String Fs = element.getElementsByTagName("F").item(0).getTextContent();
				String Gs = element.getElementsByTagName("G").item(0).getTextContent();
				String Hs = element.getElementsByTagName("H").item(0).getTextContent();
				
				double Ax = Double.parseDouble(As);
				double Bx = Double.parseDouble(Bs);
				double Cx = Double.parseDouble(Cs);
				double Dx = Double.parseDouble(Ds);
				double Ex = Double.parseDouble(Es);
				double Fx = Double.parseDouble(Fs);
				double Gx = Double.parseDouble(Gs);
				double Hx = Double.parseDouble(Hs);
				
				Ability A = new Ability("A", Ax);
				abilities.add(A);
				System.out.println(A.level);
				
				Ability B = new Ability("B", Bx);
				abilities.add(B);
				System.out.println(B.level);
				
				Ability C = new Ability("C", Cx);
				abilities.add(C);
				System.out.println(C.level);
				
				Ability D = new Ability("D", Dx);
				abilities.add(D);
				System.out.println(D.level);
				
				Ability E = new Ability("E", Ex);
				abilities.add(E);
				
				Ability F = new Ability("F", Fx);
				abilities.add(F);
				
				Ability G = new Ability("G", Gx);
				abilities.add(G);
				
				Ability H = new Ability("H", Hx);
				abilities.add(H);
				
				Employee employee = new Employee(abilities, workaholism, depreciation, name, lname, empId);
				employees.add(employee);
				}
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		return employees;
		
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
