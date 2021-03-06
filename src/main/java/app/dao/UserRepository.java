package app.dao;

import app.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author marsel.maximov
 */

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

}
