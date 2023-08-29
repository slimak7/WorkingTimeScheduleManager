package sk.scheduleManager.Models;

import jakarta.persistence.*;

@Embeddable
public class ScheduleID {

    @ManyToOne
    @JoinColumn(name = "RecordID_PK_FK", columnDefinition="uniqueidentifier", referencedColumnName = "RecordID")
    private Record Record;

    @Column(name = "DayNumberPK")
    private int DayNumber;

    public sk.scheduleManager.Models.Record getRecord() {
        return Record;
    }

    public void setRecord(sk.scheduleManager.Models.Record record) {
        Record = record;
    }

    public int getDayNumber() {
        return DayNumber;
    }

    public void setDayNumber(int dayNumber) {
        DayNumber = dayNumber;
    }
}
