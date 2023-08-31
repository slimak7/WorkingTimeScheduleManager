package sk.scheduleManager.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.scheduleManager.Models.Employee;

public interface IEmployeesRepo extends JpaRepository<Employee, String> {


    @Query(
            "SELECT E FROM Employee E WHERE E.ID = ?1"
    )
    Employee GetByID(String ID);
}
