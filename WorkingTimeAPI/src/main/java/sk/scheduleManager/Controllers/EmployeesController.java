package sk.scheduleManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> GetAll() {

        try {
            return new ResponseEntity<>(employeeService.GetAll(), HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/Employees/Add")
    public ResponseEntity<Object> Add(@RequestBody EmployeeReq employee) {

        try {

            return new ResponseEntity<>(employeeService.Add(employee), HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
