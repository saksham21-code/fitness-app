package main.java.service;

import main.java.model.FitnessClass;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ClassService {
    private final Map<String, FitnessClass> classes = new HashMap<>();

    public void createClass(String id, String type, int capacity, LocalDateTime time) {
        classes.put(id, new FitnessClass(id, type, capacity, time));
    }

    public void cancelClass(String id) {
        if (classes.containsKey(id)) {
            classes.get(id).isCancelled = true;
        }
    }

    public FitnessClass getClass(String id) {
        return classes.get(id);
    }

    public List<FitnessClass> getAllClasses() {
        return new ArrayList<>(classes.values());
    }
}