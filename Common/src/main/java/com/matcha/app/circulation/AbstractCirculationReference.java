package com.matcha.app.circulation;

/**
 * Created by Administrator on 2017/4/27.
 */
public abstract class AbstractCirculationReference implements ICirculationReference
{
    private String name;
    private ICirculationReference reference;

    protected AbstractCirculationReference(String name, ICirculationReference reference)
    {
        this.name = name;
        this.reference = reference;
    }

    @Override
    public void printName()
    {
        System.out.println(this.name);
    }

    @Override
    public ICirculationReference getReference()
    {
        return this.reference;
    }

    public void setReference(ICirculationReference reference)
    {
        this.reference = reference;
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
