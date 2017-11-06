package com.matcha.auto;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public interface IInnerBean extends ApplicationContextAware
{
    ApplicationContext getApplicationContext();
}
