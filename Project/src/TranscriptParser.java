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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * @author Erik Ayavaca-Tirado
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

	public static void main(String[] args) throws IOException {
//		GUI window = new GUI();
//		window.setVisible(true);
		
//		XPath xpath = XPathFactory.newInstance().newXPath();
//		String expression = "/Majors/*";
//		InputSource inputSource = new InputSource("completedmajors.xml");
//		try {
//			Node nodes = (Node) xpath.evaluate(expression, inputSource, XPathConstants.STRING);
//		} catch (XPathExpressionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		private File file;
//		 
//	    public TranscriptParser(File file) {
//	        this.file = file;
//	        FileInputStream fileIS = new FileInputStream(this.getFile());
//	        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//	        DocumentBuilder builder = builderFactory.newDocumentBuilder();
//	        Document xmlDocument = builder.parse(fileIS);
//	        XPath xPath = XPathFactory.newInstance().newXPath();
//	        String expression = "/Tutorials/Tutorial";
//	        nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
//	    }
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			document = builder.parse(new File("test.xml"));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element rootElement = document.getDocumentElement();
		NodeList test = rootElement.getElementsByTagName("*");
		
		System.out.println(rootElement);
		System.out.println(test.item(1));
		System.out.println(test.item(2));
//		System.out.println(test.item(3));
		System.out.println(rootElement.getTextContent());
		System.out.println(rootElement.hasAttribute("American Indian Studies BA"));	//getNodeValue() vs getContentText
		System.out.println(rootElement.getTagName());
		System.out.println(rootElement.getAttribute("Majors"));
		System.out.println(rootElement.getAttributeNode("Majors"));
		System.out.println(test.item(2).getTextContent());
    }
	
	
	
	
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
	public static void readFile(File a, String nameOfFile) {
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

		createFile(nameOfFile, scanLine, abbreviationList);
	}

	/**
	 * This method will create a text file and store in neatly,
	 * the course's abbreviations.
	 *
	 * @param nameOfFile       the name of the file being created
	 * @param scanLine         the Scanner for the text file
	 * @param abbreviationList the Scanner for abbreviations String
	 */
	public static void createFile(String nameOfFile, Scanner scanLine, Scanner abbreviationList) {
		try {
			FileWriter writeToFile = new FileWriter(nameOfFile);
			BufferedWriter out = new BufferedWriter(writeToFile);

			while (scanLine.hasNext()) 
			{
				String word = scanLine.next();
				if (word.endsWith(","))
				{
					word = word.substring(0, word.length() - 1);
				}
				
				//System.out.println("String word: " + word); //Testing purpose, uncheck if you want to see how it works
				abbreviationList = new Scanner(abbreviations);

				//only check words with length between 6-7
				while (abbreviationList.hasNext() && word.length() >= 6 && word.length() <= 7) 
				{
					String courseAbbrev = abbreviationList.next();
					courseAbbrev.replace(",", "");

					if (word.regionMatches(0, courseAbbrev, 0, 3)) 
					{
						out.write(word);
						out.newLine();
						break; //exit out 2nd loop if found a match
					}
				}
			}
			out.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Error - IOException");
		}

		System.out.println(nameOfFile + " text file has been created.");
	}

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


