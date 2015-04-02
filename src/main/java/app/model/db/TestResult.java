package app.model.db;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author marsel.maximov
 */

@Embeddable
public class TestResult {

    @Column(nullable = false)
    private Boolean result;

    @Column(nullable = false)
    private String cause;

    public TestResult() {
    }

    public TestResult(Boolean result, String cause) {
        this.result = result;
        this.cause = cause;
    }

    public Boolean getResult() {
        return result;
    }

    public String getCause() {
        return cause;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("result", result)
                .append("cause", cause)
                .toString();
    }
}
