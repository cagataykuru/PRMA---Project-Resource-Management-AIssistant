package taskgenerator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TaskGenerator {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("src/xmlparser_generic/tasks.xml", "UTF-8");
		Random randomGenerator = new Random();
		int taskCounter = 1;
		ArrayList<Integer> priorityList = new ArrayList<Integer>();
		writer.println("<projects>");
		for(int j = 0 ; j < 10; j++)
		{
			writer.println("\t<project>");
			writer.println("\t\t<project-id>" + (j + 1) + "</project-id>");
			writer.println("\t\t<tasks>");
			for(int i = 0; i < 10; i++){
				priorityList.add(i + 1);
			}
			for(int i = 0; i < 10; i++)
			{
				long seed = System.nanoTime();
				Collections.shuffle(priorityList, new Random(seed));
				int priorityOfTask = priorityList.get(9 - i);
				priorityList.remove(9 - i);
				writer.println("\t\t\t<task>");
				writer.println("\t\t\t\t<task-id>"+(taskCounter)+"</task-id>");
				writer.println("\t\t\t\t<name>" + "Task " + taskCounter + "</name>");
				taskCounter++;
				writer.println("\t\t\t\t<duration>" + ((randomGenerator.nextFloat() * 14) + 1) + "</duration>");
				writer.println("\t\t\t\t<startDate> </startDate>");
				writer.println("\t\t\t\t<belongsToProjectWithId>" +(j+1) +  "</belongsToProjectWithId>");
				writer.println("\t\t\t\t<abilities>\n\t\t\t\t\t"
								+	"<A>" + randomGenerator.nextInt(11) + "</A>\n\t\t\t\t\t"
								+	"<B>" + randomGenerator.nextInt(11) + "</B>\n\t\t\t\t\t"
								+	"<C>" + randomGenerator.nextInt(11) + "</C>\n\t\t\t\t\t"
								+ 	"<D>" + randomGenerator.nextInt(11) + "</D>\n\t\t\t\t\t"
								+	"<E>" + randomGenerator.nextInt(11) + "</E>\n\t\t\t\t\t"
								+	"<F>" + randomGenerator.nextInt(11) + "</F>\n\t\t\t\t\t"
								+	"<G>" + randomGenerator.nextInt(11) + "</G>\n\t\t\t\t\t"
								+	"<H>" + randomGenerator.nextInt(11) + "</H>\n"
								+ "\t\t\t\t</abilities>");
				writer.println("\t\t\t\t<task-priority>" + priorityOfTask +  "</task-priority>");
				writer.println("\t\t\t</task>");
			}
			writer.println("\t\t</tasks>");
			writer.println("\t\t<priority>" + (j + 1) + "</priority>");
			writer.println("\t\t<projectStartDate></projectStartDate>");
			
			String date1 = "2016-04-28 10:00:00.0";
			String date2 = "2016-05-02 09:00:00.0";
			
			if(j == 9)
				writer.println("\t\t<dueDate>" + date1 + "</dueDate>");
			else
				writer.println("\t\t<dueDate>" + date2 + "</dueDate>");
			
			writer.println("\t</project>");
		}
		writer.println("</projects>");
		writer.close();
	}
}
