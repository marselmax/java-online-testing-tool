package app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author marsel.maximov
 */

@Entity
public class Task {

    @Id
    private Long id;

    @Column(nullable = false, length = Integer.MAX_VALUE)
    private String condition;

    @Column(nullable = false)
    private String invokerClass;

    @Column(nullable = false)
    private String testClass;

    public Long getId() {
        return id;
    }

    public String getCondition() {
        return condition;
    }

    public String getInvokerClass() {
        return invokerClass;
    }

    public String getTestClass() {
        return testClass;
    }
}
