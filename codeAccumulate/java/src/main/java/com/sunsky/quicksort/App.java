package com.sunsky.quicksort;

public class App {
    public static void main(String[] args) {
        int[] a = new int[]{2,7,4,5,10,1,9,3,8,6};
        int[] b = new int[]{1,2,3,4,5,6,7,8,9,10};
        int[] c = new int[]{10,9,8,7,6,5,4,3,2,1};
        int[] d = new int[]{1,10,2,9,3,2,4,7,5,6};

        QuickSort.sort(a, 0, a.length-1);

        System.out.println("排序后的结果：");
        for(int x : a){
            System.out.print(x+" ");
        }

    }
}
