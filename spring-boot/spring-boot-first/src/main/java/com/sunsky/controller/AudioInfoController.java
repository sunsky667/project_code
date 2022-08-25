package com.sunsky.controller;

import com.sunsky.dto.AudioData;
import com.sunsky.dto.AudioInfo;
import com.sunsky.dto.BaseResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AudioInfoController {

    @RequestMapping("/audio/list")
    @ResponseBody
    public BaseResponse<List<AudioInfo>> audioList() {
        BaseResponse baseResponse = BaseResponse.buildSuccessResult();
        List<AudioData> audioDataList = new ArrayList<AudioData>();
        AudioData audioData = new AudioData();
        audioData.setStar("李克勤");
        audioData.setImg("https://ossimg.hitv.com/platform_oss/1651893642192/keqin.jpg");
        audioData.setTitle("傻女");
        audioData.setMedia("https://ossimg.hitv.com/platform_oss/1651893415233/shanv.m4a");
        audioData.setIsPlay(false);
        audioData.setTime("04:02");
        audioData.setDate("第三期");
        audioData.setVip(true);
        audioDataList.add(audioData);

        AudioData audioData1 = new AudioData();
        audioData1.setStar("李克勤");
        audioData1.setImg("https://ossimg.hitv.com/platform_oss/1651893642192/keqin.jpg");
        audioData1.setTitle("傻女");
        audioData1.setMedia("https://ossimg.hitv.com/platform_oss/1651893415233/shanv.m4a");
        audioData1.setIsPlay(false);
        audioData1.setTime("04:02");
        audioData1.setDate("第三期");
        audioData1.setVip(true);
        audioDataList.add(audioData1);

        AudioInfo audioInfo = new AudioInfo();
        audioInfo.setTitle("第三期");
        audioInfo.setVid("436773");
        audioInfo.setCid("436773");
        audioInfo.setData(audioDataList);

        List<AudioInfo> audioInfoList = new ArrayList<AudioInfo>();
        audioInfoList.add(audioInfo);
        baseResponse.setData(audioInfoList);
        return baseResponse;
    }

}
