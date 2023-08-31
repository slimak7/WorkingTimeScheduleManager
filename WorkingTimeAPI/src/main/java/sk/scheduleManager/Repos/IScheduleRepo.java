package sk.scheduleManager.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.scheduleManager.Models.Schedule;
import sk.scheduleManager.Models.ScheduleID;

import java.util.Collection;

public interface IScheduleRepo extends JpaRepository<Schedule, ScheduleID> {

    @Query(
            "SELECT S FROM Schedule S WHERE S.ScheduleID.Record.ID = ?1 AND S.ScheduleID.DayNumber = ?2"
    )
    Schedule GetByRecordIDDay(String recordID, int dayNumber);

    @Query(
            "SELECT S FROM Schedule S WHERE S.ScheduleID.Record.ID = ?1"
    )
    Collection<Schedule> GetAllByRecordID(String recordID);
}
