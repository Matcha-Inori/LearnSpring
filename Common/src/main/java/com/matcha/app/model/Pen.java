package com.matcha.app.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/5/15.
 */
public class Pen
{
    private static final AtomicLong nextId;

    static
    {
        nextId = new AtomicLong(0);
    }

    private long id;
    private String name;

    public Pen()
    {
    }

    public Pen(String name)
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
