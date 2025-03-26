package main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class FitnessApp implements CommandLineRunner {

    private final main.java.service.UserService userService;
    private final main.java.service.ClassService classService;
    private final main.java.service.BookingService bookingService;

    public FitnessApp(main.java.service.UserService userService, main.java.service.ClassService classService, main.java.service.BookingService bookingService) {
        this.userService = userService;
        this.classService = classService;
        this.bookingService = bookingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FitnessApp.class, args);
    }

    @Override
    public void run(String... args) {
        userService.register("u1", "Alice", main.java.enums.Tier.GOLD);
        userService.register("u2", "Bob", main.java.enums.Tier.SILVER);

        classService.createClass("c1", "Yoga", 1, LocalDateTime.now().plusHours(1));

        bookingService.bookClass("u1", "c1");
        bookingService.bookClass("u2", "c1");
        bookingService.cancelBooking("u1", "c1");
    }
}
