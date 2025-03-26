package main.java.model;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class FitnessClass {
    public String id;
    public String type;
    public int capacity;
    public LocalDateTime scheduledTime;
    public Set<String> attendees = new HashSet<>();
    public Queue<String> waitlist = new LinkedList<>();
    public boolean isCancelled = false;
    public ReentrantLock lock = new ReentrantLock();

    public FitnessClass(String id, String type, int capacity, LocalDateTime scheduledTime) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.scheduledTime = scheduledTime;
    }
}
