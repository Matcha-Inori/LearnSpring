package com.matcha.app.factory;

import com.matcha.app.model.Pen;
import org.springframework.beans.factory.FactoryBean;

import java.util.Random;

/**
 * Created by Administrator on 2017/5/15.
 */
public class PenFactory implements FactoryBean<Pen>
{
    private static final String[] names;

    static
    {
        names = new String[]{
                "CarA",
                "CarB",
                "CarC",
                "CarD",
                "CarE"
        };
    }

    private Random random;

    public PenFactory()
    {
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public Pen getObject() throws Exception
    {
        int nameIndex = random.nextInt(names.length);
        return new Pen(names[nameIndex]);
    }

    @Override
    public Class<?> getObjectType()
    {
        return Pen.class;
    }

    @Override
    public boolean isSingleton()
    {
        return true;
    }
}
