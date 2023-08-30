package sk.scheduleManager.Exceptions;

public class ScheduleDataAccessException extends Exception{

    public ScheduleDataAccessException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "An exception was thrown during schedule data processing. MESSAGE: " + super.getMessage();
    }
}
