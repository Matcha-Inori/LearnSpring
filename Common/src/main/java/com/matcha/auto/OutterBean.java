package com.matcha.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutterBean implements IOutterBean
{
    @Autowired
    private IInnerBean innerBean;

    @Override
    public boolean hasBean(String beanName)
    {
        return false;
    }
}
