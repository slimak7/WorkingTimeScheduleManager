package sk.scheduleManager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.scheduleManager.Models.Employee;
import sk.scheduleManager.Repos.IEmployeesRepo;
import sk.scheduleManager.ResponseModels.EmployeeRes;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeesRepo employeesRepo;

    @Override
    public List<EmployeeRes> GetAll() {

        var employees = employeesRepo.findAll();

        List<EmployeeRes> employeeResList = new ArrayList<>();

        for (var employee : employees) {
            employeeResList.add(new EmployeeRes(employee.getID(), employee.getFirstName(), employee.getLastName()));
        }
        return employeeResList;
    }

    @Override
    public EmployeeRes Add(Employee employee) {

        var entry = employeesRepo.save(employee);

        return new EmployeeRes(entry.getID(), employee.getFirstName(), entry.getLastName());
    }
}
