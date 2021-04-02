package project.vaadin.view.additional;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("settings")
public class Settings extends AppLayout {

    public Settings () {
        Button back = new Button("back to the USSR");
        back.addClickListener(click -> UI.getCurrent().navigate(""));
        HorizontalLayout navbar = new HorizontalLayout(back);
        navbar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.START);
        addToNavbar(navbar);
    }
}
