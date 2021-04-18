package project.vaadin.view;

import com.vaadin.componentfactory.Autocomplete;
import com.vaadin.componentfactory.Chat;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import project.vaadin.additionalElement.JokeGenerator;
import project.vaadin.entity.Message;
import project.vaadin.entity.Role;
import project.vaadin.entity.User;
import project.vaadin.repo.MessageRepo;
import project.vaadin.repo.UserRepo;
import project.vaadin.view.login.LoginView;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Route()
public class MainView extends VerticalLayout {
    private UserRepo userRepo;
    private MessageRepo messageRepo;
    private List<Message> messagesFromRepo = new ArrayList<>();
    private List<com.vaadin.componentfactory.model.Message> messages = new ArrayList<>();
    private ArrayList<User> companions = new ArrayList<>();
    private Chat chat;
    private User principal;
    private User recipient;
    private HorizontalLayout header;
    private VerticalLayout leftColumn;
    private VerticalLayout centerColumn;
    private VerticalLayout rightColumn;

    @Autowired
    public MainView (UserRepo uRepo, MessageRepo mRepo) throws FileNotFoundException {
        userRepo = uRepo;
        messageRepo = mRepo;
        setSizeFull();
        setData();
        createHeader();
        createLeftColumn();
        createCenterColumn();
        createRightColumn();

        HorizontalLayout main = new HorizontalLayout();
        main.setSizeFull();
        main.add(leftColumn, centerColumn, rightColumn);
        main.setSizeFull();
        header.setSizeFull();
        add(header);
        add(main);
    }

    private void createLeftColumn() {
        leftColumn = new VerticalLayout();
        leftColumn.setWidth("20%");
        leftColumn.setHeight("85%");
        leftColumn.getStyle().set("overflow", "auto");

        ArrayList<User> companions = new ArrayList<>();
        companions.addAll(messageRepo.customFindRecipient(principal));
        companions.addAll(messageRepo.customFindAuthor(principal));
        ArrayList<User> result = checkRepeating(companions);
        companions.clear();
        companions.addAll(result);
        for (User s: companions) {
            Button button = createButtonRecipient(s);
            leftColumn.add(button);
        }
    }

    private Button createButtonRecipient(User s) {
        Button button = new Button(s.getUsername());
        button.setWidth("100%");
        button.addClickListener(click -> {
            recipient = s;
            setChat();
        });
        return button;
    }

    private ArrayList<User> checkRepeating(ArrayList<User> users) {
        ArrayList<User> result = new ArrayList<>();
        ArrayList<Long> ids = new ArrayList<>();
        for (User u: users) {
            if (!ids.contains(u.getId())) {
                result.add(u);
                ids.add(u.getId());
            }
        }
        return result;
    }

    private void setChat() { // устанавливает чат по 2 конкретным собеседникам
        centerColumn.remove(chat);
        chat = null;
        chat = new Chat();
        chat.getElement().getStyle().set("width", "100%");
        centerColumn.add(chat);
        setMessages();
        chat.setLoadingIndicator(new Div());
        chat.setMessages(messages);
        chat.setDebouncePeriod(200);
        chat.setLazyLoadTriggerOffset(2500);
        chat.scrollToBottom();
        chat.addChatNewMessageListener(send -> {
            Message message =  new Message(send.getMessage(), new Date(), principal, recipient);
            messageRepo.save(message);
            chat.addNewMessage(convertMessage(message));
            chat.clearInput();
            chat.scrollToBottom();
        });
    }


    private void setData() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal = userRepo.findByUsername(userDetails.getUsername());
    }

    private void setMessages() {
        messagesFromRepo.clear();
        messagesFromRepo = messageRepo.findByPrincipalAndCompanion(principal, recipient);
        Collections.sort(messagesFromRepo);
        converterToChatMessage();
    }

    private void createCenterColumn() {
        centerColumn = new VerticalLayout();
        centerColumn.setWidth("60%");
        chat = new Chat();
        chat.setLoadingIndicator(new Div());
        chat.setLoading(false);
        chat.getElement().getStyle().set("width", "100%");
        centerColumn.add(chat);
    }

    private void createRightColumn() throws FileNotFoundException {
        rightColumn = new VerticalLayout();
        rightColumn.setWidth("20%");
        Label joke = new Label(JokeGenerator.getInstance().generateJoke());
        rightColumn.add(joke);
    }

    private void createHeader() {
        H5 logo = new H5("Hello, " + principal.getUsername());
        logo.getElement().getStyle().set("margin", "15px 0px 9px 16px");


        Button settings = new Button("settings");
        settings.setClassName("settings-button");
        settings.addClickListener(click -> UI.getCurrent().navigate("settings"));
        Button logout = new Button("logout");
        logout.addClickListener(click -> {
            SecurityContextHolder.clearContext();
            UI.getCurrent().navigate(LoginView.class);
        });


        Button toAdmin = new Button("admin");
        toAdmin.addClickListener(click -> UI.getCurrent().navigate("admin"));
        toAdmin.setVisible(false);
        if (principal.getRole().equals(Role.ADMIN)) {
            toAdmin.setVisible(true);
        }

        Autocomplete autocomplete = new Autocomplete();
        autocomplete.setPlaceholder("find user");

        autocomplete.addChangeListener(click ->  {
            String text = click.getValue();
            List<User> userList = userRepo.findByPartOfUsername(text);
            List<String> userNames = new ArrayList<>();
            for (User user: userList) {
                if (!user.getUsername().equals(principal.getUsername()))
                    userNames.add(user.getUsername());
            }
            autocomplete.setOptions(userNames);
        });

        autocomplete.addAutocompleteValueAppliedListener(click -> {
            recipient = userRepo.findByUsername(click.getValue());
            if (companions.stream().noneMatch(u -> u.getId().equals(recipient.getId()))) {
                leftColumn.add(createButtonRecipient(recipient));
            }
            setChat();
            autocomplete.clear();
        });

        header = new HorizontalLayout();
        header.add(settings, toAdmin, autocomplete, logo, logout);
        logout.getStyle().set("margin-right", "auto");
        header.setSpacing(true);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassName("header");
    }


    private void converterToChatMessage() {
        messages.clear();
        for (Message message: messagesFromRepo) {
            messages.add(convertMessage(message));
        }
    }

    private com.vaadin.componentfactory.model.Message convertMessage(Message message) {
        return  new com.vaadin.componentfactory.model.Message(
                message.getMessage(),
                message.getAuthor().getUsername(),
                message.getAuthor().getUsername(),
                isCurrentUser(message)
        );
    }

    private boolean isCurrentUser(Message message) {
        return message.getAuthor().getUsername().equals(principal.getUsername());
    }

}