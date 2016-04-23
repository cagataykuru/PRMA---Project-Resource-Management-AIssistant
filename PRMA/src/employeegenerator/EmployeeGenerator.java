package employeegenerator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class EmployeeGenerator {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		PrintWriter writer = new PrintWriter("src/xmlparser_generic/employee-ability.xml", "UTF-8");
		Random randomGenerator = new Random();
		int taskCounter = 1;
		writer.println("<employees>");
		for(int j = 0 ; j < 5; j++)
		{
			writer.println("\t<employee>");
			writer.println("\t\t<employee-id>" + (j+1) + "</employee-id>");
			if(j == 0){
				writer.println("\t\t<firstname>Liam</firstname>");
				writer.println("\t\t<lastname>Miller</lastname>");
			}
			else if(j == 1){
				writer.println("\t\t<firstname>Kyle</firstname>");
				writer.println("\t\t<lastname>Williams</lastname>");
			}
			else if(j == 2){
				writer.println("\t\t<firstname>Owen</firstname>");
				writer.println("\t\t<lastname>Taylor</lastname>");
			}
			else if(j == 3){
				writer.println("\t\t<firstname>Max</firstname>");
				writer.println("\t\t<lastname>Anderson</lastname>");
			}
			else if(j == 4){
				writer.println("\t\t<firstname>Oliver</firstname>");
				writer.println("\t\t<lastname>Thompson</lastname>");
			}
			writer.println("\t\t<depreciation>" + 17.0 + "</depreciation>");
			
			int rand = randomGenerator.nextInt(2);
			if(rand == 0)
				writer.println("\t\t<workaholism>true</workaholism>");
			else
				writer.println("\t\t<workaholism>false</workaholism>");
			
			writer.println("\t\t<abilities>\n\t\t\t"
							+	"<A>" + (randomGenerator.nextInt(10)+1) + "</A>\n\t\t\t"
							+	"<B>" + (randomGenerator.nextInt(10)+1) + "</B>\n\t\t\t"
							+	"<C>" + (randomGenerator.nextInt(10)+1) + "</C>\n\t\t\t"
							+ 	"<D>" + (randomGenerator.nextInt(10)+1) + "</D>\n\t\t\t"
							+	"<E>" + (randomGenerator.nextInt(10)+1) + "</E>\n\t\t\t"
							+	"<F>" + (randomGenerator.nextInt(10)+1) + "</F>\n\t\t\t"
							+	"<G>" + (randomGenerator.nextInt(10)+1) + "</G>\n\t\t\t"
							+	"<H>" + (randomGenerator.nextInt(10)+1) + "</H>\n"
							+ "\t\t</abilities>");
			writer.println("\t</employee>");
		}
		writer.println("</employees>");
		writer.close();
	}

}
