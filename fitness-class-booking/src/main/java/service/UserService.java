package main.java.service;
import main.java.enums.Tier;
import main.java.model.User;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public void register(String id, String name, Tier tier) {
        users.put(id, new User(id, name, tier));
    }

    public User getUser(String id) {
        return users.get(id);
    }
}
