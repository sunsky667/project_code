package com.sunsky.imageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PrintScreen {

    public static void prtSc(String path,String filename){
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenRectangle = new Rectangle(screenSize);
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(screenRectangle);
            ImageIO.write(image,"png",new File(filename));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        prtSc("d://","printsc");

    }
}
