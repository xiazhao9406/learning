package com.xiazhao.learning.mymirror.activity;


import static android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.xiazhao.learning.mymirror.R;
import com.xiazhao.learning.mymirror.untils.AudioRecordManger;
import com.xiazhao.learning.mymirror.untils.SetBrightness;
import com.xiazhao.learning.mymirror.view.DrawView;
import com.xiazhao.learning.mymirror.view.FunctionView;
import com.xiazhao.learning.mymirror.view.PictureView;
import com.zys.brokenview.BrokenCallback;
import com.zys.brokenview.BrokenTouchListener;
import com.zys.brokenview.BrokenView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, SeekBar.OnSeekBarChangeListener,
        DrawView.OnCaYiCaCompleteListener,
        View.OnTouchListener, FunctionView.onFunctionItemClickListener{

    private static String TAG = MainActivity.class.getSimpleName();
    private static final int CODE_WRITE_SETTINGS_PERMISSION = 0xFF01;
    private static final int CODE_REQUEST_PHOTO = 0xFF02;
    private SurfaceHolder holder;
    private SurfaceView surfaceView;
    private PictureView pictureView;
    private FunctionView functionView;
    private SeekBar seekBar;
    private ImageView add;
    private ImageView minus;
    private LinearLayout bottom;
    private ImageView save;
    private ProgressDialog dialog;
    private DrawView drawView;

    private boolean haveCamera;
    private Camera camera;
    private int mCurrentCamIndex;
    private int ROTATE;
    private int minFocus;
    private int maxFocus;
    private int everyFocus;
    private int nowFocus;

    private int frame_index;
    private int[] frame_index_id;

    private int brightnessValues = 128;
    private int segmentLength;
    private boolean isAutoBrightness;

    private AudioRecordManger audioRecordManger;
    private static final int RECORD = 2;

    private BrokenView brokenView;
    private BrokenTouchListener brokenTouchListener;
    private MyBrokenCallback callback;
    private Paint brokenPaint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frame_index = 0;
        frame_index_id = new int[] {R.drawable.mag_0001, R.drawable.mag_0003, R.drawable.mag_0005,
                R.drawable.mag_0006, R.drawable.mag_0007, R.drawable.mag_0008, R.drawable.mag_0009,
                R.drawable.mag_0011, R.drawable.mag_0012, R.drawable.mag_0014};

        initView();
        setView();
        requestForWriteSettingsPermission(this);
        audioRecordManger = new AudioRecordManger(RECORD, handler);
        audioRecordManger.getNoiseLevel();

        mySimpleGestureListener = new MySimpleGestureListener();
        gestureDetector = new GestureDetector(this, mySimpleGestureListener);
    }

    private void initView() {
        surfaceView = findViewById(R.id.surface);
        pictureView = findViewById(R.id.pictureView);
        functionView = findViewById(R.id.function);
        seekBar = findViewById(R.id.seekBar);
        add = findViewById(R.id.add);
        minus = findViewById(R.id.minus);
        bottom = findViewById(R.id.bottom_bar);
        drawView = findViewById(R.id.draw_glasses);
    }

    private void hideView() {
        bottom.setVisibility(View.INVISIBLE);
        functionView.setVisibility(View.INVISIBLE);
    }

    private void showView() {
        pictureView.setImageBitmap(null);
        bottom.setVisibility(View.VISIBLE);
        functionView.setVisibility(View.VISIBLE);
    }

    private void getSoundValues(double values) {
        Log.d(TAG, "value = " + values);
        if (values > 60) {
            hideView();


            drawView.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.in_window);
            drawView.setAnimation(animation);
            audioRecordManger.isGetVoiceRun = false;
            Log.e("glasses", "show");
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
//            Log.d(TAG, "received msg");
            switch(msg.what) {
                case RECORD:
                    double soundValues = (double)msg.obj;
                    getSoundValues(soundValues);
                    break;
                default:
                    break;
            }
        }
    };


    private void setView() {
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        seekBar.setOnSeekBarChangeListener(this);
        add.setOnTouchListener(this);
        minus.setOnTouchListener(this);
        pictureView.setOnTouchListener(this);
        functionView.setOnFunctionViewItemClickListener(this);
        pictureView.setOnTouchListener(this);
        drawView.setOnCaYiCaCompleteListener(this);
        setToBrokenTheView();

    }

    private boolean checkCameraHardware() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private Camera openFrontFacingCameraGingerbread() {
        Camera mCamera = null;
        int cameraCount;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();

        for (int camIndex = 0; camIndex < cameraCount; camIndex++) {
            Camera.getCameraInfo(camIndex, cameraInfo);
            if (cameraInfo.facing == CAMERA_FACING_FRONT) {
                try {
                    mCamera = Camera.open(camIndex);
                    mCurrentCamIndex = camIndex;
                } catch (RuntimeException e) {
                    Log.e(TAG, "fail to open camera: " + e.getLocalizedMessage());
                }
            }
        }
        return mCamera;
    }

    private void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degree = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 0;
                break;
            case Surface.ROTATION_90:
                degree = 90;
                break;
            case Surface.ROTATION_180:
                degree = 180;
                break;
            case Surface.ROTATION_270:
                degree = 270;
                break;
            default:
                break;
        }
        int result = 0;
        if (info.facing == CAMERA_FACING_FRONT) {
            result = (info.orientation + degree) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degree + 360) % 360;
        }
        ROTATE = result + 180;
        camera.setDisplayOrientation(result);
    }

    private void setCamera() {
        if (checkCameraHardware()) {
            camera = openFrontFacingCameraGingerbread();
            setCameraDisplayOrientation(this, mCurrentCamIndex, camera);
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPictureFormat(ImageFormat.JPEG);
            List<String> list = parameters.getSupportedFocusModes();
            for (String str : list) {
                Log.e(TAG, "the mode is : " + str);
            }

            List<Camera.Size> pictureList = parameters.getSupportedPictureSizes();
            List<Camera.Size> previewList = parameters.getSupportedPreviewSizes();
            parameters.setPictureSize(pictureList.get(0).width, pictureList.get(0).height);
            parameters.setPictureSize(previewList.get(0).width, previewList.get(0).height);
            minFocus = parameters.getZoom();
            maxFocus = parameters.getMaxZoom();
            everyFocus = 1;
            nowFocus = minFocus;
            seekBar.setMax(maxFocus);
            Log.e(TAG, "the lens distance is : " + minFocus + "\t\tthe max distance is : " + maxFocus);
            camera.setParameters(parameters);
        }
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.e("SurfaceCreated", "Start to draw");
        try {
            setCamera();
            camera.setPreviewDisplay(holder);
            camera.startPreview();

        } catch (IOException e) {
            camera.release();
            camera = null;
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.e("surfaceChanged", "the draw is changed");
        try {
            camera.stopPreview();
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.e("surfaceDestroyed", "the draw is destroyed");
        toRelease();
    }

    private void toRelease() {
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    private void setZoomValues(int want) {
        Camera.Parameters parameters = camera.getParameters();
        parameters.setZoom(want);
        seekBar.setProgress(want);
        camera.setParameters(parameters);
    }

    private int getZoomValues() {
        Camera.Parameters parameters = camera.getParameters();
        int values = parameters.getZoom();
        return values;
    }

    private void addZoomValues() {
        if (nowFocus >= maxFocus) {
            return;
        }
        setZoomValues(getZoomValues() + everyFocus);
    }

    private void minusZoomValues() {
        if (nowFocus <= minFocus) {
            return;
        }
        setZoomValues(getZoomValues() - everyFocus);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Camera.Parameters parameters = camera.getParameters();
        nowFocus = progress;
        parameters.setZoom(progress);
        camera.setParameters(parameters);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.add:
                addZoomValues();
                break;
            case R.id.minus:
                minusZoomValues();
                break;
            case R.id.pictureView:
                gestureDetector.onTouchEvent(event);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void hint() {
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }

    @Override
    public void choose() {
        Intent intent = new Intent(this, PhotoFrameActivity.class);
        startActivityForResult(intent, CODE_REQUEST_PHOTO);
        Toast.makeText(this, "Choose", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void down() {
        downCurrentActivityBrightnessValues();
    }

    @Override
    public void up() {
        upCurrentActivityBrightnessValues();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CODE_REQUEST_PHOTO:
                if (resultCode == RESULT_OK) {
                    int position = data.getIntExtra("POSITION", 0);
                    frame_index = position;
                    Log.e(TAG, "onActivityResult" + position);
                    pictureView.setPhotoFrame(position);
                }
            break;

            case CODE_WRITE_SETTINGS_PERMISSION:
                if (resultCode == RESULT_OK) {
                    getBrightnessFromWindow();
                } else {
                    Toast.makeText(this, "request permission failed.", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_WRITE_SETTINGS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getBrightnessFromWindow();
                } else {
                    Toast.makeText(this, "request permission failed.", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    private void setMyActivityBright(int brightnessValues) {
        SetBrightness.setBrightness(this, brightnessValues);
        SetBrightness.saveBrightness(this, brightnessValues);
    }

    private void getAfterMySetBrightnessValues() {
        brightnessValues = SetBrightness.getScreenBrightness(this);
        Log.e(TAG, "the current brightness is : " + brightnessValues);
    }

    public void getBrightnessFromWindow() {
        isAutoBrightness = SetBrightness.isAutoBrightness(SetBrightness.getResolver(this));
        Log.e(TAG, "is aoto bright" + isAutoBrightness);

        if (isAutoBrightness) {
            SetBrightness.stopAutoBrightness(this);
            setMyActivityBright(255 / 2 + 1);
        }

        segmentLength = (255 / 2 + 1) / 4;
        getAfterMySetBrightnessValues();
    }

    private void upCurrentActivityBrightnessValues() {
        segmentLength = (255 / 2 + 1) / 4;
        if (brightnessValues < 255) {
            if (brightnessValues + segmentLength >= 256) {
                return;
            }
            setMyActivityBright(brightnessValues + segmentLength);
        }
        getAfterMySetBrightnessValues();
    }

    private void downCurrentActivityBrightnessValues() {
        if (brightnessValues > 0) {
            setMyActivityBright(brightnessValues - segmentLength);
        }
        getAfterMySetBrightnessValues();
    }

    public static void requestForWriteSettingsPermission(Activity context){
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context);
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (permission) {
            //do your code
        }  else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivityForResult(intent, CODE_WRITE_SETTINGS_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(context, new String[]{
                        Manifest.permission.WRITE_SETTINGS,
                        Manifest.permission.WRITE_SECURE_SETTINGS
                }, CODE_WRITE_SETTINGS_PERMISSION);
            }
        }
    }

    @Override
    public void complete() {
        showView();
        audioRecordManger.getNoiseLevel();
        drawView.setVisibility(View.GONE);
    }

    private boolean isBroken;
    class MyBrokenCallback extends BrokenCallback {

        @Override
        public void onStart(View v) {
            super.onStart(v);
            Log.e("Broken", "onStart");
        }
        @Override
        public void onFalling(View v) {
            super.onFalling(v);
            Log.e("Broken", "onFalling");
        }
        @Override
        public void onFallingEnd(View v) {
            super.onFallingEnd(v);
            Log.e("Broken", "onFallingEnd");
            brokenView.reset();
            pictureView.setOnTouchListener(MainActivity.this);
            pictureView.setVisibility(View.VISIBLE);
            isBroken = false;
            Log.e("isEnable", isBroken + "");
            brokenView.setEnable(isBroken);
            audioRecordManger.getNoiseLevel();
            showView();
        }
        @Override
        public void onCancelEnd(View v) {
            super.onCancelEnd(v);
            Log.e("Broken", "onCancelEnd");
        }
    }

    private void setToBrokenTheView() {
        brokenPaint = new Paint();
        brokenPaint.setStrokeWidth(5);
        brokenPaint.setColor(Color.BLACK);
        brokenPaint.setAntiAlias(true);
        brokenView = BrokenView.add2Window(this);

        brokenTouchListener = new BrokenTouchListener.Builder(brokenView).setPaint(brokenPaint).setBreakDuration(2000).setFallDuration(5000).build();
        brokenView.setEnable(true);
        callback = new MyBrokenCallback();
        brokenView.setCallback(callback);
    }

    private GestureDetector gestureDetector;
    private MySimpleGestureListener mySimpleGestureListener;
    class MySimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            Log.e("pose", "long pressing");
            isBroken = true;//ÀÈ∆¡
            brokenView.setEnable(isBroken);
            pictureView.setOnTouchListener(brokenTouchListener);
            hideView();
            audioRecordManger.isGetVoiceRun = false;
        }
    }


}