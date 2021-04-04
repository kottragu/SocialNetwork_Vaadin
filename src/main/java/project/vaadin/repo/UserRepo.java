package project.vaadin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import project.vaadin.entity.Role;
import project.vaadin.entity.User;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Long> {
    @Transactional
    User findByUsername(String username);
    @Transactional
    Optional<User> findById(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.username=?2, u.password=?3 where u.id=?1")
    void updatePassword(@Param("id") Long id, @Param("username") String username, @Param("password") String password);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.username=?2, u.password=?3, u.role=?4 where u.id=?1")
    void updateUser(@Param("id") Long id, @Param("username") String username, @Param("password") String password, @Param("role") Role role);

}
