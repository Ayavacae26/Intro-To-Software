import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


	  public static void main(String[] args) throws IOException {
		  StringBuilder format = new StringBuilder();
		  
//		  GUI window = new GUI();
//		  window.setVisible(true);
		  
		  /*---------------------------------------------------------------------*/
		  // First, take in the user's transcript and turn it into an array
		  ArrayList<String> transcript = readFile(new File("TranscriptTest.txt"));
		  
//		  // Test call - making sure transcript array was created successfully
//		  System.out.println("User's Transcript: " + transcript);
		  
		  /*---------------------------------------------------------------------*/
		  // GUI will set the string to a variable for this call
		  /* Second, find major comparing against and store that major into 
		  two arrays; one for required, and one for special cases (1 of, 3 of, etc... */
		  String[][] majorRequirements = grabMajorRequirements("American Indian Studies BA");
		  
//		  // Test call - making sure major arrays were created successfully 
//		  System.out.println("Required: " + Arrays.toString(majorRequirements[0]));
//		  System.out.println("SomeOf: " + Arrays.toString(majorRequirements[1]));
		  
		  /*---------------------------------------------------------------------*/
		  // Third, scan transcript array against the major's required classes 
		  String[][] transcriptRequired = compareRequiredClasses(transcript, majorRequirements[0]);
		  
//		  // Test call - making sure arrays were created successfully
//		  System.out.println("Required Taken: " + Arrays.toString(transcriptRequired[0]));
//		  System.out.println("Required Needed: " + Arrays.toString(transcriptRequired[1]));
		  
		  /*---------------------------------------------------------------------*/
		  // Fourth, scan transcript array against the major's SomeOf classes 
		  String[][] transcriptSomeOf = compareSomeOfClasses(transcript, majorRequirements[1]);
		  
//		  // Test call - making sure arrays were created successfully 
//		  System.out.println("SomeOf Taken: "+Arrays.toString(transcriptSomeOf[0]));
//		  System.out.println("SomeOf Need " + Arrays.toString(transcriptSomeOf[1]));
		  
		  /*---------------------------------------------------------------------*/
		  // Fifth, read the arrays of the taken 
		  StringBuilder classesTaken1 = readRequiredTaken(transcriptRequired[0]);
		  StringBuilder classesTaken2 = readRequiredTaken(transcriptSomeOf[0]);
		  format = new StringBuilder("You have taken these class(es) that fit the major:");
		  
		  // Test call - making sure StringBuilder was created successfully 
		  System.out.println(format);
		  System.out.println(classesTaken1.toString());
		  System.out.println(classesTaken2.toString());
		  
		  /*---------------------------------------------------------------------*/
		  // Sixth, read the arrays of the classes still need
		  StringBuilder classesNeed1 = readRequiredTaken(transcriptRequired[1]);
		  StringBuilder classesNeed2 = readSomeOfTaken(transcriptSomeOf[1]);
		  
		  // Test call - making sure StringBuilder was created successfully 
		  format = new StringBuilder("\nYou still need these class(es) from the major: ");
		  System.out.println(format);
		  System.out.println(classesNeed1.toString());
		  
		  format = new StringBuilder("\nAlong with these other class(es).");
		  System.out.print(format.toString());
		  System.out.println(classesNeed2.toString());
		  
		  /*---------------------------------------------------------------------*/
		  // Minimum Requirement - Progress Towards Degree 
		  // First, go through 'still need' arrays and add their courses together 
		  int amountNeed = amountOfClassesNeed(transcriptRequired[1], transcriptSomeOf[1]);
		  
		  // Test call - making sure the addition is correct 
		  System.out.println(amountNeed);
		  
		  /*---------------------------------------------------------------------*/
		  //Second, go through 'already taken' arrays and add their courses together 
		  int amountTaken = amountOfClassesTaken(transcriptRequired[0], transcriptSomeOf[0]);
		  
		  // Test - call
		  System.out.println(amountTaken);
		  
		  // amount taken / amount taken + amount need 
		  // 3 / (3 + 12) 
		  System.out.println("Degree Progress: " + amountTaken + "\\" 
		  + (amountTaken+amountNeed)+ " (" + 
				  (double)amountTaken/(amountNeed+amountTaken) + "%)");
		  
		  // Minimum Requirement - Check progress against other majors
		  checkMajorProgress("American Indian Studies BA", transcript);
		  
		  // Suggest a plan to meet the Program Requirements
		  /* - Search for all classes still need in the database... if they have
		  any prerequisite(s) then output those classes. Also check if those 
		  classes have any other prerequisite(s). Store all the classes 
		  into an ArrayList so that we can read it from reverse? 
		  This will then tell the users which classes to take before the next class
		  (CSC400 --> CSC300 --> CSC 200, CSC101, CSC301 --> CSC200) 
		  
		  A recursive call inside? 
		  public static void anyPrerequisite(String[] classesNeed)
		  {
		  		for(int i = 0; i<classesNeed.length; i++)
		  		{
		  			
		  		}
		  }
		  
		  Or we only check 4 trees down. 400 --> 300 --> 200 -- > 100
		  public static void anyPrerequisite(String[] classesNeed)
		  {
		  		for(int i = 0; i>classesNeed.length; i++)
		  		{
		  			// grab class prereq
		  			// if there is, store to array
		  			prereq1 = 
		  			for(int j = 0; 
		  			// store it into [csc[art mat[phi [phy]]]]
		  		}
		  }
		  */ 
		  
		  
//		  // test arrayAdd
//		  String[] array = {"hello"};
//		  array = arrayAdd(array, "please");
//		  System.out.println(Arrays.toString(arrayAdd(array, "please")));
//		  
//		  System.out.println(Arrays.toString(array));
//		  System.out.println(array.length);
//		  System.out.println(arrayAdd(array, "please").length);
		  
}
	  
	  public static void checkMajorProgress(String majorName, ArrayList<String> transcript)
	  {
		  String[] majors = 
			  {"American Indian Studies BA","Art History BA"," Biopsychology BS",
					  "Accounting BA","Finance BA","Mangement BA",
					  "Management Information Systems BA","Computational Economics BA",
					  "Communications Studies BA","Computational Philosophy BA",
					  "Computer Science BA","Computer Science BS","Applied Economics BA",
					  "Economics BA","Mathematical Economics BS","Film:production track BA",
					  "Film:Theory and culture track BA", "Exercise Science BA",
					  "Health Education BA or  BS", "Physical Education BA or  BS", 
					  "Mathematics BA","New Media:Game Design BA","New Media:Web Design BA", 
					  "Nursing BS", "Physics BA","Physics BS", "Physics:Space Physics BS",
					  "Political Science:Public Policy/Change BA",
					  "Psychology: Psychology and Law  BA", "Sociology BA",
					  "Social Work  BS"};
		  
		  boolean courseRemoved = false;
		  
		  // if found a match, remove it and start shifting everything down one
		  for(int i = 0; i<majors.length; i++)
		  {
			  
			  if(majors[i].equals(majorName))
			  {
				  majors[i] = majors[i+1];
				  courseRemoved = true;
			  }
			  else if(i == majors.length - 1)
			  {
				  majors[i] = null;
			  }
			  else if(courseRemoved)
			  {
				  majors[i] = majors[i+1];
			  }
		  }
//		  System.out.println(Arrays.toString(majors));
		  
		  // go through all majors in String and calculate the degree progress 
		  for(int i = 0; i<majors.length - 1; i++)
		  {
			  String[][] majorRequirements = grabMajorRequirements(majors[i]);
			  String[][] transcriptRequired = compareRequiredClasses(transcript, majorRequirements[0]);
			  String[][] transcriptSomeOf = compareSomeOfClasses(transcript, majorRequirements[1]);
			  int amountNeed = amountOfClassesNeed(transcriptRequired[1], transcriptSomeOf[1]);
			  int amountTaken = amountOfClassesTaken(transcriptRequired[0], transcriptSomeOf[0]);
			  System.out.println(majors[i] + ": " + amountTaken + "\\" 
					  + (amountTaken+amountNeed)+ " (" + 
							  (double)amountTaken/(amountNeed+amountTaken) + "%)");
		  }
		  
	  }
	  
	  /**
	   * This method calculates the amount of classes still needed. It doesn not handle
	   * arrays formatted in a special case. 
	   * @param transcriptRequired	read this array's length
	   * @param transcriptSomeOf	read this array's length
	   * @return	the amount of classes taken
	   */
	  public static int amountOfClassesTaken(String[] transcriptRequired, String[] transcriptSomeOf)
	  {
		  return transcriptRequired.length + transcriptSomeOf.length;
	  }
	  
	  /**
	   * This method calculates the amount of classes still needed. It handles the array
	   * that is formatted in a special case.
	   * @param transcriptRequired	read this array's length
	   * @param transcriptSomeOf	the array in a special format
	   * @return	the amount of classes still need
	   */
	  public static int amountOfClassesNeed(String[] transcriptRequired, String[] transcriptSomeOf)
	  {
		  int returnThis = transcriptRequired.length;
		  int transcriptSomeOfNumber = 0;
		  String majorCourse = null;
		  
		  for(int i = 0; i<transcriptSomeOf.length; i++)
		  {
			  majorCourse = transcriptSomeOf[i];
			  
			  // if its the number (not class abbreviations) then add that number to 
			  if(Character.isDigit(majorCourse.charAt(0)))
			  {
				  transcriptSomeOfNumber = transcriptSomeOfNumber + Integer.parseInt(majorCourse);
			  }
		  }
		  
		  return returnThis + transcriptSomeOfNumber;
	  }
	  
	  /**
	   * This method reads the arrays of courses formatted in a special case.
	   * @param transcriptSomeOf	the array that will be read
	   * @return	a string to the user, displaying what is read inside the array
	   */
	  public static StringBuilder readSomeOfTaken(String[] transcriptSomeOf)
	  {
		  StringBuilder returnThis = new StringBuilder();
		  String majorCourse = null;
		  
		  // reverse the array so numbers come first before courses
		  for(int i = 0; i < (transcriptSomeOf.length/2); i++)
		  {
			  String temp = transcriptSomeOf[i];
			  transcriptSomeOf[i] = transcriptSomeOf[transcriptSomeOf.length - i - 1];
			  transcriptSomeOf[transcriptSomeOf.length - i - 1] = temp;
		  }
		  
		  // build a string by reading through the array
		  for(int i = 0; i < transcriptSomeOf.length; i++)
		  {
			  majorCourse = transcriptSomeOf[i];
			  
			  // if it is a number in the array, store as new category in String
			  if(Character.isDigit(majorCourse.charAt(0)))
			  {
				  returnThis.append("\nYou need " + majorCourse + " more class(es) from this group:\n");
			  }
			  else
			  {
				  returnThis.append(majorCourse + " ");
			  }
			  
		  }
		  
		  return returnThis;
	  }
	  
	  /**
	   * This method reads the arrays of courses not formatted in any special cases.
	   * @param transcriptRequired	the array that will be read
	   * @return	a string to the user, displaying what was read inside the array
	   */
	  public static StringBuilder readRequiredTaken(String[] transcriptRequired)
	  {
		  StringBuilder returnThis = new StringBuilder();
		  
		  // read the array into the StringBuilder 
		  for(int i = 0; i < transcriptRequired.length; i++)
		  {
			  if (i == transcriptRequired.length - 1)
			  {
				  returnThis.append(transcriptRequired[i]);
			  }
			  else
			  {
				  returnThis.append(transcriptRequired[i] + ", ");
			  }
			  
		  }
		  
		  return returnThis;
	  }
	  
	  
	  /**
	   * Compares the transcript and the major's 'SomeOf' classes outputting an
	   * array that holds in two arrays. At position 1, the array is for classes 
	   * taken and at position 2, is for classes still needed. 
	   * @param transcript	of the user
	   * @param someOfRequirements	an array that holds 'SomeOf' the major's requirement 
	   * @return
	   */
	  public static String[][] compareSomeOfClasses(ArrayList<String> transcript, String[] someOfRequirements)
	  {
		  String[][] returnThis = new String[2][];
		  String[] coursesTaken = new String[0];
		  String[] coursesNeed = new String[0];
		  String majorCourse = null;
		  
		  int classesNeeded = 0;
		  int classesMet = 0;
		  
		  for(int i = 0; i < someOfRequirements.length; i++)
		  {
			  majorCourse = someOfRequirements[i];
			  
			  // if it is a number in the array, store it 
			  if(Character.isDigit(majorCourse.charAt(0)))
			  {
				  // reset variables 
				  classesNeeded = Integer.parseInt(majorCourse);
				  classesMet = 0;
			  }
			  else 
			  {
				  for(int j = 0; j < transcript.size(); j++)
				  {
					  // if equal, remove from transcript then 
					  // store in CoursesTaken and increment classesMet 
					  if(majorCourse.equals(transcript.get(j)))
					  {
						   coursesTaken = arrayAdd(coursesTaken, transcript.get(j));
						   
//						   // Also add to coursesNeed so we can see that it's a class needed
//						   // but we have taken already
//						   coursesNeed = arrayAdd(coursesNeed, majorCourse);
						   
//						   transcript.remove(j);
						   classesMet++;
						   break;
					  }
					  else if( j == (transcript.size()-1) )
					  {
//						  // if not equal to any inside transcript 
//						  if( j == (transcript.size()-1) )
//						  {
							  coursesNeed = arrayAdd(coursesNeed, majorCourse);
//						  }
						  
					  }
				  }
				  
				  if((i+1) > someOfRequirements.length - 1)
				  {
					  coursesNeed = arrayAdd(coursesNeed, String.valueOf(classesNeeded - classesMet));
				  }
				  else if(Character.isDigit(someOfRequirements[i+1].charAt(0)))
				  {
					  coursesNeed = arrayAdd(coursesNeed, String.valueOf(classesNeeded - classesMet));
				  }
			  }
		  }
		  returnThis[0] = coursesTaken;
		  returnThis[1] = coursesNeed;
		  
		  return returnThis;
	  }
	  
	  /**
	   * Compares the transcript and the major's required classes outputting an
	   * array that holds in two arrays. At position 1, the array is for classes 
	   * taken and at position 2, is for classes still needed. 
	   * 
	   * @param transcript of the user
	   * @param majorRequirements	an array that hold major requirements of the major
	   */
	  public static String[][] compareRequiredClasses(ArrayList<String> transcript, String[] majorRequirements)
	  {
		  String[][] returnThis = new String[2][];
		  String[] coursesTaken = new String[0];
		  String[] coursesNeed = new String[0];
		  String majorCourse = null;
		  
		  for(int i = 0; i < majorRequirements.length; i++)
		  {
			  majorCourse = majorRequirements[i];
			  
			  // Take a major's course, compare through all transcript's courses 
			  for(int j = 0; j < transcript.size(); j++)
			  {
				  
				  if(majorCourse.equals(transcript.get(j)))
				  {
					  
					  coursesTaken = arrayAdd(coursesTaken, majorCourse);
					  break;	//break out of the for-loop if did find a match
				  }
				  else
				  {
					  if( j == (transcript.size()-1) )
					  {
						  coursesNeed = arrayAdd(coursesNeed, majorCourse);
					  }
				  }
			  }
		  }
		  
		  returnThis[0] = coursesTaken;
		  returnThis[1] = coursesNeed;
		  return returnThis;
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
	  public static String[][] grabMajorRequirements(String majorName)
	  {
		  String[][] returnThis = new String[2][];
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      String[] requiredArray = new String[0];
	      String[] someofArray = new String[0];
	        
	      try {
	          DocumentBuilder builder = factory.newDocumentBuilder();
	          Document doc = builder.parse("Majors.xml");
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

}


