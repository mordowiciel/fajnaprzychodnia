package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.model.Visit;

@Repository("visitRepository")
public interface VisitRepository extends JpaRepository<Visit, Integer>, JpaSpecificationExecutor<Visit> {
}
