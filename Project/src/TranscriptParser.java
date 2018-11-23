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
		  
		  ArrayList<String> transcript = readFile(new File("TranscriptTest.txt"));
		  
		  // call testing method
		  System.out.println(transcript);
		  
		  // GUI will set the string to a variable for this call
		  Object[] majorRequirements = grabMajorRequirements("American Indian Studies BA");
		  
		  System.out.println(majorRequirements[0]);
		  System.out.println(majorRequirements[1]);

}
	  
	  public static Object[] grabMajorRequirements(String majorName)
	  {
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      ArrayList<String> requiredArray = new ArrayList<String>();
	      ArrayList<String> someofArray = new ArrayList<String>();
	        
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
//		                	  System.out.println(name.getTextContent()); //verify with this line of code
		                	  requiredArray = new ArrayList<String>();
		                	  Scanner scan = new Scanner(requiredList);
		                    	
		                	  // store our String requiredList into an array
		                	  while(scan.hasNext())
		                	  {
		                		  requiredArray.add(scan.next());
		                	  }
		                	  scan.close();
		                	  
//		                	  System.out.println(requiredList);
//		                	  System.out.println(requiredArray.toString());
		                  }
		                  // Stores 'SomeOf' classes into one array
		                  if(n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("SomeOf"))
		                  {
		                	  Element name = (Element)n;
		                	  String someofList = name.getTextContent();
//		                	  System.out.println(name.getTextContent()); //verify with this line of code
		                	  someofArray = new ArrayList<String>();
		                	  Scanner scan = new Scanner(someofList);
		                    	
		                	  // store our String someofList into an array
		                	  while(scan.hasNext())
		                	  {
		                		  someofArray.add(scan.next());
		                	  }
		                	  scan.close();
		                    	
//		                	  System.out.println(someofList);
//		                	  System.out.println(someofArray.toString());
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
	      
	      //return two arrays
		  return new Object[] {requiredArray, someofArray};
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
	 * Reads a file and "outputs a text file containing the course abbreviations
	 * in a nice format."
	 *
	 * @param a the text file to scan through and retrieve course abbreviations
	 */
	/* This method will scan the text file word by word and ONLY compare
	the word to the abbreviations IF AND ONLY IF the scanned word has a length
	of between 6 and 7. */
	public static ArrayList<String> readFile(File a) {
		Scanner scanLine = null;
		Scanner abbreviationList = null;

		try 
		{
			//set scanner to file 'a'
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


