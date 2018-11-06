package com.sunsky.jvm.stackOverflow;

/**

 * @Described：栈层级不足探究

 * @VM args:-Xss128k

 * @FileNmae com.yhj.jvm.memory.stack.StackOverFlow.java

 */
public class StackOverFlow {

    private int i;

    public void plus(){
        i++;
        plus(); //递归调用
    }

    public static void main(String[] args) {
        StackOverFlow stackOverFlow = new StackOverFlow();

        try {
            stackOverFlow.plus();
        }catch (Exception e){
            System.out.println("Exception:stack length:"+stackOverFlow.i);
            e.printStackTrace();
        }catch (Error error){
            System.out.println("Error:stack length:"+stackOverFlow.i);
            error.printStackTrace();
        }


    }
}
