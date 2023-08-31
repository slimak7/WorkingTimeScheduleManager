package sk.scheduleManager.Services;

import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.ResponseModels.RecordsRes;

public interface IRecordService {

    RecordsRes GetRecordsForEmployee(String employeeID) throws ScheduleDataAccessException;
}
