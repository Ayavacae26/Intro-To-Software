import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
		FileReader text = new FileReader("CourseDescriptions.txt"); 
		Scanner scan = null; 
		BufferedWriter outputWriter = null; 
		
		// Regex... find matches of in the file
		String courseabbrev = "([A-Z]{3}[0-9]{3})"; 
		String prereq = "(.*)Prerequisite(.*)"; 
		String credit = "(.*)Credit(.*)"; 
		
		String storeCredit = null; 
		boolean writeDescription = false; 
		
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
		outputWriter.write("<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n<Courses>");
		outputWriter.newLine();
		
		// go though entire text file
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			
			if(line.matches(courseabbrev)) 
			{
				outputWriter.write("<CourseName id=\"" + line + "\">");
				outputWriter.newLine();
				outputWriter.write("<Description>");
				outputWriter.newLine();
				writeDescription = true;
				
				while(writeDescription)
				{
					line = scan.nextLine();
					if(line.matches(prereq))
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
				}
				outputWriter.flush();
				
			}
		}
		outputWriter.newLine();
		outputWriter.write("</Courses>");
		outputWriter.flush();
		scan.close();

	}
}
