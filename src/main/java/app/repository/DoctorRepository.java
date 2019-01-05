package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.entity.Doctor;

@Repository("doctorRepository")
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
