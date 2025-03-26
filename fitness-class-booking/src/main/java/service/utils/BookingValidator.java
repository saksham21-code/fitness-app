package service.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;
import main.java.model.User;
import main.java.model.FitnessClass;
import main.java.enums.Tier;
import main.java.strategy.PackageStrategy;


@Slf4j
public class BookingValidator {
    public boolean canBook(User user, FitnessClass fc, Map<Tier, PackageStrategy> strategyMap) {
        if (fc == null || fc.isCancelled) {
            log.info("Class not found or cancelled.");
            return false;
        }
        if (user.bookedClasses.contains(fc.id)) {
            log.info("Already booked.");
            return false;
        }
        if (user.bookedClasses.size() >= strategyMap.get(user.tier).getMaxBookingLimit()) {
            log.info("Booking limit exceeded.");
            return false;
        }
        return true;
    }

    public boolean canCancel(User user, FitnessClass fc) {
        if (fc == null || fc.isCancelled) {
            log.info("Class not found or cancelled.");
            return false;
        }
        if (!user.bookedClasses.contains(fc.id) && !user.waitlistedClasses.contains(fc.id)) {
            log.info("No booking or waitlist found for user.");
            return false;
        }
        if (user.bookedClasses.contains(fc.id) && fc.scheduledTime.isBefore(LocalDateTime.now().plusMinutes(30))) {
            log.info("Cannot cancel within 30 minutes of class.");
            return false;
        }
        return true;
    }
}
