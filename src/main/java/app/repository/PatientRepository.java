package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Patient;

@Repository("patientRepository")
public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
