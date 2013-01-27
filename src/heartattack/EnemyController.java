/**
 * @author Kevin
 */
package heartattack;

//XML stuff
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class EnemyController
{
    private enum enemyType 
    {
        NORMAL
    }
    private static EnemyWave[] waves;
    private static DocumentBuilder db;
    private static DocumentBuilderFactory dbf;
    private static File xmlFile;
    
    public static void init(String filename)
    {
        try 
        {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            xmlFile = new File(filename);
            Document doc = db.parse(xmlFile);
            NodeList waveNodes = doc.getElementsByTagName("Wave");
            waves = new EnemyWave[waveNodes.getLength()];
            for (int i =0; i < waveNodes.getLength(); ++i)
            {
                waves[0] = new EnemyWave();
            }
        }
        catch (Exception e)
        {
            
        }
    }
}