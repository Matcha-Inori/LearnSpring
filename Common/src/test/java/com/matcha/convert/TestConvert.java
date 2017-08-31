package com.matcha.convert;

import com.matcha.app.TestConverterBean;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestConvert
{
    private static final String resourcePosition = "spring/application.xml";

    @Test
    public void test() throws Exception
    {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(resourcePosition);
        context.registerShutdownHook();
        TestConverterBean testConverterBean = context.getBean("testConverterBean", TestConverterBean.class);
        System.out.println(testConverterBean);
    }
}
