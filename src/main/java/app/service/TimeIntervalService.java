package app.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import app.model.WeekDay;

@Service
public class TimeIntervalService {

    public List<LocalTime> getTimeIntervals(LocalTime start, LocalTime end, int minutesBetween) {

        if(start.isAfter(end)) {
            throw new IllegalArgumentException("Start time is after end time!");
        }

        long period = ChronoUnit.MINUTES.between(start, end);
        long periodCount = period / minutesBetween;

        List<LocalTime> timeIntervals = new ArrayList<>();
        timeIntervals.add(start);
        LocalTime tmpTimeInterval = start;

        for (int i = 0; i < periodCount; i++) {
            LocalTime timeInterval = tmpTimeInterval.plus(minutesBetween, ChronoUnit.MINUTES);
            timeIntervals.add(timeInterval);
            tmpTimeInterval = timeInterval;
        }

        return timeIntervals;
    }

    public WeekDay getWeekdayFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return WeekDay.values()[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }
}
