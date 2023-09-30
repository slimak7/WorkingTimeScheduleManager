package sk.scheduleManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.scheduleManager.Exceptions.ScheduleAuthException;
import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.Models.Employee;
import sk.scheduleManager.RequestModels.EmployeeReq;
import sk.scheduleManager.RequestModels.LogInReq;
import sk.scheduleManager.ResponseModels.EmployeeRes;
import sk.scheduleManager.Services.*;

import java.util.List;

@RestController("/Employees")
public class EmployeesController {

    @Autowired
    private IEmployeeService employeeService;
    @GetMapping("/Employees/GetAll")
    public ResponseEntity<Object> GetAll(@RequestParam(name = "page_number") int pageNumber, @RequestParam(name = "count") int count) {

        try {
            return new ResponseEntity<>(employeeService.GetAll(pageNumber, count), HttpStatus.OK);
        }
        catch (ScheduleDataAccessException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
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


    @PostMapping("/Employees/LogIn")
    public ResponseEntity<Object> LogIn(@RequestBody LogInReq logInReq) {

        try {

            return new ResponseEntity<>(employeeService.LogIn(logInReq), HttpStatus.OK);
        }
        catch (ScheduleAuthException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
