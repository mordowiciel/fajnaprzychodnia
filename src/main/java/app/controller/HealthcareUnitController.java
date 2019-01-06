package app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.DtoMapper;
import app.model.dto.HealthcareUnitDto;
import app.repository.HealthcareUnitRepository;

@RestController
@RequestMapping(value = "/unit")
public class HealthcareUnitController {

    @Autowired
    private HealthcareUnitRepository healthcareUnitRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllHealthcareUnits() {
        List<HealthcareUnitDto> healthcareUnitDtos = healthcareUnitRepository.findAll()
                .stream()
                .map(DtoMapper::map)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(healthcareUnitDtos);
    }
}
