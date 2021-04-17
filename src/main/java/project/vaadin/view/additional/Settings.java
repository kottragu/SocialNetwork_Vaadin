package project.vaadin.view.additional;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import project.vaadin.entity.User;
import project.vaadin.repo.UserRepo;


@Route("settings")
public class Settings extends VerticalLayout {

    @Autowired
    private UserRepo userRepo;

    private User principal;

    public Settings() {
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
            if (userRepo.findByUsername(changeUsername.getValue()) != null ) {
                Notification notification = new Notification();
                notification.setText("Username is already in use");
                notification.setDuration(5000);
                notification.open();
            } else {
                userRepo.updateUser(principal.getId(), changeUsername.getValue(), principal.getPassword(), principal.getRole());
            }
        });
        usernameChanger.add(changeUsername, applyChangeUsername);
        usernameChanger.setDefaultVerticalComponentAlignment(Alignment.END);
        add(usernameChanger);



        HorizontalLayout passwordChanger = new HorizontalLayout();
        TextField changePassword = new TextField("change password");
        Button button = new Button("apply");
        button.addClickListener(click -> {
            userRepo.updateUser(principal.getId(), principal.getUsername(), changePassword.getValue(), principal.getRole());
        });
        passwordChanger.setDefaultVerticalComponentAlignment(Alignment.END);
        passwordChanger.add(changePassword, button);
        add(passwordChanger);

    }


    public void setData() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal = userRepo.findByUsername(userDetails.getUsername());
    }
}
