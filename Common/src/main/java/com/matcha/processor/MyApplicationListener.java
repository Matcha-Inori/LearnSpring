package com.matcha.processor;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by Matcha on 2017/5/17.
 */
public class MyApplicationListener implements ApplicationListener
{
    @Override
    public void onApplicationEvent(ApplicationEvent event)
    {
        System.out.println("MyApplicationListener is called");
    }
}
