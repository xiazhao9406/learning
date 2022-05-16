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

    public final LocalDateTime start;
    public final LocalDateTime end;

    public TimerItem(int hours, int minutes) {
        start = LocalDateTime.now();
        end = start.plusHours(hours).plusMinutes(minutes);
    }

    public String getRemainingTime() {
        return DATE_FORMAT.format(new Date(ChronoUnit.MILLIS.between(LocalDateTime.now(), end)));
    }
}
