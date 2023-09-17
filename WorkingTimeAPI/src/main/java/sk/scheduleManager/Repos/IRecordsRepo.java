package sk.scheduleManager.Repos;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.scheduleManager.Models.Record;

import java.util.Collection;

public interface IRecordsRepo extends JpaRepository<Record, String> {

    @Query(
            "SELECT R FROM Record R WHERE R.Employee.ID = ?1 AND MONTH(R.MonthYear) = ?2 AND YEAR(R.MonthYear) = ?3"
    )
    Collection<Record> GetRecordByUserDate(String employeeID, int month, int year);

    @Query(
            "SELECT R FROM Record R WHERE R.ID = ?1"
    )
    Record GetRecordByID(String ID);

    @Query(
            "SELECT R FROM Record R WHERE R.Employee.ID = ?1"
    )
    Collection<Record> GetAllByEmployee(String employeeID, PageRequest pageRequest);
}
