package project.vaadin.repo;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Transactional
    List<Message> findByAuthorAndRecipient(@NonNull User author, @NonNull User recipient);

    //допустим, что нижеследующее работает
    @Transactional
    int countDistinctByRecipientOrAuthor(@NonNull User recipient, @NonNull User author);

    @Transactional
    @Query("select m.recipient from Message m where m.author=?1")
    List<User> customFindRecipient (@NonNull User author);

    @Transactional
    @Query("select m.author from Message m where m.recipient=?1")
    List<User> customFindAuthor (@NonNull User recipient);

    @Transactional
    @Query("select m from Message m where m.recipient=?1 and m.author=?2 or m.recipient=?2 and m.author=?1")
    List<Message> findByPrincipalAndCompanion(@NonNull User principal, @NonNull User recipient);

    /*@Transactional
    @Query("select ")
    List<Message> findByAuthorWithRecipient(@NonNull User user1, @NonNull User user2);*/
}
