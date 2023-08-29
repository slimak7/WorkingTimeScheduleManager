package sk.scheduleManager.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.scheduleManager.Models.Schedule;
import sk.scheduleManager.Models.ScheduleID;

public interface IScheduleRepo extends JpaRepository<Schedule, ScheduleID> {
}
