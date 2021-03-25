package project.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("main")
public class MainPage extends HorizontalLayout {
    public MainPage () {
        Button button = new Button("to root");
        button.addClickListener(event -> button.getUI().ifPresent(ui -> ui.navigate("")));
        add(button);
    }
}

