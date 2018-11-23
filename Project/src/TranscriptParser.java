import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import javax.xml.soap.Node;
//import javax.xml.xpath.XPath;
//import javax.xml.xpath.XPathConstants;
//import javax.xml.xpath.XPathExpressionException;
//import javax.xml.xpath.XPathFactory;
//
//
//import org.xml.sax.InputSource;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * @author Erik, Olson, and Mahamed
 *  Program is going to be comparing the 2 files transcript and course list and spitiing out the difference between them.
 *  Orginal implentation ideas:
 *  // posibly use the hasmap
 * 	// Takes in the user's transcript
 * 	// Takes in user's major course list
 * 	// Compares both lists
 * 	// Puts classes not already taken into an array
 * 	// outputs classes as " you still need to complete these"
 * 	// Goes back into Transcript and looks for classes with F,D, 1.5,1,.5 or 0
 * 	// these classes are put into list or array
 * 	// outputs to user as " need to retake this"
 * 	// Output is returned to user
 *
 */
// Can also be used as which classes a user needs to take
public class TranscriptParser {

	static final String abbreviations = "CSC MAT ENL AIS COM PSY ANT CCS SOC THR WST"
			+ " POL ART HIS ECO PHI BUS FIN BIO HPE EDC EED CHM SPE SWK"
			+ " ENV PHY HON KEY MIS MKT HUM RLN THR MUS SPA MUE ACC SCI"
			+ " NUR NMS FLM THP URB YST REL AUG";
	//Erik added more abbreviations into this list

	  //comment out due to running  the extraction xml


	  public static void main(String[] args) throws IOException {
		  
//		  GUI window = new GUI();
//		  window.setVisible(true);
		  
		  // First, take in the user's transcript and turn it into an array
		  ArrayList<String> transcript = readFile(new File("TranscriptTest.txt"));
		  
		  // Test call - making sure transcript array was created successfully
		  System.out.println(transcript);
		  
		  // GUI will set the string to a variable for this call
		  /* Second, find major comparing against and store that major into 
		  two arrays; one for required, and one for special cases (1 of, 3 of, etc... */
		  String[][] majorRequirements = grabMajorRequirements("American Indian Studies BA");
		  //old call
		  //Object[] majorRequirements = grabMajorRequirements("American Indian Studies BA");
		  
		  // Test call - making sure major arrays were created successfully 
		  System.out.println(Arrays.toString(majorRequirements[0]));
		  System.out.println(Arrays.toString(majorRequirements[1]));
		  
		  // Third, scan transcript array against the major's arrays 
		   compareRequiredClasses(transcript, majorRequirements[0]);
		  // compareSomeOfClasses(transcript, majorRequirements[1])
		  
//		  //test arrayAdd
//		  String[] array = {"hello"};
//		  array = arrayAdd(array, "please");
//		  System.out.println(Arrays.toString(arrayAdd(array, "please")));
//		  
//		  System.out.println(Arrays.toString(array));
//		  System.out.println(array.length);
//		  System.out.println(arrayAdd(array, "please").length);
		  
}
	  
	  public static void compareRequiredClasses(ArrayList<String> transcript, String[] majorRequirements)
	  {
		  String majorCourse = null;
		  ArrayList<String> coursesTaken = new ArrayList<String>();
		  ArrayList<String> coursesNeed = new ArrayList<String>();
		  
		  for(int i = 0; i < majorRequirements.length; i++)
		  {
			  majorCourse = majorRequirements[i];
			  
			  for (int j = 0; j < transcript.size(); j++)
			  {
				  
				  if(majorCourse.equals(transcript.get(j)))
				  {
					  coursesTaken.add(majorCourse);
					  break;	//break out of the for-loop if did find a match
				  }
				  else
				  {
					  coursesNeed.add(majorCourse);
				  }
			  }
		  }
		  
		  System.out.println(coursesTaken.toString());
		  System.out.println(coursesNeed.toString());
	  }
	  
	  
	  
