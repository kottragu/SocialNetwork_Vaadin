package project.vaadin.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "usr", schema = "vaadin_new_project")
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue
    @Column(name = "usr_id")
    private Long id;

    /*@OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<Message> messages;*/
    @NonNull
    private String username;
    @NonNull
    private String password;

    private Role role;
    private Boolean active;
}
