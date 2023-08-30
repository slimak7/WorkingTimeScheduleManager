package sk.scheduleManager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;
import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.Models.Employee;
import sk.scheduleManager.Models.Record;
import sk.scheduleManager.Repos.IEmployeesRepo;
import sk.scheduleManager.Repos.IRecordsRepo;
import sk.scheduleManager.Repos.IScheduleRepo;
import sk.scheduleManager.RequestModels.NewScheduleReq;
import sk.scheduleManager.ResponseModels.DailyWorkingTime;
import sk.scheduleManager.ResponseModels.ScheduleRes;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ScheduleService implements IScheduleService {

    @Autowired
    private IEmployeesRepo employeesRepo;
    @Autowired
    private IRecordsRepo recordsRepo;
    @Autowired
    private IScheduleRepo scheduleRepo;

    @Override
    public ScheduleRes CreateNew(NewScheduleReq newScheduleReq) throws ScheduleDataAccessException, ParseException {

        var possible = new Record();

        Employee employee = new Employee();
        employee.setID(newScheduleReq.EmployeeID);

        var selectedEmployee = employeesRepo.findOne(Example.of(employee, ExampleMatcher.matchingAny())).orElse(null);

        if (selectedEmployee == null) {

            throw new ScheduleDataAccessException("Employee with given ID does not exists.");
        }

        var startDate = newScheduleReq.MonthYear;
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(Calendar.DATE, 1);

        possible.setEmployee(selectedEmployee);

        var records = recordsRepo.findAll(Example.of(possible, ExampleMatcher.matchingAny()));

        if (!records.isEmpty()) {

            for (var record : records) {
                var date = record.getMonthYear();

                if (date.getMonth() == start.getTime().getMonth() && date.getYear() == start.getTime().getYear()) {

                    throw new ScheduleDataAccessException("Record for given employee and date already exists.");
                }
            }
        }


        possible.setEmployee(selectedEmployee);
        possible.setMonthYear(start.getTime());
        var created = recordsRepo.save(possible);

        List<DailyWorkingTime> dailyHours = new ArrayList<>();

        Calendar end = Calendar.getInstance();
        end.setTime(startDate);
        end.add(Calendar.MONTH, 1);
        end.set(Calendar.DATE, 1);

        for (Date date = start.getTime(); date.before(end.getTime()); start.add(Calendar.DATE, 1), date = start.getTime()) {

            Time startTime;
            Time endTime;

            var day = Calendar.getInstance();
            day.setTime(date);
            if (day.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && day.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {


                startTime = new Time(new SimpleDateFormat("hh:mm").parse("8:00").getTime());
                endTime = new Time(new SimpleDateFormat("hh:mm").parse("16:00").getTime());

                dailyHours.add(new DailyWorkingTime(day.get(Calendar.DAY_OF_MONTH), startTime, endTime));

            }
        }

        return new ScheduleRes(created.getID(), created.getEmployee().getID(), start.getTime(), dailyHours);

    }
}
