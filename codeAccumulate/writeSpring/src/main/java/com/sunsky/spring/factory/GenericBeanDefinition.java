package com.sunsky.spring.factory;

import java.util.List;

/**
 * store XML file inject bean
 */
public class GenericBeanDefinition {

    //xml file defined class
    private String className;

    //bean's property in xml file
    private List<ChildBeanDefinition> childBeanDefinitionList;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ChildBeanDefinition> getChildBeanDefinitionList() {
        return childBeanDefinitionList;
    }

    public void setChildBeanDefinitionList(List<ChildBeanDefinition> childBeanDefinitionList) {
        this.childBeanDefinitionList = childBeanDefinitionList;
    }

    @Override
    public String toString() {
        return "GenericBeanDefinition{" +
                "className='" + className + '\'' +
                ", childBeanDefinitionList=" + childBeanDefinitionList +
                '}';
    }
}