	  /**
	   * Takes in a String and looks inside the XML database to see if that String/Major
	   * exists. If it does then capture those data into two arrays. 
	   * 
	   * @param majorName is the String you want to search for inside the XML database
	   * @return an Object holding two arrays. First array holds in all classes that are 
	   * required to be taken. Second array holds in special cases for when a certain 
	   * amount of classes need to be taken from a group of courses. 
	   */
//	  public static Object[] grabMajorRequirements(String majorName)
//	  {
//	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//	      ArrayList<String> requiredArray = new ArrayList<String>();
//	      ArrayList<String> someofArray = new ArrayList<String>();
//	        
//	      try {
//	          DocumentBuilder builder = factory.newDocumentBuilder();
//	          Document doc = builder.parse("CompletedMajors2.xml");
//	          NodeList majorList = doc.getElementsByTagName("MajorName");
//	          for(int i =0; i<majorList.getLength(); i++) 
//	          {
//	              Node p = majorList.item(i);
//	              Element major = (Element) p;
//	              String id = major.getAttribute("id");
//	              
//	              if(id.equals(majorName))	//testing to grab only when id equals
//	              {
//	            	  NodeList nameList  = major.getChildNodes();
//	                	
//		              for(int j=0; j<nameList.getLength(); j++) {
//		                  Node n = nameList.item(j);
//		                    
//		                  // Stores 'Required' classes into one array
//		                  if(n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("Required")) 
//		                  {
//		                	  Element name = (Element)n;
//		                	  String requiredList = name.getTextContent();
//		                	  requiredArray = new ArrayList<String>();
//		                	  Scanner scan = new Scanner(requiredList);
//		                    	
//		                	  // store our String requiredList into an array
//		                	  while(scan.hasNext())
//		                	  {
//		                		  requiredArray.add(scan.next());
//		                	  }
//		                	  scan.close();
//		                	  
////		                	  System.out.println(requiredList);
////		                	  System.out.println(requiredArray.toString());
//		                  }
//		                  // Stores 'SomeOf' classes into one array
//		                  if(n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("SomeOf"))
//		                  {
//		                	  Element name = (Element)n;
//		                	  String someofList = name.getTextContent();
//		                	  someofArray = new ArrayList<String>();
//		                	  Scanner scan = new Scanner(someofList);
//		                    	
//		                	  // store our String someofList into an array
//		                	  while(scan.hasNext())
//		                	  {
//		                		  someofArray.add(scan.next());
//		                	  }
//		                	  scan.close();
//		                    	
////		                	  System.out.println(someofList);
////		                	  System.out.println(someofArray.toString());
//		                  }
//		              }
//	              }
//	          }
//	      } catch (ParserConfigurationException e) {
//	          // TODO Auto-generated catch block
//	          e.printStackTrace();
//	      } catch (SAXException e) {
//	          // TODO Auto-generated catch block
//	          e.printStackTrace();
//	      } catch (IOException e) {
//	          // TODO Auto-generated catch block
//	          e.printStackTrace();
//	      }
//	      
//		  return new Object[] {requiredArray, someofArray};
//	  }
	
	  
	  public static String[][] grabMajorRequirements(String majorName)
	  {
		  String[][] returnThis = new String[2][];
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      String[] requiredArray = new String[0];
	      String[] someofArray = new String[0];
	        
	      try {
	          DocumentBuilder builder = factory.newDocumentBuilder();
	          Document doc = builder.parse("CompletedMajors2.xml");
	          NodeList majorList = doc.getElementsByTagName("MajorName");
	          for(int i =0; i<majorList.getLength(); i++) 
	          {
	              Node p = majorList.item(i);
	              Element major = (Element) p;
	              String id = major.getAttribute("id");
	              
	              if(id.equals(majorName))	//testing to grab only when id equals
	              {
	            	  NodeList nameList  = major.getChildNodes();
	                	
		              for(int j=0; j<nameList.getLength(); j++) {
		                  Node n = nameList.item(j);
		                    
		                  // Stores 'Required' classes into one array
		                  if(n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("Required")) 
		                  {
		                	  Element name = (Element)n;
		                	  String requiredList = name.getTextContent();
		                	  Scanner scan = new Scanner(requiredList);
		                    	
		                	  // store our String requiredList into an array
		                	  while(scan.hasNext())
		                	  {
		                		  requiredArray = arrayAdd(requiredArray, scan.next());
		                	  }
		                	  scan.close();
		                	  
		                  }
		                  // Stores 'SomeOf' classes into one array
		                  if(n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("SomeOf"))
		                  {
		                	  Element name = (Element)n;
		                	  String someofList = name.getTextContent();
		                	  Scanner scan = new Scanner(someofList);
		                    	
		                	  // store our String someofList into an array
		                	  while(scan.hasNext())
		                	  {
		                		  someofArray = arrayAdd(someofArray, scan.next());
		                	  }
		                	  scan.close();
		                	  
		                  }
		              }
	              }
	          }
	      } catch (ParserConfigurationException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	      } catch (SAXException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	      } catch (IOException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	      }
	      returnThis[0] = requiredArray;
	      returnThis[1] = someofArray;
//		  return new Object[] {requiredArray, someofArray};
	      return returnThis;
	  }
	
