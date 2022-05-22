package com.xiazhao.mytimer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

public class TimerItem {

    private static final String DATE_FORMAT_PATTERN = "HH:mm:ss";
    private static final String TIME_ZONE = "GMT";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_PATTERN);

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
    }

    private CountDownTimeListener listener;
    private String oldCountDownTime;
    private boolean isTimeOut = false;

    public final LocalDateTime start;
    public final LocalDateTime end;

    public TimerItem(int minutes) {
        int hours = minutes / 60;
        minutes = minutes % 60;
        start = LocalDateTime.now();
        if (Config.debug) {
            end = start.plusSeconds(minutes);
        } else {
            end = start.plusHours(hours).plusMinutes(minutes);
        }
        oldCountDownTime = getCountDownTime();
    }

    public String getCountDownTime() {
        return DATE_FORMAT.format(new Date(ChronoUnit.MILLIS.between(LocalDateTime.now(), end)));
    }

    public void setListener(CountDownTimeListener countDownTimeListener) {
        listener = countDownTimeListener;
    }

    public void updateCountDownTime() {
        if (ChronoUnit.MILLIS.between(LocalDateTime.now(), end) < 0) {
            if (!isTimeOut) {
                if (listener != null) {
                    listener.onTimeUp();
                }
            }
            isTimeOut = true;
        }

        String newCountDownTime = getCountDownTime();
        if (!oldCountDownTime.equals(newCountDownTime)) {
            oldCountDownTime = newCountDownTime;
            if (listener != null && !isTimeOut) {
                listener.onCountDownTimeChanged();
            }
        }
    }
}
