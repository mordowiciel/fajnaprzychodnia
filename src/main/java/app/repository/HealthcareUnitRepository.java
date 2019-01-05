package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.entity.HealthcareUnit;

@Repository
public interface HealthcareUnitRepository extends JpaRepository<HealthcareUnit, Integer> {
}
