import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class extractionXML {
    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("/Users/erikayavaca/IdeaProjects/softwareproject/Project/CompletedMajors2.xml");
            NodeList majorList =doc.getElementsByTagName("MajorName");
            for(int i =0;i<majorList.getLength();i++) {
                Node p = majorList.item(i);
                if(p.getNodeType()==Node.ELEMENT_NODE) {
                    Element major = (Element) p;
                    String id = major.getAttribute("id");
                    NodeList nameList  = major.getChildNodes();
                    for(int j=0;j<nameList.getLength();j++) {
                        Node n = nameList.item(j);
                        if(n.getNodeType()==Node.ELEMENT_NODE) {
                            Element name = (Element)n;
                            System.out.println("Major " + id + ":" + name.getTagName()+"=" + name.getTextContent());
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

    }
}
