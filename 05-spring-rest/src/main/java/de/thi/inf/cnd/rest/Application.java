package de.thi.inf.cnd.rest;

import de.thi.inf.cnd.rest.model.Comment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // <-
public class Application {
    public static void main(String[] args) {
        // Startet die Anwendung, hier die Main-Methode nutzen um die Anwendung auszuführen
        SpringApplication.run(Application.class, args);
    }
}
