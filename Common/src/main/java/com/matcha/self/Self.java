package com.matcha.self;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Self
{
    private String name;
    private int age;
    private String[] evaluates;

    public Self()
    {
    }

    public Self(String name, int age, String[] evaluates)
    {
        this.name = name;
        this.age = age;
        this.evaluates = evaluates;
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

    public String[] getEvaluates()
    {
        return evaluates;
    }

    public void setEvaluates(String[] evaluates)
    {
        this.evaluates = evaluates;
    }
}
