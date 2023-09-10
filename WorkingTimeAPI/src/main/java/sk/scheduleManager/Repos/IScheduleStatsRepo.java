package sk.scheduleManager.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.scheduleManager.Models.ScheduleStats;

public interface IScheduleStatsRepo extends JpaRepository<ScheduleStats, String> {

    @Query(
            "SELECT S FROM ScheduleStats S WHERE S.RecordID = ?1"
    )
    ScheduleStats GetByID(String ID);
}
