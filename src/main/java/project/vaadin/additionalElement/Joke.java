package project.vaadin.additionalElement;

import java.util.ArrayList;

public class Joke {
    private ArrayList<String> joke = new ArrayList<>();

    public Joke () {
    }

    public Joke (String line) {
        joke.add(line);
    }

    public void addLine(String line) {
        joke.add(line);
    }

    public String getJoke() {
        StringBuilder result = new StringBuilder();
        for (String s:joke) {
            result.append(s).append(" \n ");
        }
        return result.toString();
    }
}
