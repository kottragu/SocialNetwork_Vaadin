package project.vaadin.view;

import com.vaadin.componentfactory.Chat;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
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
public class MainView extends VerticalLayout {
    private UserRepo userRepo;
    private MessageRepo messageRepo;
    private List<Message> messages;
    private User principal;
    private User user;
    private HorizontalLayout header = new HorizontalLayout();
    private VerticalLayout leftColumn;
    private VerticalLayout centerColumn;
    private VerticalLayout rightColumn;

    @Autowired
    public MainView (UserRepo uRepo, MessageRepo mRepo) {
        userRepo = uRepo;
        messageRepo = mRepo;
        setData();
        createHeader();
        createLeftColumn();
        createCenterColumn();
        createRightColumn();

        HorizontalLayout main = new HorizontalLayout();
        main.add(leftColumn, centerColumn, rightColumn);
        main.setSizeFull();
        header.setSizeFull();
        add(header);
        add(main);
    }

    private void setData() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal = userRepo.findByUsername(userDetails.getUsername());
        user = userRepo.findByUsername("user");
        messages = messageRepo.findByAuthorOrRecipient(principal, user);
    }

    private void createLeftColumn() {
        leftColumn = new VerticalLayout();
        leftColumn.setWidth("20%");
        Button button = new Button("VOT");
        leftColumn.add(button);
    }

    private void createCenterColumn() {
        centerColumn = new VerticalLayout();
        centerColumn.setWidth("60%");
        Button send = new Button("send");

        TextField messageForm = new TextField();
        messageForm.setWidthFull();
        for (Message message: messages) {
            Label label = new Label(message.getMessage());
            centerColumn.add(label);
        }

        send.addClickListener(click -> {
            Message message = new Message(messageForm.getValue(), new Date(), principal, user);
            messageRepo.save(message);
        });
        centerColumn.add(messageForm, send);
    }

    private void createRightColumn() {
        rightColumn = new VerticalLayout();
        rightColumn.setWidth("20%");
        Button button = new Button("right");;
        rightColumn.add(button);
    }





    private void createHeader() {
        H5 logo = new H5("Hello, " + principal.getUsername());
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
        if (principal.getRole().equals(Role.ADMIN)) {
            toAdmin.setVisible(true);
        }

        HorizontalLayout header = new HorizontalLayout();
        header.setDefaultVerticalComponentAlignment(Alignment.END);
        header.add(settings, toAdmin, logo, logout);
        logout.getStyle().set("margin-right", "auto");
        header.setSpacing(true);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassName("header");
        this.header.add(header);
    }

}