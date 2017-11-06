package com.matcha.auto;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class InnerBean implements IInnerBean
{
    private ApplicationContext applicationContext;

    @Override
    public ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}
