package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
