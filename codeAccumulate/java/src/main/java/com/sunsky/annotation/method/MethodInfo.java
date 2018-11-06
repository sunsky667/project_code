package com.sunsky.annotation.method;

import java.lang.annotation.*;

@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface MethodInfo {

    public String author() default "nobody";

    public String date();

    public String description();

}
