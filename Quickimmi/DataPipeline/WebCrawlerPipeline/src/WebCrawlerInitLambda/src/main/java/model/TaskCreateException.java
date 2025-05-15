package model;

public class TaskCreateException extends RuntimeException {
    public TaskCreateException(String message, Exception exp) {
        super(message, exp);
    }
}
