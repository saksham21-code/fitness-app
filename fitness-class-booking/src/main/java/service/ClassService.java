package main.java.service;

import enums.FitnessClassType;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import main.java.model.FitnessClass;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassService {
    Map<String, FitnessClass> classes = new HashMap<>();

    public void createClass(String id, FitnessClassType type, int capacity, LocalDateTime time) {
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