package main.tls_maps.ui.home;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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

import main.tls_maps.map.Map;
import main.tls_maps.map.Vector2;
import main.tls_maps.map.Wall;
import main.tls_maps.map.WayPoint;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class CustomView extends View {

    public static final String[] MAPNAMES = new String[] {"1stholstein", "2stholsten", "EGHolsten", "Hauptgebäude1Stock", "HauptgebäudeEg"};

    // TODO all WayPoints
    private static final String[] WAYPOINTS = new String[] {"WPEGHolsten", "WPEGHolsten.xml"};

    private ArrayList<WayPoint> WayPoints = new ArrayList<>();

    private Paint Paint;

    private Map BackGround;
    private Map CurrentMap;


    private int Level = 0;
    private Vector2 CameraPosition = new Vector2();
    private double CameraAngle = 0;

    private double ZoomScale = .5;

    private final int MinLevel = 0;
    private final int MaxLevel = 2;

    private ScaleGestureDetector ScaleDetector;

    private ArrayList<Map> Maps = new ArrayList<>(3);

    private float LastTouchX;
    private float LastTouchY;
    private double lastangle;

    /**
     * Method needed
     * @param context - The Context of the Activity
     */
    public CustomView(Context context) {
        super(context);
        try {
            init(context);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(200);
        }
    }

    /**
     * Method needed
     * @param context
     * @param attrs
     */
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        try {
            init(context);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(200);
        }
    }

    /**
     * Method needed
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            init(context);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(200);
        }
    }

    /**
     * Method needed
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        try {
            init(context);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(200);
        }
    }
  
    /**
     * Finds which Map is at the Specific Level.
     * @param level which Level is searched for.
     * @return the Map at that Level, if found.
     */
    protected Map getMapAtLevel(int level) {
        Map levelMap = null;
        for (int i = MinLevel; i< Maps.size(); i++) {
            Map tempMap = Maps.get(i);
            if (tempMap.Level == level) {
                levelMap = tempMap;
                break;
            }
        }
        return levelMap;
    }

    /**
     * This is just a Method for after the Constructor.
     * @param context Context.
     *
     * the Initialing
     * @throws Exception a Expection if there is Something critical Wrong with the WayPoint, what will kill the App
     */
    private void init(Context context) throws Exception {

        ScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

        Paint = new Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
        //CurrentMap = new Map(1,new Vector2(0,0),"Erdgeschoss");
        for (int i = 0; i<= MaxLevel; i++) {
            Map newLevelMap = new Map(i);
            Maps.add(newLevelMap);
            //Log.d("Hm",""+i);
        }

        BackGround = new Map(-500000);
        CurrentMap = getMapAtLevel(Level);
        //BackGround.addWall(new Wall(new Vector2(100,100),new Vector2(50,75),0,"#FF0000"));
        //BackGround.addWall(new Wall(new Vector2(),new Vector2(100,100),45,"BLACK"));
        BackGround.addWall(new Wall(new Vector2(0,-0.5),new Vector2(1,10000),90,"BlACK"));
        BackGround.addWall(new Wall(new Vector2(-0.5,0),new Vector2(1,10000),0,"BLACK"));
        BackGround.addWall(new Wall(new Vector2(),new Vector2(1,10000),45,"BLACK"));
        BackGround.addWall(new Wall(new Vector2(),new Vector2(1,10000),-45,"BLACK"));
        //Log.d("Test",""+String.valueOf(R.));
        for(String MapName : MAPNAMES){
            ReadFile("Maps/"+MapName+".xml");
        }
        for(String WayPoints : WAYPOINTS){
            ReadFile("WayPoints/"+WayPoints+".xml");
        }
        if((WayPoints.get(Integer.parseInt("" + (int) Math.floor(Math.random()*WayPoints.size()))).getNeighbourPoints().size()) == 0) {
            throw new Exception("Nachbar Punkte konnten nicht gesetzt werden");
        }
    }

    /**
     * This opens a XML File To read.
     * @param fileName the String Name of what File to read.
     */
    private void ReadFile(String fileName) {
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        AssetManager assetManager = getResources().getAssets();

        try {

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            // read from a project's resources folder
            InputStream stream = assetManager.open(fileName);
            Document doc = db.parse(stream);

            //System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            //System.out.println("------");

            if (doc.hasChildNodes() && fileName.contains("Maps")) {
                getLinesfromNodes(doc.getChildNodes());
            } else if(doc.hasChildNodes() && fileName.contains("WayPoints")) {
                getWayPoints(doc.getChildNodes());
            }
            Log.d("XML READING","File " + ((doc.hasChildNodes())?"":"not ") + "Found: "+fileName);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the Current Levelo of the Map
     * @return the Current Map Level
     */
    public int getLevel() {
        return this.Level;
    }
  
    /*
     * This reads the XML file that was Opened by ReadFile, Recursively on itself for nodes.
     * @param nodeList the XML Nodes that should be searched through now.
     */
    private void getLinesfromNodes(NodeList nodeList) {
        Node mapNode = nodeList.item(0);
        NamedNodeMap namedNodeMapAttr = mapNode.getAttributes();
        String level = namedNodeMapAttr.getNamedItem("Level").getNodeValue();
        String xOff = namedNodeMapAttr.getNamedItem("xoff").getNodeValue();
        String yOff = namedNodeMapAttr.getNamedItem("yoff").getNodeValue();
        Vector2 posOff = new Vector2(Double.parseDouble(xOff),Double.parseDouble(yOff));
        //Log.d("Map Name",""+MapNode.getNodeName());


        double rotation = Double.parseDouble(namedNodeMapAttr.getNamedItem("rotation").getNodeValue());
        double scale = Double.parseDouble(namedNodeMapAttr.getNamedItem("scale").getNodeValue());

        Map MapToAddTo = getMapAtLevel(Integer.parseInt(level));

        for (int count=0;count<mapNode.getChildNodes().getLength();count++) {
            Node lineNode = mapNode.getChildNodes().item(count);
            if (lineNode.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap namedNodeMap = lineNode.getAttributes();
                String stroke = namedNodeMap.getNamedItem("stroke").getNodeValue();
                String x1 = namedNodeMap.getNamedItem("x1").getNodeValue();
                String y1 = namedNodeMap.getNamedItem("y1").getNodeValue();
                String x2 = namedNodeMap.getNamedItem("x2").getNodeValue();
                String y2 = namedNodeMap.getNamedItem("y2").getNodeValue();
                Vector2 p1 = new Vector2(Double.parseDouble(x1),Double.parseDouble(y1));
                Vector2 p2 = new Vector2(Double.parseDouble(x2),Double.parseDouble(y2));

                p1 = p1.Transform(rotation);
                p2 = p2.Transform(rotation);

                Vector2 nullVector = new Vector2();
                p1 = nullVector.lerp(p1,scale);
                p2 = nullVector.lerp(p2,scale);

                Vector2 middle = p1.lerp(p2,0.5).add(posOff);
                Vector2 size = new Vector2(10,p1.sub(p2).magnitude());
                double rotationOfVector = -Math.toDegrees(p1.sub(p2).angle());

                Wall NewWall = new Wall(middle,size,rotationOfVector,stroke);
                MapToAddTo.addWall(NewWall);
                //Log.d("Wall Creation","Created wall at: "+Middle.ToString()+" with size of "+Size.ToString()+" and a rotation of "+rotation+" and color "+stroke);
                //Log.d("Xml reading","Found item: "+stroke);
            }
        }
    }

    /**
     * Small function to find a Color.
     * @param name String name of the Color.
     * @return Returns int of the Color.
     */
    private int getColor(String name) {
        @ColorInt int color = Color.parseColor(name);
        return color;
    }

    /**
     * Is in HomeFragment needed
     * @return the ArrayList of all WayPoints
     */
    public ArrayList<WayPoint> getWayPoints () {
        return this.WayPoints;
    }

    /**
     * Change Level
     * @param going change Level to X
     * Open Function for the Buttons to change the CurrentMap Level.
     * @param going If the Level is going down or up.
     */
    protected void ChangeLevel(int going) {
        Level = Math.min(Math.max(Level +going, MinLevel), MaxLevel);
        CurrentMap = getMapAtLevel(Level);
    }

    private int ActivePointerId = INVALID_POINTER_ID;

    /**
     * This Is for the Movemnt tracking on the Map
     * @param event the Event self
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

            final int action = ev.getAction();

            if(ev.getPointerCount() >= 2) {

                if (ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                    lastangle = calcAngle(ev);
                }

                // TODO Skalierung zum mittelpunkt von x1,y1 und x2,y2
                /**
                 * Bitte das System nicht auf Velocity umstellen, es entstehen dadurch Sprünge
                 * einfach Offsets nehmen
                 */

                // Calculate the angle of the Rotation
                double angle = this.calcAngle(ev);
                this.CameraAngle +=(lastangle-angle);

                Vector2 pointer0 = new Vector2(ev.getX(0),ev.getY(0));
                Vector2 pointer1 = new Vector2(ev.getX(1),ev.getY(1));

                Vector2 _center = pointer0.lerp(pointer1,0.5);
                Vector2 screenMiddle = new Vector2(getWidth()/2,getHeight()/2);
                Vector2 centertoscreenMiddle = screenMiddle.sub(_center);

                Vector2 pivotPoint = centertoscreenMiddle.add(CameraPosition);



                //CameraPosition = CameraPosition.sub(pivotPoint).Transform(CameraAngle).add(pivotPoint);



                //double centerX = (ev.getX(0)+ev.getX(1))/2;
                //double centerY = (ev.getY(0)+ev.getY(1))/2;

                //Vector2 otherCenter = new Vector2(centerX,centerY);
                //

                //Vector2 RootScreenSize = new Vector2(MainActivity.displayMetrics.widthPixels,MainActivity.displayMetrics.heightPixels);



                //Log.d("REEEE",""+ev.getSize());

                //Vector2 Center = _center.sub(screenMiddle);
                //Position = Position.add(Center).Transform((lastangle-angle)).sub(Center);
                lastangle = angle;
                //this.GlobRotation = (rotation == -1)?this.GlobRotation : rotation;


                // Hier wird nach events geprüft
                ScaleDetector.onTouchEvent(ev);
                return true;
            }

            switch (action) {

                case MotionEvent.ACTION_DOWN: {

                    // get the Coordinates
                    final float x = ev.getX();
                    final float y = ev.getY();

                    // Remember where we started (for dragging)
                    LastTouchX = x;
                    LastTouchY = y;

                    // Save the ID of this pointer (for dragging)
                    ActivePointerId = ev.getActionIndex();
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    // get the Coordinates
                    final float x = ev.getX();
                    final float y = ev.getY();

                    // Calculate the distance moved
                    final float dx = x - LastTouchX;
                    final float dy = y - LastTouchY;

                    // Set the Pos
                    CameraPosition = CameraPosition.add(new Vector2(-dx,-dy).Transform(-CameraAngle));

                    invalidate();

                    // Remember this touch position for the next move event
                    LastTouchX = x;
                    LastTouchY = y;

                    break;
                }

                case MotionEvent.ACTION_UP: {
                    ActivePointerId = INVALID_POINTER_ID;
                    break;
                }

                case MotionEvent.ACTION_CANCEL: {
                    ActivePointerId = INVALID_POINTER_ID;
                    break;
                }

                case MotionEvent.ACTION_POINTER_UP: {

                    final int pointerId = ev.getActionIndex();

                    if (pointerId == ActivePointerId) {
                        // This was our active pointer going up. Choose a new
                        // active pointer and adjust accordingly.
                        final int newPointerIndex = pointerId == 0 ? 1 : 0;
                        LastTouchX = ev.getX();
                        LastTouchY = ev.getY();
                        ActivePointerId = ev.getPointerId(newPointerIndex);
                    }
                    break;
                }
            }
            return true;
    }
  
  
    /*
     *  This Method is need for the 2 Finger Rotaion, and also for debug the angle
     * @param ev the MotionEvent is needed to get all Coordinations
     * @return the angle that need to be set, there is also a Debug inside of the Methode for the >= 180° problem with triangles
     */

    /**
     * This calculates the Angle the Pointers have been Rotated by.
     * @param ev MotionEvent.
     * @return Double of the Angle that it was turned by.
     */
    private double calcAngle(MotionEvent ev) {

        // This is just calculation for the Circle wich is needed to make a correct Angle
        // punktDis is the point that can be do magic,
        // With this point is should be possible to make
        // the Rotation start not hopping, but there can be
        // conflicts with the readjustment for 45° angles
        //double  distance = Math.sqrt((Math.pow(ev.getX(0)-ev.getX(1),2)+Math.pow(ev.getY(0)-ev.getY(1),2))),
        //r = distance/2,
        double centerX = (ev.getX(0)+ev.getX(1))/2;
        double centerY = (ev.getY(0)+ev.getY(1))/2;
        //centerDis = Math.sqrt((Math.pow(ev.getX(0)-centerX,2)+Math.pow(ev.getY(0)-centerY,2))),
        ///punktDis = Math.sqrt((Math.pow(ev.getX(0)-centerX,2))),
        //angle = Math.atan(punktDis/centerDis)*(180/Math.PI);

        //if(r-centerDis > 2 && r-centerDis < -2)
        //    return -1;

        // Lets Fix the Angle, because its impossible for a Triangle to have a angle that is greater or equal than 180°
        // and we work with triangles here

        // Be Careful with this, little Changes can make it Jump
        /*if(ev.getY(0) <= ev.getY(1) && ev.getX(0) <= ev.getX(1))
            angle = -(angle + 180)*2;
        else if(ev.getY(0) <= ev.getY(1) && ev.getX(0) >= ev.getX(1))
            angle = angle - 90;
        else if(ev.getY(0) >= ev.getY(1) && ev.getX(0) <= ev.getX(1))
            angle = angle*2;
        else if(ev.getY(0) >= ev.getY(1) && ev.getX(0) >= ev.getX(1))
            angle = -angle;*/


        Vector2 E = new Vector2(ev.getX(0)-centerX,ev.getY(0)-centerY);
        double angle = Math.toDegrees(E.unit().angle()+360);
        //Log.d("Position Debug", E.unit().ToString()+" Angle: "+angle);


        //Vector2 screenMiddle = new Vector2(getWidth()/2,getHeight()/2);

        //Vector2 Center = new Vector2(centerX,centerY).sub(screenMiddle);

        //Position = Position.Transform((angle-lastangle));//-ByPass.unit().angle())
        return angle;
    }

    /**
     * This Offets the Positions to the Camera.
     * @param topLeft the topleft Vertice Position.
     * @param topRight the topright Vertice Position.
     * @param bottomRight the bottomright Vertice Position.
     * @param bottomLeft the bottomleft Vertice Position.
     * @return an Array of float marking the positions of each Vertice to draw as x,y,x,y...
     */
    private float[] getCameraOffset(Vector2 topLeft, Vector2 topRight, Vector2 bottomRight, Vector2 bottomLeft) {
        Vector2 screenMiddle = new Vector2(getWidth()/2,getHeight()/2);

        // First subtract from the Corner Position the Camera Position to get an Offset
        // transform that around the Camera with CameraAngle and getting the rotated
        // And finally add screenMiddle to account for Canvas starting at the top left to bottom right
        topLeft = topLeft.sub(CameraPosition).Transform(CameraAngle).add(screenMiddle);
        topRight = topRight.sub(CameraPosition).Transform(CameraAngle).add(screenMiddle);
        bottomLeft = bottomLeft.sub(CameraPosition).Transform(CameraAngle).add(screenMiddle);
        bottomRight = bottomRight.sub(CameraPosition).Transform(CameraAngle).add(screenMiddle);

        // Now Return an float array of x,y Positions
        return new float[] {
                (float) topLeft.x, (float) topLeft.y,
                (float) bottomRight.x, (float) bottomRight.y,
                (float) bottomLeft.x, (float) bottomLeft.y,
                (float) topRight.x, (float) topRight.y,
        };
    }
  
    /*
     * This draws the Actual Line, used for Walls and Waypoint Lines.
     * @param position this determines where the Line is Positioned.
     * @param size this determines the size of the Line (NOTE: x is limited to 4).
     * @param rotation this determines the rotation of the Line.
     * @param color this is the Color of the line.
     * @param canvas the Object to draw on.
     */
    private void drawLine(Vector2 position, Vector2 size, double rotation, String color, Canvas canvas) {
        // Apply ZoomScale to Position and Size
        position = position.mul(ZoomScale);
        size = new Vector2(size.x<=4?size.x:4,size.y*ZoomScale);

        // Calculate the 4 Corners of the Vertices for Lines
        Vector2 topRight = position.add(size.mul(0.5).Transform(rotation));
        Vector2 bottomLeft = position.add(size.mul(-0.5).Transform(rotation));
        Vector2 topLeft = position.add(new Vector2(-size.x/2,size.y/2).Transform(rotation));
        Vector2 bottomRight = position.add(new Vector2(size.x/2,-size.y/2).Transform(rotation));

        int colorWanted = getColor(color);

        // Draw a Triangle on the Canvas
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_STRIP,
                8,
                getCameraOffset(topLeft,topRight,bottomRight,bottomLeft),
                0,
                null,
                0,
                new int[] {colorWanted,colorWanted,colorWanted,colorWanted},
                0,
                new short[] {0,2,1,0,3,1},
                0,
                6,
                Paint);
        postInvalidate();
    }
  
    /*
     * This draws all the Walls from a Specific Map.
     * @param canvas the Object to draw on.
     * @param mapToDraw the Map which to get the Walls from.
     */
    private void drawMap(Canvas canvas, Map mapToDraw) {
        for (int i=0;i<mapToDraw.WallsOnMap.size();i++) {
            Wall thisWall = mapToDraw.WallsOnMap.get(i);
            drawLine(thisWall.Position,thisWall.Size,thisWall.Rotation,thisWall.Color,canvas);
        }
    }
  
    /**
     * This gets Repeatedly activated by a parent Class, it gives the Canvas to draw on.
     * @param canvas the Object to draw on.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // Turn the Background Gray, this can be removed cause it causes the button background to be different.
        canvas.drawColor(Color.LTGRAY);

        // First draw the Background as the lowest Layer to get overdrawn by others.
        drawMap(canvas, BackGround);

        // Draw the Current Map, like Rooms hallways and such.
        drawMap(canvas, CurrentMap);

        // Draw the Waypoints and their Connections, for the Route.
        if (RouteSet) {
            // Change the "GREEN" Color for what you need/want.
            drawWayPoints(canvas, CurrentMap.WayPointsOnMap,"GREEN");
        }

        super.onDraw(canvas);
    }
  
    /**
     * This draws a Line between two Waypoints.
     * @param canvas the Object to drawn on.
     * @param wp1 Waypoint 1
     * @param wp2 Waypoint 2
     */
    private void drawLine_WayPoint(Canvas canvas, WayPoint wp1, WayPoint wp2,String Color) {
        // Calculates all the needed information to draw the Line between the two Waypoints.
        Vector2 position = wp1.getPosition().lerp(wp2.getPosition(),0.5);
        Vector2 size = new Vector2(3,wp1.getPosition().sub(wp2.getPosition()).magnitude());
        double rotation = -Math.toDegrees(wp1.getPosition().sub(wp2.getPosition()).angle());
        drawLine(position,size,rotation,Color,canvas);
    }

    /**
     * This is for Drawing an Array of WayPoints, made for Routes.
     * @param canvas the Object to draw on.
     * @param wayPoints the ArrayList of WayPoints which tell the Route to go.
     */
    private void drawWayPoints(Canvas canvas, ArrayList<WayPoint> wayPoints,String Color) {
        WayPoint lastWP = null;
        Vector2 screenMiddle = new Vector2(getWidth()/2,getHeight()/2);
        // WayPoint Size
        double size = 1*ZoomScale;

        // Loop through all Waypoints to draw them
        for (int i=0;i<wayPoints.size();i++) {
            WayPoint currentWayPoint = wayPoints.get(i);
            Vector2 ActualPosition = currentWayPoint.getPosition().mul(ZoomScale).sub(CameraPosition).Transform(CameraAngle).add(screenMiddle);
            Paint.setColor(getColor(Color));
            canvas.drawCircle((float) (ActualPosition.x),(float) (ActualPosition.y), (float) size,Paint);

            // Check if a previous Waypoint exists.
            if (lastWP != null) {
                // Draw a Line between Current and Last Waypoint.
                drawLine_WayPoint(canvas,currentWayPoint,lastWP,Color);
            }
            // Overwrite the Last to the Current Waypoint.
            lastWP = currentWayPoint;
        }
    }

    private boolean RouteSet = true;
  
    /**
     * Start the Recursiv WayPoint reading
     * @param nodeList - The NodeList of the XML Document
     */
    private void getWayPoints(NodeList nodeList) {
        Node mapNode = nodeList.item(0);
        NamedNodeMap namedNodeMapAttr = mapNode.getAttributes();
        String start = namedNodeMapAttr.getNamedItem("start").getNodeValue();
        getNeighbor(start, nodeList);
    }

    /**
     * This Method reads all WayPoints and is also Checking for Error in the Connection of the WayPoints
     *
     * This Method is Recursiv
     * @param start - The Current WayPoint
     * @param nodeList - the NodeList of the XML Document
     */
    private void getNeighbor(String start, NodeList nodeList ) {
        int index = WayPoints.size();
        Node mapNode = nodeList.item(0);
        NamedNodeMap namedNodeMapAttr = mapNode.getAttributes();
        int lvl = Integer.parseInt(namedNodeMapAttr.getNamedItem("level").getNodeValue());
        double rotation = Double.parseDouble(namedNodeMapAttr.getNamedItem("rotation").getNodeValue());

        for (int count = 0; count < mapNode.getChildNodes().getLength(); count++) {
            Node lineNode = mapNode.getChildNodes().item(count);
            if (lineNode.getNodeType() == Node.ELEMENT_NODE) {
                String RMNumber = lineNode.getAttributes().getNamedItem("name").getNodeValue();
                if(!RMNumber.equals(start))
                    continue;
                String x = lineNode.getAttributes().getNamedItem("x").getNodeValue();
                String y = lineNode.getAttributes().getNamedItem("y").getNodeValue();
                String[] neighbors = (lineNode.getAttributes().getNamedItem("neighbours").getNodeValue()).split("/");
                String knot = lineNode.getAttributes().getNamedItem("knot").getNodeValue();
                WayPoint now = new WayPoint(start, new Vector2(Double.parseDouble(x), Double.parseDouble(y)), lvl);
                if(!knot.isEmpty()) {
                    now.sethasKnot();
                    if(!find(knot))
                        getNeighbor(knot, nodeList);
                    try {
                        now.setKnot(getWayPoint(knot));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                WayPoints.add(now);
                Log.d("TAG", "created: " + start);
                for(String name: neighbors) {
                    if(!find(name))
                        getNeighbor(name, nodeList);
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
     * This Methode Checks if the WayPoint already exits or not
     * @param name the WayPoint Name
     * @return the WayPoint
     * @throws Exception Throw a Exception if the WayPoint doesnt exists, but this Should happen because of the Check in creating them
     *
     * If this Error Occruing then it might be that a Connection in the WayPoints is Wrong
     */
    private WayPoint getWayPoint(String name) throws Exception {
        for(int i = 0; i < WayPoints.size(); i++) {
            if(WayPoints.get(i).getName().equals(name))
                return WayPoints.get(i);
        }
        Log.d("Not Found", "getWayPoint: " + name);
        throw new Exception("WayPoint doesnt exists!\t" + name);
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

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // Apply the Change in Scale
            ZoomScale *= detector.getScaleFactor();

            // Keep the Position the same.
            if (ZoomScale >= 0.25 && ZoomScale <= 2) CameraPosition = CameraPosition.mul(detector.getScaleFactor());

            // Don't let the object get too small or too large.
            ZoomScale = Math.max(0.25f, Math.min(ZoomScale, 2.0f));

            invalidate();
            return true;
        }
    }
    // TODO -Add a better Rotation
}

