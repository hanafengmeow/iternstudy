package model;

import com.google.gson.Gson;

public class UnvisitedUrlQueueMessage {
    private String taskId;
    private String source;
    private String url;
    private String urlType;
    private Integer curDepth;
    private Integer depth;
    private String parentURL;
    private String rootUrl;

    public String getUrlType() {
        return urlType;
    }

    public UnvisitedUrlQueueMessage setUrlType(String urlType) {
        this.urlType = urlType;
        return this;
    }

    public UnvisitedUrlQueueMessage() {
    }

    public String getSource() {
        return source;
    }

    public UnvisitedUrlQueueMessage setSource(String source) {
        this.source = source;
        return this;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public UnvisitedUrlQueueMessage setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
        return this;
    }

    public String getParentURL() {
        return parentURL;
    }

    public UnvisitedUrlQueueMessage setParentURL(String parentURL) {
        this.parentURL = parentURL;
        return this;
    }

    public Integer getCurDepth() {
        return curDepth;
    }

    public UnvisitedUrlQueueMessage setCurDepth(Integer curDepth) {
        this.curDepth = curDepth;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public UnvisitedUrlQueueMessage setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public UnvisitedUrlQueueMessage setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getDepth() {
        return depth;
    }

    public UnvisitedUrlQueueMessage setDepth(Integer depth) {
        this.depth = depth;
        return this;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}