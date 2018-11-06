package com.sunsky.spring.factory;

import com.sunsky.spring.annotation.SunSkyAutowired;
import com.sunsky.spring.utils.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InitBean extends BeanDefinition {

    //beans instance container, key is class name, value is instance Object
    public Map<String,Object> beanContainerMap = new ConcurrentHashMap<String, Object>();

    public void initBeans(){
        Class<?> clzz = null;

    }

    /**
     * @fucntion
     * init all bean in xml file config
     * and put the instance to beanContainerMap
     * @param contextConfigLocation
     */
    public void initXMLBeans(String contextConfigLocation){
        ApplicationContext applicationContext = new ApplicationContext(contextConfigLocation);
        //key is beanId
        Map<String,GenericBeanDefinition> beanDefinitionMap = super.getBeanDefinitionXmlMap(contextConfigLocation);
        for(Map.Entry<String,GenericBeanDefinition> entry : beanDefinitionMap.entrySet()){
            String beanId = entry.getKey();
            String className = entry.getValue().getClassName();
            Class<?> clzz = null;
            try {
                 clzz = Class.forName(className);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            beanContainerMap.put(className,clzz.cast(applicationContext.getBean(beanId)));
        }
    }

    /**
     * init the bean have annotation
     * and put the bean to container
     * @param contextConfigLocation
     */
    public void initAutoWiredBeans(String contextConfigLocation){
        List<String> componentList = super.getComponentList(contextConfigLocation);
        System.out.println("init order is : "+componentList);
        for(String className : componentList){
            try{
                initClass(className);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }catch (InstantiationException e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 1. dao, class is interface
     * 2. controller, class no interface
     * 3. class have interfaces
     * @param className
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void initClass(String className) throws ClassNotFoundException,IllegalAccessException,InstantiationException{
        Class<?> clazz = Class.forName(className);
        Class<?>[] interfaces = clazz.getInterfaces();

        //clazz is interface,dao interface
        if(clazz.isInterface()){

        }else if(interfaces == null || interfaces.length == 0){//if isn't interfaces implements class, like Controller
            noInterfaceInit(className,className);
        }else{
            for(Class<?> interfaceClass : interfaces){
                boolean flag = isExistInContainer(className);
                if(flag){
                    //TODO 这里不清楚为毛要用clazz.newInstance()，
                    //TODO 我尼玛觉得应该是beanContainerMap.put(interfaceClass.getName(),beanContainerMap.get(className));
                    //TODO 不需要再clazz.newInstance()
//                    beanContainerMap.put(interfaceClass.getName(),clazz.newInstance());
                    beanContainerMap.put(interfaceClass.getName(),beanContainerMap.get(className));
                }else {
                    //没有的话就初始化后再加入到container里去
                    noInterfaceInit(className,interfaceClass.getName());
                }
            }
        }
    }

    /**
     * @function
     * 1. if the class contains SunSkyAutowired annotation
     * 2. set the access visible and get the field type name
     * 3. find the field in beans container
     * 4. if beans container contains field then set the bean to field value
     * 5. put the object to beans container
     * @param className
     * @param interfaceName
     * the class implements interface
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void noInterfaceInit(String className,String interfaceName) throws ClassNotFoundException,InstantiationException,IllegalAccessException{
        //get class
        Class<?> clazz = Class.forName(className);
        System.out.println("instance class name : "+clazz.getName());
        //new instance
        Object object = clazz.newInstance();
        //get fields
        Field[] declaredFields = clazz.getDeclaredFields();

        for(Field field : declaredFields){
            //if field has SunSkyAutowired annotation
            if(!AnnotationUtils.isEmpty(field.getAnnotation(SunSkyAutowired.class))){
                //set field access visible
                field.setAccessible(true);
                //get field type
                String type = field.getType().getName();
                if(beanContainerMap.containsKey(type)){
                    field.set(object,beanContainerMap.get(type));
                }
//                for(Map.Entry<String,Object> entry : beanContainerMap.entrySet()){
//                    String type = field.getType().getName();
//                    if(entry.getKey().equals(type)){
//                        field.set(object,entry.getValue());
//                    }
//                }
            }
        }
        beanContainerMap.put(interfaceName,object);
    }

    /**
     * judge the bean container contains class
     * @param className
     * @return
     */
    public boolean isExistInContainer(String className){
        if(beanContainerMap != null && beanContainerMap.containsKey(className)){
            return true;
        }
        return false;
    }

}
