package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Doctor;
import app.model.WeekDay;
import app.model.WorkingHours;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Integer> {
    WorkingHours findByWeekDayAndDoctor(WeekDay weekDay, Doctor doctor);
}
