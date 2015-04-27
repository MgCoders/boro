package uy.mgcoders.boro.comm;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import uy.mgcoders.boro.objects.Issue;

/**
 * Created by r on 25/04/15.
 */
public class XmlParser {

    static final String KEY_ISSUE = "issue";
    static final String KEY_FIELD = "field";


    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    private Map<String, String> getFields(Node n) {
        Map<String, String> map = new HashMap<>();
        NodeList nl = n.getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {
            Node child = nl.item(i);
            NamedNodeMap attrs = child.getAttributes();
            Node z = attrs.getNamedItem("name");
            if (z != null) {
                String nm = z.getNodeValue();
                NodeList grandchilren = child.getChildNodes();
                for (int j = 0; j < grandchilren.getLength(); j++) {
                    Node v = grandchilren.item(j);
                    if (v.getNodeName().equals("value"))
                        if (v.hasChildNodes()) {
                            map.put(nm, v.getChildNodes().item(0).getTextContent());
                            break;
                        }

                }
            }




            /*for (int j = 0; j < attrs.getLength() ; j++) {
                Node attr = attrs.item(j);
                map.put(attr.getNodeName(),attr.getNodeValue());
            }*/
        }

        return map;
    }

    public Issue getIssue(Node e) {

        /* Issue issue = new Issue();

       NamedNodeMap map = e.getAttributes();

        Node id = map.getNamedItem("id");
        issue.setId(id.getTextContent());

        issue.setNombre(issue.getId());*/

        Map<String, String> map = getFields(e);

        String numberInProject = map.get("numberInProject");
        String summary = map.get("summary");
        String description = map.get("description");
        String priority = map.get("Priority");
        String state = map.get("State");
        String projectShortName = map.get("projectShortName");


        return new Issue(numberInProject, summary, description, priority, state, projectShortName);

    }
}
