package app.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.Doctor;
import app.model.WeekDay;
import app.model.WorkingHours;
import app.repository.DoctorRepository;
import app.repository.VisitRepository;
import app.repository.WorkingHoursRepository;
import app.service.TimeIntervalService;

@RestController()
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @Autowired
    private TimeIntervalService timeIntervalService;

    @RequestMapping(value = "/allDoctors", method = RequestMethod.GET)
    public ResponseEntity allDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctors);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity doctor(@PathVariable(value = "id") Integer id) {
        Doctor doctor = doctorRepository.findById(id).get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctor);
    }

    @RequestMapping(value = "/{id}/free", method = RequestMethod.GET)
    public ResponseEntity getDoctorFreeVisitTimes(@PathVariable(value = "id") Integer doctorId,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                  @RequestParam(value = "visitDate") Date visitDate) {

        // Get week day from received date
        WeekDay weekDay = timeIntervalService.getWeekdayFromDate(visitDate);
        Doctor doctor = doctorRepository.findById(doctorId).get();

        WorkingHours workingHoursInGivenDay = workingHoursRepository.findByWeekDayAndDoctor(weekDay, doctor);
        List<LocalTime> visitTimesInWorkingHours = timeIntervalService.getTimeIntervals(
                workingHoursInGivenDay.getShiftStart(),
                workingHoursInGivenDay.getShiftEnd(),
                30
        );

        LocalDateTime convertedVisitDate = visitDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        List<LocalTime> currentlyAssignedVisitsTimes = visitRepository
                .findByDoctorAndVisitDate(doctor, convertedVisitDate)
                .stream()
                .map(visit -> visit.getVisitDate().toLocalTime())
                .collect(Collectors.toList());

        List<LocalTime> freeVisitTimes = visitTimesInWorkingHours.stream()
                .filter(visitTime -> !currentlyAssignedVisitsTimes.contains(visitTime))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(freeVisitTimes);
    }

}