package controller;

import dto.UserRequest;
import dto.ClassRequest;
import dto.BookingRequest;
import enums.FitnessClassType;
import lombok.RequiredArgsConstructor;
import main.java.enums.Tier;
import main.java.service.BookingService;
import main.java.service.ClassService;
import main.java.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FitnessController {

    private final UserService userService;
    private final ClassService classService;
    private final BookingService bookingService;

    @PostMapping("/users")
    public String registerUser(@RequestBody UserRequest request) {
        userService.register(request.getId(), request.getName(), Tier.valueOf(request.getTier()));
        return "User registered successfully";
    }

    @PostMapping("/classes")
    public String createClass(@RequestBody ClassRequest request) {
        classService.createClass(request.getId(), FitnessClassType.valueOf(request.getType()), request.getCapacity(), LocalDateTime.parse(request.getScheduledTime()));
        return "Class created successfully";
    }

    @PostMapping("/book")
    public String book(@RequestBody BookingRequest request) {
        bookingService.bookClass(request.getUserId(), request.getClassId());
        return "Booking processed successfully";
    }

    @PostMapping("/cancel")
    public String cancel(@RequestBody BookingRequest request) {
        bookingService.cancelBooking(request.getUserId(), request.getClassId());
        return "Booking cancelled successfully";
    }
}
