package com.sunsky.thread;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {

    public static void main(String[] args) throws Exception {
//        noAtomic();
        atomicRef();
    }

    /**
     * 没有使用AtomicReference
     * 会造成多个线程抢占的情况
     * 打印的结果会出现：
     * thread1 change age
     * thread change age
     * thread1 value is : Person{name='jerry', age=20}
     * thread value is : Person{name='jerry', age=21}
     * now person is : Person{name='jerry', age=21}
     * @throws Exception
     */
    public static void noAtomic() throws Exception{
        final Person person = new Person("Tom",18);
        System.out.println("Person is : "+person);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread change name");
                person.setName("jerry");
                System.out.println("thread change age");
                person.setAge(person.getAge()+1);
                System.out.println("thread value is : "+person);
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1 change name");
                person.setName("cat");
                System.out.println("thread1 change age");
                person.setAge(person.getAge()+2);
                System.out.println("thread1 value is : "+person);
            }
        });

        thread.start();
        thread1.start();

        thread.join();
        thread1.join();

        System.out.println("now person is : "+person);
    }

    /**
     * AtomicReference可以保证原子性，
     * 同时只有一个线程能操作AtomicReference变量
     * @throws Exception
     */
    public static void atomicRef() throws Exception{
        Person person = new Person("tom",18);
        final AtomicReference<Person> atomicReference = new AtomicReference<>(person);
        System.out.println("person is : "+atomicReference.get().toString());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" start");
                Person old = atomicReference.getAndSet(new Person("jerry",atomicReference.get().getAge()+1));
                System.out.println("thread old person is : "+old);
                System.out.println("thread new person is : "+atomicReference.get().toString());
                System.out.println(Thread.currentThread().getName()+" stop");
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" start");
                Person old = atomicReference.getAndSet(new Person("cat",atomicReference.get().getAge()+1));
                System.out.println("thread1 old person is : "+old);
                System.out.println("thread1 new person is : "+atomicReference.get().toString());
                System.out.println(Thread.currentThread().getName()+" stop");
            }
        });

        thread.start();
        thread1.start();

        thread.join();
        thread.join();

        System.out.println("now person is : "+atomicReference.get().toString());
    }

    /**
     * 如果当前值 == 预期值，则以原子方式将该值设置为给定的更新值。
     */
    public static void compareDemo(){
        String initialReference = "initial value referenced";
        AtomicReference<String> atomicStringReference = new AtomicReference<String>(initialReference);
        System.out.println(atomicStringReference.get());

        String newReference = "new value referenced";

        //若atomicStringReference里面存储的值等于initialReference，则将atomicStringReference的值更新为newReference
        boolean exchanged = atomicStringReference.compareAndSet(initialReference, newReference);
        System.out.println("exchanged: " + exchanged);
        System.out.println(atomicStringReference.get());

        exchanged = atomicStringReference.compareAndSet(initialReference, newReference);
        System.out.println("exchanged: " + exchanged);
        System.out.println(atomicStringReference.get());
    }

}

/**
 * entity class
 */
class Person{

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
