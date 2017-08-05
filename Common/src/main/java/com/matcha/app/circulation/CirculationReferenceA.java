package com.matcha.app.circulation;

/**
 * Created by Administrator on 2017/4/27.
 */
public class CirculationReferenceA extends AbstractCirculationReference
{
    public CirculationReferenceA()
    {
        this(null);
    }

    public CirculationReferenceA(ICirculationReference reference)
    {
        super("ReferenceA", reference);
    }
}
