package project.vaadin.view.additional;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import project.vaadin.entity.User;
import project.vaadin.service.ManageUserService;


@Route("settings")
public class SettingsView extends VerticalLayout {
    private User principal;
    private final ManageUserService manageUserService;

    public SettingsView(ManageUserService manageUserService) {
        this.manageUserService = manageUserService;
        setData();
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setHeader();
        setDataChanger();
    }

    private void setHeader() {
        Button back = new Button("back");
        back.addClickListener(click -> UI.getCurrent().navigate(""));
        HorizontalLayout header = new HorizontalLayout(back);
        header.setAlignItems(Alignment.START);
        setAlignSelf(Alignment.START, header);
        add(header);
    }

    private void setDataChanger() {
        HorizontalLayout usernameChanger = new HorizontalLayout();
        TextField changeUsername = new TextField();
        changeUsername.setLabel("change username");
        Button applyChangeUsername = new Button("apply");
        applyChangeUsername.addClickListener(click -> {
            if (manageUserService.getUserByUsername(changeUsername.getValue()) != null ) {
                Notification notification = new Notification();
                notification.setText("Username is already in use");
                notification.setDuration(5000);
                notification.open();
            } else {
                principal.setUsername(changeUsername.getValue());
                manageUserService.updateUser(principal);
            }
        });
        usernameChanger.add(changeUsername, applyChangeUsername);
        usernameChanger.setDefaultVerticalComponentAlignment(Alignment.END);
        add(usernameChanger);



        HorizontalLayout passwordChanger = new HorizontalLayout();
        TextField changePassword = new TextField("change password");
        Button button = new Button("apply");
        button.addClickListener(click -> {
            principal.setPassword(changePassword.getValue());
            manageUserService.updateUser(principal);
        });
        passwordChanger.setDefaultVerticalComponentAlignment(Alignment.END);
        passwordChanger.add(changePassword, button);
        add(passwordChanger);

    }


    public void setData() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal = manageUserService.getUserByUsername(userDetails.getUsername());
    }
}
