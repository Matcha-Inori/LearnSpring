package com.matcha.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/5/9.
 */
public class Car
{
    private static final AtomicLong nextId;

    static
    {
        nextId = new AtomicLong(0);
    }

    private long id;
    private String name;

    public Car()
    {
    }

    public Car(String name)
    {
        this.id = nextId.getAndIncrement();
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
