package com.sunsky.dto;

public class VideoData {
    private String videoTitle;
    private String videoIntro;
    private String videoCover;
    private Integer jumpType;
    private String jumpInfo;

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoIntro() {
        return videoIntro;
    }

    public void setVideoIntro(String videoIntro) {
        this.videoIntro = videoIntro;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public Integer getJumpType() {
        return jumpType;
    }

    public void setJumpType(Integer jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpInfo() {
        return jumpInfo;
    }

    public void setJumpInfo(String jumpInfo) {
        this.jumpInfo = jumpInfo;
    }
}
