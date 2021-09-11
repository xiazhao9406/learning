package com.xiazhao.learning.mymirror.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.xiazhao.learning.mymirror.R;

public class PictureView extends ImageView {

    private int[] bitmap_id_array;
    private Canvas mCanvas;
    private int draw_width;
    private int draw_height;
    private Bitmap mBitmap;
    private int bitmap_index;


    public PictureView(Context context) {
        this(context, null);
    }

    public PictureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getTheWindowSize((Activity) context);
        init();
    }

    private void initBitmaps() {
        bitmap_id_array = new int[] {R.drawable.mag_0001, R.drawable.mag_0003, R.drawable.mag_0005,
                R.drawable.mag_0006, R.drawable.mag_0007, R.drawable.mag_0008, R.drawable.mag_0009,
                R.drawable.mag_0011, R.drawable.mag_0012, R.drawable.mag_0014};
    }

    private void init() {
        initBitmaps();
        bitmap_index = 0;
        mBitmap = Bitmap.createBitmap(draw_width, draw_height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.TRANSPARENT);
    }

    public void setPhotoFrame(int index) {
        bitmap_index = index;
        invalidate();
    }

    public int getPhotoFrames() {
        return bitmap_index;
    }

    private void getTheWindowSize(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        draw_width = dm.widthPixels;
        draw_height = dm.heightPixels;
        Log.e("1, the width is " , draw_width + "\t\tthe height is :"  + draw_height);
    }

    private Bitmap getNewBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), bitmap_id_array[bitmap_index]).copy(Bitmap.Config.ARGB_8888, true);
        bitmap = Bitmap.createScaledBitmap(bitmap, draw_width, draw_height, true);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        Rect rect1 = new Rect(0, 0, this.getWidth(), getHeight());
        canvas.drawBitmap(getNewBitmap(), null, rect1, null);
    }

}
