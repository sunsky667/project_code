package com.sunsky.annotation.databaseConnectInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = {FIELD})//Target:此注解用到的地方有TYPE、FIELD、METHOD
@Retention(value = RUNTIME)
public @interface CustomConnection {
    String url() default "";
    String driverClass() default "";
    String username() default "";
    String password() default "";
}
