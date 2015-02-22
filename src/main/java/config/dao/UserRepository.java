package config.dao;

import config.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author marsel.maximov
 */

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
