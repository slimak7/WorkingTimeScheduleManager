package sk.scheduleManager.Models;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "DailySchedule")
public class Schedule {

    @EmbeddedId
    private ScheduleID ScheduleID;

    @Column(name = "StartTime")
    private Time StartTime;

    @Column(name = "EndTime")
    private Time EndTime;

    public Time getStartTime() {
        return StartTime;
    }

    public void setStartTime(Time startTime) {
        StartTime = startTime;
    }

    public Time getEndTime() {
        return EndTime;
    }

    public void setEndTime(Time endTime) {
        EndTime = endTime;
    }

    public sk.scheduleManager.Models.ScheduleID getScheduleID() {
        return ScheduleID;
    }

    public void setScheduleID(sk.scheduleManager.Models.ScheduleID scheduleID) {
        ScheduleID = scheduleID;
    }
}
