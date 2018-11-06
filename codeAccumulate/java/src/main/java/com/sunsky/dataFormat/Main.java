package com.sunsky.dataFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Main {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss",Locale.ENGLISH);

    private static SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

    public static void main(String[] args) throws Exception{

        String timeStr = "20/Aug/2018:13:30:25";

        System.out.println(simpleDateFormat1.format(simpleDateFormat.parse(timeStr)));

        String timeStr1 = "25/Apr/2018:13:30:25";

        System.out.println(simpleDateFormat1.format(simpleDateFormat.parse(timeStr1)));

    }

}
