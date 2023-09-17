package sk.scheduleManager.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.Services.IStatsService;

@RestController("/Stats")
public class StatsController {

    @Autowired
    private IStatsService statsService;

    @GetMapping("/Stats/GetForRecord")
    public ResponseEntity<Object> GetStatsForRecord(@RequestParam(name = "record_id") String recordID) {

        try {
            return new ResponseEntity<>(statsService.GetStats(recordID), HttpStatus.OK);
        }
        catch (ScheduleDataAccessException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
