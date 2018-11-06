package com.sunsky.spring.annotation;

import java.lang.annotation.*;

@Target({
        ElementType.CONSTRUCTOR,ElementType.METHOD,ElementType.PARAMETER,
        ElementType.FIELD,ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SunSkyAutowired {

    boolean required() default true;

}
