package project.vaadin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.vaadin.entity.User;
import java.util.Optional;

public interface userRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long id);
}
