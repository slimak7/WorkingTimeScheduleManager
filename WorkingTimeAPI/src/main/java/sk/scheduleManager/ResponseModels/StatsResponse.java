package sk.scheduleManager.ResponseModels;

import java.util.Date;

public class StatsResponse {

    public Record Record;
    public int TotalMinutes;
    public int NumberOfWorkingDays;
    public int TotalOvertimeMinutes;

    public StatsResponse(sk.scheduleManager.ResponseModels.Record record, int totalMinutes, int numberOfWorkingDays, int totalOvertimeMinutes) {
        Record = record;
        TotalMinutes = totalMinutes;
        NumberOfWorkingDays = numberOfWorkingDays;
        TotalOvertimeMinutes = totalOvertimeMinutes;
    }
}
