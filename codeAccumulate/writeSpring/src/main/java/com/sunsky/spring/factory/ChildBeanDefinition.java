package com.sunsky.spring.factory;

/**
 * store beans property
 * @see com.sunsky.spring.factory.GenericBeanDefinition
 */
public class ChildBeanDefinition {

    private String childrenType; //property or constructor-arg type

    private String firstAttribute; //first value

    private String secondAttribute; //second value

    public String getChildrenType() {
        return childrenType;
    }

    public void setChildrenType(String childrenType) {
        this.childrenType = childrenType;
    }

    public String getFirstAttribute() {
        return firstAttribute;
    }

    public void setFirstAttribute(String firstAttribute) {
        this.firstAttribute = firstAttribute;
    }

    public String getSecondAttribute() {
        return secondAttribute;
    }

    public void setSecondAttribute(String secondAttribute) {
        this.secondAttribute = secondAttribute;
    }

    @Override
    public String toString() {
        return "ChildBeanDefinition{" +
                "childrenType='" + childrenType + '\'' +
                ", firstAttribute='" + firstAttribute + '\'' +
                ", secondAttribute='" + secondAttribute + '\'' +
                '}';
    }
}
