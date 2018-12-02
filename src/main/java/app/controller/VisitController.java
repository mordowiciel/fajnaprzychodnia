package app.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.Visit;
import app.model.VisitStatus;
import app.repository.VisitRepository;
import app.repository.specification.VisitSpecification;
import app.repository.specification.request.VisitRequest;

@RestController
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    VisitRepository visitRepository;
    @Autowired
    VisitSpecification visitSpecification;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity allVisits() {
        List<Visit> visits = visitRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(visits);
    }

    // TODO: trzeba dac date +1 do przodu, aby zalapalo przedzial
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(value = "status", required = false) VisitStatus visitStatus,
                                 @RequestParam(value = "patientId", required = false) Integer patientId,
                                 @RequestParam(value = "doctorId", required = false) Integer doctorId,
                                 @RequestParam(value = "healthcareUnitId", required = false) Integer healthCareUnitId,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 @RequestParam(value = "from", required = false) Date from,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 @RequestParam(value = "to", required = false) Date to) {

        VisitRequest visitRequest = new VisitRequest(from, to, visitStatus, patientId, doctorId, healthCareUnitId);
        List<Visit> prescriptions = visitRepository.findAll(visitSpecification.getFilter(visitRequest));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(prescriptions);
    }
}
