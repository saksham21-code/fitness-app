package service.utils;
import main.java.model.User;
import main.java.model.FitnessClass;
import main.java.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitlistHandler {
    private final UserService userService;

    public WaitlistHandler(UserService userService) {
        this.userService = userService;
    }

    public void addToWaitlist(User user, FitnessClass fc) {
        if (!fc.waitlist.contains(user.id)) {
            fc.waitlist.add(user.id);
            user.waitlistedClasses.add(fc.id);
            log.info("Class full. Added to waitlist.");
        } else {
            log.info("Already waitlisted.");
        }
    }

    public void promoteFromWaitlist(FitnessClass fc, String classId) {
        if (!fc.waitlist.isEmpty()) {
            String nextUserId = fc.waitlist.poll();
            User nextUser = userService.getUser(nextUserId);
            fc.attendees.add(nextUserId);
            nextUser.bookedClasses.add(classId);
            nextUser.waitlistedClasses.remove(classId);
            log.info("Waitlisted user " + nextUserId + " promoted to attendee.");
        }
    }
}
