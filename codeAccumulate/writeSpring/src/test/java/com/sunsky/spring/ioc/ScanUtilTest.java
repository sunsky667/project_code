package com.sunsky.spring.ioc;

import com.sunsky.spring.annotation.SunSkyAutowired;
import com.sunsky.spring.annotation.SunSkyController;
import com.sunsky.spring.annotation.SunSkyService;
import com.sunsky.spring.utils.scan.ScanUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

public class ScanUtilTest {

    @Test
    public void testGetComponentList(){
        List<String> result = ScanUtil.getComponentList("com.sunsky.spring.demo");
        System.out.println(result.size());
    }

    @Test
    public void testAnnotation() throws ClassNotFoundException{
        Class<?> clazz = Class.forName("com.sunsky.spring.demo.controller.LoginController");
        if(clazz.getAnnotation(SunSkyController.class) != null){
            System.out.println("controller annotation");
        }

        Field[] fields = clazz.getDeclaredFields();
        System.out.println("fields length "+fields.length);
        for(Field field : fields){
            if(field.getAnnotation(SunSkyAutowired.class) !=  null){
                String filedType = field.getType().getName();
                System.out.println("filedType : "+filedType);
            }
        }
    }
}
