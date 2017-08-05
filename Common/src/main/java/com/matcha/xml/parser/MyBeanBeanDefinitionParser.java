package com.matcha.xml.parser;

import com.matcha.app.util.ArrayUtil;
import com.matcha.app.util.CollectionUtil;
import com.matcha.app.util.StringUtil;
import com.matcha.xml.attribute.MyBeanAttribute;
import com.matcha.xml.element.MyBeanElement;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.parsing.ReaderContext;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Matcha on 2017/4/23.
 */
public class MyBeanBeanDefinitionParser implements BeanDefinitionParser
{
    private final Set<String> usedName = new HashSet<>(16);

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext)
    {
        ReaderContext readerContext = parserContext.getReaderContext();
        BeanDefinitionRegistry registry = parserContext.getRegistry();

        String beanId = element.getAttribute(MyBeanAttribute.MY_BEAN_ID.getAttributeName());
        String beanAlias = element.getAttribute(MyBeanAttribute.MY_BEAN_NAME.getAttributeName());

        String[] aliasArray = StringUtil.isEmpty(beanAlias) ? null : beanAlias.split("[,; ]");
        List<String> aliases = aliasArray == null ? null : Arrays.asList(aliasArray);

        String beanName = StringUtil.isEmpty(beanId) ?
                CollectionUtil.isEmpty(aliases) ? null : aliases.remove(0) :
                beanId;

        if((usedName.contains(beanName) || CollectionUtil.contains(aliases, beanName))
                && readerContext != null)
        {
            readerContext.error("Bean name '" + beanName + "' is already used in this <beans> element", element);
            return null;
        }

        BeanDefinition beanDefinition = createBeanDefinition(element);
        if(StringUtil.isEmpty(beanName))
            beanName = XmlReaderContext.class.cast(readerContext).generateBeanName(beanDefinition);

        parseBeanProperty(beanDefinition, element, parserContext);

        BeanDefinitionHolder bdHolder = CollectionUtil.isEmpty(aliases) ?
                new BeanDefinitionHolder(beanDefinition, beanName) :
                new BeanDefinitionHolder(beanDefinition, beanName, aliases.toArray(new String[aliases.size()]));
        BeanDefinitionReaderUtils.registerBeanDefinition(bdHolder, registry);
        this.usedName.add(beanName);
        this.usedName.addAll(aliases);

        return beanDefinition;
    }

    private BeanDefinition createBeanDefinition(Element element)
    {
        String className =
                StringUtil.isEmpty((className = element.getAttribute(MyBeanAttribute.MY_BEAN_CLASS.getAttributeName())))
                        ? null :
                        className.trim();
        return new RootBeanDefinition(className);
    }

    private void parseBeanProperty(BeanDefinition beanDefinition, Element element, ParserContext parserContext)
    {
        String propertyElementName = MyBeanElement.DEPENDENCE.getElementName();
        NodeList nodeList = element.getChildNodes();
        if(nodeList == null || nodeList.getLength() == 0) return;
        int nodeListLength = nodeList.getLength();
        Node item;
        String itemName;
        String itemLocalName;
        for(int index = 0;index < nodeListLength;index++)
        {
            item = nodeList.item(index);
            itemName = item.getNodeName();
            itemLocalName = item.getLocalName();
            if(!ArrayUtil.contains(new String[]{itemName, itemLocalName}, propertyElementName)) continue;
            parseBeanProperty(beanDefinition, item, parserContext);
        }
    }

    private void parseBeanProperty(BeanDefinition beanDefinition, Node node, ParserContext parserContext)
    {
        NodeList nodeList = node.getChildNodes();
        if(nodeList == null || nodeList.getLength() == 0) return;
        MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
        ReaderContext readerContext = parserContext.getReaderContext();
        int nodeListLength = nodeList.getLength();
        Node nodeItem;
        Element item;
        String propertyName;
        PropertyValue propertyValue;
        Object propertyValueObj;
        for(int index = 0;index < nodeListLength;index++)
        {
            nodeItem = nodeList.item(index);
            if(!Element.class.isInstance(nodeItem)) continue;
            item = (Element) nodeItem;
            propertyName = item.getAttribute(MyBeanAttribute.BASE_DEPENDENCE_ELEMENT_NAME.getAttributeName());
            if(!StringUtil.isEmpty(propertyName)) propertyName = propertyName.trim();
            if(mutablePropertyValues.contains(propertyName))
            {
                readerContext.error("Multiple 'property' definitions for property '" + propertyName + "'", item);
                continue;
            }
            propertyValueObj = parsePropertyValue(item);
            propertyValue = new PropertyValue(propertyName, propertyValueObj);
            mutablePropertyValues.addPropertyValue(propertyValue);
        }
    }

    private Object parsePropertyValue(Element element)
    {
        String name = element.getNodeName();
        String localName = element.getLocalName();
        String[] names = new String[]{name, localName};
        String value = element.getAttribute(MyBeanAttribute.BASE_DEPENDENCE_ELEMENT_VALUE.getAttributeName());
        if(!StringUtil.isEmpty(value)) value = value.trim();
        if(ArrayUtil.contains(names, MyBeanElement.REF.getElementName()))
            return new RuntimeBeanReference(value);
        else if(ArrayUtil.contains(names, MyBeanElement.VALUE.getElementName()))
            return new TypedStringValue(value);
        else
            return null;
    }
}
