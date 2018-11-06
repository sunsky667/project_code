package com.migu;

import com.migu.lock.DistributedLock;
import com.migu.util.DateUtil;
import com.migu.util.InitRaceTime;
import com.migu.util.WCTime;
import com.migu.util.ZkWatchAPI;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        logger.info("=============start program==================");

//        ZkWatchAPI zkWatchAPI = new ZkWatchAPI();
//        zkWatchAPI.connectZookeeper("192.168.80.128:2181",10000);
//        Map<String,String> maps = WCTime.getPidTime();
//
//        if(!zkWatchAPI.isExists("/WC_PIDS_PLAY_DURATION")){
//            zkWatchAPI.createPath("/WC_PIDS_PLAY_DURATION","");
//        }
//
//        System.out.println("==================="+maps.size());
//
//        for(Map.Entry<String,String> map : maps.entrySet()) {
//
//            System.out.println("###########################################################################");
//
//
//            if(!zkWatchAPI.isExists("/WC_PIDS_PLAY_DURATION/pids"+map.getKey())){
//                zkWatchAPI.createPath("/WC_PIDS_PLAY_DURATION/pids"+map.getKey(),DateUtil.transTime(DateUtil.getWCTime(map.getValue())));
//            }
//       }
//
//        zkWatchAPI.realseConnection();

        InitRaceTime.initRaceTime();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, Integer.parseInt(DateUtil.getTimerStartTime("1"))); // 控制分
        calendar.set(Calendar.SECOND, 30);    // 控制秒
        Date time = calendar.getTime();
        long period = 1000 * 60 * 1;

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                DistributedLock dc = null;
                try {

                    dc = new DistributedLock();
                    dc.createConnection();
                    dc.createPath(DateUtil.getPreFiveMinStr(), true);

                    dc.getLock();

                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }, time, period);// 这里设定将延时每天固定执行


    }
}
