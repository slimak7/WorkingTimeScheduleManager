package sk.scheduleManager.ResponseModels;

import java.util.Date;

public class Record {

    public Record(String recordID, Date monthYear) {
        RecordID = recordID;
        MonthYear = monthYear;
    }

    public Record() {
    }

    public String RecordID;
    public Date MonthYear;
}
