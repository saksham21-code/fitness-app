package main.java.service;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import main.java.enums.Tier;
import main.java.model.User;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    Map<String, User> users = new HashMap<>();

    public void register(String id, String name, Tier tier) {
        users.put(id, new User(id, name, tier));
    }

    public User getUser(String id) {
        return users.get(id);
    }
}
