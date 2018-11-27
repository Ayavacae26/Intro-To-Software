import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.File;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class DatabaseCourseDescription {
	
	public static void main(String args[]) throws IOException
	{
		File text = new File("CourseDescriptions.txt");
		Scanner scan = null;
		BufferedWriter outputWriter = null;
		
		boolean writeDescription = false;
		String regex = "([A-Z]{3}[0-9]{3})";
		String prereq = "(.*)Prerequisite(.*)";
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
		
		while(scan.hasNext()) {
			String line = (scan.nextLine());
			
			if(line.matches(regex)) 
			{
//				System.out.println("<CourseName id=" + line + ">");
//				System.out.println("<Description>");
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
//						System.out.println("</Description>");
//						System.out.println("<Prerequisite>");
						outputWriter.write("</Description>");
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
						writeDescription = false;
					}
					else if(line.matches(credit))
					{
						outputWriter.write("<Credit>");
						outputWriter.newLine();
						outputWriter.write(line);
						outputWriter.newLine();
						outputWriter.write("</Credit>");
						outputWriter.newLine();
					}
					else	// write the description 
					{
//						System.out.println(line);
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
	}

}
