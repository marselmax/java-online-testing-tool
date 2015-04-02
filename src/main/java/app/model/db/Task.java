package app.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author marsel.maximov
 */

@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "TASKID")
    private Integer taskId;

    @Column(nullable = false, length = Integer.MAX_VALUE)
    private String condition;

    @Column(name = "INVOKER_CLASS_PATH")
    private String invokerClassPath;

    @Column(name = "INVOKERCLASS", nullable = false)
    private String invokerClass;

    @Column(name = "TEST_CLASS_PATH")
    private String testClassPath;

    @Column(name = "TESTCLASS", nullable = false)
    private String testClass;

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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getInvokerClassPath() {
        return invokerClassPath;
    }

    public void setInvokerClassPath(String invokerClassPath) {
        this.invokerClassPath = invokerClassPath;
    }

    public String getInvokerClass() {
        return invokerClass;
    }

    public void setInvokerClass(String invokerClass) {
        this.invokerClass = invokerClass;
    }

    public String getTestClassPath() {
        return testClassPath;
    }

    public void setTestClassPath(String testClassPath) {
        this.testClassPath = testClassPath;
    }

    public String getTestClass() {
        return testClass;
    }

    public void setTestClass(String testClass) {
        this.testClass = testClass;
    }

    public static class Builder {
        private final Task task = new Task();

        public Builder id(Long id) {
            task.setId(id);

            return this;
        }

        public Builder taskId(Integer taskId) {
            task.setTaskId(taskId);

            return this;
        }

        public Builder condition(String condition) {
            task.setCondition(condition);

            return this;
        }

        public Builder invokerClassPath(String invokerClassPath) {
            task.setTestClassPath(invokerClassPath);

            return this;
        }

        public Builder invokerClass(String invokerClass) {
            task.setInvokerClass(invokerClass);

            return this;
        }

        public Builder testClassPath(String testClassPath) {
            task.setTestClassPath(testClassPath);

            return this;
        }

        public Builder testClass(String testClass) {
            task.setTestClass(testClass);

            return this;
        }

        public Task build() {
            return task;
        }
    }
}
