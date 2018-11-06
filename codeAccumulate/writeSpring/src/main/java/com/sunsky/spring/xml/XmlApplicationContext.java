package com.sunsky.spring.xml;

import com.sunsky.spring.constants.Constants;
import com.sunsky.spring.exception.XMLException;
import com.sunsky.spring.factory.ChildBeanDefinition;
import com.sunsky.spring.factory.GenericBeanDefinition;
import com.sunsky.spring.utils.ListAddUtils;
import com.sunsky.spring.utils.StringUtils;
import com.sunsky.spring.utils.scan.ScanUtil;
import com.sunsky.spring.xmlRules.IocRules;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XmlApplicationContext {

    /**
     *
     * Parse XML config,
     * Inject XML config bean to Container
     * Container is a Map
     * key is bean id in xml
     * value is GenericBeanDefinition
     * @see com.sunsky.spring.factory.GenericBeanDefinition
     * @param contextConfigLocation
     * @return
     */
    public Map<String, GenericBeanDefinition> getBeanDefinitionMap(String contextConfigLocation){
        Map<String,GenericBeanDefinition> beanDefinitionXMLMap = new ConcurrentHashMap<String, GenericBeanDefinition>(256);
        List<Element> elementList = getElements(contextConfigLocation);

        for(Element element : elementList){
            if("bean".equals(element.getName())){
                GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
                List<ChildBeanDefinition> childBeanDefinitionList = new ArrayList<ChildBeanDefinition>();
                String beanId = element.attributeValue(IocRules.BEAN_RULE.getName());
                String beanClass = element.attributeValue(IocRules.BEAN_RULE.getValue());

                if(!StringUtils.isEmpty(beanId) && !StringUtils.isEmpty(beanClass)){
                    genericBeanDefinition.setClassName(beanClass);
                    List<Element> elements = element.elements();
                    //如果匹配构造器注入规则,则注入到容器
                    if(elements != null){
                        for(Element childrenElement : elements){
                            if(IocRules.SET_INJECT.getType().equals(childrenElement.getName())){
                                ChildBeanDefinition childBeanDefinition = new ChildBeanDefinition();
                                childBeanDefinition.setChildrenType(IocRules.SET_INJECT.getType());
                                String name = IocRules.SET_INJECT.getName();
                                String value = IocRules.SET_INJECT.getValue();
                                setChildBeanDefinitionByType(childrenElement,childBeanDefinition,name,value,childBeanDefinitionList);
                            }else if(IocRules.CONSTRUCT_INJECT.getType().equals(childrenElement.getName())){
                                ChildBeanDefinition childBeanDefinition = new ChildBeanDefinition();
                                childBeanDefinition.setChildrenType(IocRules.CONSTRUCT_INJECT.getType());
                                String name = IocRules.CONSTRUCT_INJECT.getName();
                                String value = IocRules.CONSTRUCT_INJECT.getValue();
                                setChildBeanDefinitionByType(childrenElement,childBeanDefinition,name,value,childBeanDefinitionList);
                            }
                        }
                    }else {
                        System.out.println(beanId+" have not children element");
                    }
                    genericBeanDefinition.setChildBeanDefinitionList(childBeanDefinitionList);
                    beanDefinitionXMLMap.put(beanId,genericBeanDefinition);
                }
            }
        }
        return beanDefinitionXMLMap;
    }

    /**
     * 根据指定的xml，获得注解扫描的bean容器
     * according to xml file, get annotation bean container
     * @param contextConfigLocation
     * @return
     */
    public List<String> getComponentList(String contextConfigLocation){
        List<String> componentList = new ArrayList<String>();
        List<Element> elementList = getElements(contextConfigLocation);
        for(Element element : elementList){
            if(IocRules.SCAN_RULE.getType().equals(element.getName())){
                String packageName = element.attributeValue(IocRules.SCAN_RULE.getName());
                componentList.addAll(resolveCompomentList(packageName));
            }
        }
        return componentList;
    }


    /**
     * according to the package name
     * scan all class in this package
     * and find all class that have annotation
     * return the class that have annotation
     * @param packageName
     * @return
     */
    public List<String> resolveCompomentList(String packageName){
        if(StringUtils.isEmpty(packageName)){
            throw new XMLException("set correct "+ IocRules.SCAN_RULE.getType()+" attribute");
        }
        List<String> componentList = new ArrayList<String>();
        List<String> componentListAfter = ScanUtil.getComponentList(packageName);
        componentList.addAll(componentListAfter);
        return componentList;
    }

    /**
     * Each bean's child elements inject to container
     * 1, Get bean's all property
     * 2, Set bean's property to ChildBeanDefinition
     * 3, Add ChildBeanDefinition to List
     * {@link com.sunsky.spring.factory.ChildBeanDefinition}
     * @param element
     * @param childBeanDefinition
     * @param name
     * @param value
     * @param childBeanDefinitionList
     */
    private void setChildBeanDefinitionByType(Element element, ChildBeanDefinition childBeanDefinition,
                                              String name,String value,List<ChildBeanDefinition> childBeanDefinitionList){

        if(childBeanDefinition != null){
            childBeanDefinition.setFirstAttribute(element.attributeValue(name));
            childBeanDefinition.setSecondAttribute(element.attributeValue(value));
            childBeanDefinitionList.add(childBeanDefinition);
        }else{
            throw new XMLException("XML Format Error or Not support change attribute");
        }

    }

    /**
     * parse xml,
     * according to element name ,
     * get all root's node child elements list
     * @param contextConfigLocation
     * @return
     */
    private List<Element> getElements(String contextConfigLocation){
        SAXReader saxReader = new SAXReader();
        Document document = null;
        String path = Constants.PAHT + contextConfigLocation;

        try {
            document = saxReader.read(new File(path));

        }catch (Exception e){
            System.out.println("xml file path not found : "+path);
            e.printStackTrace();
        }

        //get root element
        Element root = document.getRootElement();
        //get all bean
        List<Element> elements = root.elements();
        return elements;
    }
}
