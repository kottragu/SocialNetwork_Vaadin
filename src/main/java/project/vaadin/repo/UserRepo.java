package project.vaadin.repo;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.vaadin.entity.Role;
import project.vaadin.entity.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Transactional
    User findByUsername(String username);

    @Transactional
    Optional<User> findById(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.username=?2, u.password=?3, u.role=?4 where u.id=?1")
    void updateUser(@Param("id") Long id, @Param("username") String username, @Param("password") String password, @Param("role") Role role);

    @Transactional
    @Query("select u from User u where u.username like %:username%")
    List<User> findByPartOfUsername(@Param("username") @NonNull String username);
}
