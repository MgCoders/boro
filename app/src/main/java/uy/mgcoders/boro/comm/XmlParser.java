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

    public Issue getIssue(Node e) {

        Issue issue = new Issue();

        NamedNodeMap map = e.getAttributes();

        Node id = map.getNamedItem("id");
        issue.setId(id.getTextContent());

        issue.setNombre(issue.getId());
        NodeList nl = e.getChildNodes();
        return issue;

    }
}
