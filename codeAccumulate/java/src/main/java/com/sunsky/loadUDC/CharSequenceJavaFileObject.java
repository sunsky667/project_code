package com.sunsky.loadUDC;


import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/**
 * 用于保存Java Code，
 * 提供方法给JavaCompiler获取String形式的Java Code
 */
@SuppressWarnings("all")
public class CharSequenceJavaFileObject extends SimpleJavaFileObject{

    /**
     * 保存java code
     */
    private String content;

    /**
     * 调用父类构造器，并设置content
     * @param className
     * @param content
     */
    public CharSequenceJavaFileObject(String className, String content){
        super(URI.create("string:///" + className.replace('.', '/')
                + Kind.SOURCE.extension), Kind.SOURCE);
        this.content = content;
    }

    /**
     * 实现getCharContent，使得JavaCompiler可以从content获取java源码
     * @param ignoreEncodingErrors
     * @return
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return content;
    }
}
