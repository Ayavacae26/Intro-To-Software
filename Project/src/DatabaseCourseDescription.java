import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class DatabaseCourseDescription {
	
	public static void main(String args[]) throws IOException
	{
		FileReader text = new FileReader("CourseDescriptions.txt");
//		BufferedReader scan = new BufferedReader(text);
		Scanner scan = null;
		BufferedWriter outputWriter = null;
		
		boolean writeDescription = false;
		// These Strings are used to check against matches of 
		String regex = "([A-Z]{3}[0-9]{3})";
		String prereq = "(.*)Prerequisite(s):(.*)";
		String credit = "(.*)Credit(.*)";
		
		try 
		{
			scan = new Scanner(text);
			outputWriter = new BufferedWriter(new FileWriter("example.xml"));
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("No such file exists");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		outputWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Courses>");
		outputWriter.newLine();
		
//		StringBuilder courseabbrev = new StringBuilder();  
//		StringBuilder credits = new StringBuilder(); 
//		StringBuilder description = new StringBuilder(); 
//		StringBuilder courseprereq = new StringBuilder(); 
		
//		String test = "ACC221(Introduction to Financial Accounting)";
//		System.out.println(test.matches(regex));
		String storeCredit = null;
		
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			
//			if(line.matches(regex))
//			{
////				System.out.println("course: "+line);
////				courseabbrev = new StringBuilder(line);
//				outputWriter.write("<CourseName id=\"" + line + "\">");
//				outputWriter.newLine();
//				outputWriter.write("<Description>");
//				outputWriter.newLine();
//			}
//			else if (line.matches(credit))
//			{
////				System.out.println("credit: " + line);
////				credits = new StringBuilder(line);
//				storeCredit = line;
//			}
//			else if (line.matches(prereq))
//			{
//				System.out.println("prereq: " + line);
////				courseprereq = new StringBuilder(line);
//				outputWriter.write("</Description>");
//				outputWriter.newLine();
//				outputWriter.write("<Credit>");
//				outputWriter.newLine();
//				outputWriter.write(storeCredit);
//				outputWriter.newLine();
//				outputWriter.write("</Credit>");
//				outputWriter.newLine();
//				outputWriter.write("<Prerequisite>");
//				outputWriter.newLine();
//			}
//			else	// description
//			{
//				outputWriter.write(line);
//				outputWriter.newLine();
//			}
			
			if(line.matches(regex)) 
			{
//				System.out.println("<CourseName id=" + line + ">");
//				System.out.println("<Description>");
				outputWriter.write("<CourseName id=\"" + line + "\">");
				outputWriter.newLine();
				outputWriter.write("<Description>");
				outputWriter.newLine();
				line = scan.nextLine();
				outputWriter.write(line);
				outputWriter.newLine();
			}
					
			else if(line.matches(prereq))
					{
//						System.out.println("</Description>");
//						System.out.println("<Prerequisite>");
						outputWriter.write("</Description>");
						outputWriter.newLine();
						outputWriter.write("<Credit>");
						outputWriter.newLine();
						outputWriter.write(storeCredit);
						outputWriter.newLine();
						outputWriter.write("</Credit>");
						outputWriter.newLine();
						outputWriter.write("<Prerequisite>");
						outputWriter.newLine();
						
						// clean up before writing the prerequisite line 
						line = line.replace("Prerequisite(s): ", "");
//						System.out.println(line);
//						System.out.println("</Prerequisite>");
//						System.out.println("</CourseName>");
						outputWriter.write(line);
						outputWriter.newLine();
						outputWriter.write("</Prerequisite>");
						outputWriter.newLine();
						outputWriter.write("</CourseName>");
						outputWriter.newLine();
						
						// we aren't reading no more course descriptions 
						writeDescription = false;
					}
					else if(line.matches(credit))
					{
						storeCredit = line;
					}
					else	// write the description 
					{
//						System.out.println(line);
						outputWriter.write(line);
						outputWriter.newLine();
					}
				outputWriter.flush();
				
			
		}
	}

}
