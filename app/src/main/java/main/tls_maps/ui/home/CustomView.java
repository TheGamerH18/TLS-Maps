package main.tls_maps.ui.home;

import android.annotation.TargetApi;
import android.content.Context;
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
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Field;

import main.tls_maps.R;

public class CustomView extends View {

    //private Bitmap Er;

    private Paint paint;

    public CustomView(Context context) {
        super(context);
        paint = new Paint();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint = new Paint();
    }


    private int getColor(String Name) {
        @ColorInt int color = Color.parseColor(Name);
        return color;
    }



    @Override
    protected void onDraw(Canvas canvas) {

        float[] verts = {
                100f,100f, //Top Left
                500f,300f, //Bottom Right
                100f,200f, //Bottom Left
                500f,200f, // Top Right
        };
        @ColorInt int ColorWanted = getColor("CYAN");
        int[] colors2 = {ColorWanted,ColorWanted,ColorWanted,ColorWanted};
        //int[] colors3 = {0xff888888,0xff888888,0xff888888,0xff888888};
        //int[] colors4 = {Color.BLACK,Color.BLACK,Color.BLACK,Color.BLACK,Color.BLACK,Color.BLACK,Color.BLACK,Color.BLACK};
        //System.out.println("MYGODPLS: "+ColorWanted);
        short[] indices = {0,2,1,0,3,1};
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_STRIP,8,verts,0, null,0,colors2,0,indices,0,6,paint);

        super.onDraw(canvas);
    }
}
