package com.sunsky.spring.xml;

import com.sunsky.spring.factory.GenericBeanDefinition;

import java.util.Map;

/**
 * invoke XmlApplicationContext getBeanDefinitionMap
 * and return result Map
 */
public class FileSystemXmlApplicationContext extends XmlApplicationContext {

    /**
     * @see com.sunsky.spring.xml.XmlApplicationContext
     * invoke XmlApplicationContext getBeanDefinitionMap
     * return bean definition in xml file
     * key is bean id , value is GenericBeanDefinition
     * @see com.sunsky.spring.factory.GenericBeanDefinition
     * @param contextConfigLocation
     * @return
     */
    public Map<String, GenericBeanDefinition> getGenericBeanDefinition(String contextConfigLocation){
        Map<String,GenericBeanDefinition> genericBeanDefinition = super.getBeanDefinitionMap(contextConfigLocation);
        return genericBeanDefinition;
    }

}
