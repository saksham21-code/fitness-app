package service.utils;

import java.time.LocalDateTime;
import java.util.Map;

public class BookingValidator {
    public boolean canBook(main.java.model.User user, main.java.model.FitnessClass fc, Map<main.java.enums.Tier, main.java.strategy.PackageStrategy> strategyMap) {
        if (fc == null || fc.isCancelled) {
            System.out.println("Class not found or cancelled.");
            return false;
        }
        if (user.bookedClasses.contains(fc.id)) {
            System.out.println("Already booked.");
            return false;
        }
        if (user.bookedClasses.size() >= strategyMap.get(user.tier).getMaxBookingLimit()) {
            System.out.println("Booking limit exceeded.");
            return false;
        }
        return true;
    }

    public boolean canCancel(main.java.model.User user, main.java.model.FitnessClass fc) {
        if (fc == null || fc.isCancelled) {
            System.out.println("Class not found or cancelled.");
            return false;
        }
        if (!user.bookedClasses.contains(fc.id) && !user.waitlistedClasses.contains(fc.id)) {
            System.out.println("No booking or waitlist found for user.");
            return false;
        }
        if (user.bookedClasses.contains(fc.id) && fc.scheduledTime.isBefore(LocalDateTime.now().plusMinutes(30))) {
            System.out.println("Cannot cancel within 30 minutes of class.");
            return false;
        }
        return true;
    }
}
