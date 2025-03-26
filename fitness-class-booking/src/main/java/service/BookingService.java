package main.java.service;


import exceptions.BookingException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import main.java.enums.Tier;
import main.java.model.FitnessClass;
import main.java.model.User;
import main.java.strategy.PackageStrategy;
import main.java.strategy.impl.GoldStrategy;
import main.java.strategy.impl.PlatinumStrategy;
import main.java.strategy.impl.SilverStrategy;
import main.java.service.UserService;
import main.java.service.ClassService;
import org.springframework.stereotype.Service;
import service.utils.BookingValidator;
import service.utils.WaitlistHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BookingService{
    UserService userService;
    ClassService classService;
    BookingValidator validator;
    WaitlistHandler waitlistHandler;
    Map<Tier, PackageStrategy> strategyMap = new HashMap<>() {{
        put(Tier.PLATINUM, new PlatinumStrategy());
        put(Tier.GOLD, new GoldStrategy());
        put(Tier.SILVER, new SilverStrategy());
    }};

    public BookingService(UserService userService, ClassService classService) {
        this.userService = userService;
        this.classService = classService;
        this.validator = new BookingValidator();
        this.waitlistHandler = new WaitlistHandler(userService);
    }

    public synchronized void bookClass(String userId, String classId) {
        User user = userService.getUser(userId);
        FitnessClass fc = classService.getClass(classId);

        if (!validator.canBook(user, fc, strategyMap)) {
            throw new BookingException("User " + userId + " cannot book class " + classId);
        }

        fc.lock.lock();
        try {
            if (fc.attendees.size() < fc.capacity) {
                fc.attendees.add(userId);
                user.bookedClasses.add(classId);
                System.out.println("Booking successful.");
            } else {
                waitlistHandler.addToWaitlist(user, fc);
            }
        } finally {
            fc.lock.unlock();
        }
    }

    public void cancelBooking(String userId, String classId) {
        User user = userService.getUser(userId);
        FitnessClass fc = classService.getClass(classId);

        if (!validator.canCancel(user, fc)) return;

        fc.lock.lock();
        try {
            fc.attendees.remove(userId);
            user.bookedClasses.remove(classId);
            System.out.println("Cancelled successfully.");
            waitlistHandler.promoteFromWaitlist(fc, classId);
        } finally {
            fc.lock.unlock();
        }
    }
}

