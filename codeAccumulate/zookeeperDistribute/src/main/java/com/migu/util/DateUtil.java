package com.migu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	private static SimpleDateFormat wcPidSdf = new SimpleDateFormat("yyyy/M/d H:mm");
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");

	public static String getDayStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		return sdf.format(date);
	}
	
	public static String getDayMinStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();
		return sdf.format(date);
	}
	
	public static String getFiveMinStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		Date date = new Date();
		return sdf.format(date)+getMinStr(date);
	}
	
	public static String getMinStr(Date date){
	    int min = date.getMinutes() / 5 * 5;
	    if(min == 0){
	    	return "00";
	    }else if(min == 5){
	    	return "05";
	    }else{
    		return String.valueOf(min);
    	}	   
	}
	
	public static String getTenStr(Date date){
		return date.getMinutes()/10 + "2";
	}

	public static String getTimerStartTime(String time){
		Date date = new Date();
		return date.getMinutes()/10 + time;
	}
	
	public static String getPreFiveMinStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		long time = System.currentTimeMillis();
		long fiveBefore = time - 10*60*1000;
		Date date = new Date(fiveBefore);
		return sdf.format(date)+getMinStr(date);
	}
	
	public static String getPreDayStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day-1);
		Date tomDate = calendar.getTime();
		return sdf.format(tomDate);
	}
	
	public static String getTomDayStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day+1);
		Date tomDate = calendar.getTime();
		return sdf.format(tomDate);
	}
	
	public static String getHourFiveMin(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		Date date = new Date();
		return sdf.format(date)+"_"+getMinStr(date);
	}
	
	/**
	 * 获取当前五分钟的下一个十分钟
	 * @param nowTime
	 * @return
	 */
	public static String getNextTimeMin(String nowTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = null;
		try {
			date = sdf.parse(nowTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int minute = calendar.get(Calendar.MINUTE);
		calendar.set(Calendar.MINUTE, minute+10);
		Date next = calendar.getTime();
		return sdf.format(next);
	}
	
	/**
	 * 判断当前的时间是否和给的时间差6min
	 * @param nowTime
	 * @return
	 */
	public static boolean jugeHaveMin(String nowTime){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = null;
		try {
			date = sdf.parse(nowTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Date now = new Date();
		
		if(now.getTime() - date.getTime() > 6*60*1000){
			return true;
		}else{
			return false;
		}
	}

	public static String getThreeDaysAgoStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day-3);
		Date tomDate = calendar.getTime();
		return sdf.format(tomDate);
	}


	public static Long getWCTime(String time){

		Date date = null;
		try {
			date = wcPidSdf.parse(time);
		}catch (Exception e){
			e.printStackTrace();
		}
		return date.getTime();
	}

	public static Long getWCEndTime(String time){
		Date date = null;
		try {
			date = wcPidSdf.parse(time);
		}catch (Exception e){
			e.printStackTrace();
		}
		return date.getTime() + 27*60*60*1000;
	}

	public static Long getTime(String time){
		Date date = null;
		try {
			date = simpleDateFormat.parse(time);
		}catch (Exception e){
			e.printStackTrace();
		}
		return date.getTime();
	}

	public static  String transTime(Long timestampe){
		Date date = new Date(timestampe);
		System.out.println("============================================================================"+simpleDateFormat.format(date));
		return simpleDateFormat.format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(getNextTimeMin("201804102355"));
		System.out.println(getThreeDaysAgoStr());
	}
	
}
