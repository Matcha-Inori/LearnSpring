package com.matcha.app.circulation;

/**
 * Created by Administrator on 2017/4/27.
 */
public class CirculationReferenceB extends AbstractCirculationReference
{
    public CirculationReferenceB()
    {
        this(null);
    }

    public CirculationReferenceB(ICirculationReference reference)
    {
        super("ReferenceB", reference);
    }
}
