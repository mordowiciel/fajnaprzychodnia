package app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.model.Doctor;
import app.model.Visit;

@Repository("visitRepository")
public interface VisitRepository extends JpaRepository<Visit, Integer>, JpaSpecificationExecutor<Visit> {
    @Query("SELECT v FROM Visit v WHERE v.doctor = :doctor AND date(v.visitDate) = date(:visitDate)")
    List<Visit> findByDoctorAndVisitDate(@Param(value = "doctor") Doctor doctor,
                                         @Param(value = "visitDate") LocalDateTime visitDate);
}
