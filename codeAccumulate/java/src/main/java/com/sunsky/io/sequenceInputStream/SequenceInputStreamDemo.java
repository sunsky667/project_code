package com.sunsky.io.sequenceInputStream;

import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

public class SequenceInputStreamDemo {
    /**
     * @param args
     * SequenceInputStream合并流，将与之相连接的流集组合成一个输入流并从第一个输入流开始读取，
     * 直到到达文件末尾，接着从第二个输入流读取，依次类推，直到到达包含的最后一个输入流的文件末尾为止。
     * 合并流的作用是将多个源合并合一个源。可接收枚举类所封闭的多个字节流对象。
     */
    public static void main(String[] args) {
        doSequence();
    }

    private static void doSequence() {
        SequenceInputStream sis = null;
        BufferedOutputStream bos = null;

        try {
            Vector<InputStream> vector = new Vector<InputStream>();
            vector.add(new FileInputStream("d:"+ File.separatorChar+"tmp"+File.separatorChar+"file.dat"));
            vector.add(new FileInputStream("d:"+ File.separatorChar+"tmp"+File.separatorChar+"filecp.dat"));
            vector.add(new FileInputStream("d:"+ File.separatorChar+"tmp"+File.separatorChar+"readConsole.dat"));

            Enumeration<InputStream> es = vector.elements();

            sis = new SequenceInputStream(es);

            bos = new BufferedOutputStream(new FileOutputStream("d:"+File.separatorChar+"out.txt"));

            byte[] buf = new byte[1024];
            int len = 0;
            while((len = sis.read(buf)) != -1){
                bos.write(buf,0,len);
                bos.flush();
            }

        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            try {
                if(sis != null){
                    sis.close();
                }

                if(bos != null){
                    bos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
