package sk.scheduleManager.RequestModels;

import sk.scheduleManager.ResponseModels.DailyWorkingTime;

import java.util.List;

public class UpdateScheduleReq {

    public String RecordID;
    public List<DailyWorkingTime> WorkingHours;
}
