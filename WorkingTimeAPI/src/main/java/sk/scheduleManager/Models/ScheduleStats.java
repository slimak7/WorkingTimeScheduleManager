package sk.scheduleManager.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "ScheduleStats")
public class ScheduleStats {
    @Id
    @Column(name = "RecordID_PK_FK" , columnDefinition="uniqueidentifier")
    private String RecordID;

    @Column(name = "TotalMinutes")
    private int TotalMinutes;

    @Column(name = "NumberOfWorkingDays")
    private int NumberOfWorkingDays;

    @Column(name = "TotalOvertimeMinutes")
    private int TotalOvertimeMinutes;

    public String getRecordID() {
        return RecordID;
    }

    public void setRecordID(String recordID) {
        RecordID = recordID;
    }

    public int getTotalMinutes() {
        return TotalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        TotalMinutes = totalMinutes;
    }

    public int getNumberOfWorkingDays() {
        return NumberOfWorkingDays;
    }

    public void setNumberOfWorkingDays(int numberOfWorkingDays) {
        NumberOfWorkingDays = numberOfWorkingDays;
    }

    public int getTotalOvertimeMinutes() {
        return TotalOvertimeMinutes;
    }

    public void setTotalOvertimeMinutes(int totalOvertimeMinutes) {
        TotalOvertimeMinutes = totalOvertimeMinutes;
    }
}
