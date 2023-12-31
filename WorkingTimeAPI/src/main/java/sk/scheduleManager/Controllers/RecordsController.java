package sk.scheduleManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.Services.IRecordService;

@RestController("/Records")
public class RecordsController {

    @Autowired
    private IRecordService recordService;

    @GetMapping("/Records/GetForEmployee")
    public ResponseEntity<Object> GetRecordsForEmployee(@RequestParam(name = "employee_id") String employeeID, @RequestParam(name = "page_number") int pageNumber, @RequestParam(name = "count") int count) {

        try {
            return new ResponseEntity<>(recordService.GetRecordsForEmployee(employeeID, pageNumber, count) , HttpStatus.OK);
        }
        catch (ScheduleDataAccessException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
