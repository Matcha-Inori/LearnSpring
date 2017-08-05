package com.matcha.xml;

import com.matcha.xml.element.DescriptionElement;
import com.matcha.xml.parser.DescriptionBeanDefinitionDecorator;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by Matcha on 2017/4/24.
 */
public class DescriptionXMLNamespaceHandler extends NamespaceHandlerSupport
{
    @Override
    public void init()
    {
        this.registerBeanDefinitionDecorator(
                DescriptionElement.DESCRIPTION.getElementName(),
                new DescriptionBeanDefinitionDecorator()
        );
    }
}
