package service.utils;

public class WaitlistHandler {
    private final main.java.service.UserService userService;

    public WaitlistHandler(main.java.service.UserService userService) {
        this.userService = userService;
    }

    public void addToWaitlist(main.java.model.User user, main.java.model.FitnessClass fc) {
        if (!fc.waitlist.contains(user.id)) {
            fc.waitlist.add(user.id);
            user.waitlistedClasses.add(fc.id);
            System.out.println("Class full. Added to waitlist.");
        } else {
            System.out.println("Already waitlisted.");
        }
    }

    public void promoteFromWaitlist(main.java.model.FitnessClass fc, String classId) {
        if (!fc.waitlist.isEmpty()) {
            String nextUserId = fc.waitlist.poll();
            main.java.model.User nextUser = userService.getUser(nextUserId);
            fc.attendees.add(nextUserId);
            nextUser.bookedClasses.add(classId);
            nextUser.waitlistedClasses.remove(classId);
            System.out.println("Waitlisted user " + nextUserId + " promoted to attendee.");
        }
    }
}
