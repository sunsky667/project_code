package com.sunsky.io.file;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileApi {

    public static void main(String[] args) throws Exception{
//        filePorps();
//        createFile("test.dat");
//        deleteFile("test.dat");
//        createDir("demo");
//        deleteDir("demo");
//        listFile("tmp");
        filterFile("tmp");
    }

    public static void filePorps(){
        File file = new File("d:"+File.separatorChar+"tmp"+File.separatorChar+"file.dat");
        String fileName = file.getName();
        System.out.println("file name : "+fileName);

        long fileLength = file.length();
        System.out.println("file length : "+fileLength);

        boolean isHide = file.isHidden();
        System.out.println("file is hide : "+isHide);

        boolean canRead = file.canRead();
        System.out.println("file can read : "+canRead);

        boolean canWrite = file.canWrite();
        System.out.println("file can write : "+canWrite);

        long tms = file.lastModified();
        Date date = new Date(tms);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String dateStr = sdf.format(date);
        System.out.println("last modify time : "+dateStr);
    }

    public static void createFile(String fileName) throws Exception{
        File file = new File("d:"+File.separatorChar+"tmp"+File.separatorChar+fileName);
        if(!file.exists()){
            file.createNewFile();
            if(file.exists()){
                System.out.println("create file success ");
            }
        }else{
            System.out.println("file exists ");
        }
    }

    public static void deleteFile(String fileName){
        File file = new File("d:"+File.separatorChar+"tmp"+File.separatorChar+fileName);
        if(file.exists()){
            file.delete();
            if(!file.exists()){
                System.out.println("delete file success");
            }
        }else{
            System.out.println("file not exists");
        }
    }

    public static void createDir(String dirName){
        File dir = new File("d:"+File.separatorChar+"tmp"+File.separatorChar+dirName);
        if(!dir.exists()){
            dir.mkdir();
            if(dir.exists()){
                System.out.println("create dir success");
            }
        }else{
            System.out.println("dir exists");
        }
    }

    public static void deleteDir(String dirName){
        File dir = new File("d:"+File.separatorChar+"tmp"+File.separatorChar+dirName);
        if(dir.exists()){
            dir.delete();
            if(!dir.exists()){
                System.out.println("delete dir success");
            }
        }else{
            System.out.println("dir not exists ");
        }
    }

    public static void listFile(String dir) throws Exception{
        File file = new File("d:"+File.separatorChar+dir);
        if(file.isDirectory()){
            File[] subFiles = file.listFiles();
            for(File sub:subFiles){
                System.out.println(" "+sub.getName());
            }
        }
    }

    public static void filterFile(String dir) throws Exception{
        File file = new File("d:"+File.separatorChar+dir);

        FileFilter filter = new FileFilter() {
            public boolean accept(File pathname) {
                String fileName = pathname.getName();
                return fileName.startsWith("f");
            }
        };

        if(file.isDirectory()){
            File[] subFiles = file.listFiles(filter);
            for(File sub:subFiles){
                System.out.println(" "+sub.getName());
            }
        }
    }
}
