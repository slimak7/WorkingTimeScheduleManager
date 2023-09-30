package sk.scheduleManager.ResponseModels;

public class StatsRes {

    public Record Record;
    public int TotalMinutes;
    public int NumberOfWorkingDays;
    public int TotalOvertimeMinutes;

    public StatsRes(sk.scheduleManager.ResponseModels.Record record, int totalMinutes, int numberOfWorkingDays, int totalOvertimeMinutes) {
        Record = record;
        TotalMinutes = totalMinutes;
        NumberOfWorkingDays = numberOfWorkingDays;
        TotalOvertimeMinutes = totalOvertimeMinutes;
    }
}
