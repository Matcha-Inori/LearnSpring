package com.matcha.factory;

import com.matcha.model.Car;

import java.util.Random;

/**
 * Created by Administrator on 2017/5/9.
 */
public class CarFactory
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

    public CarFactory()
    {
        this.random = new Random(System.currentTimeMillis());
    }

    public Car createCar()
    {
        int nameIndex = this.random.nextInt(names.length);
        return new Car(names[nameIndex]);
    }
}
