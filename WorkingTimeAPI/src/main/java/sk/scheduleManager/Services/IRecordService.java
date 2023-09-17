package sk.scheduleManager.Services;

import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.ResponseModels.RecordsRes;

public interface IRecordService {

    RecordsRes GetRecordsForEmployee(String employeeID, int pageNumber, int count) throws ScheduleDataAccessException;
}
