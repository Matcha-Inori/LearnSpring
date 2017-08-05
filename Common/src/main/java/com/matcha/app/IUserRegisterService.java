package com.matcha.app;

import com.matcha.app.model.User;

import java.util.UUID;

/**
 * Created by Matcha on 2017/4/17.
 */
public interface IUserRegisterService
{
    User registerUser(String userName, int userAge);
    void unRegisterUser(UUID userId);
    boolean containUser(UUID userId);
    User getUser(UUID userId);
}
