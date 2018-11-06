package com.sunsky.io.readJarFile;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MainApp {

    public static void main(String[] args) throws Exception{
        readJar("D:","customPluggin-1.0-SNAPSHOT.jar");

        InputStream in = getJarInputStream("D:"+File.separatorChar+"customPluggin-1.0-SNAPSHOT.jar","META-INF/maven/com.sunsky/customPluggin/pom.xml");

        System.out.println(in);
        String str = readFile("D:"+File.separatorChar+"customPluggin-1.0-SNAPSHOT.jar","META-INF/maven/com.sunsky/customPluggin/pom.xml");
        System.out.println(str);
    }

    public static void readJar(String path,String name) throws IOException{
        JarFile jarFile = new JarFile(new File(path+File.separatorChar+name));

        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()){
            JarEntry jarEntry = entries.nextElement();
            String fileName = jarEntry.getName();
            System.out.println(fileName);
        }

        jarFile.close();

    }

    //filePath是jar文件位置，name是jar文件里面文件的路径，相当于上面代码框中的entryName
    public static InputStream getJarInputStream(String filePath, String name) throws Exception {
        URL url = new URL("jar:file:"+filePath+"!/"+name);
        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();

        InputStream in = jarURLConnection.getInputStream();

        return in;
    }

    public static String readFile(String filePath,String entryName){
        InputStream in = null;
        BufferedReader br = null;
        StringBuffer sb = null;

        try {
            in = getJarInputStream(filePath, entryName);
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String con = null;

            sb = new StringBuffer();
            while ((con = br.readLine()) != null) {

                sb.append(con);

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (in != null)
                    in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
