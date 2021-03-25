package project.vaadin.view.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("password-recovery")
public class PasswordReset extends VerticalLayout {
    private LoginForm reset;

    public PasswordReset() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        reset = new LoginForm();
        reset.setI18n(getLoginI18n());
        reset.setAction("reset");
        reset.addForgotPasswordListener(clicked -> reset.getUI().ifPresent(ui -> ui.navigate("login")));
        add(reset);
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
        return loginI18n;
    }
}
