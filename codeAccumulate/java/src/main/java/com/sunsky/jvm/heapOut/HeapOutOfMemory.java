package com.sunsky.jvm.heapOut;

import java.util.ArrayList;
import java.util.List;

/**

 * @Described：堆溢出测试

 * @VM args:-verbose:gc -Xms20M -Xmx20M -XX:+PrintGCDetails

 * @FileNmae com.yhj.jvm.memory.heap.HeapOutOfMemory.java

 */

public class HeapOutOfMemory {
    public static void main(String[] args) {
        List<TestCase> cases = new ArrayList<>();
        while (true){
            cases.add(new TestCase());
        }
    }
}

class TestCase{

}