package sk.scheduleManager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.Models.Record;
import sk.scheduleManager.Models.ScheduleStats;
import sk.scheduleManager.Repos.IRecordsRepo;
import sk.scheduleManager.Repos.IScheduleRepo;
import sk.scheduleManager.Repos.IScheduleStatsRepo;
import sk.scheduleManager.ResponseModels.StatsResponse;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

@Component
public class StatsService implements IStatsService{

    @Autowired
    private IScheduleRepo scheduleRepo;
    @Autowired
    private IRecordsRepo recordsRepo;

    @Autowired
    private IScheduleStatsRepo statsRepo;

    @Override
    public void UpdateScheduleStats(Record record) throws ParseException {

        var schedule = scheduleRepo.GetAllByRecordID(record.getID());

        if (schedule.isEmpty()) {
            return;
        }

        var stats = statsRepo.GetByID(record.getID());

        if (stats == null) {

            stats = new ScheduleStats();
            stats.setRecordID(record.getID());
        }

        int totalMinutes = 0;
        int numberOfWorkingDays = 0;
        int overtimeMinutes = 0;

        for (var day:schedule
             ) {

            var startTime = day.getStartTime();
            var endTime = day.getEndTime();

            if (Objects.equals(startTime, new Time(new SimpleDateFormat("hh:mm").parse("0:00").getTime()))
            && Objects.equals(endTime, new Time(new SimpleDateFormat("hh:mm").parse("0:00").getTime()))) {

                continue;
            }

            Calendar start = Calendar.getInstance();
            start.setTime(startTime);
            Calendar end = Calendar.getInstance();
            end.setTime(endTime);


            int totalNumberOfHours = (end.get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY));
            int dailyMinutes = (totalNumberOfHours * 60) - start.get(Calendar.MINUTE) + end.get(Calendar.MINUTE);
            totalMinutes += dailyMinutes;
            numberOfWorkingDays++;

            Calendar date = Calendar.getInstance();
            date.setTime(record.getMonthYear());
            date.set(Calendar.DATE, day.getScheduleID().getDayNumber());

            if (date.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {

                int defaultWorkingMinutes = 8 * 60;
                overtimeMinutes += dailyMinutes > defaultWorkingMinutes ? dailyMinutes - defaultWorkingMinutes : 0;
            }
            else {

                overtimeMinutes += dailyMinutes;
            }

        }

        stats.setTotalMinutes(totalMinutes);
        stats.setNumberOfWorkingDays(numberOfWorkingDays);
        stats.setTotalOvertimeMinutes(overtimeMinutes);

        statsRepo.save(stats);
    }

    @Override
    public StatsResponse GetStats(String recordID) throws ScheduleDataAccessException {

        var record = recordsRepo.GetRecordByID(recordID);

        if (record == null) {

            throw new ScheduleDataAccessException("There is no record with given ID");
        }

        var stats = statsRepo.GetByID(recordID);

        if (stats == null) {
            throw new ScheduleDataAccessException("There is no stats for given recordID");
        }

        return new StatsResponse(new sk.scheduleManager.ResponseModels.Record(record.getID(), record.getMonthYear()),
                stats.getTotalMinutes(), stats.getNumberOfWorkingDays(), stats.getTotalOvertimeMinutes());

    }
}
