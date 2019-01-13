package app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.model.entity.Doctor;
import app.model.entity.Patient;
import app.model.entity.Visit;

@Repository("visitRepository")
public interface VisitRepository extends JpaRepository<Visit, Integer>, JpaSpecificationExecutor<Visit> {
    List<Visit> findByPatient(Patient patient);

    List<Visit> findByDoctor(Doctor doctor);
    List<Visit> findByDoctorAndVisitDate(Doctor doctor, LocalDateTime dateTime);
}
