package com.xiazhao.learning.mymirror.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.xiazhao.learning.mymirror.R;

public class DrawView extends View {

    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float moveX, moveY;
    private Bitmap mBitmap;
    private Bitmap bitmap;
    private volatile boolean mComplete = false;

    public interface OnCaYiCaCompleteListener {
        void complete();
    }

    private OnCaYiCaCompleteListener mListener;

    public void setOnCaYiCaCompleteListener(OnCaYiCaCompleteListener mListener) {
        this.mListener = mListener;
    }

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.glasses).copy(Bitmap.Config.ARGB_8888, true);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(100);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        if (!mComplete) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            canvas.drawBitmap(mBitmap, 0, 0, null);
            mCanvas.drawPath(mPath, mPaint);
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }

        if (mComplete) {
            if (mListener != null) {
                mListener.complete();
                setEndValues();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.TRANSPARENT);
        mCanvas.drawBitmap(bitmap, 0, 0, null);
    }

    public void setEndValues() {
        moveX = 0;
        moveY = 0;
        mPath.reset();
        mComplete = false;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int w = getWidth();
            int h = getHeight();

            float wipeArea = 0;
            float totalArea = w * h;
            Bitmap bitmap = mBitmap;
            int[] mPixels = new int[w * h];

            bitmap.getPixels(mPixels, 0, w,  0, 0, w, h);

            if (mPixels != null && mPixels.length > 0) {
                for (int i = 0; i < w; i++) {
                    for (int j = 0; j < h; j++) {
                        int index = i + j * w;
                        if (mPixels[index] == 0) {
                            wipeArea++;
                        }
                    }
                }
            }

            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                if (percent > 20) {
                    mComplete = true;
                    postInvalidate();
                }
            }

        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveX = x;
                moveY = y;
                mPath.moveTo(moveX, moveY);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) Math.abs(moveX - x);
                int dy = (int) Math.abs(moveY - y);
                if (dx > 1 || dy > 1) {
                    mPath.quadTo(x, y, (moveX + x) / 2, (moveY + y) / 2);
                }
                moveX = x;
                moveY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (!mComplete) {
                    new Thread(mRunnable).start();
                }

                break;
            default:
                break;
        }

        if (!mComplete) {
            invalidate();
        }
        return true;
    }
}
