package sk.scheduleManager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Component;
import sk.scheduleManager.Models.Employee;
import sk.scheduleManager.Repos.IEmployeesRepo;
import sk.scheduleManager.RequestModels.EmployeeReq;
import sk.scheduleManager.ResponseModels.EmployeeRes;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeesRepo employeesRepo;

    @Override
    public List<EmployeeRes> GetAll(int pageNumber, int count) {

        var pageRequest = PageRequest.of(pageNumber, count);
        var employees = employeesRepo.GetAll(pageRequest);

        List<EmployeeRes> employeeResList = new ArrayList<>();

        for (var employee : employees) {
            employeeResList.add(new EmployeeRes(employee.getID(), employee.getFirstName(), employee.getLastName()));
        }
        return employeeResList;
    }

    @Override
    public EmployeeRes Add(EmployeeReq employee) {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employee.FirstName);
        newEmployee.setLastName(employee.LastName);
        var entry = employeesRepo.save(newEmployee);

        return new EmployeeRes(entry.getID(), entry.getFirstName(), entry.getLastName());
    }
}
