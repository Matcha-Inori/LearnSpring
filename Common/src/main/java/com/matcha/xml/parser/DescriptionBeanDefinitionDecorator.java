package com.matcha.xml.parser;

import com.matcha.app.util.StringUtil;
import com.matcha.xml.attribute.DescriptionAttribute;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.xml.BeanDefinitionDecorator;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Created by Matcha on 2017/4/24.
 */
public class DescriptionBeanDefinitionDecorator implements BeanDefinitionDecorator
{
    @Override
    public BeanDefinitionHolder decorate(Node node, BeanDefinitionHolder definition, ParserContext parserContext)
    {
        if(!Element.class.isInstance(node)) return null;
        Element element = Element.class.cast(node);
        String name = element.getAttribute(DescriptionAttribute.NAME.getAttributeName());
        String value = element.getAttribute(DescriptionAttribute.VALUE.getAttributeName());
        if(!StringUtil.isEmpty(name)) name = name.trim();
        if(StringUtil.isEmpty(value)) value = "default";
        TypedStringValue typedStringValue = new TypedStringValue(value);
        BeanDefinition beanDefinition = definition.getBeanDefinition();
        MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
        PropertyValue propertyValue = new PropertyValue(name, typedStringValue);
        mutablePropertyValues.addPropertyValue(propertyValue);
        return definition;
    }
}
