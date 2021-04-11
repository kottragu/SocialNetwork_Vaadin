package project.vaadin.view.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import project.vaadin.entity.User;
import project.vaadin.repo.UserRepo;

@Route("password-recovery")
public class PasswordReset extends VerticalLayout {
    private final LoginForm reset;

    @Autowired
    UserRepo userRepo;

    public PasswordReset() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        reset = new LoginForm();
        reset.setI18n(getLoginI18n());
        reset.addLoginListener(this::resetPassword);
        reset.addForgotPasswordListener(clicked -> reset.getUI().ifPresent(ui -> ui.navigate("login")));
        add(reset);
    }

    private void resetPassword(AbstractLogin.LoginEvent loginEvent) {
        User userFromDB;
        userFromDB = userRepo.findByUsername(loginEvent.getUsername());
        if (userFromDB != null) {
            userFromDB.setPassword(loginEvent.getPassword());
            userRepo.updatePassword(userFromDB.getId(), userFromDB.getUsername(), userFromDB.getPassword());
            Notification notification = new Notification();
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.setText("Password update successfully");
            notification.setDuration(5000);
            notification.open();
            UI.getCurrent().navigate("login");
        } else
            reset.setError(true);
    }


    private LoginI18n getLoginI18n() {
        LoginI18n loginI18n = new LoginI18n();
        loginI18n.setHeader(new LoginI18n.Header());
        loginI18n.setForm(new LoginI18n.Form());
        loginI18n.getForm().setForgotPassword("Don't want? Come back");
        loginI18n.getForm().setPassword("New Password");
        loginI18n.getForm().setUsername("Username");
        loginI18n.getForm().setTitle("Are you really want to reset password?");
        loginI18n.getForm().setSubmit("Reset");
        LoginI18n.ErrorMessage errorMessage = new LoginI18n.ErrorMessage();
        errorMessage.setMessage("User doesn't exist");
        loginI18n.setErrorMessage(errorMessage);
        return loginI18n;
    }
}
