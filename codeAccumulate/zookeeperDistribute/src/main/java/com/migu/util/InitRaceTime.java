package com.migu.util;

import com.migu.lock.DistributedLock;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class InitRaceTime {

    private static String CONNECTION_STRING ;
    private static String PIDS_SUB_PATH ;
    private static String PIDS_ROOT_PATH;

    static{
        Properties properties = new Properties();
        try {
            properties.load(DistributedLock.class.getClassLoader().getResourceAsStream("application.properties"));
            CONNECTION_STRING = properties.getProperty("zookeeper.connect.url");
            PIDS_ROOT_PATH = properties.getProperty("zookeeper.pidroot.path");
            PIDS_SUB_PATH = properties.getProperty("zookeeper.pidsub.path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void initRaceTime(){

        /************************************记录每场比赛计算时间*********************************************/
        ZkWatchAPI zkWatchAPI = new ZkWatchAPI();
        zkWatchAPI.connectZookeeper(CONNECTION_STRING,100000);
        Map<String,String> maps = WCTime.getPidTime();

        if(!zkWatchAPI.isExists(PIDS_ROOT_PATH)){
            zkWatchAPI.createPath(PIDS_ROOT_PATH,"");
        }

        for(Map.Entry<String,String> map : maps.entrySet()) {
            if(!zkWatchAPI.isExists(PIDS_SUB_PATH+map.getKey())){
                zkWatchAPI.createPath(PIDS_SUB_PATH+map.getKey(), DateUtil.transTime(DateUtil.getWCTime(map.getValue())));
            }
        }

        zkWatchAPI.realseConnection();
    }

}
