package app;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import app.model.Doctor;
import app.model.Visit;
import app.model.WeekDay;
import app.model.WorkingHours;
import app.repository.DoctorRepository;
import app.repository.VisitRepository;
import app.repository.WorkingHoursRepository;
import app.service.TimeIntervalService;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TimeIntervalService timeIntervalService;

    @MockBean
    private VisitRepository visitRepository;
    @MockBean
    private WorkingHoursRepository workingHoursRepository;
    @MockBean
    private DoctorRepository doctorRepository;

    @Test
    public void shouldReturnAllPossibleVisitTimesForNoVisitsScheduledBefore() throws Exception {

        Doctor testDoctor = new Doctor();
        testDoctor.setId(1);

        LocalTime start = LocalTime.of(12, 30);
        LocalTime end = LocalTime.of(14, 30);

        WorkingHours doctorWorkingHours = new WorkingHours();
        doctorWorkingHours.setId(1);
        doctorWorkingHours.setShiftStart(start);
        doctorWorkingHours.setShiftEnd(end);
        doctorWorkingHours.setWeekDay(WeekDay.MONDAY);
        doctorWorkingHours.setDoctor(testDoctor);

        // MONDAY
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.DECEMBER, 10);
        LocalDateTime visitDate = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        when(doctorRepository.findById(1))
                .thenReturn(Optional.of(testDoctor));
        when(workingHoursRepository.findByWeekDayAndDoctor(WeekDay.MONDAY, testDoctor))
                .thenReturn(doctorWorkingHours);
        when(visitRepository.findByDoctorAndVisitDate(testDoctor, visitDate)).thenReturn(new ArrayList<>());

        String expectedJson = "[\"12:30:00\",\"13:00:00\",\"13:30:00\",\"14:00:00\",\"14:30:00\"]";
        this.mockMvc.perform(get("/doctor/1/free?visitDate=2018-12-10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnNoPossibleVisitTimes() throws Exception {

        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1);

        LocalTime start = LocalTime.of(12, 30);
        LocalTime end = LocalTime.of(14, 30);

        WorkingHours doctorWorkingHours = new WorkingHours();
        doctorWorkingHours.setId(1);
        doctorWorkingHours.setShiftStart(start);
        doctorWorkingHours.setShiftEnd(end);
        doctorWorkingHours.setWeekDay(WeekDay.MONDAY);
        doctorWorkingHours.setDoctor(mockDoctor);


        List<Visit> allVisits = new ArrayList<>();
        List<LocalTime> possibleVisitTimes = timeIntervalService.getTimeIntervals(start, end, 30);
        for (LocalTime visitTime : possibleVisitTimes) {

            Calendar visitCalendar = Calendar.getInstance();
            visitCalendar.set(2018, Calendar.DECEMBER, 10);
            LocalDate visitDate = visitCalendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Visit visit = new Visit();
            visit.setVisitDate(LocalDateTime.of(visitDate, visitTime));
            allVisits.add(visit);
        }

        when(doctorRepository.findById(1))
                .thenReturn(Optional.of(mockDoctor));
        when(workingHoursRepository.findByWeekDayAndDoctor(WeekDay.MONDAY, mockDoctor))
                .thenReturn(doctorWorkingHours);
        when(visitRepository.findByDoctorAndVisitDate(any(), any())).thenReturn(allVisits);

        String expectedJson = "[]";
        this.mockMvc.perform(get("/doctor/1/free?visitDate=2018-12-10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnPossibleVisitTimesExcludingVisitsScheduledBefore() throws Exception {

        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1);

        LocalTime start = LocalTime.of(12, 30);
        LocalTime end = LocalTime.of(14, 30);

        WorkingHours doctorWorkingHours = new WorkingHours();
        doctorWorkingHours.setId(1);
        doctorWorkingHours.setShiftStart(start);
        doctorWorkingHours.setShiftEnd(end);
        doctorWorkingHours.setWeekDay(WeekDay.MONDAY);
        doctorWorkingHours.setDoctor(mockDoctor);


        List<Visit> allVisits = new ArrayList<>();
        List<LocalTime> possibleVisitTimes = timeIntervalService.getTimeIntervals(start, end, 30);
        for (LocalTime visitTime : possibleVisitTimes) {

            Calendar visitCalendar = Calendar.getInstance();
            visitCalendar.set(2018, Calendar.DECEMBER, 10);
            LocalDate visitDate = visitCalendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Visit visit = new Visit();
            visit.setVisitDate(LocalDateTime.of(visitDate, visitTime));
            allVisits.add(visit);
        }

        allVisits.removeIf(visit -> !visit.getVisitDate()
                .toLocalTime()
                .equals(LocalTime.of(13, 30, 0)));

        when(doctorRepository.findById(1))
                .thenReturn(Optional.of(mockDoctor));
        when(workingHoursRepository.findByWeekDayAndDoctor(WeekDay.MONDAY, mockDoctor))
                .thenReturn(doctorWorkingHours);
        when(visitRepository.findByDoctorAndVisitDate(any(), any())).thenReturn(allVisits);

        String expectedJson = "[\"12:30:00\",\"13:00:00\",\"14:00:00\",\"14:30:00\"]";
        this.mockMvc.perform(get("/doctor/1/free?visitDate=2018-12-10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

    }

}
