import java.io.FileNotFoundException;
<<<<<<< HEAD
import java.io.FileReader;
import java.io.BufferedReader;
=======
import java.io.FileOutputStream;
import java.io.FileReader;
>>>>>>> 095f9c3580ad6f4b4d6218391d38f57f929563b1
import java.io.BufferedWriter;
import java.util.*;
import java.io.IOException;
import java.io.OutputStreamWriter;

/* This program will produce our database, taking in the Major Course Description file. 
 The output will be an XML file, correctly outlining the correct format. Each 
 section of a major will hold a description, credit, and prerequisite. The only 
 thing this file doesn't take care of is ampersands (&). Ampersands are a special 
 character in a ML (mark up language) and so the only manual work for the user other 
 than running this code is to open up this file in a text editor and replace
 all ampersands with 'and'. If ampersands are removed correctly, user can view the 
 database by dragging and dropping the file in a web browser (which will understand
 and execute the code) and display it. */
public class DatabaseCourseDescription {
	
	public static void main(String args[]) throws IOException
	{
<<<<<<< HEAD
		FileReader text = new FileReader("CourseDescriptions.txt");
//		BufferedReader scan = new BufferedReader(text);
		Scanner scan = null;
		BufferedWriter outputWriter = null;
		
		boolean writeDescription = false;
		// These Strings are used to check against matches of 
		String regex = "([A-Z]{3}[0-9]{3})";
		String prereq = "(.*)Prerequisite(s):(.*)";
		String credit = "(.*)Credit(.*)";
=======
		FileReader text = new FileReader("CourseDescriptions.txt"); 
		Scanner scan = null; 
		BufferedWriter outputWriter = null; 
		
		// Regex... find matches of in the file
		String courseabbrev = "([A-Z]{3}[0-9]{3})"; 
		String prereq = "(.*)Prerequisite(.*)"; 
		String credit = "(.*)Credit(.*)"; 
		
		String storeCredit = null; 
		boolean writeDescription = false; 
>>>>>>> 095f9c3580ad6f4b4d6218391d38f57f929563b1
		
		try 
		{
			scan = new Scanner(text);
			outputWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("CourseDescriptions.xml"), "UTF-8")); 
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("No such file exists"); 
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		// First line in the xml file
		outputWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Courses>");
		outputWriter.newLine();
		
<<<<<<< HEAD
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
=======
		// go though entire text file
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
>>>>>>> 095f9c3580ad6f4b4d6218391d38f57f929563b1
			
			if(line.matches(courseabbrev)) 
			{
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
						outputWriter.write(line);
						outputWriter.newLine();
					}
				outputWriter.flush();
				
			
		}
<<<<<<< HEAD
	}
=======
		outputWriter.newLine();
		outputWriter.write("</Courses>");
		outputWriter.flush();
		scan.close();
>>>>>>> 095f9c3580ad6f4b4d6218391d38f57f929563b1

	}
}
