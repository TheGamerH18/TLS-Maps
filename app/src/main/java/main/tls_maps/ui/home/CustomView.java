package main.tls_maps.ui.home;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import main.tls_maps.R;

public class CustomView extends View {

    private Paint paint;

    private Map BackGround;
    private Map CurrentMap;
    private int Level = 0;

    private Vector2 Position = new Vector2();
    private double Scale = .5;
    private double GlobRotation = 0;

    private final int min_Level = 0;
    private final int max_Level = 2;

    private ArrayList<Map> Maps = new ArrayList<Map>();

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
        Map LevelMap = null;
        for (int i=min_Level;i<Maps.size();i++) {
            Map TempMap = Maps.get(i);
            if (TempMap.Level == Level) {
                LevelMap = TempMap;
                break;
            }
        }
        return LevelMap;
    }

    private void init() {
        paint = new Paint();
        //CurrentMap = new Map(1,new Vector2(0,0),"Erdgeschoss");
        for (int i=0;i<=max_Level;i++) {
            Map NewLevelMap = new Map(i);
            Maps.add(NewLevelMap);
            //Log.d("Hm",""+i);
        }
        BackGround = new Map(-500000);
        CurrentMap = getMapAtLevel(Level);
        //BackGround.AddWall(new Wall(new Vector2(100,100),new Vector2(50,75),0,"#FF0000"));
        BackGround.AddWall(new Wall(new Vector2(),new Vector2(100,100),45,"BLACK"));
        BackGround.AddWall(new Wall(new Vector2(),new Vector2(10000,1),0,"CYAN"));
        BackGround.AddWall(new Wall(new Vector2(),new Vector2(1,10000),0,"BLACK"));
        BackGround.AddWall(new Wall(new Vector2(),new Vector2(1,10000),45,"BLACK"));
        BackGround.AddWall(new Wall(new Vector2(),new Vector2(1,10000),-45,"BLACK"));
        //Log.d("Test",""+String.valueOf(R.));
        ReadFile("1stholstein.xml");
        ReadFile("2stholstein.xml");
        ReadFile("EGHolsten.xml");
        ReadFile("Hauptgebäude1Stock.xml");
        ReadFile("HauptgebäudeEg.xml");
    }


    private void ReadFile(String FILENAME) {
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        AssetManager assetmanager = getResources().getAssets();

        try {

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            // read from a project's resources folder
            InputStream stream = assetmanager.open("Maps/"+FILENAME);
            Document doc = db.parse(stream);

            //System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            //System.out.println("------");

            if (doc.hasChildNodes()) {
                //printNote(doc.getChildNodes());
                Log.d("XML READING","File Found: "+FILENAME);
                getLinesfromNodes(doc.getChildNodes());

            } else {
                Log.d("XML READING","File Not Found: "+FILENAME);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void getLinesfromNodes(NodeList nodeList) {
        Node MapNode = nodeList.item(0);
        NamedNodeMap namedNodeMapAttr = MapNode.getAttributes();
        String Level = namedNodeMapAttr.getNamedItem("Level").getNodeValue();
        String xoff = namedNodeMapAttr.getNamedItem("xoff").getNodeValue();
        String yoff = namedNodeMapAttr.getNamedItem("yoff").getNodeValue();
        Vector2 PosOff = new Vector2(Double.parseDouble(xoff),Double.parseDouble(yoff));
        //Log.d("Map Name",""+MapNode.getNodeName());


        double Rotation = Double.parseDouble(namedNodeMapAttr.getNamedItem("rotation").getNodeValue());
        double Scale = Double.parseDouble(namedNodeMapAttr.getNamedItem("scale").getNodeValue());

        Map MapToAddTo = getMapAtLevel(Integer.parseInt(Level));

        for (int count=0;count<MapNode.getChildNodes().getLength();count++) {
            Node LineNode = MapNode.getChildNodes().item(count);
            //Log.d("XML NODE TYPE",""+LineNode.getNodeType());
            if (LineNode.getNodeType() == Node.ELEMENT_NODE) {
                //Log.d("Node Name", "" + LineNode.getNodeName());
                NamedNodeMap namedNodeMap = LineNode.getAttributes();
                String stroke = namedNodeMap.getNamedItem("stroke").getNodeValue();
                String x1 = namedNodeMap.getNamedItem("x1").getNodeValue();
                String y1 = namedNodeMap.getNamedItem("y1").getNodeValue();
                String x2 = namedNodeMap.getNamedItem("x2").getNodeValue();
                String y2 = namedNodeMap.getNamedItem("y2").getNodeValue();
                Vector2 p1 = new Vector2(Double.parseDouble(x1),Double.parseDouble(y1));
                Vector2 p2 = new Vector2(Double.parseDouble(x2),Double.parseDouble(y2));

                p1 = p1.Transform(Rotation);
                p2 = p2.Transform(Rotation);

                Vector2 nullvector = new Vector2();
                p1 = nullvector.lerp(p1,Scale);
                p2 = nullvector.lerp(p2,Scale);

                Vector2 Middle = p1.lerp(p2,0.5).add(PosOff);
                Vector2 Size = new Vector2(10,p1.sub(p2).magnitude());
                double rotation = -Math.toDegrees(p1.sub(p2).angle());

                Wall NewWall = new Wall(Middle,Size,rotation,stroke);
                MapToAddTo.AddWall(NewWall);
                //Log.d("Wall Creation","Created wall at: "+Middle.ToString()+" with size of "+Size.ToString()+" and a rotation of "+rotation+" and color "+stroke);
                //Log.d("Xml reading","Found item: "+stroke);
            }
        }
    }

    private void printNote(NodeList nodeList) {

        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            Log.d("XML NODE TYPE",""+tempNode.getNodeType());
            if (tempNode.getNodeType() == Node.ATTRIBUTE_NODE) {

                // get node name and value
                System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
                System.out.println("Node Value =" + tempNode.getTextContent());
                String Name = tempNode.getNodeName();
                if (Name == "line") {

                    Log.d("XML READER","Found List Item ");

                    //Instance Inst = clazz.newInstance();
                    NodeList Properties = tempNode.getChildNodes();
                    for (int c=0;c<Properties.getLength();c++) {
                        Node vtempNode = Properties.item(c);
                        String Prop_Name = vtempNode.getNodeName();
                    }

                }

				/*if (tempNode.hasAttributes()) {

					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : " + node.getNodeValue());
					}

				}*/

                if (tempNode.hasChildNodes()) {
                    // loop again if has child nodes
                    printNote(tempNode.getChildNodes());
                }

            }
        }
    }

    private int getColor(String Name) {
        @ColorInt int color = Color.parseColor(Name);
        return color;
    }

    protected void ChangeLevel(int Going) {
        Level = Math.min(Math.max(Level+Going,min_Level),max_Level);
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
        int ColorWanted = getColor(Color);
        int[] colors = {ColorWanted,ColorWanted,ColorWanted,ColorWanted};
        short[] indices = {0,2,1,0,3,1};
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_STRIP,8,verts,0, null,0,colors,0,indices,0,6,paint);
        postInvalidate();
    }

    private void drawMap(Canvas canvas,Map MapToDraw) {
        for (int i=0;i<MapToDraw.WallsOnMap.size();i++) {
            Wall ThisWall = MapToDraw.WallsOnMap.get(i);
            drawLine(ThisWall.Position,ThisWall.Size,ThisWall.Rotation,ThisWall.Color,canvas);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawMap(canvas,BackGround);

        drawMap(canvas,CurrentMap);

        //canvas.drawText("Position: "+Position.ToString(),50,50,paint);
        super.onDraw(canvas);
    }

    /*TODO
    - Hand Movements for scaling, rotation
    - Add a better Rotation
    
     */
}
