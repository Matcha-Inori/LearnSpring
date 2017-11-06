package com.matcha.bean;

/**
 * Created by Matcha on 2017/4/23.
 */
public class MyFirstBean
{
    private UserManager userManager;
    private String description;

    public UserManager getUserManager()
    {
        return userManager;
    }

    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
