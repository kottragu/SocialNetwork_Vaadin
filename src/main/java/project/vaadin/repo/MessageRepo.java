package project.vaadin.repo;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import project.vaadin.entity.Message;
import project.vaadin.entity.User;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    @Transactional
    List<Message> findByAuthor(@NonNull User author);
    @Transactional
    List<Message> findByRecipient(@NonNull User recipient);
    @Transactional
    List<Message> findByAuthorOrRecipient(@NonNull User author, @NonNull User recipient);
}
