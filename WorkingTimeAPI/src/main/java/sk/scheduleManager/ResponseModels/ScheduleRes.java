package sk.scheduleManager.ResponseModels;

import java.util.Date;
import java.util.List;

public class ScheduleRes {

    public ScheduleRes(String recordID, String employeeID, Date monthYear, List<DailyWorkingTime> workingHours) {
        RecordID = recordID;
        EmployeeID = employeeID;
        MonthYear = monthYear;
        WorkingHours = workingHours;
    }

    public String RecordID;
    public String EmployeeID;
    public Date MonthYear;
    public List<DailyWorkingTime> WorkingHours;
}
