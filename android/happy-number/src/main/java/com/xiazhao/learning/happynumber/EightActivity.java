package com.xiazhao.learning.happynumber;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("HandlerLeak")
public class EightActivity extends Activity {
    private ImageView imageView;
    int i = 1;
    float x1;
    float y1;
    float x2;
    float y2;
    float x3;
    float y3;

    int igvx;
    int igvy;
    int type = 0;
    int widthPixels;
    int heightPixels;
    float scaleWidth;
    float scaleHeight;
    Timer touchTimer = null;
    Bitmap arrdown;
    boolean typeDialog = true;
    private LinearLayout linearLayout = null;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight);
        if (MainActivity.isPlay) {
            playMusic();
        }
        initView();


    }

    private void playMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.music8);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    private void initView() {
        imageView = findViewById(R.id.iv_frame8);
        linearLayout = findViewById(R.id.LinearLayout8);
        LinearLayout writeLayout = findViewById(R.id.LinearLayout_number8);
        writeLayout.setBackgroundResource(R.drawable.bg8);
        widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        heightPixels = this.getResources().getDisplayMetrics().heightPixels;
        scaleWidth = widthPixels / 720;
        scaleHeight = heightPixels / 1280;

        try {
            InputStream is = getResources().getAssets().open("on8_1.png");
            arrdown = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = (int) (arrdown.getWidth() * scaleWidth);
        layoutParams.height = (int) (arrdown.getHeight() * scaleHeight);
        imageView.setLayoutParams(layoutParams);
        loadingImage(1);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        y1 = motionEvent.getY();
                        igvx = imageView.getLeft();
                        igvy = imageView.getTop();

                        if (x1 >= igvx && x1 <= igvx + (int) (arrdown.getWidth() * scaleWidth)
                                && y1 >= igvy & y1 <= igvy + (int) (arrdown.getHeight() * scaleHeight)) {
                            type = 1;
                        } else {
                            type = 0;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = motionEvent.getX();
                        y2 = motionEvent.getY();
                        igvx = imageView.getLeft();
                        igvy = imageView.getTop();

                        if (type == 1) {
                            if (x2 >= igvx && x2 <= igvx + (int) (arrdown.getWidth() * scaleWidth)) {
                                if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 && y2 >= igvy) {
                                    loadingImage(1);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 2) {
                                    loadingImage(2);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 3) {
                                    loadingImage(3);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 4) {
                                    loadingImage(4);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 5) {
                                    loadingImage(5);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 6) {
                                    loadingImage(6);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 7) {
                                    loadingImage(7);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 8) {
                                    loadingImage(8);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 9) {
                                    loadingImage(9);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 10) {
                                    loadingImage(10);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 11) {
                                    loadingImage(11);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 12) {
                                    loadingImage(12);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 13) {
                                    loadingImage(13);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 14) {
                                    loadingImage(14);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 15) {
                                    loadingImage(15);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 16) {
                                    loadingImage(16);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 17) {
                                    loadingImage(17);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 18) {
                                    loadingImage(18);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 19) {
                                    loadingImage(19);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 20) {
                                    loadingImage(20);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 21) {
                                    loadingImage(21);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 22) {
                                    loadingImage(22);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 23) {
                                    loadingImage(23);
                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 24) {
                                    loadingImage(24);
                                } else {
                                    type = 0;
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        type = 0;
                        if (touchTimer != null) {
                            touchTimer.cancel();
                            touchTimer = null;
                        }
                        touchTimer = new Timer();
                        touchTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Message message = new Message();
                                        message.what = 2;
                                        mHandler.sendMessage(message);
                                    }
                                });
                                thread.start();
                            }
                        }, 300, 200);

                    default:
                        break;
                }
                return true;
            }
        });
    }

    public static Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 2:
                        jloadingImage();
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    private void jloadingImage() {
        if (i == 25) {

        } else if (i < 25) {
            if (i > 1) {
                i--;
            } else if (i == 1) {
                i = 1;
                if (touchTimer != null) {
                    touchTimer.cancel();
                    touchTimer = null;
                }
            }
            String name = "on8_" + i;

            int imGid = getResources().getIdentifier(name, "drawable", "com.xiazhao.learning.happynumber");
            imageView.setBackgroundResource(imGid);
        }
    }


    private synchronized void loadingImage(int j) {
        i = j;
        if (i < 25) {
            String name = "on8_" + i;
            int imGid = getResources().getIdentifier(name, "drawable", "com.xiazhao.learning.happynumber");
            imageView.setBackgroundResource(imGid);
            i++;
        }

        if (i == 24) {
            if (typeDialog) {
                dialog();
            }
        }
    }

    private void dialog() {
        typeDialog = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(EightActivity.this);
        builder.setMessage("太棒了！ 书写完成！");
        builder.setTitle("提示");
        builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                typeDialog = true;
                finish();
            }
        });

        builder.setNegativeButton("再来一次", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                typeDialog = true;
                i = 1;
                loadingImage(1);
            }
        });

        builder.create().show();
    }
}