package com.sunsky.io.file;

import java.io.File;

public class DeleteDir {
    public static void main(String[] args){
        File file = new File("d:"+File.separatorChar+"tmp"+File.separatorChar+"a");
        deleteDir(file);
    }

    public static void deleteDir(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                deleteDir(f);
            }
        }
        file.delete();
    }
}
