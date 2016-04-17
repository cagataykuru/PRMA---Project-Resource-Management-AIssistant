package xmlparser;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import xmlparser_generic.*;

public class ParseXml {

  public static void main(String argv[]) {

    try {

	File fXmlFile = new File("src/xmlparser_generic/task-project.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
	
	//XMLParser parser = new XMLParser();
			
	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();

	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
	NodeList nList = doc.getElementsByTagName("project");
			
	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
				
		System.out.println("\nCurrent Element " + temp + " :" + nNode.getNodeName());
				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			
			NodeList tasklist = doc.getElementsByTagName("task");
			
			for(int i = 0; i < tasklist.getLength(); i++){
				
				Node task = tasklist.item(i);
				System.out.println("\nCurrent Sub Element " + i +  " of Element " + temp + task.getNodeName());
				
				Element eElement = (Element) task;

				System.out.println("Task id : " + eElement.getElementsByTagName("id").item(0).getTextContent());
				System.out.println("Task Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
				System.out.println("Task Duration : " + eElement.getElementsByTagName("duration").item(0).getTextContent());
				System.out.println("StartDate : " + eElement.getElementsByTagName("startDate").item(0).getTextContent());				
				
			}
			
			

		}
	}
    } catch (Exception e) {
	e.printStackTrace();
    }
  }

}