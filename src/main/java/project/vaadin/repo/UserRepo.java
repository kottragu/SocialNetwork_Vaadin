package project.vaadin.repo;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import project.vaadin.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long id);

}
