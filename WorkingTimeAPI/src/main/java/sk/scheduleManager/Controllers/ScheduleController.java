package sk.scheduleManager.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ScheduleController {

    @GetMapping("/test")
    public String Test() {

        return "Success!!";
    }
}
