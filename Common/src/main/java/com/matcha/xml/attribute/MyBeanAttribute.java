package com.matcha.xml.attribute;

/**
 * Created by Matcha on 2017/4/24.
 */
public enum MyBeanAttribute
{
    MY_BEAN_ID("id"),
    MY_BEAN_NAME("name"),
    MY_BEAN_CLASS("class"),
    BASE_DEPENDENCE_ELEMENT_NAME("name"),
    BASE_DEPENDENCE_ELEMENT_VALUE("value");

    private String attributeName;

    MyBeanAttribute(String attributeName)
    {
        this.attributeName = attributeName;
    }

    public String getAttributeName()
    {
        return attributeName;
    }
}
