package project.vaadin.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import project.vaadin.entity.Message;
import project.vaadin.entity.Role;
import project.vaadin.entity.User;
import project.vaadin.repo.MessageRepo;
import project.vaadin.repo.UserRepo;
import project.vaadin.view.login.LoginView;

import java.util.Date;
import java.util.List;

@Route()
public class MainView extends AppLayout {
    private UserRepo userRepo;
    private MessageRepo messageRepo;
    private List<Message> messages;
    private User principal;
    private User user;

    @Autowired
    public MainView (UserRepo uRepo, MessageRepo mRepo) {
        userRepo = uRepo;
        messageRepo = mRepo;

        setData();
        createHeader();
        createLeftColumn();
        createCenterColumn();
        createRightColumn();
    }

    private void setData() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal = userRepo.findByUsername(userDetails.getUsername());
        user = userRepo.findByUsername("user");
        messages = messageRepo.findByAuthorOrRecipient(principal, user);
    }

    private void createLeftColumn(){
        VerticalLayout leftColumn = new VerticalLayout();
        leftColumn.setWidth("20%");
        addToDrawer(leftColumn);
    }

    private void createCenterColumn() {
        VerticalLayout centerColumn = new VerticalLayout();
        centerColumn.setWidth("60%");
        Button send = new Button("send");

        TextField messageForm = new TextField();
        messageForm.setWidthFull();

        send.addClickListener(click -> {
            Message message = new Message(messageForm.getValue(), new Date(), principal, user);
            System.out.println("From " + message.getAuthor().getUsername() + " to " + message.getRecipient().getUsername() + " value " + message.getMessage() + " with date " + message.getDate().toString());
            messageRepo.save(message);
        });
        centerColumn.add(messageForm, send);
        addToDrawer(centerColumn);
    }

    private void createRightColumn() {
        VerticalLayout rightColumn = new VerticalLayout();
        rightColumn.setWidth("20%");


        addToDrawer(rightColumn);
    }





    private void createHeader() {
        H5 logo = new H5("Hello, " + user.getUsername());
        logo.addClassName("logo");

        Button settings = new Button("settings");
        settings.addClickListener(click -> UI.getCurrent().navigate("settings"));

        Button logout = new Button("logout");
        logout.addClickListener(click -> {
            SecurityContextHolder.clearContext();
            UI.getCurrent().navigate(LoginView.class);
        });


        Button toAdmin = new Button("admin");
        toAdmin.addClickListener(click -> UI.getCurrent().navigate("admin"));
        toAdmin.setVisible(false);
        if (user.getRole().equals(Role.ADMIN)) {
            toAdmin.setVisible(true);
        }

        HorizontalLayout header = new HorizontalLayout(settings, toAdmin, logo, logout);
        header.setSpacing(true);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.START);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
    }

}