package com.sunsky.collection;

import java.util.ArrayList;
import java.util.Collections;

public class CollectionIndex {

    public static void main(String[] args){
        ArrayList collection = new ArrayList<String>();
        collection.add("first");
        collection.add("second");
        collection.add("third");

        Collections.sort(collection);

        int index = collection.indexOf("third");
        System.out.println(index);

    }
}
