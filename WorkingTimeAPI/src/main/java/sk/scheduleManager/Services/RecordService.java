package sk.scheduleManager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.Repos.IRecordsRepo;
import sk.scheduleManager.ResponseModels.Record;
import sk.scheduleManager.ResponseModels.RecordsRes;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecordService implements IRecordService{

    @Autowired
    private IRecordsRepo recordsRepo;
    @Override
    public RecordsRes GetRecordsForEmployee(String employeeID) throws ScheduleDataAccessException {

        var records = recordsRepo.FindAllByEmployee(employeeID);

        if (records.isEmpty()) {
            throw new ScheduleDataAccessException("There are no records for given employee ID.");
        }
        else {

            List<Record> recordList = new ArrayList<>();

            for (var r : records) {

                recordList.add(new Record(r.getID(), r.getMonthYear()));
            }
            return new RecordsRes(employeeID, recordList);
        }
    }
}
