package sk.scheduleManager.Services;

import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.RequestModels.NewScheduleReq;
import sk.scheduleManager.ResponseModels.ScheduleRes;

import java.text.ParseException;

public interface IScheduleService {

    ScheduleRes CreateNew(NewScheduleReq newScheduleReq) throws ScheduleDataAccessException, ParseException;
}
