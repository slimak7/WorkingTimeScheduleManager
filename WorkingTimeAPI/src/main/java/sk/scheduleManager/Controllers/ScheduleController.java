package sk.scheduleManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.RequestModels.NewScheduleReq;
import sk.scheduleManager.ResponseModels.ScheduleRes;
import sk.scheduleManager.Services.ScheduleService;

import java.text.ParseException;

@RestController("/Schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/Schedule/CreateNew")
    public ResponseEntity<Object> CreateNewSchedule(@RequestBody NewScheduleReq newScheduleReq) {

        try {
            return new ResponseEntity<>(scheduleService.CreateNew(newScheduleReq), HttpStatus.OK);
        }
        catch (ScheduleDataAccessException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ParseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
