package com.sunsky.spring.factory;

import com.sunsky.spring.xml.XmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * get class name need to instance
 */
public class BeanDefinition extends XmlApplicationContext {

    public List<String> getComponentList(String contextConfigLocation){
        List<String> componetList = super.getComponentList(contextConfigLocation);
        return componetList;
    }

    public Map<String,GenericBeanDefinition> getBeanDefinitionXmlMap(String contextConfigLocation){
        Map<String,GenericBeanDefinition> genericBeanDefinitionMap = super.getBeanDefinitionMap(contextConfigLocation);
        return genericBeanDefinitionMap;
    }
}
