package com.sunsky.quicksort;

/**
 * 算法优缺点
 快速排序最“快”的地方在于左右两边能够快速同时递归排序下去，
 所以最优的情况是基准值刚好取在无序区的中间，这样能够最大效率地让两边排序，
 同时最大地减少递归划分的次数。此时的时间复杂度仅为O(NlogN)。
 快速排序也有存在不足的情况，当每次划分基准值时，
 得到的基准值总是当前无序区域里最大或最小的那个元素，
 这种情况下基准值的一边为空，
 另一边则依然存在着很多元素(仅仅比排序前少了一个)，
 此时时间复杂度为O(N*N)。
 */
public class QuickSort {


    /**
     * 对数组进行切分，小的在前，大的在后
     * @param array
     * @param start
     * @param end
     * @return
     */
    public static int divide(int[] array,int start,int end){

        //每次都以最右边的元素作为基准值
        int base = array[end];

        //start一旦等于end，就说明左右两个指针合并到了同一位置，可以结束此轮循环。
        while (start < end){

            //从左边开始遍历，如果比基准值小，就继续向右走
            while(start < end && array[start] <= base){
                start = start + 1;
            }
            //如果出现比基准值大的情况，则和基准值交换位置
            if(start < end){
                int temp = array[start];
                array[start] = array[end];
                array[end] = temp;
                //交换后，此时的那个被调换的值也同时调到了正确的位置(基准值右边)，因此右边也要同时向前移动一位
                end = end - 1;
            }

            //从右边开始遍历，如果比基准值大，就继续向左走
            while(start < end && array[end] >= base){
                end = end - 1;
            }

            //上面的while循环结束时，就说明当前的a[end]的值比基准值小，应与基准值进行交换
            if(start < end){
                int temp = array[start];
                array[start] = array[end];
                array[end] = temp;
                //交换后，此时的那个被调换的值也同时调到了正确的位置(基准值左边)，因此左边也要同时向后移动一位
                start = start+1;
            }

        }

        return end;
    }

    public static void sort(int[] array,int start,int end){
        if(start > end){
            return;
        }else {//如果不止一个元素，继续划分两边递归排序下去
            int position = divide(array,start,end);
            sort(array,start,position-1);
            sort(array,position+1,end);
        }
    }

}
