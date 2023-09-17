package sk.scheduleManager.Services;

import sk.scheduleManager.Models.Employee;
import sk.scheduleManager.RequestModels.EmployeeReq;
import sk.scheduleManager.ResponseModels.EmployeeRes;

import java.util.List;

public interface IEmployeeService {
    List<EmployeeRes> GetAll(int pageNumber, int count);
    public EmployeeRes Add(EmployeeReq employee);
}
