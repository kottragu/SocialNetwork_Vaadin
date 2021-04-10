package project.vaadin.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "usr", schema = "vaadin_new_project")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "usr_id")
    private Long id;

    @NonNull
    private String username;
    @NonNull
    private String password;

    private Role role;
    private Boolean active;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
