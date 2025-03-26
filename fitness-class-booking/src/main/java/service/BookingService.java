package main.java.service;


import main.java.enums.Tier;
import main.java.model.FitnessClass;
import main.java.model.User;
import main.java.strategy.PackageStrategy;
import main.java.strategy.impl.GoldStrategy;
import main.java.strategy.impl.PlatinumStrategy;
import main.java.strategy.impl.SilverStrategy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookingService {
    private final Map<Tier, PackageStrategy> strategyMap = new HashMap<>() {{
        put(Tier.PLATINUM, new PlatinumStrategy());
        put(Tier.GOLD, new GoldStrategy());
        put(Tier.SILVER, new SilverStrategy());
    }};

    private final UserService userService;
    private final ClassService classService;

    public BookingService(UserService userService, ClassService classService) {
        this.userService = userService;
        this.classService = classService;
    }

    public synchronized void bookClass(String userId, String classId) {
        User user = userService.getUser(userId);
        FitnessClass fc = classService.getClass(classId);
        if (fc == null || fc.isCancelled) {
            System.out.println("Class not found or cancelled.");
            return;
        }
        if (user.bookedClasses.contains(classId)) {
            System.out.println("Already booked.");
            return;
        }
        if (user.bookedClasses.size() >= strategyMap.get(user.tier).getMaxBookingLimit()) {
            System.out.println("Booking limit exceeded.");
            return;
        }

        fc.lock.lock();
        try {
            if (fc.attendees.size() < fc.capacity) {
                fc.attendees.add(userId);
                user.bookedClasses.add(classId);
                System.out.println("Booking successful.");
            } else {
                if (!fc.waitlist.contains(userId)) {
                    fc.waitlist.add(userId);
                    user.waitlistedClasses.add(classId);
                    System.out.println("Class full. Added to waitlist.");
                } else {
                    System.out.println("Already waitlisted.");
                }
            }
        } finally {
            fc.lock.unlock();
        }
    }

    public void cancelBooking(String userId, String classId) {
        User user = userService.getUser(userId);
        FitnessClass fc = classService.getClass(classId);
        if (fc == null || fc.isCancelled) {
            System.out.println("Class not found or cancelled.");
            return;
        }

        if (user.bookedClasses.contains(classId)) {
            if (fc.scheduledTime.isBefore(LocalDateTime.now().plusMinutes(30))) {
                System.out.println("Cannot cancel within 30 minutes of class.");
                return;
            }
            fc.lock.lock();
            try {
                fc.attendees.remove(userId);
                user.bookedClasses.remove(classId);
                System.out.println("Cancelled successfully.");
                if (!fc.waitlist.isEmpty()) {
                    String nextUserId = fc.waitlist.poll();
                    User nextUser = userService.getUser(nextUserId);
                    fc.attendees.add(nextUserId);
                    nextUser.bookedClasses.add(classId);
                    nextUser.waitlistedClasses.remove(classId);
                    System.out.println("Waitlisted user " + nextUserId + " promoted to attendee.");
                }
            } finally {
                fc.lock.unlock();
            }
        } else if (user.waitlistedClasses.contains(classId)) {
            fc.waitlist.remove(userId);
            user.waitlistedClasses.remove(classId);
            System.out.println("Removed from waitlist.");
        } else {
            System.out.println("No booking or waitlist found for user.");
        }
    }
}

