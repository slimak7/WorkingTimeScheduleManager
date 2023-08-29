package sk.scheduleManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.scheduleManager.Models.Employee;
import sk.scheduleManager.RequestModels.EmployeeReq;
import sk.scheduleManager.ResponseModels.EmployeeRes;
import sk.scheduleManager.Services.*;

import java.util.List;

@RestController("/Employees")
public class EmployeesController {

    @Autowired
    private IEmployeeService employeeService;
    @GetMapping("/Employees/GetAll")
    public List<EmployeeRes> GetAll() {

        var employees = employeeService.GetAll();

        return employees;
    }

    @PostMapping("/Employees/Add")
    public EmployeeRes Add(@RequestBody EmployeeReq employee) {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employee.FirstName);
        newEmployee.setLastName(employee.LastName);
        return employeeService.Add(newEmployee);
    }
}
