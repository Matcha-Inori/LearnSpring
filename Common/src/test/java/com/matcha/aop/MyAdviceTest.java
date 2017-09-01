package com.matcha.aop;

import com.matcha.IUserRegisterService;
import com.matcha.MyFirstBean;
import com.matcha.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Matcha on 2017/5/22.
 */
public class MyAdviceTest
{
    private final String aopResourcePosition = "spring/aopApplication.xml";

    private ConfigurableApplicationContext applicationContext;

    public MyAdviceTest()
    {
        this.applicationContext = new ClassPathXmlApplicationContext(aopResourcePosition);
    }

    @Test
    public void testAop() throws Exception
    {
        IUserRegisterService iUserRegisterService =
                this.applicationContext.getBean("userRegisterService", IUserRegisterService.class);
        User userRiven = iUserRegisterService.registerUser("Riven", 25);
        User otherUserRiven = iUserRegisterService.getUser(userRiven.getId());
        Assert.assertTrue(userRiven == otherUserRiven);
        iUserRegisterService.unRegisterUser(userRiven.getId());
        User finalUserRiven = iUserRegisterService.getUser(userRiven.getId());
        Assert.assertNull(finalUserRiven);

        MyFirstBean myFirstBean = this.applicationContext.getBean("myFirstBean", MyFirstBean.class);
        System.out.println(myFirstBean.getDescription());

        IUserRegisterService iUserRegisterService2 =
                this.applicationContext.getBean("userRegisterService2", IUserRegisterService.class);
        User userRiven2 = iUserRegisterService2.registerUser("Riven", 25);
        User otherUserRiven2 = iUserRegisterService2.getUser(userRiven2.getId());
        Assert.assertTrue(userRiven2 == otherUserRiven2);
        iUserRegisterService2.unRegisterUser(userRiven2.getId());
        User finalUserRiven2 = iUserRegisterService2.getUser(userRiven.getId());
        Assert.assertNull(finalUserRiven2);
    }
}