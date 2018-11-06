package com.sunsky.spring.factory;

import com.sunsky.spring.Person;
import com.sunsky.spring.constants.Constants;
import com.sunsky.spring.exception.XMLException;
import com.sunsky.spring.utils.GetMethodName;
import com.sunsky.spring.xml.FileSystemXmlApplicationContext;
import com.sunsky.spring.xmlRules.IocRules;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApplicationContext extends FileSystemXmlApplicationContext implements BeanFactory {

    //store bean defined in xml, key is bean id ,value is GenericBeanDefinition
    public Map<String,GenericBeanDefinition> subMap = null;

    public ApplicationContext(String contextConfigLocation){
        //get bean definition in xml file, key is bean id , value is GenericBeanDefinition
        this.subMap = super.getGenericBeanDefinition(contextConfigLocation);
    }

    /**
     * @Function use beanId to get bean from container
     * 1.Get bean's class and bean's property from subMap
     * 2.Judge the bean use method or construct to inject
     * 3.use different method to init or instance object that we want
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) {

        assert beanId == null : "beanId is null";
        Object object = null;
        Class<?> clazz = null;

        Set<Map.Entry<String,GenericBeanDefinition>> entries = subMap.entrySet();
        if(subMap.containsKey(beanId)){
//            for(Map.Entry<String,GenericBeanDefinition> entry : entries){
//                if(beanId.equals(entry.getKey())){
            GenericBeanDefinition genericBeanDefinition = subMap.get(beanId);
            String beanName = genericBeanDefinition.getClassName();

            List<ChildBeanDefinition> childBeanDefinitionList = genericBeanDefinition.getChildBeanDefinitionList();

            try {
                clazz = Class.forName(beanName);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            try {
                object = clazz.newInstance();
            }catch (InstantiationException e){
                e.printStackTrace();
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }

            //use set method to init object field ,or use construct to instance object
            for(ChildBeanDefinition childBeanDefinition : childBeanDefinitionList){
                if(IocRules.SET_INJECT.getType().equals(childBeanDefinition.getChildrenType())){
                    setValue(clazz,childBeanDefinition,object);
                }else if(IocRules.CONSTRUCT_INJECT.getType().equals(childBeanDefinition.getChildrenType())){
                    List<ChildBeanDefinition> constructorChildBeanDefinition = new ArrayList<ChildBeanDefinition>();
                    for(ChildBeanDefinition conChildBeanDefinition : childBeanDefinitionList){
                        if(IocRules.CONSTRUCT_INJECT.getType().equals(conChildBeanDefinition.getChildrenType())){
                            constructorChildBeanDefinition.add(conChildBeanDefinition);
                        }
                    }
                    object = constructValue(clazz,constructorChildBeanDefinition,object);
                    break;
                }
            }
//                }
//            }
        }else{
            throw new XMLException("container not exists bean");
        }

        return object;
    }

    /**
     * construct method inject
     * @function
     * according to the application.xml config
     * use reflect construct and param to instance a object
     * @param clazz
     * @param childBeanDefinitionList
     * @param object
     * @return object use construct instance
     */
    private Object constructValue(Class<?> clazz, List<ChildBeanDefinition> childBeanDefinitionList,Object object){
        Constructor<?> constructor = null;
        //get class declared fields
        Field[] fields = clazz.getDeclaredFields();
        //get field type class
        Class<?>[] classArray = new Class[fields.length];
        //only support String,int,Integer type
        for(int i=0;i<fields.length;i++){
            if("String".equals(fields[i].getType().getSimpleName())){
                classArray[i] = String.class;
            }else if("int".equals(fields[i].getType().getSimpleName())){
                classArray[i] = int.class;
            }else if("Integer".equals(fields[i].getType().getSimpleName())){
                classArray[i] = Integer.class;
            }
        }

        try {
            //get all declared fields construct
            constructor = clazz.getConstructor(classArray);
            try {
                //get field params
                Object[] paramObject = new Object[childBeanDefinitionList.size()];
                for(ChildBeanDefinition childBeanDefinition : childBeanDefinitionList){
                    int index = Integer.parseInt(childBeanDefinition.getSecondAttribute())-1;
                    paramObject[index] = childBeanDefinition.getFirstAttribute();
                }
//                object = constructor.newInstance(childBeanDefinitionList.get(0).getFirstAttribute(),childBeanDefinitionList.get(1).getFirstAttribute());
                //use constructor and param to instance object
                object = constructor.newInstance(paramObject);
            }catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }
        return object;
    }

    /**
     * set method inject
     * @function get set method, and according to
     * the xml config to init field
     * @param clazz
     * @param childBeanDefinition
     * @param object
     */
    private void setValue(Class<?> clazz,ChildBeanDefinition childBeanDefinition,Object object){
        Field field = null;
        Method[] methods = null;

        String type = null;
        //property name
        String propertyName = childBeanDefinition.getFirstAttribute();
        //property value
        String propertyValue = childBeanDefinition.getSecondAttribute();

        //get (set method) after set, first char is Caps
        String methodName = GetMethodName.getSetMethodNameByField(propertyName);

        try {
            //get field
            field = clazz.getDeclaredField(propertyName);
            //get filed type
            type = field.getType().getSimpleName();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }

        try {
            //get all method
            methods = clazz.getMethods();
            for(Method method : methods){
                //if param equals set+<FieldName>()
                if(methodName.equals(method.getName())){
                    //invoke set method , only support string,int integer
                    try {
                        if("String".equals(type)){
                            method.invoke(object,propertyValue);
                        }else if("int".equals(type)){
                            Integer integer = Integer.parseInt(propertyValue);
                            method.invoke(object,integer);
                        }else if("Integer".equals(type)){
                            Integer integer = Integer.parseInt(propertyValue);
                            method.invoke(object,integer);
                        }else{
                            System.out.println("not support type");
                        }
                    }catch (IllegalAccessException e){
                        e.printStackTrace();
                    }catch (InvocationTargetException e){
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(Constants.contextConfigLocation);
        Person person  = (Person) applicationContext.getBean("hostess");
        System.out.println(person);
    }
}
