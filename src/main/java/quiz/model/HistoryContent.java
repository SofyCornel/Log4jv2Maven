package quiz.model;

import org.example.Constants;

public class HistoryContent {
    String className;
    String createDate;
    String actor = Constants.ACTOR;

    public enum Status {SUCCESS, FAULT};
    Status status;
    String jsonEntity;

    public HistoryContent(String className, String createDate, Status status, String jsonEntity) {
        this.className = className;
        this.createDate = createDate;
        this.status = status;
        this.jsonEntity = jsonEntity;
    }

    public HistoryContent(String className, String createDate, String actor, Status status, String jsonEntity) {
        this.className = className;
        this.createDate = createDate;
        this.actor = actor;
        this.status = status;
        this.jsonEntity = jsonEntity;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getJsonEntity() {
        return jsonEntity;
    }

    public void setJsonEntity(String jsonEntity) {
        this.jsonEntity = jsonEntity;
    }

    @Override
    public String toString() {
        return "HistoryContent{" +
                "className='" + className + '\'' +
                ", createDate='" + createDate + '\'' +
                ", actor='" + actor + '\'' +
                ", status=" + status +
                ", jsonEntity='" + jsonEntity + '\'' +
                '}';
    }
}