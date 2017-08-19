package com.matcha.test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTest
{
    protected static final String springConfigPosition;

    static
    {
        springConfigPosition = "spring/transaction.xml";
    }

    protected ConfigurableApplicationContext context;

    public BaseTest()
    {
        context = new ClassPathXmlApplicationContext(springConfigPosition);
        context.registerShutdownHook();
    }
}
