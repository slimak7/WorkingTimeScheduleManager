package sk.scheduleManager.Services;

import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.Models.Record;
import sk.scheduleManager.ResponseModels.StatsRes;

import java.text.ParseException;

public interface IStatsService {

    void UpdateScheduleStats(Record record) throws ParseException;
    StatsRes GetStats(String recordID) throws ScheduleDataAccessException;
}
