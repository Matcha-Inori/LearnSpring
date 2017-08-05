package com.matcha.xml.element;

/**
 * Created by Matcha on 2017/4/23.
 */
public enum MyBeanElement
{
    MY_BEAN("myBean"),
    DEPENDENCE("dependence"),
    REF("ref"),
    VALUE("value");
    private String elementName;

    MyBeanElement(String elementName)
    {
        this.elementName = elementName;
    }

    public String getElementName()
    {
        return elementName;
    }
}
