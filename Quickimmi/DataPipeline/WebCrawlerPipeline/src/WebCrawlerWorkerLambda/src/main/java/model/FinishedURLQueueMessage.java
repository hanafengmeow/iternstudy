package model;

import com.google.gson.Gson;



public class FinishedURLQueueMessage {
    private String taskId;
    private String source;
    private String urlId;
    private String s3Key;

    public FinishedURLQueueMessage(String taskId, String source, String urlId, String s3Key) {
        this.taskId = taskId;
        this.source = source;
        this.urlId = urlId;
        this.s3Key = s3Key;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
