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

public class CustomView extends View {

    public static final String[] MAPNAMES = new String[] {"1stholstein", "2stholstein", "EGHolsten", "Hauptgebäude1Stock", "HauptgebäudeEg"};

    private Paint Paint;

    private Map BackGround;
    private Map CurrentMap;
    private int Level = 0;

    private Vector2 Position = new Vector2();
    private double Scale = .5;
    private double GlobRotation = 0;

    private final int Min_Level = 0;
    private final int Max_Level = 2;

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

    private Map getMapAtLevel(int Level) {
        Map levelMap = null;
        for (int i = Min_Level; i< Maps.size(); i++) {
            Map TempMap = Maps.get(i);
            if (TempMap.Level == Level) {
                levelMap = TempMap;
                break;
            }
        }
        return levelMap;
    }

    private void init() {
        Paint = new Paint();
        //CurrentMap = new Map(1,new Vector2(0,0),"Erdgeschoss");
        for (int i = 0; i<= Max_Level; i++) {
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

        AssetManager assetmanager = getResources().getAssets();

        try {

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            // read from a project's resources folder
            InputStream stream = assetmanager.open("Maps/"+fileName);
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


        double Rotation = Double.parseDouble(namedNodeMapAttr.getNamedItem("rotation").getNodeValue());
        double Scale = Double.parseDouble(namedNodeMapAttr.getNamedItem("scale").getNodeValue());

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

                p1 = p1.Transform(Rotation);
                p2 = p2.Transform(Rotation);

                Vector2 nullVector = new Vector2();
                p1 = nullVector.lerp(p1,Scale);
                p2 = nullVector.lerp(p2,Scale);

                Vector2 middle = p1.lerp(p2,0.5).add(posOff);
                Vector2 size = new Vector2(10,p1.sub(p2).magnitude());
                double rotation = -Math.toDegrees(p1.sub(p2).angle());

                Wall NewWall = new Wall(middle,size,rotation,stroke);
                MapToAddTo.addWall(NewWall);
                //Log.d("Wall Creation","Created wall at: "+Middle.ToString()+" with size of "+Size.ToString()+" and a rotation of "+rotation+" and color "+stroke);
                //Log.d("Xml reading","Found item: "+stroke);
            }
        }
    }

    private int getColor(String Name) {
        @ColorInt int color = Color.parseColor(Name);
        return color;
    }

    protected void ChangeLevel(int Going) {
        Level = Math.min(Math.max(Level +Going, Min_Level), Max_Level);
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

    private float[] getCameraOffset(Vector2 TopLeft, Vector2 TopRight, Vector2 BottomRight, Vector2 BottomLeft) {
        Vector2 ScreenMiddle = new Vector2(getWidth()/2,getHeight()/2);
        TopLeft = TopLeft.sub(Position).Transform(GlobRotation).add(ScreenMiddle);
        TopRight = TopRight.sub(Position).Transform(GlobRotation).add(ScreenMiddle);
        BottomLeft = BottomLeft.sub(Position).Transform(GlobRotation).add(ScreenMiddle);
        BottomRight = BottomRight.sub(Position).Transform(GlobRotation).add(ScreenMiddle);

        float[] verts = {
                (float) TopLeft.x, (float) TopLeft.y,
                (float) BottomRight.x, (float) BottomRight.y,
                (float) BottomLeft.x, (float) BottomLeft.y,
                (float) TopRight.x, (float) TopRight.y,
        };
        return verts;
    }

    private void drawLine(Vector2 Position, Vector2 Size, double Rotation,String Color,Canvas canvas) {

        Position = Position.mul(Scale);
        Size = new Vector2(10,Size.mul(Scale).y);

        Vector2 TopRight = Position.add(Size.mul(0.5).Transform(Rotation));
        Vector2 BottomLeft = Position.add(Size.mul(-0.5).Transform(Rotation));
        Vector2 TopLeft = Position.add(new Vector2(-Size.x/2,Size.y/2).Transform(Rotation));
        Vector2 BottomRight = Position.add(new Vector2(Size.x/2,-Size.y/2).Transform(Rotation));

        float[] verts = getCameraOffset(TopLeft,TopRight,BottomRight,BottomLeft);
        int colorWanted = getColor(Color);
        int[] colors = {colorWanted,colorWanted,colorWanted,colorWanted};
        short[] indices = {0,2,1,0,3,1};
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_STRIP,8,verts,0, null,0,colors,0,indices,0,6, Paint);
        postInvalidate();
    }

    private void drawMap(Canvas canvas,Map mapToDraw) {
        for (int i=0;i<mapToDraw.WallsOnMap.size();i++) {
            Wall ThisWall = mapToDraw.WallsOnMap.get(i);
            drawLine(ThisWall.Position,ThisWall.Size,ThisWall.Rotation,ThisWall.Color,canvas);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawMap(canvas, BackGround);

        drawMap(canvas, CurrentMap);

        //canvas.drawText("Position: "+Position.ToString(),50,50,paint);
        super.onDraw(canvas);
    }

    // TODO - Hand Movements for scaling, rotation | -Add a better Rotation | -Refactor?
}
