package app.dao;

import app.model.db.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author marsel.maximov
 */

public interface TaskRepository extends JpaRepository<Task, Long> {
}
