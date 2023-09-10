package sk.scheduleManager.Services;

import sk.scheduleManager.Models.Record;

import java.text.ParseException;

public interface IStatsService {

    void UpdateScheduleStats(Record record) throws ParseException;
}
