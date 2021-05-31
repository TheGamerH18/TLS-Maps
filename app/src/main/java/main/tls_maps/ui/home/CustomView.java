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

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import main.tls_maps.R;

public class CustomView extends View {

    //private Bitmap Er;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap getBitmap(VectorDrawable vectorDrawable,int maxx,int maxy) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
       // vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.setBounds(0, 0, maxx, maxy);
        vectorDrawable.draw(canvas);
        //Log.e(TAG, "getBitmap: 1");
        return bitmap;
    }

    private static Bitmap getBitmap(Context context, int drawableId,int maxx,int maxy) {
        //Log.e(TAG, "getBitmap: 2");
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return BitmapFactory.decodeResource(context.getResources(), drawableId);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable,maxx,maxy);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
        Matrix matrix = new Matrix();

        RectF src = new RectF(0,0,bitmap.getWidth(),bitmap.getHeight());
        RectF dst = new RectF(0,0,reqWidth,reqHeight);

        matrix.setRectToRect(src,dst, Matrix.ScaleToFit.CENTER);

        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }


    private void init(@Nullable AttributeSet set) {
        //Er = BitmapFactory.decodeResource(getResources(), R.drawable.ic_hauptgebaeude_1stock_1_2_3);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint pp = new Paint();
        pp.setARGB(255,255,0,0);
        //canvas.drawCircle(10,10,5,pp);
        //Bitmap Er = convertDrawableResToBitmap(R.drawable.ic_hauptgebaeude_1stock_1_2_3 , 500, 500);
        Bitmap Er = getBitmap(getContext(),R.drawable.ic_hauptgebaeude_1stock_1_2_3,getWidth(),getHeight());
        //Er = getResizedBitmap(Er,getWidth(),getHeight());
        //Bitmap.createBitmap("@drawable/ic_hauptgebaeude_1stock_1_2_3");
        canvas.drawBitmap(Er,20,0,null);

        super.onDraw(canvas);
    }
}
