package app.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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
}
