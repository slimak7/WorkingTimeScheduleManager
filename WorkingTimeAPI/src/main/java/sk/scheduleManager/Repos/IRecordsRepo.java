package sk.scheduleManager.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.scheduleManager.Models.Record;

public interface IRecordsRepo extends JpaRepository<Record, String> {
}
