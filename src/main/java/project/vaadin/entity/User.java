package project.vaadin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(schema = "vaadin_data")
public class User {
    @Id
    @GeneratedValue
    private Long Id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    private Role role;
    private boolean active;
}
