package com.sunsky.dto;

import java.util.List;

public class VideoInfo {
    private String videoModuleTitle;
    private List<VideoData> videoList;

    public String getVideoModuleTitle() {
        return videoModuleTitle;
    }

    public void setVideoModuleTitle(String videoModuleTitle) {
        this.videoModuleTitle = videoModuleTitle;
    }

    public List<VideoData> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoData> videoList) {
        this.videoList = videoList;
    }
}
