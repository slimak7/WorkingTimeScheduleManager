package sk.scheduleManager.Exceptions;

public class ScheduleAuthException extends Exception {

    public ScheduleAuthException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "An exception was thrown during auth process. MESSAGE: " + super.getMessage();
    }
}
