package taskgenerator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class TaskGenerator {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("tasks.txt", "UTF-8");
		Random randomGenerator = new Random();
		int taskCounter = 1;
		for(int j = 0 ; j < 3; j++)
		{
			writer.println("<project>");
			writer.println("\t<project-id>" + (j + 1) + "</project-id>");
			writer.println("\t<tasks>");
			for(int i = 0; i < 50; i++)
			{
				writer.println("\t\t<task>");
				writer.println("\t\t\t<task-id>"+(taskCounter)+"</task-id>");
				taskCounter++;
				writer.println("\t\t\t<name>" + "Task " + (taskCounter) + "</name>");
				writer.println("\t\t\t<duration>" + ((randomGenerator.nextFloat() * 14) + 1) + "</duration>");
				//writer.println("\t\t\t<knowledge>" + ((randomGenerator.nextFloat() * 9) + 1) + "</knowledge>");
				writer.println("\t\t\t<startDate> </startDate>");
				writer.println("\t\t\t<belongsToProjectWithId>" +(j+1) +  "</belongsToProjectWithId>");
				writer.println("\t\t\t<abilities>\n\t\t\t\t"
								+	"<A>" + randomGenerator.nextInt(11) + "</A>\n\t\t\t\t"
								+	"<B>" + randomGenerator.nextInt(11) + "</B>\n\t\t\t\t"
								+	"<C>" + randomGenerator.nextInt(11) + "</C>\n\t\t\t\t"
								+ 	"<D>" + randomGenerator.nextInt(11) + "</D>\n\t\t\t\t"
								+	"<E>" + randomGenerator.nextInt(11) + "</E>\n\t\t\t\t"
								+	"<F>" + randomGenerator.nextInt(11) + "</F>\n\t\t\t\t"
								+	"<G>" + randomGenerator.nextInt(11) + "</G>\n\t\t\t\t"
								+	"<H>" + randomGenerator.nextInt(11) + "</H>\n"
								+ "\t\t\t</abilities>");
				writer.println("\t\t</task>");
			}
			writer.println("\t</tasks>");
			writer.println("\t<priority>" + (j + 1) + "</priority>");
			writer.println("\t<projectStartDate> </projectStartDate>");
			writer.println("\t<dueDate> </dueDate>");
			writer.println("</project>");
		}
		writer.close();
	}
}
