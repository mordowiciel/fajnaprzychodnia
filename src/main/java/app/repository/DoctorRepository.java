package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.entity.Doctor;
import app.model.entity.HealthcareUnit;

@Repository("doctorRepository")
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByHealthcareUnit(HealthcareUnit healthcareUnit);
}
