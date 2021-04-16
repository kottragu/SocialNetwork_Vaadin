package project.vaadin.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "user_message", schema = "vaadin_new_project")
public class Message implements Comparable<Message> {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String message;

    @NonNull
    private Date date;

    @NonNull
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private User author;

    @NonNull
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient")
    private User recipient;


    @Override
    public int compareTo(Message o) {
        return date.compareTo(o.getDate());
    }
}
