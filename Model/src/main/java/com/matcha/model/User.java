package com.matcha.model;

import java.util.UUID;

/**
 * Created by Matcha on 2017/4/17.
 */
public class User
{
    private UUID id;
    private String name;
    private int age;

    public User()
    {
        id = null;
        name = null;
        age = -1;
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }
}
