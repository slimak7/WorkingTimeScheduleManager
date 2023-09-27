package sk.scheduleManager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Component;
import sk.scheduleManager.Helpers.StringHashing;
import sk.scheduleManager.Models.Employee;
import sk.scheduleManager.Repos.IEmployeesRepo;
import sk.scheduleManager.RequestModels.EmployeeReq;
import sk.scheduleManager.ResponseModels.EmployeeRes;

import java.awt.print.Pageable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
}
