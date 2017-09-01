package com.matcha;

import com.matcha.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matcha on 2017/4/17.
 */
@Component
public class UserManager
{
    private Map<UUID, User> registeredUserMap;

    public UserManager()
    {
        this.registeredUserMap = new ConcurrentHashMap<>();
    }

    public User registerUser(String userName, int userAge)
    {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setAge(userAge);
        this.registeredUserMap.put(userId, user);
        return user;
    }

    public void unRegisterUser(UUID userId)
    {
        this.registeredUserMap.remove(userId);
    }

    public boolean containUser(UUID userId)
    {
        return this.registeredUserMap.containsKey(userId);
    }

    public User getUser(UUID userId)
    {
        return this.registeredUserMap.get(userId);
    }
}
