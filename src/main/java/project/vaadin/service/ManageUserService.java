package project.vaadin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vaadin.entity.User;
import project.vaadin.repo.UserRepo;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageUserService {

    private final UserRepo userRepo;


    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void saveUser(User user) {
        if (userRepo.findByUsername(user.getUsername()) == null) {
            userRepo.save(user);
        }
    }

    public void updateUser(User user) {
        if (userRepo.findById(user.getId()).isPresent()) {
            userRepo.updateUser(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
        }
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> getUsersByPartUsername(String partOfUsername) {
        return userRepo.findByPartOfUsername(partOfUsername);
    }
}
