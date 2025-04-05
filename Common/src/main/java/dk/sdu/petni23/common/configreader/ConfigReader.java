package dk.sdu.petni23.common.configreader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

public class ConfigReader {
    static {
        try {
            InputStream input = ConfigReader.class.getResourceAsStream("/config/config.xml");
            if(input == null){
                System.out.println("Could not find file..");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document conf = builder.parse(input);
            conf.getDocumentElement().normalize();
            config_file = conf;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        
    }

    public static Document config_file;

    //fx ("woodenWall,gold")
    public static int getItemPrices(String item, String resource){
        NodeList documentNodes = config_file.getElementsByTagName(item);
        if(documentNodes.getLength()>0){
            Node node = documentNodes.item(0);
            NodeList children = node.getChildNodes();
            for(int i = 0;i<children.getLength();i++){
                if(children.item(i).getNodeType() == Node.ELEMENT_NODE && children.item(i).getNodeName()==resource){
                    Element element = (Element) children.item(i);
                    return Integer.parseInt(element.getTextContent());
                }
            }
            throw new RuntimeException("item or resource not specified");
        }else{
            throw new RuntimeException("Document empty");
        }
    }
}
