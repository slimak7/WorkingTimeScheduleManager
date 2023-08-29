package sk.scheduleManager.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.scheduleManager.Models.Employee;

public interface IEmployeesRepo extends JpaRepository<Employee, String> {


}
