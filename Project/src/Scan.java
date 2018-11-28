import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.File;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;


public class Scan {

	
	
	
	public static void courseDetail() throws IOException {
		
		File text = new File("src/CourseDescriptions.txt");
		Scanner scan = new Scanner(text);
		
		ArrayList<String> courNum = new ArrayList<String>();
		ArrayList<String> courDes = new ArrayList<String> ();
		
		String regex = "([A-Z]{3}[0-9]{3})";
		String credit = "(.*)Semester(.*)";
		String prequ = "(^.*\\b(Prerequisite(s):)\\b.*$)";
		
		
		BufferedWriter outputWriter = null;
		outputWriter = new BufferedWriter(new FileWriter("src/example.xml"));
		
			while(scan.hasNext()) {
				 String line = scan.nextLine();
				
				if(line.matches(credit)) {
					System.out.println(" <here> " + line + " </course> " + '\n');
					
				}else {
					System.out.println(" <course> " + line + " </course> " + '\n');
					
				}
				
				
			}
	}
	
	
	
		public static void main(String args[]) throws IOException {
			
			
//			Scan.courseDetail();
			
						  
//	        creating File instance to reference text file in Java
	        
//	        File text = new File("src/CourseDescriptions.txt");
//	      
//	        //Creating Scanner instnace to read File in Java
//	        Scanner scnr = new Scanner(text);
//	        
//	        ArrayList<String> courseNum = new ArrayList<String>();
//	        ArrayList<String> courseDet = new ArrayList<String>();
//	        
//	        BufferedWriter outputWriter = null;
//	        outputWriter = new BufferedWriter(new FileWriter("src/example.xml"));
//	       
//	        //Reading each line of file using Scanner class
//	       
//	        String regex = "([A-Z]{3}[0-9]{3})";
//	        while(scnr.hasNextLine()){
//	            String line = scnr.nextLine();
//	            if(line.matches(regex)) {
//	            	System.out.println(" <course> " + line + " </course> " + '\n');
//	            	courseNum.add(line);  
//	            	   	
////	            	for (String i:courseNum) {
////
////		        	    outputWriter.write(" <courseNumber category = 'course'> " + i + " </course> " + '\n');
////	        	  }
////		        	  outputWriter.flush();  
////		        	  outputWriter.close();
////	            
//	            }
//		            System.out.println(" <course> " + line + " </course> " + '\n');
//		            courseDet.add(line);	
////		            for (String i:courseDet) {
////
////		        	    outputWriter.write(" <courseNumber category = 'course'> " + i + " </course> " + '\n');
////	        	  
////		            
////	            }
//	        }	      	 
////	        	  for (String i:courseDet) {
////	        	    // Maybe:
////	        	    outputWriter.write(" <courseNumber category = 'course'> " + i + " </course> " + '\n');
////	        	  }
////	        	  outputWriter.flush();  
////	        	  outputWriter.close();  
////	      


	        }
		}

		



