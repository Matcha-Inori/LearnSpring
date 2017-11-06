package com.matcha.bean;

/**
 * Created by Matcha on 2017/5/17.
 */
public class AutoWireBean
{
    private MyFirstBean myFirstBean;
    private UserManager userManager;

    public MyFirstBean getMyFirstBean()
    {
        return myFirstBean;
    }

    public void setMyFirstBean(MyFirstBean myFirstBean)
    {
        this.myFirstBean = myFirstBean;
    }

    public UserManager getUserManager()
    {
        return userManager;
    }

    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }
}
