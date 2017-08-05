package com.matcha.xml;

import com.matcha.xml.element.MyBeanElement;
import com.matcha.xml.parser.MyBeanBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by Matcha on 2017/4/23.
 */
public class MyBeanXMLNamespaceHandler extends NamespaceHandlerSupport
{
    @Override
    public void init()
    {
        this.registerBeanDefinitionParser(MyBeanElement.MY_BEAN.getElementName(), new MyBeanBeanDefinitionParser());
    }
}
