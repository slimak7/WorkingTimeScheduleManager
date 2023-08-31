package sk.scheduleManager.ResponseModels;

import java.util.List;

public class RecordsRes {

    public RecordsRes(String employeeID, List<Record> records) {
        EmployeeID = employeeID;
        Records = records;
    }

    public String EmployeeID;
    public List<Record> Records;
}
