package io.github.matywaky.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class CrudApplication {

    /**
     * The main method that serves as the entry point for the Spring Boot application.
     *
     * <p>This method calls {@link SpringApplication#run(Class, String...)} to launch the application and
     * initialize the Spring context.</p>
     *
     * @param args command-line arguments passed to the application (not used in this case).
     */
    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }

}
