package app.model.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author marsel.maximov
 */
public class ResultUI {

    private Long id;

    private Integer taskId;

    private String taskCondition;

    private Boolean result;

    private LocalDateTime submitDateTime;

    private String source;

    private String cause;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskCondition() {
        return taskCondition;
    }

    public void setTaskCondition(String taskCondition) {
        this.taskCondition = taskCondition;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public LocalDateTime getSubmitDateTime() {
        return submitDateTime;
    }

    public String getSubmitDateTimeString() {
        if (submitDateTime == null) {
            return null;
        }
        return submitDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy\nHH:mm:ss"));
    }

    public void setSubmitDateTime(LocalDateTime submitDateTime) {
        this.submitDateTime = submitDateTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public static class Builder {
        private final ResultUI resultUI;

        public Builder() {
            this.resultUI = new ResultUI();
        }

        public Builder id(Long id) {
            resultUI.setId(id);

            return this;
        }

        public Builder taskId(Integer taskId) {
            resultUI.setTaskId(taskId);

            return this;
        }

        public Builder taskCondition(String taskCondition) {
            resultUI.setTaskCondition(taskCondition);

            return this;
        }

        public Builder result(Boolean result) {
            resultUI.setResult(result);

            return this;
        }

        public Builder submitDateTime(LocalDateTime submitDateTime) {
            resultUI.setSubmitDateTime(submitDateTime);

            return this;
        }

        public Builder source(String source) {
            resultUI.setSource(source);

            return this;
        }

        public Builder cause(String cause) {
            resultUI.setCause(cause);

            return this;
        }

        public ResultUI build() {
            return resultUI;
        }
    }
}





