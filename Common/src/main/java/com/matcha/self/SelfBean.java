package com.matcha.self;

/**
 * Created by Administrator on 2017/4/26.
 */
public class SelfBean
{
    private Self self;

    public SelfBean()
    {
    }

    public SelfBean(Self self)
    {
        this.self = self;
    }

    public Self getSelf()
    {
        return self;
    }

    public void setSelf(Self self)
    {
        this.self = self;
    }
}
