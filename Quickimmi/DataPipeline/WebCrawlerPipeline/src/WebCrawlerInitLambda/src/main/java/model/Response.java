package model;

public class Response {
    private String code;
    private String taskId;
    private String message;

    public Response(String taskId, String code, String message) {
        this.taskId = taskId;
        this.code = code;
        this.message = message;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
