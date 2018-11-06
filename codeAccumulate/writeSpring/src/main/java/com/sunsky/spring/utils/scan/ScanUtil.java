package com.sunsky.spring.utils.scan;

import com.sunsky.spring.annotation.SunSkyAutowired;
import com.sunsky.spring.annotation.SunSkyController;
import com.sunsky.spring.annotation.SunSkyRepository;
import com.sunsky.spring.annotation.SunSkyService;
import com.sunsky.spring.utils.AnnotationUtils;
import com.sunsky.spring.utils.ListAddUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * scan util class,
 * core method : getClassName(), getComponentList()
 * function:
 *      1, scan Annotation in package
 *      2, scan Class in package
 */
public class ScanUtil {

    //store all classes name that in scan package
    private static List<String> classNameList = new ArrayList<String>();
    //store classes that the class have annotation
    private static List<String> componentList = new ArrayList<String>();
    //store interface name and implement class name or interface need to proxy
    //key is interface name, value is implement class or "proxy"
    private static Map<String,String> interfaceAndImplMap = new ConcurrentHashMap<String, String>();

    /**
     * 1. giving a package name
     * 2. transfer the package name to File System path
     * 3. recursive scan all classes in file system path
     * 4. store the class name to classNameList
     * The store name like 'com.sunsky.spring.scan.ScanUtil'
     * @param packageName
     * @return
     */
    public static List<String> getClassName(String packageName){
        Enumeration<URL> urls = null;
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        String newPackageName = packageName.replace(".",File.separator);
        System.out.println(newPackageName);
        try{
            urls = contextClassLoader.getResources(newPackageName);
            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                File packageFile = new File(url.getPath());
                File[] files = packageFile.listFiles();
                if(files == null){
                    break;
                }

                for(File file : files){
                    //if is class, then put to list
                    if(file.getName().endsWith(".class")){
                        String templeName = (packageName.replace(File.separator,".")+"."+file.getName());
                        String newTempleName = templeName.substring(0,templeName.lastIndexOf("."));
                        classNameList.add(newTempleName);
                    }else{ //if is package,then keep find
                        String nextPackageName = newPackageName+"."+file.getName();
                        getClassName(nextPackageName);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return classNameList;
    }

    /**
     *
     * @param packageName
     * @return have annotation instance list
     */
    public static List<String> getComponentList(String packageName){
        //get all class
        List<String> classNameList = getClassName(packageName);

        makeInterfaceAndImplMap(classNameList);

        for(String className:classNameList){
            try {
                resolveComponent(className);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }

        return componentList;

    }

    /**
     * 依次判断class上是否有@SunSkyController,@SunSkyService,@SunSkyRepository这三个注解
     * getComponentList();递归调用的子方法
     * @param className
     * @throws ClassNotFoundException
     */
    public static void resolveComponent(String className) throws ClassNotFoundException{
        Class<?> clazz = Class.forName(className);
        //在此处添加要识别的注解，也是每次扫描的顺序，最好遵循习惯
        addNewAnnotation(SunSkyController.class, clazz);
        addNewAnnotation(SunSkyService.class, clazz);
        addNewAnnotation(SunSkyRepository.class, clazz);
    }

    /**
     * @funtion
     * 1.首先判断类名上是否有@SunSkyController,@SunSkyService,@SunSkyRepository这三个注解中的任意一个
     * 2.若类名上有这三个注解中的任意一个，将其加入到componentList中
     * 3.然后判断类里的每个成员变量是否有@SunSkyAutowired这个注解
     * 4.然后判断这个成员变量类型是接口还是实现类，若为接口，则找到接口的实现类，
     * 判断在实现类在componentList是否已经存在了这个实现类，若不存在，则又开始判断这个实现类
     * 是否有@SunSkyController,@SunSkyService,@SunSkyRepository这三个注解，重复所有步骤
     * 直到类上没有@SunSkyController,@SunSkyService,@SunSkyRepository这些注解
     * 5.若是实现类，直接判断这个实现类是否有@SunSkyController,@SunSkyService,@SunSkyRepository这三个注解
     * 重复所有步骤，直到类上没有@SunSkyController,@SunSkyService,@SunSkyRepository这些注解
     * 6.若为代理接口，也将其放到componentList中
     * @param annotationClass
     * @param clazz
     * @param <A>
     * @throws ClassNotFoundException
     */
    public static <A extends Annotation> void addNewAnnotation(Class<A> annotationClass,Class<?> clazz) throws ClassNotFoundException {
        //if class has annotation
        if(!AnnotationUtils.isEmpty(clazz.getAnnotation(annotationClass))){
            Field[] fields = clazz.getDeclaredFields();
            //if class haven't field
            if(fields == null && fields.length == 0){
                ListAddUtils.add(componentList,clazz.getName());
            }else{
                //跳出递归的语句，也就是最底层的类，如果所有属性没有@SunSkyAutowired注解，则将类名注入到链表中
                if(isEmptyAutowired(fields)){
                    ListAddUtils.add(componentList,clazz.getName());
                }else {
                    //遍历字段
                    for(Field field : fields){
                        //if the field have SunSkyAutowired annotation,
                        // 若字段包含有SunSkyAutowired注解
                        if(field.getAnnotation(SunSkyAutowired.class) != null){
                            //get field type name
                            // 获取字段类型名称
                            String fieldName = field.getType().getName();
                            //if field is interface
                            //若包含注解的字段是接口
                            if(Class.forName(fieldName).isInterface()){
                                //则在interfaceAndImplMap这个map里查找，若有实现的类，则给出实现类的名称
                                //若没有实现类，即需要代理的接口，给出接口名称
                                //find in interfaceAndImplMap, if the interface have implement class
                                //then get the implement class name, if the interface is proxy
                                //then get the interface name
                                String nextName = converInteraceToImpl(fieldName);
                                //if componentList not contains class name or interface name
                                //then find the children class if exists class annotation and field annotation
                                if(!componentList.contains(nextName)){
                                    //递归调用判断
                                    resolveComponent(nextName);
                                }
                            }else {
                                //if class is not interface, then find the field instance have or not annotation
                                resolveComponent(fieldName);
                            }
                        }
                    }
                    //add the class name to componentList
                    ListAddUtils.add(componentList,clazz.getName());
                }
            }
            //if clazz have not annotation and clazz is interface and interface is "proxy"
            //add interface name to componentList
        }else if(clazz.isInterface() && "proxy".equals(interfaceAndImplMap.get(clazz.getName()))) {
            ListAddUtils.add(componentList,clazz.getName());
        }
    }

    /**
     * judge the attribute has or not 'SunSkyAutowired' annotation
     * @see com.sunsky.spring.annotation.SunSkyAutowired
     * @param fields
     * @return
     */
    private static boolean isEmptyAutowired(Field[] fields){
        for(Field field : fields){
            if(!AnnotationUtils.isEmpty(field.getAnnotation(SunSkyAutowired.class))){
                return false;
            }
        }
        return true;
    }

    /**
     * component interface and implement class
     * @function
     * 1. if interface have implement class then
     * put the interface name and implement class name to interfaceAndImplMap
     * 2. if interface haven't implement class ther
     * put the interface name and  "proxy" to interfaceAndImplMap
     * @param classNameList
     * @return
     */
    private static Map<String,String> makeInterfaceAndImplMap(List<String> classNameList){
        Class<?> clazz = null;
        //interfaceNameList store all interface name list
        List<String> interfaceNameList = new ArrayList<String>();
        //interfaceExist store the interface name that has implement class
        List<String> interfaceExist = new ArrayList<String>();

        //judge the class is interface
        //add the interface name to interfaceNameList
        for(String className : classNameList){
            try {
                clazz = Class.forName(className);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            if(clazz.isInterface()){
                interfaceNameList.add(clazz.getName());
            }
        }
        //judge the interface have implement class
        //put the interface name and implement class to map
        //add the interface name that have implement to interfaceExist
        for(String className:classNameList){
            Class<?> cz = null;
            try {
                cz = Class.forName(className);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            //class interfaces
            Class<?>[] interfaces = cz.getInterfaces();
            if(interfaces != null && interfaces.length > 0){
                for(String interfaceName : interfaceNameList){
                    for(Class<?> interfaceClass : interfaces){
                        /**
                         * if interface name equals class's interface name
                         * add interface name and class name to map
                         * and add the interface name to interfaceExists list
                         */
                        if(interfaceName.equals(interfaceClass.getName())){
                            interfaceAndImplMap.put(interfaceName,className);
                            interfaceExist.add(interfaceName);
                        }
                    }
                }
            }
        }

        //remove the interface name that the interface have implement class
        interfaceNameList.removeAll(interfaceExist);
        //after move the interface name that have implement class
        //the rest of interface name is need to proxy
        if(interfaceNameList.size() > 0){
            for(String spareInterfaceName : interfaceNameList){
                //add proxy interface to interfaceAndImplMap
                interfaceAndImplMap.put(spareInterfaceName,"proxy");
            }
            System.out.println("already exist " +interfaceNameList);
        }
        return null;
    }

    /**
     * @function
     * 1. if interface have implement class then return implement class name
     * 2. if interface haven't implement class then return interface name
     * @param newFileName
     * @return
     */
    private static String converInteraceToImpl(String newFileName){
        Set<Map.Entry<String,String>> entries = interfaceAndImplMap.entrySet();
        for(Map.Entry<String,String> entry : entries){
            if(newFileName.equals(entry.getKey()) && !entry.getValue().equals("proxy")){
                return entry.getValue();
            }else if(newFileName.equals(entry.getKey()) && entry.getValue().equals("proxy")){
                return entry.getKey();
            }
        }
        return null;
    }

}
