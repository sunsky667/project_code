package com.sunsky.controller;

import com.sunsky.dto.BaseResponse;
import com.sunsky.dto.VideoData;
import com.sunsky.dto.VideoInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VideoInfoController {

    @RequestMapping("/recomend/video/list")
    @ResponseBody
    public BaseResponse<VideoInfo> recomendVideoList() {
        BaseResponse<VideoInfo> baseResponse = BaseResponse.buildSuccessResult();
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setVideoModuleTitle("模块标题");
        List<VideoData> videoDataList = new ArrayList<VideoData>();
        VideoData videoData = new VideoData();
        videoData.setVideoTitle("视频标题");
        videoData.setVideoIntro("这是一个视频标题");
        videoData.setVideoCover("https://ossimg.hitv.com/platform_oss/1651893642192/keqin.jpg");
        videoData.setJumpType(1);
        videoData.setJumpInfo("123456");
        videoDataList.add(videoData);
        videoInfo.setVideoList(videoDataList);
        baseResponse.setData(videoInfo);
        return baseResponse;
    }

}
