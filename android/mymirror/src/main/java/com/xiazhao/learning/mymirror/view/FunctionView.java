package com.xiazhao.learning.mymirror.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.xiazhao.learning.mymirror.R;

public class FunctionView extends LinearLayout implements View.OnClickListener{

    private ImageView hint, choose, down, up;
    public static final int HINT_ID = R.id.hint;
    public static final int CHOOSE_ID = R.id.choose;
    public static final int DOWN_ID = R.id.light_dowm;
    public static final int UP_ID = R.id.light_up;

    private LayoutInflater mInflater;
    private onFunctionItemClickListener listener;
    public interface onFunctionItemClickListener {
        void hint();
        void choose();
        void down();
        void up();
    }

    public FunctionView(Context context) {
        this(context, null);
    }

    public FunctionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FunctionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        init();
        setView();
    }

    private void setView() {
        hint.setOnClickListener(this);
        choose.setOnClickListener(this);
        down.setOnClickListener(this);
        up.setOnClickListener(this);
    }

    private void init() {

        View view = mInflater.inflate(R.layout.view_function, this);
        hint = view.findViewById(HINT_ID);
        choose = view.findViewById(CHOOSE_ID);
        down = view.findViewById(DOWN_ID);
        up = view.findViewById(UP_ID);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            switch (v.getId()) {
                case HINT_ID:
                    listener.hint();
                    break;
                case CHOOSE_ID:
                    listener.choose();
                    break;
                case DOWN_ID:
                    listener.down();
                    break;
                case UP_ID:
                    listener.up();
                    break;
                default:
                    break;
            }
        }
    }

    public void setOnFunctionViewItemClickListener(onFunctionItemClickListener onFunctionViewItemClickListener) {
        this.listener = onFunctionViewItemClickListener;
    }
}
