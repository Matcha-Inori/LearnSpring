package com.matcha;

import com.matcha.bean.*;
import com.matcha.circulation.ICirculationReference;
import com.matcha.model.Car;
import com.matcha.model.User;
import com.matcha.overried.ReplacedMethod;
import com.matcha.reflect.MatchaClassLoader;
import com.matcha.self.SelfBean;
import com.matcha.thread.ApplicationContextRunnable;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.net.URL;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Matcha on 2017/4/18.
 */
public class UserRegisterServiceTest
{
    private static final String resourcePosition = "spring/application.xml";
    private static final String autoScanResourcePosition = "spring/autoScanApplication.xml";

    private ClassLoader selfClassLoader;
    private DefaultListableBeanFactory defaultListableBeanFactory;
    private ConfigurableApplicationContext classPathApplicationContext;
    private ConfigurableApplicationContext fileSystemApplicationContext;
    private ConfigurableApplicationContext autoScanApplicationContext;

    public UserRegisterServiceTest()
    {
        selfClassLoader = new MatchaClassLoader();
        Thread.currentThread().setContextClassLoader(selfClassLoader);

        ClassPathResource classPathResource = new ClassPathResource(resourcePosition);
        defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(classPathResource);

        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resourceURL = classLoader.getResource(resourcePosition);
        this.fileSystemApplicationContext = new FileSystemXmlApplicationContext(resourceURL.toString());
        this.fileSystemApplicationContext.registerShutdownHook();

        this.classPathApplicationContext = new ClassPathXmlApplicationContext(resourcePosition);
        this.classPathApplicationContext.registerShutdownHook();

        this.autoScanApplicationContext = new ClassPathXmlApplicationContext(autoScanResourcePosition);
        this.autoScanApplicationContext.registerShutdownHook();
    }

    @Test
    public void testAutoScanApplicationContext() throws Exception
    {
        test(autoScanApplicationContext);
    }

    @Test
    public void testFileSystemApplicationContext() throws Exception
    {
        test(fileSystemApplicationContext);
    }

    @Test
    public void testBeanFactory()
    {
        test(defaultListableBeanFactory);
    }

    @Test
    public void testClassPathApplicationContext() throws Exception
    {
        test(classPathApplicationContext);
    }

    @Test
    public void testMulThread() throws Exception
    {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(resourcePosition);
        context.registerShutdownHook();
        ThreadGroup threadGroup = new ThreadGroup("testMulThread");
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
        for(int threadCount = 0;threadCount < 4;threadCount++)
        {
            new Thread(threadGroup, new ApplicationContextRunnable(cyclicBarrier, context)).start();
        }
        synchronized (threadGroup)
        {
            threadGroup.wait();
        }
    }

    @Test(expected = BeanCreationException.class)
    public void testNotAllowCircularApplicationContext() throws Exception
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{resourcePosition},
                false
        );
        context.registerShutdownHook();
        context.setAllowCircularReferences(false);
        context.refresh();
        test(context);
    }

    private void test(BeanFactory beanFactory)
    {
        IUserRegisterService iUserRegisterService =
                beanFactory.getBean("userRegisterService", IUserRegisterService.class);
        test(iUserRegisterService);

        MyFirstBean myFirstBean = beanFactory.getBean("firstBean", MyFirstBean.class);
        Assert.assertNotNull(myFirstBean);
        MyFirstBean otherReference = beanFactory.getBean("myFirstBean", MyFirstBean.class);
        Assert.assertNotNull(otherReference);
        Assert.assertTrue(myFirstBean == otherReference);

        System.out.println(myFirstBean.getDescription());
        System.out.println(myFirstBean.getUserManager());

        try
        {
            Class<?> selfBeanClass = selfClassLoader.loadClass(SelfBean.class.getName());
            Object selfBean = beanFactory.getBean("selfBean", selfBeanClass);
            Object otherSelfBean = beanFactory.getBean("self_bean", selfBeanClass);
            Assert.assertTrue(selfBean == otherSelfBean);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        ReplacedMethod replacedMethod = beanFactory.getBean("replacedMethod", ReplacedMethod.class);
        String[] splitStrArray = (String[]) replacedMethod.method("\\s[,]\\s", "A,B,C");
        for(String oneOfSplitStrArray : splitStrArray)
            System.out.println(oneOfSplitStrArray);

        UserManagerGetter userManagerGetter = beanFactory.getBean("userManagerGetter", UserManagerGetter.class);
        UserManager theUserManager = userManagerGetter.getUserManager();
        System.out.println(theUserManager);

        ICirculationReference circulationReferenceA =
                beanFactory.getBean("circulationReferenceA", ICirculationReference.class);
        ICirculationReference circulationReferenceB =
                beanFactory.getBean("circulationReferenceB", ICirculationReference.class);
        Assert.assertTrue(circulationReferenceA.getReference() == circulationReferenceB);
        Assert.assertTrue(circulationReferenceB.getReference() == circulationReferenceA);

        Car oneCar = beanFactory.getBean("car", Car.class);
        Car twoCar = beanFactory.getBean("car", Car.class);

        Object penFactoryBeanOne = beanFactory.getBean("&pen");
        Object penFactoryBeanTwo = beanFactory.getBean("pen");
        Assert.assertFalse(oneCar == twoCar);

        ClassLoader classLoader = this.getClass().getClassLoader();
        URL autoScanResourcePositionURL = classLoader.getResource(autoScanResourcePosition);
        TestConstructor testConstructor = (TestConstructor) beanFactory.getBean(
                "testConstructor",
                "A",
                autoScanResourcePositionURL,
                20
        );
        System.out.println(testConstructor);
    }

    private void test(IUserRegisterService iUserRegisterService)
    {
        User riven = iUserRegisterService.registerUser("Riven", 22);
        boolean containRiven = iUserRegisterService.containUser(riven.getId());
        Assert.assertTrue(containRiven);
        User user = iUserRegisterService.getUser(riven.getId());
        Assert.assertEquals(riven, user);
        iUserRegisterService.unRegisterUser(riven.getId());
        containRiven = iUserRegisterService.containUser(riven.getId());
        Assert.assertFalse(containRiven);
        Assert.assertTrue(UserRegisterService.class.isInstance(iUserRegisterService));
        Assert.assertEquals(
                UserRegisterService.class.cast(iUserRegisterService).getDescription(),
                "the description"
        );
    }
}