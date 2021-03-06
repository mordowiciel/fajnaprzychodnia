package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.model.entity.Prescription;

@Repository("prescriptionRepository")
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer>,
        JpaSpecificationExecutor<Prescription> {
}
