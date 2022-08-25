package com.sunsky.dto;

import java.util.List;

public class AudioInfo {
    private String title;
    private String vid;
    private String cid;
    private List<AudioData> Data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<AudioData> getData() {
        return Data;
    }

    public void setData(List<AudioData> data) {
        Data = data;
    }
}
