package project.vaadin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vaadin.entity.Message;
import project.vaadin.entity.User;
import project.vaadin.repo.MessageRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageMessageService {
    private final MessageRepo messageRepo;

    public void save(Message message) {
        messageRepo.save(message);
    }

    public List<Message> getUsersByRecipient(User recipient) {
        return messageRepo.findByRecipient(recipient);
    }

    public List<User> getInterlocutors(User user) {
        List<User> interlocutors = messageRepo.customFindAuthor(user);
        interlocutors.addAll(messageRepo.customFindRecipient(user));
        return checkRepeating(interlocutors);
    }

    private List<User> checkRepeating(List<User> users) {
        List<User> result = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (User u: users) {
            if (!ids.contains(u.getId())) {
                result.add(u);
                ids.add(u.getId());
            }
        }
        return result;
    }

    public List<Message> getMessagesByPrincipalAndInterlocutor(User principal, User recipient) {
        return messageRepo.findByPrincipalAndRecipient(principal, recipient);
    }
}
