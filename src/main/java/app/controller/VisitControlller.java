package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Visit;
import app.repository.VisitRepository;

@RestController
@RequestMapping("/visit")
public class VisitControlller {

    @Autowired
    VisitRepository visitRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity allVisits() {
        List<Visit> prescriptions = visitRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(prescriptions);
    }
}
