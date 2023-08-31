package sk.scheduleManager.ResponseModels;

import java.sql.Time;

public class DailyWorkingTime {

    public DailyWorkingTime() {
    }

    public DailyWorkingTime(int dayNumber, Time startTime, Time endTime) {
        DayNumber = dayNumber;
        StartTime = startTime;
        EndTime = endTime;
    }

    public int DayNumber;
    public Time StartTime;
    public Time EndTime;
}
