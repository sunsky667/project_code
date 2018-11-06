package com.sunsky.jvm.directoryMemoryOut;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**

 * @Described：直接内存溢出测试

 * @VM args: -Xmx20M -XX:MaxDirectMemorySize=10M

 */
public class DirectoryMemoryOutOfmemory {

    private static final int ONE_MB = 1024*1024;

    private static int count = 1;

    public static void main(String[] args) throws Exception{

        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);

            while (true){
                unsafe.allocateMemory(ONE_MB);
                count ++;
            }
        }catch (Exception e){
            System.out.println("Exception:instance created "+count);
            e.printStackTrace();
        }catch (Error error){
            System.out.println("Error:instance created "+count);
            error.printStackTrace();
        }


    }
}
