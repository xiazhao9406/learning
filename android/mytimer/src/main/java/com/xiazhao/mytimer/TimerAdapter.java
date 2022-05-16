package com.xiazhao.mytimer;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerViewerHolder> {

    private static final String TAG = TimerAdapter.class.getSimpleName();
    private static final String DATE_FORMAT_PATTERN = "HH:mm:ss";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    private static final long COUNT_DOWN_INTERVAL = 500;

    private Handler uiHandler;
    private Thread timeUpdateThread;
    private ArrayList<TimerItem> timerItems = new ArrayList<>();

    public TimerAdapter(Handler handler) {
        uiHandler = handler;
    }

    @NonNull
    @Override
    public TimerViewerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder()");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timer, parent, false);
        return new TimerViewerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimerViewerHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder()");
        holder.bindData(timerItems.get(position));
    }

    @Override
    public int getItemCount() {
        return timerItems.size();
    }

    public void addTimer(TimerItem timerItem) {
        if (timerItems.size() == 0) {
            startCountDown();
        }
        timerItems.add(timerItem);
        notifyItemInserted(timerItems.size());
    }

    public void removeTimer(TimerItem timerItem) {
        final int position = timerItems.indexOf(timerItem);
        timerItems.remove(timerItem);
        notifyItemRemoved(position);
        Log.i(TAG, "removeTimer at position " + position);
    }

    private void startCountDown() {
        if (timeUpdateThread != null) {
            return;
        }

        timeUpdateThread = new Thread() {
            @Override
            public void run() {
                final Runnable UPDATE_RUNNABLE = new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                };

                while (true) {
                    try {
                        sleep(COUNT_DOWN_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    uiHandler.post(UPDATE_RUNNABLE);
                }
            }
        };

        timeUpdateThread.start();
    }

    protected class TimerViewerHolder extends RecyclerView.ViewHolder {

        private final TextView countDown;
        private final TextView start;
        private final TextView end;
        private final Button deleteButton;

        private TimerItem timerItem;

        public TimerViewerHolder(@NonNull View itemView) {
            super(itemView);
            countDown = itemView.findViewById(R.id.count_down);
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
            deleteButton = itemView.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "delete button onClick()");
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (timerItem != null) {
                                removeTimer(timerItem);
                            }
                        }
                    });
                }
            });
        }

        private void bindData(TimerItem timerItem) {
            countDown.setText(timerItem.getRemainingTime());
            start.setText(timerItem.start.format(dateTimeFormatter));
            end.setText(timerItem.end.format(dateTimeFormatter));
            this.timerItem = timerItem;
        }
    }
}



