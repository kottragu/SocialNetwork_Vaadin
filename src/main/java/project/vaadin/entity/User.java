package project.vaadin.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(schema = "vaadin_data")
public class User {
    @Id
    @GeneratedValue
    private Long Id;
    private String username;
    private String password;
    private Role role;
    private boolean active;
}