	  /**
	   * Our version of having a dynamic array, where adding elements to the array 
	   * will increment the size of the array along with adding the String to the 
	   * end of the array. 
	   * NOTE: Because arrays in Java are pass by copy, when using this method you 
	   * must call it as -- array = arrayAdd(array, "Foo");
	   * 
	   * @param array is the array you want to dynamically add to
	   * @param addMe is the String you want to add to the array 
	   * @return the new array
	   */
	  // Had to use a dynamic array instead of ArrayLists because method  
	  // grabMajorTranscript needs to return two arrays. Originally had an 
	  // Object store in two ArrayList<String> and return it but there were 
	  // errors in a method that would use those ArrayLists stored in the 
	  // Object. Switched to this route to avoid complications. 
	  public static String[] arrayAdd (String[] array, String addMe)
	  {
		  String[] newArray = array;
		  
		  if(array == null)
		  {
			  newArray = new String[1];
			  newArray[0] = addMe;
		  }
		  else
		  {
			  newArray = new String[array.length + 1];
			  
			  for(int i = 0; i < array.length; i++)
			  {
				  newArray[i] = array[i];
			  }
			  
			  newArray[newArray.length - 1] = addMe; 
		  }
		  array = newArray;
		  return array;
	  }
	
	// won't need this method but might use it for reference 
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	public static void compareTranscriptAndDegree()
	{
		Path firstFile = Paths.get("degree.txt");
		Path secondFile = Paths.get("transcript.txt");
		List<String> firstFileContent = null;
		List<String> secondFileContent = null;
		
		try 
		{
			firstFileContent = Files.readAllLines(firstFile, Charset.defaultCharset());
			secondFileContent = Files.readAllLines(secondFile, Charset.defaultCharset());
		} 
		catch (IOException e) 
		{
			//change println to reflect problem more clearer -Olson
			System.out.println("One of the uploaded files were empty.");
		}
		
		System.out.println("Classes completed in the degree: " + sameFiles(firstFileContent, secondFileContent));
		System.out.println("Classes still needed for degree: " + diffFiles(firstFileContent, secondFileContent));
		System.out.println("Classes taken outside the degree requirement: " + diffFiles(secondFileContent,firstFileContent));
	}

	/**
	 * Reads a file searching for keywords that match course abbreviations. 
	 * Word is stored into an array if it catches one of the abbreviations and
	 * IF AND ONLY IF the scanned word has a length of between 6 and 7. 
	 *
	 * @param a the text file to scan through and retrieve course abbreviations
	 */
	public static ArrayList<String> readFile(File a) {
		Scanner scanLine = null;
		Scanner abbreviationList = null;

		
		try 
		{
			scanLine = new Scanner(a);
		} 
		catch (Exception e) 
		{
			System.out.println("File not found");
			System.exit(1);
		}

		return createTranscriptArray(scanLine, abbreviationList);
	}

	/**
	 * This method will create a text file and store in neatly,
	 * the course's abbreviations.
	 *
	 * @param nameOfFile       the name of the file being created
	 * @param scanLine         the Scanner for the text file
	 * @param abbreviationList the Scanner for abbreviations String
	 * @return 
	 */
	public static ArrayList<String> createTranscriptArray(Scanner scanLine, Scanner abbreviationList) {
		ArrayList<String> test = new ArrayList<String>();
		
//			FileWriter writeToFile = new FileWriter(nameOfFile);
//			BufferedWriter out = new BufferedWriter(writeToFile);
		while (scanLine.hasNext()) 
		{
			String word = scanLine.next();
			
			if (word.endsWith(","))
			{
				word = word.substring(0, word.length() - 1);
			}
			//System.out.println("String word: " + word); 
			abbreviationList = new Scanner(abbreviations);
			
			//only check words with length between 6-7
			while (abbreviationList.hasNext() && word.length() >= 6 && word.length() <= 7) 
			{
				String courseAbbrev = abbreviationList.next();
				courseAbbrev.replace(",", "");
				
				if (word.regionMatches(0, courseAbbrev, 0, 3)) 
				{
					test.add(word);
					break; //exit out 2nd loop if found a match
				}
			}
		}
		return test;
	}
	
	// won't need this function but will keep for reference
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	// function that prints out the differences in the 2 files
	public static List<String> diffFiles( List<String> firstFileContent,List<String> secondFileContent)
	{
		List<String> diff = new ArrayList<String>();
		for(String line : firstFileContent) {
			if (!secondFileContent.contains(line)) {
				diff.add(line);
			}
		}
		return diff;
	}

	// won't need this function but will use for reference
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	// Function prints class that do count for the major
	public static List<String> sameFiles( List<String> firstFileContent,List<String> secondFileContent)
	{
		List<String> same = new ArrayList<String>();
		for(String line : firstFileContent) {
			if (secondFileContent.contains(line)) {
				same.add(line);
			}
		}
		return same;
	}



}


