package sk.scheduleManager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Component;
import sk.scheduleManager.Exceptions.ScheduleAuthException;
import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.Helpers.StringHashing;
import sk.scheduleManager.JWT.JWTManager;
import sk.scheduleManager.Models.Employee;
import sk.scheduleManager.Repos.IEmployeesRepo;
import sk.scheduleManager.RequestModels.EmployeeReq;
import sk.scheduleManager.RequestModels.LogInReq;
import sk.scheduleManager.ResponseModels.EmployeeRes;
import sk.scheduleManager.ResponseModels.JwtTokenRes;

import java.awt.print.Pageable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeesRepo employeesRepo;


    @Override
    public List<EmployeeRes> GetAll(int pageNumber, int count) throws ScheduleDataAccessException {

        var pageRequest = PageRequest.of(pageNumber, count);
        var employees = employeesRepo.GetAll(pageRequest);

        if (employees.isEmpty()) {
            throw new ScheduleDataAccessException("There is no more employees for given page number.");
        }

        List<EmployeeRes> employeeResList = new ArrayList<>();

        for (var employee : employees) {
            employeeResList.add(new EmployeeRes(employee.getID(), employee.getFirstName(), employee.getLastName(),
                    employee.getLogin(), employee.isAdmin()));
        }
        return employeeResList;
    }

    @Override
    public EmployeeRes Add(EmployeeReq employee) throws NoSuchAlgorithmException, InvalidKeySpecException {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employee.FirstName);
        newEmployee.setLastName(employee.LastName);
        newEmployee.setLogin(employee.FirstName.toCharArray()[0] + employee.LastName);
        var password = StringHashing.GetPBKDF2("Default");
        newEmployee.setPassword(password);
        newEmployee.setAdmin(employee.IsAdmin);
        var entry = employeesRepo.save(newEmployee);

        return new EmployeeRes(entry.getID(), entry.getFirstName(), entry.getLastName(), entry.getLogin(), entry.isAdmin());
    }

    @Override
    public JwtTokenRes LogIn(LogInReq logInReq) throws ScheduleAuthException, NoSuchAlgorithmException, InvalidKeySpecException {

        var employee = employeesRepo.GetByLogin(logInReq.Login);

        if (employee == null) {
            throw new ScheduleAuthException("Provided data is incorrect.");
        }

        var passwordHash = StringHashing.GetPBKDF2(logInReq.Password);

        if (!Arrays.equals(employee.getPassword(), passwordHash)) {
            throw new ScheduleAuthException("Provided data is incorrect.");
        }

        var token = JWTManager.GenerateToken(employee);

        return new JwtTokenRes(token);
    }
}
