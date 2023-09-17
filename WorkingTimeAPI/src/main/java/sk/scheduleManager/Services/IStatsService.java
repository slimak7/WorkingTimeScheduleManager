package sk.scheduleManager.Services;

import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.Models.Record;
import sk.scheduleManager.ResponseModels.StatsResponse;

import java.text.ParseException;

public interface IStatsService {

    void UpdateScheduleStats(Record record) throws ParseException;
    StatsResponse GetStats(String recordID) throws ScheduleDataAccessException;
}
