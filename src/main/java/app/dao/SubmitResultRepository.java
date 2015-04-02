package app.dao;

import app.model.db.SubmitResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author marsel.maximov
 */
public interface SubmitResultRepository extends JpaRepository<SubmitResult, Long> {

    @Query("select result from SubmitResult result " +
            "where result.user.name = :username " +
            "order by result.submitDateTime desc")
    List<SubmitResult> findByUser(@Param("username") String username);
}
