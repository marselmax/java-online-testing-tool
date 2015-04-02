package app.model.db;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author marsel.maximov
 */
@Entity
@Table(name = "SUBMITRESULT")
public class SubmitResult {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Task task;

    @Embedded
    private TestResult testResult;

    @Column(name = "SUBMITDATETIME")
    private Timestamp submitDateTime;

    @Column(name = "PATHTOFILE")
    private String pathToFile;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public Timestamp getSubmitDateTime() {
        return submitDateTime;
    }

    public void setSubmitDateTime(Timestamp submitDateTime) {
        this.submitDateTime = submitDateTime;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }
}
