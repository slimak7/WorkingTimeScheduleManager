package sk.scheduleManager.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.scheduleManager.Models.Record;

import java.util.Collection;
import java.util.Date;

public interface IRecordsRepo extends JpaRepository<Record, String> {

    @Query(
            "SELECT R FROM Record R WHERE R.Employee.ID = ?1 AND MONTH(R.MonthYear) = ?2 AND YEAR(R.MonthYear) = ?3"
    )
    Collection<Record> FindRecordByUserDate(String employeeID, int month, int year);

    @Query(
            "SELECT R FROM Record R WHERE R.ID = ?1"
    )
    Record FindRecordByID(String ID);

    @Query(
            "SELECT R FROM Record R WHERE R.Employee.ID = ?1"
    )
    Collection<Record> FindAllByEmployee(String employeeID);
}
