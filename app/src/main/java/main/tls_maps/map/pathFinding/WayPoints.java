package main.tls_maps.map.pathFinding;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import main.tls_maps.map.Vector2;
import main.tls_maps.map.WayPoint;

public class WayPoints {

    // TODO all WayPoints, maybe it could be better if we can switch all to JSON? Just to remind me Later

    /**
     * This class is to get all the WayPoints Synchronised with the Buildings
     */

    private static final ArrayList<WayPoint> WayPoints = new ArrayList<>();
    public static final String[] WAYPOINTS = new String[] {"placeHolder", "placeHolder", "placeHolder", "WP1stPark", "WPEGPark"};

    /**
     * This Reads the given Input Stream
     * @param xOff Offset for the WayPoints
     * @param yOff Offset for the WayPoints
     * @param stream The File as Input Stream
     */
    public void ReadFile(String xOff, String yOff, InputStream stream) {
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            // read from a project's resources folder
            // parse XML file
            Document doc = db.parse(stream);

            // Check if its a Maps or Waypoints XML
            getWayPointsStart(doc.getChildNodes(), xOff, yOff);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the Recursiv WayPoint reading
     * @param nodeList - The NodeList of the XML Document
     */
    public void getWayPointsStart(NodeList nodeList, String xOff, String yOff) {
        Node mapNode = nodeList.item(0);
        NamedNodeMap namedNodeMapAttr = mapNode.getAttributes();
        String start = namedNodeMapAttr.getNamedItem("start").getNodeValue();
        getNeighbor(start, nodeList, xOff, yOff);
    }

    /**
     * This Method reads all WayPoints and is also Checking for Error in the Connection of the WayPoints
     *
     * This Method is Recursiv
     * @param start - The Current WayPoint
     * @param nodeList - the NodeList of the XML Document
     */
    private void getNeighbor(String start, NodeList nodeList, String xOff, String yOff) {
        int index = WayPoints.size();
        Node mapNode = nodeList.item(0);
        NamedNodeMap namedNodeMapAttr = mapNode.getAttributes();

        double rotation = Double.parseDouble(namedNodeMapAttr.getNamedItem("rotation").getNodeValue());
        double scale = Double.parseDouble(namedNodeMapAttr.getNamedItem("scale").getNodeValue());

        for (int count = 0; count < mapNode.getChildNodes().getLength(); count++) {
            Node lineNode = mapNode.getChildNodes().item(count);
            if (lineNode.getNodeType() == Node.ELEMENT_NODE) {
                String RMNumber = lineNode.getAttributes().getNamedItem("name").getNodeValue();
                if(!RMNumber.equals(start))
                    continue;
                String x = lineNode.getAttributes().getNamedItem("x").getNodeValue();
                String y = lineNode.getAttributes().getNamedItem("y").getNodeValue();
                Vector2 p1 = new Vector2(Double.parseDouble(x)+Double.parseDouble(xOff),Double.parseDouble(y)+Double.parseDouble(yOff));

                p1 = p1.Transform(rotation);

                Vector2 nullVector = new Vector2();
                p1 = nullVector.lerp(p1,scale);
                String[] neighbors = (lineNode.getAttributes().getNamedItem("neighbours").getNodeValue()).split("/");
                String knot = lineNode.getAttributes().getNamedItem("knot").getNodeValue();
                WayPoint now = new WayPoint(start, new Vector2(Double.parseDouble(xOff)+Double.parseDouble(x), Double.parseDouble(yOff)+Double.parseDouble(y)));
                if(!knot.isEmpty()) {
                    now.sethasKnot();
                    if(!find(knot))
                        getNeighbor(knot, nodeList, xOff, yOff);
                    try {
                        now.setKnot(getWayPoint(knot));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                WayPoints.add(now);
                for(String name: neighbors) {
                    if(!find(name))
                        getNeighbor(name, nodeList, xOff, yOff);
                }
                for(String name: neighbors) {
                    try {
                        WayPoints.get(index).addNeighbourPoint(getWayPoint(name));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return;
    }

    /**
     * This Method is for the Reading of the WayPoints
     * @param name the Name of the WayPoint
     * @return true if the WayPoint exists
     */
    private boolean find(String name) {
        for(WayPoint wp: WayPoints){
            if(wp.getName().equals(name))
                return true;
        }
        return false;
    }

    /**
     * This Method Checks if the WayPoint already exists or not
     * @param name the WayPoint Name
     * @return the WayPoint
     * @throws Exception Throw a Exception if the WayPoint doesnt exists, but this Should happen because of the Check in creating them
     *
     * If this Error is Occuring then it might be that a Connection in the WayPoints is Wrong
     */
    private WayPoint getWayPoint(String name) throws Exception {
        for(int i = 0; i < WayPoints.size(); i++) {
            if(WayPoints.get(i).getName().equals(name))
                return WayPoints.get(i);
        }
        Log.e("No WayPoint found", "getWayPoint: " + name);
        throw new Exception("WayPoint doesnt exists!\t" + name);
    }

    public static ArrayList<WayPoint> getWayPoints() {
        return WayPoints;
    }
}
