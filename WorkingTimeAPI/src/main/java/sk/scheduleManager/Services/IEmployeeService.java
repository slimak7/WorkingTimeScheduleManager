package sk.scheduleManager.Services;

import sk.scheduleManager.Models.Employee;
import sk.scheduleManager.ResponseModels.EmployeeRes;

import java.util.List;

public interface IEmployeeService {
    List<EmployeeRes> GetAll();
    public EmployeeRes Add(Employee employee);
}
