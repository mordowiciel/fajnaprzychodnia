package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.entity.Patient;
import app.model.security.User;

@Repository("patientRepository")
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findByUser(User user);
}
