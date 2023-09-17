package sk.scheduleManager.Repos;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.scheduleManager.Models.Employee;

import java.awt.print.Pageable;
import java.util.Collection;

public interface IEmployeesRepo extends JpaRepository<Employee, String> {


    @Query(
            "SELECT E FROM Employee E WHERE E.ID = ?1"
    )
    Employee GetByID(String ID);

    @Query(
            "SELECT E FROM Employee E"
    )
    Collection<Employee> GetAll(PageRequest pageRequest);
}
