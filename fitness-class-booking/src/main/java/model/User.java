package main.java.model;
import main.java.enums.Tier;

import java.util.HashSet;
import java.util.Set;

public class User {
    public String id;
    public String name;
    public Tier tier;
    public Set<String> bookedClasses = new HashSet<>();
    public Set<String> waitlistedClasses = new HashSet<>();

    public User(String id, String name, Tier tier) {
        this.id = id;
        this.name = name;
        this.tier = tier;
    }
}