package com.matcha.bean;

import com.matcha.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Matcha on 2017/4/17.
 */
@Component
@Lazy
public class UserRegisterService implements IUserRegisterService
{
    @Autowired
    private UserManager userManager;

    private String description;

    @Override
    public User registerUser(String userName, int userAge)
    {
        return userManager.registerUser(userName, userAge);
    }

    @Override
    public void unRegisterUser(UUID userId)
    {
        userManager.unRegisterUser(userId);
    }

    @Override
    public boolean containUser(UUID userId)
    {
        return userManager.containUser(userId);
    }

    @Override
    public User getUser(UUID userId)
    {
        return userManager.getUser(userId);
    }

    public UserManager getUserManager()
    {
        return userManager;
    }

    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
