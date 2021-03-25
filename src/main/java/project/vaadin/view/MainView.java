package project.vaadin.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;

@Route()
public class MainView extends AppLayout {
    public MainView () {
        createHeader();
    }

    private void createHeader() {
        H5 logo = new H5("Vaadin CRM");
        logo.addClassName("logo");
        Button logout = new Button("logout");
        logout.addClickListener(click -> {
            SecurityContextHolder.clearContext();
            UI.getCurrent().navigate(LoginView.class);
        });
        HorizontalLayout header = new HorizontalLayout(logo, logout);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.START);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
    }

}