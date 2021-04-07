package project.vaadin.additionalElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JokeGenerator {
    private static JokeGenerator jokeGenerator;
    private ArrayList<Joke> jokes = new ArrayList<>();


    private JokeGenerator() throws FileNotFoundException {
        setJokes();
    }

    public static JokeGenerator getInstance() throws FileNotFoundException {
        if (jokeGenerator == null) {
            jokeGenerator = new JokeGenerator();
        }
        return jokeGenerator;
    }

    private void setJokes() throws FileNotFoundException {
        File file = new File("./src/main/resources/static/jokes.txt");
        Scanner scanner = new Scanner(file);
        Joke joke = new Joke();
        String line;
        while (true) {
            if (!scanner.hasNext()) {
                jokes.add(joke);
                break;
            }
            line = scanner.nextLine();
            if (line.isEmpty() || line.equals("")) {
                jokes.add(joke);
                joke = new Joke();
            } else {
                joke.addLine(line);
            }
        }
    }

    public String generateJoke() {
        int numberJoke = (int) (Math.random()*jokes.size());
        return jokes.get(numberJoke).getJoke();
    }
}
