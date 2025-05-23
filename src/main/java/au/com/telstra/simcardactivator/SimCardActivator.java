package au.com.telstra.simcardactivator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the SIM Card Activator microservice.
 * This microservice is responsible for activating Telstra SIM cards.
 * It receives activation requests, communicates with the SIM Card Actuator service,
 * and stores the activation records in a database.
 */
@SpringBootApplication
public class SimCardActivator {

    /**
     * Main method that starts the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SimCardActivator.class, args);
    }

}
