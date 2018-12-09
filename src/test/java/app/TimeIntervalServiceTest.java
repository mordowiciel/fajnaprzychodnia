package app;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import app.model.WeekDay;
import app.service.TimeIntervalService;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class TimeIntervalServiceTest {

    private final int MINUTES_INTERVAL = 5;

    @Autowired
    private TimeIntervalService timeIntervalService;

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenStartTimeIsAfterEndTime() {
        LocalTime start = LocalTime.of(12, 30, 0);
        LocalTime end = LocalTime.of(12, 0, 0);
        timeIntervalService.getTimeIntervals(start, end, MINUTES_INTERVAL);
    }

    @Test
    public void shouldReturnIntervalWithStartTimeOnly() {
        LocalTime start = LocalTime.of(12, 30);
        LocalTime end = LocalTime.of(12, 31);

        List<LocalTime> expectedIntervals = new ArrayList<>();
        expectedIntervals.add(start);

        List<LocalTime> intervals = timeIntervalService.getTimeIntervals(start, end, MINUTES_INTERVAL);
        assertEquals(expectedIntervals, intervals);
    }

    @Test
    public void shouldReturnIntervalWithStartTimeAndEndTimeIncluded() {

        LocalTime start = LocalTime.of(12, 30, 0);
        LocalTime end = LocalTime.of(12, 45, 0);

        List<LocalTime> expectedIntervals = new ArrayList<>();
        expectedIntervals.add(start);
        expectedIntervals.add(LocalTime.of(12, 35));
        expectedIntervals.add(LocalTime.of(12, 40));
        expectedIntervals.add(end);

        List<LocalTime> intervals = timeIntervalService.getTimeIntervals(start, end, MINUTES_INTERVAL);
        assertEquals(expectedIntervals, intervals);
    }

    @Test
    public void shouldReturnIntervalWithStartTimeIncludedAndNoEndTimeIncluded() {

        LocalTime start = LocalTime.of(12, 30);
        LocalTime end = LocalTime.of(12, 44);

        List<LocalTime> expectedIntervals = new ArrayList<>();
        expectedIntervals.add(start);
        expectedIntervals.add(LocalTime.of(12, 35));
        expectedIntervals.add(LocalTime.of(12, 40));

        List<LocalTime> intervals = timeIntervalService.getTimeIntervals(start, end, MINUTES_INTERVAL);
        assertEquals(expectedIntervals, intervals);
    }

    @Test
    public void shouldReturnMondayAsWeekDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.DECEMBER, 10);
        Date date = calendar.getTime();
        WeekDay returnedWeekday = timeIntervalService.getWeekdayFromDate(date);
        assertEquals(WeekDay.MONDAY, returnedWeekday);
    }

    @Test
    public void shouldReturnThursdayAsWeekDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.DECEMBER, 13);
        Date date = calendar.getTime();
        WeekDay returnedWeekday = timeIntervalService.getWeekdayFromDate(date);
        assertEquals(WeekDay.THURSDAY, returnedWeekday);
    }

    @Test
    public void shouldReturnSundayAsWeekDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.DECEMBER, 9);
        Date date = calendar.getTime();
        WeekDay returnedWeekday = timeIntervalService.getWeekdayFromDate(date);
        assertEquals(WeekDay.SUNDAY, returnedWeekday);
    }

}
