package dk.sdu.petni23.common.configreader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.entity.IEntitySPI.Type;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    public static Map<IEntitySPI.Type,Integer> getItemPrices(IEntitySPI.Type item){
        Map<IEntitySPI.Type,Integer> result = new HashMap<>();
        NodeList documentNodes = config_file.getElementsByTagName(item.toString().toLowerCase(Locale.ROOT));
        if(documentNodes.getLength()>0){
            Node itemNode = documentNodes.item(0);

            NodeList itemChildren = ((Element)itemNode).getElementsByTagName("price");
            Node priceNode = itemChildren.item(0);

            NodeList priceChildren = ((Element)priceNode).getChildNodes();
            for(int i = 0;i<priceChildren.getLength();i++){
                if(priceChildren.item(i).getNodeType() == Node.ELEMENT_NODE){
                    result.put(Type.getTypeFromString(priceChildren.item(i).getNodeName()), Integer.parseInt(priceChildren.item(i).getTextContent()));
                }
            }
            return result;
        }else{
            throw new RuntimeException("Document empty");
        }
    }
    public static int getItemHealth(IEntitySPI.Type item){
        NodeList documentNodes = config_file.getElementsByTagName(item.toString().toLowerCase(Locale.ROOT));
        if(documentNodes.getLength()>0){
            Node itemNode = documentNodes.item(0);
            return Integer.parseInt(((Element)itemNode).getElementsByTagName("health").item(0).getTextContent());
        }else{
            throw new RuntimeException("Document empty");
        }
    }

    
}
