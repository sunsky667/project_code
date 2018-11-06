package com.sunsky.parentNode;

import java.util.Map;

public class FindParents {

    public static Map<String,Node> treeMap;

    public FindParents(Map<String,Node> treeMap){
        FindParents.treeMap = treeMap;
    }

    public static void initFindParents(Map<String,Node> treeMap){
        FindParents.treeMap = treeMap;
    }

//    static {
//
//        treeMap = new HashMap<String, Node>();
//        Node tree1 = new Node();
//        tree1.setId("0");
//        tree1.setName("node0");
//        tree1.setPid("N");
//
//        treeMap.put("0",tree1);
//
//        Node tree2 = new Node();
//        tree2.setId("1");
//        tree2.setName("node1");
//        tree2.setPid("0");
//
//        treeMap.put("1",tree2);
//
//        Node tree3 = new Node();
//        tree3.setId("2");
//        tree3.setName("node2");
//        tree3.setPid("1");
//        treeMap.put("2",tree3);
//
//        Node tree4 = new Node();
//        tree4.setId("3");
//        tree4.setName("node3");
//        tree4.setPid("2");
//        treeMap.put("3",tree4);
//
//        Node tree5 = new Node();
//        tree5.setId("4");
//        tree5.setName("node4");
//        tree5.setPid("3");
//        treeMap.put("4",tree5);
//
//    }

//    public static StringBuffer stringBuffer = new StringBuffer();
//    public static StringBuffer stringBuffer1 = new StringBuffer();
//
//    public static String parents(String id){
//
//        stringBuffer.append(treeMap.get(id).getName()+"|");
//        stringBuffer1.insert(0,treeMap.get(id).getName()+"|");
//
//        if(!treeMap.get(id).getPid().equals("N")){
//            return parents(treeMap.get(id).getPid());
//        }else{
//            return "N";
//        }
//
//    }


    public static String[]  parents(String[] parm){

        String[] strings = new String[2];
        if(!treeMap.get(parm[0]).getPid().equals("N")){
            strings[0] = treeMap.get(parm[0]).getPid();
            if("".equals(parm[1]) || parm[1] == null){
                strings[1] = treeMap.get(treeMap.get(parm[0]).getPid()).getName();
            }else {
//                strings[1] = parm[1] + "|"+treeMap.get(treeMap.get(parm[0]).getPid()).getName();
                strings[1] =  treeMap.get(treeMap.get(parm[0]).getPid()).getName()+ "|"+parm[1];
            }

            return parents(strings);
        }else{
            strings[0] = "N";
            strings[1] = ""+parm[1];
//            strings[1] = ""+parm[1];
            return strings;
        }

    }


    public static String getParents(String id){
        String[] strings = new String[2];
        strings[0] = id;
        strings[1] = "";

        return parents(strings)[1];
    }


}
