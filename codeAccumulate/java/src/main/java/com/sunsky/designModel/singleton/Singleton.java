package com.sunsky.designModel.singleton;

public class Singleton {

    private static Singleton singleton;
    private Singleton(){ }

    public static Singleton getInstance(){
        if(singleton == null) {
            singleton = new Singleton();
        }
        return  singleton;
    }

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Singleton{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
