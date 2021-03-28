package project.vaadin.view.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import project.vaadin.entity.User;
import project.vaadin.repo.UserRepo;


@Route("login")
@PageTitle("Login by Vaadin")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private final LoginForm login = new LoginForm();

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setAction("login");
        login.addForgotPasswordListener(clicked -> login.getUI().ifPresent(ui -> ui.navigate("password-recovery")));
        Button registration = new Button("Sign up");
        registration.addClickListener(clicked -> registration.getUI().ifPresent(ui -> ui.navigate("registration")));

        //сделать H1 и H3 пониже при помощи css
        add(new H1("Welcome"), new H3("again"), login, registration);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

}
