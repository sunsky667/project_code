package com.sunsky.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sunsky.tree.FindParents.parents;

public class Main {
    public static void main(String[] args){

        Map<String, Tree> treeMap = new HashMap<String, Tree>();
        Tree tree1 = new Tree();
        tree1.setId("0");
        tree1.setName("node0");
        tree1.setPid("N");

        treeMap.put("0",tree1);

        Tree tree2 = new Tree();
        tree2.setId("1");
        tree2.setName("node1");
        tree2.setPid("0");

        treeMap.put("1",tree2);

        Tree tree3 = new Tree();
        tree3.setId("2");
        tree3.setName("node2");
        tree3.setPid("1");
        treeMap.put("2",tree3);

        Tree tree4 = new Tree();
        tree4.setId("3");
        tree4.setName("node3");
        tree4.setPid("2");
        treeMap.put("3",tree4);

        Tree tree5 = new Tree();
        tree5.setId("4");
        tree5.setName("node4");
        tree5.setPid("3");
        treeMap.put("4",tree5);

        FindParents.initFindParents(treeMap);

        List<Tree> trees = new ArrayList<Tree>();
        trees.add(tree1);
        trees.add(tree2);
        trees.add(tree3);
        trees.add(tree4);
        trees.add(tree5);

        System.out.println(FindParents.treeMap.size());

        for(Tree tree:trees){
            System.out.println(FindParents.getParents(tree.getId()));
        }
    }
}
