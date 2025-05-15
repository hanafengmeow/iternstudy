package model;

import com.google.gson.Gson;


public class FinishedResearchURLQueueMessage extends FinishedURLQueueMessage {
    private String parent3Key;

    public FinishedResearchURLQueueMessage(String taskId, String source, String urlId, String s3Key, String parent3Key) {
        super(taskId, source, urlId, s3Key);
        this.parent3Key = parent3Key;
    }
    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
