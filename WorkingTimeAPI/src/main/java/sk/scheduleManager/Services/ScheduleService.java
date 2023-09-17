package sk.scheduleManager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.Models.Record;
import sk.scheduleManager.Models.Schedule;
import sk.scheduleManager.Models.ScheduleID;
import sk.scheduleManager.Repos.IEmployeesRepo;
import sk.scheduleManager.Repos.IRecordsRepo;
import sk.scheduleManager.Repos.IScheduleRepo;
import sk.scheduleManager.RequestModels.NewScheduleReq;
import sk.scheduleManager.RequestModels.UpdateScheduleReq;
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
    @Autowired
    private IStatsService statsService;

    @Override
    public ScheduleRes CreateNew(NewScheduleReq newScheduleReq) throws ScheduleDataAccessException, ParseException {

        var possible = new Record();

        var selectedEmployee = employeesRepo.GetByID(newScheduleReq.EmployeeID);

        if (selectedEmployee == null) {

            throw new ScheduleDataAccessException("Employee with given ID does not exists.");
        }

        var startDate = newScheduleReq.MonthYear;
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(Calendar.DATE, 1);

        possible.setEmployee(selectedEmployee);

        var records = recordsRepo.GetRecordByUserDate(possible.getEmployee().getID(), start.get(Calendar.MONTH) + 1, start.get(Calendar.YEAR));

        if (!records.isEmpty()) {
            throw new ScheduleDataAccessException("Record for given employee and date already exists.");
        }
        possible.setEmployee(selectedEmployee);
        possible.setMonthYear(startDate);
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
            }
            else {
                startTime = new Time(new SimpleDateFormat("hh:mm").parse("0:00").getTime());
                endTime = new Time(new SimpleDateFormat("hh:mm").parse("0:00").getTime());
            }
            dailyHours.add(new DailyWorkingTime(day.get(Calendar.DAY_OF_MONTH), startTime, endTime));

            Schedule schedule = new Schedule();
            ScheduleID scheduleID = new ScheduleID();
            scheduleID.setRecord(created);
            scheduleID.setDayNumber(day.get(Calendar.DAY_OF_MONTH));
            schedule.setScheduleID(scheduleID);
            schedule.setStartTime(startTime);
            schedule.setEndTime(endTime);

            scheduleRepo.save(schedule);
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    statsService.UpdateScheduleStats(created);
                } catch (ParseException ignored) {

                }
            }
        });
        thread.start();

        return new ScheduleRes(created.getID(), created.getEmployee().getID(), startDate, dailyHours);

    }

    @Override
    public void Update(UpdateScheduleReq updateScheduleReq) throws ScheduleDataAccessException {

        var record = recordsRepo.GetRecordByID(updateScheduleReq.RecordID);

        if (record == null) {
            throw  new ScheduleDataAccessException("There is no record with given ID.");
        }

        var workingHours = updateScheduleReq.WorkingHours;

        for (var day : workingHours) {

            var entry = scheduleRepo.GetByRecordIDDay(updateScheduleReq.RecordID, day.DayNumber);

            if (entry == null) {

                Schedule schedule = new Schedule();
                ScheduleID scheduleID = new ScheduleID();
                scheduleID.setRecord(record);
                scheduleID.setDayNumber(day.DayNumber);
                schedule.setScheduleID(scheduleID);
                schedule.setStartTime(day.StartTime);
                schedule.setEndTime(day.EndTime);

                scheduleRepo.save(schedule);
            }
            else {

                entry.setStartTime(day.StartTime);
                entry.setEndTime(day.EndTime);

                scheduleRepo.save(entry);
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    statsService.UpdateScheduleStats(record);
                } catch (ParseException ignored) {

                }
            }
        });
        thread.start();
    }

    @Override
    public ScheduleRes GetByID(String recordID) throws ScheduleDataAccessException {

        var record = recordsRepo.GetRecordByID(recordID);

        if (record == null) {
            throw new ScheduleDataAccessException("There is no record for given ID.");
        }

        var entries = scheduleRepo.GetAllByRecordID(recordID);

        if (entries.isEmpty()) {
            throw new ScheduleDataAccessException("There is no schedule for given record ID.");
        }
        else {
            List<DailyWorkingTime> dailyHours = new ArrayList<>();

            for (var day : entries) {

                dailyHours.add(new DailyWorkingTime(day.getScheduleID().getDayNumber(), day.getStartTime(), day.getEndTime()));
            }

            return new ScheduleRes(record.getID(), record.getEmployee().getID(), record.getMonthYear(), dailyHours);
        }
    }

}
