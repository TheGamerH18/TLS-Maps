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
import android.view.VelocityTracker;
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

public class CustomView extends View {

    public static final String[] MAPNAMES = new String[] {"1stholstein", "2stholstein", "EGHolsten", "Hauptgebäude1Stock", "HauptgebäudeEg"};

    private Paint Paint;

    private Map BackGround;
    private Map CurrentMap;
    private int Level = 0;

    private Vector2 Position = new Vector2();
    private double Scale = .5;
    private double GlobRotation = 0;

    private final int MinLevel = 0;
    private final int MaxLevel = 2;

    private ArrayList<Map> Maps = new ArrayList<Map>(3);

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private Map getMapAtLevel(int level) {
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

    private void init() {
        Paint = new Paint();
        //CurrentMap = new Map(1,new Vector2(0,0),"Erdgeschoss");
        for (int i = 0; i<= MaxLevel; i++) {
            Map newLevelMap = new Map(i);
            Maps.add(newLevelMap);
            //Log.d("Hm",""+i);
        }
        BackGround = new Map(-500000);
        CurrentMap = getMapAtLevel(Level);
        BackGround.addWall(new Wall(new Vector2(100,100),new Vector2(50,75),0,"#FF0000"));
        BackGround.addWall(new Wall(new Vector2(),new Vector2(100,100),45,"BLACK"));
        BackGround.addWall(new Wall(new Vector2(),new Vector2(10000,1),0,"CYAN"));
        BackGround.addWall(new Wall(new Vector2(),new Vector2(1,10000),0,"BLACK"));
        BackGround.addWall(new Wall(new Vector2(),new Vector2(1,10000),45,"BLACK"));
        BackGround.addWall(new Wall(new Vector2(),new Vector2(1,10000),-45,"BLACK"));
        //Log.d("Test",""+String.valueOf(R.));
        for(String MapName : MAPNAMES){
            ReadFile(MapName+".xml");
        }
    }


    private void ReadFile(String fileName) {
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        AssetManager assetManager = getResources().getAssets();

        try {

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            // read from a project's resources folder
            InputStream stream = assetManager.open("Maps/"+fileName);
            Document doc = db.parse(stream);

            //System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            //System.out.println("------");

            if (doc.hasChildNodes()) {
                getLinesfromNodes(doc.getChildNodes());
            }
            Log.d("XML READING","File " + ((doc.hasChildNodes())?"":"not ") + "Found: "+fileName);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

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
            //Log.d("XML NODE TYPE",""+LineNode.getNodeType());
            if (lineNode.getNodeType() == Node.ELEMENT_NODE) {
                //Log.d("Node Name", "" + LineNode.getNodeName());
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

    private int getColor(String name) {
        @ColorInt int color = Color.parseColor(name);
        return color;
    }

    protected void ChangeLevel(int going) {
        Level = Math.min(Math.max(Level +going, MinLevel), MaxLevel);
        CurrentMap = getMapAtLevel(Level);
    }

    private VelocityTracker mVelocityTracker = null;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                if(mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the
                    // velocity of a motion.
                    mVelocityTracker = VelocityTracker.obtain();
                }
                else {
                    // Reset the velocity tracker back to its initial state.
                    mVelocityTracker.clear();
                }
                // Add a user's movement to the tracker.
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                // When you want to determine the velocity, call
                // computeCurrentVelocity(). Then call getXVelocity()
                // and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1000);
                // Log velocity of pixels per second
                // Best practice to use VelocityTrackerCompat where possible.
                //Log.d("", "X velocity: " + mVelocityTracker.getXVelocity(pointerId));
                //Log.d("", "Y velocity: " + mVelocityTracker.getYVelocity(pointerId));
                Vector2 Velocity = new Vector2(mVelocityTracker.getXVelocity(),mVelocityTracker.getYVelocity());
                Position = Position.sub(Velocity.div(50).clamp(200,-200).Transform(-GlobRotation));
                //Log.d("Position Debug","Position at: " + Position.ToString()+ " with a last Velocity of: " + Velocity.ToString());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Return a VelocityTracker object back to be re-used by others.
                mVelocityTracker.recycle();
                break;
        }
        return true;
    }

    private float[] getCameraOffset(Vector2 topLeft, Vector2 topRight, Vector2 bottomRight, Vector2 bottomLeft) {
        Vector2 screenMiddle = new Vector2(getWidth()/2,getHeight()/2);
        topLeft = topLeft.sub(Position).Transform(GlobRotation).add(screenMiddle);
        topRight = topRight.sub(Position).Transform(GlobRotation).add(screenMiddle);
        bottomLeft = bottomLeft.sub(Position).Transform(GlobRotation).add(screenMiddle);
        bottomRight = bottomRight.sub(Position).Transform(GlobRotation).add(screenMiddle);

        float[] verts = {
                (float) topLeft.x, (float) topLeft.y,
                (float) bottomRight.x, (float) bottomRight.y,
                (float) bottomLeft.x, (float) bottomLeft.y,
                (float) topRight.x, (float) topRight.y,
        };
        return verts;
    }

    private void drawLine(Vector2 position, Vector2 size, double rotation, String color, Canvas canvas) {

        position = position.mul(Scale);
        size = new Vector2(10,size.mul(Scale).y);

        Vector2 topRight = position.add(size.mul(0.5).Transform(rotation));
        Vector2 bottomLeft = position.add(size.mul(-0.5).Transform(rotation));
        Vector2 topLeft = position.add(new Vector2(-size.x/2,size.y/2).Transform(rotation));
        Vector2 bottomRight = position.add(new Vector2(size.x/2,-size.y/2).Transform(rotation));

        float[] verts = getCameraOffset(topLeft,topRight,bottomRight,bottomLeft);
        int colorWanted = getColor(color);
        int[] colors = {colorWanted,colorWanted,colorWanted,colorWanted};
        short[] indices = {0,2,1,0,3,1};
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_STRIP,8,verts,0, null,0,colors,0,indices,0,6, Paint);
        postInvalidate();
    }

    private void drawMap(Canvas canvas, Map mapToDraw) {
        for (int i=0;i<mapToDraw.WallsOnMap.size();i++) {
            Wall thisWall = mapToDraw.WallsOnMap.get(i);
            drawLine(thisWall.Position,thisWall.Size,thisWall.Rotation,thisWall.Color,canvas);
        }
    }

    private void drawWayPoints(Canvas canvas, ArrayList<WayPoint> wayPoints) {
        for (int i=0;i<wayPoints.size();i++) {
            WayPoint currentWayPoint = wayPoints.get(i);
            canvas.drawCircle(200,200,50,Paint);
        }
    }

    private boolean WayPointDebug = true;

    @Override
    protected void onDraw(Canvas canvas) {
        drawMap(canvas, BackGround);

        if (WayPointDebug) {
            //drawWayPoints();
        }

        drawMap(canvas, CurrentMap);

        //canvas.drawText("Position: "+Position.ToString(),50,50,paint);
        super.onDraw(canvas);
    }

    // TODO - Hand Movements for scaling, rotation | -Add a better Rotation
}
