package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.WeekDay;
import app.model.entity.Doctor;
import app.model.entity.WorkingHours;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Integer> {
    WorkingHours findByWeekDayAndDoctor(WeekDay weekDay, Doctor doctor);
}
