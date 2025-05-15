package model;

import com.google.gson.Gson;

import java.util.List;

public class Task {
    private String id;
    private String request;
    private Long startAt;
    private String source;

    public String getSource() {
        return source;
    }

    public Task setSource(String source) {
        this.source = source;
        return this;
    }

    public String getId() {
        return id;
    }

    public Task setId(String id) {
        this.id = id;
        return this;
    }

    public String getRequest() {
        return request;
    }

    public Task setRequest(String request) {
        this.request = request;
        return this;
    }

    public Long getStartAt() {
        return startAt;
    }

    public Task setStartAt(Long startAt) {
        this.startAt = startAt;
        return this;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
