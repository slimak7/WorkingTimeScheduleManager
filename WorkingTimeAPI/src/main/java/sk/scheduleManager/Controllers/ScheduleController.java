package sk.scheduleManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.RequestModels.NewScheduleReq;
import sk.scheduleManager.RequestModels.UpdateScheduleReq;
import sk.scheduleManager.Services.IScheduleService;



@RestController("/Schedule")
public class ScheduleController {

    @Autowired
    private IScheduleService scheduleService;

    @PostMapping("/Schedule/CreateNew")
    public ResponseEntity<Object> CreateNewSchedule(@RequestBody NewScheduleReq newScheduleReq) {

        try {
            return new ResponseEntity<>(scheduleService.CreateNew(newScheduleReq), HttpStatus.OK);
        }
        catch (ScheduleDataAccessException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Schedule/Update")
    public ResponseEntity<Object> UpdateSchedule(@RequestBody UpdateScheduleReq updateScheduleReq) {

        try {

            scheduleService.Update(updateScheduleReq);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (ScheduleDataAccessException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Schedule/GetSchedule")
    public ResponseEntity<Object> GetSchedule(@RequestParam(name = "id") String recordID) {

        try {
            return new ResponseEntity<>(scheduleService.GetByID(recordID) ,HttpStatus.OK);
        }
        catch (ScheduleDataAccessException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
