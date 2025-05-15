package model;

import com.google.gson.Gson;

import java.util.List;

public class RequestMetadata {
    private String taskId;
    private String source;
    private Integer depth;
    private List<String> rootUrls;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSource() {
        return source;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public List<String> getRootUrls() {
        return rootUrls;
    }

    public void setRootUrls(List<String> rootUrls) {
        this.rootUrls = rootUrls;
    }
}
