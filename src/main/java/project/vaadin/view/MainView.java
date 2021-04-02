package project.vaadin.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import project.vaadin.entity.Role;
import project.vaadin.entity.User;
import project.vaadin.repo.UserRepo;
import project.vaadin.view.login.LoginView;

@Route()
public class MainView extends AppLayout {

    UserRepo userRepo;

    @Autowired
    public MainView (UserRepo repo) {
        userRepo = repo;
        createHeader();

        VerticalLayout leftColumn = new VerticalLayout();
        leftColumn.setWidth("20%");
        VerticalLayout centerColumn = new VerticalLayout();
        centerColumn.setWidth("60%");
        VerticalLayout rightColumn = new VerticalLayout();
        rightColumn.setWidth("20%");
        addToDrawer(leftColumn, centerColumn, rightColumn);
    }

    private void createHeader() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByUsername(principal.getUsername());
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