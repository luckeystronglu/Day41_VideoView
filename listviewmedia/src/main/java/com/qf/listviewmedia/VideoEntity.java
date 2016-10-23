package com.qf.listviewmedia;

/**
 * Created by Ken on 2016/10/23.14:07
 */
public class VideoEntity {

    private String mediaUrl;//视频播放的网络地址
    private String title;//视频相关的标题
    private String imgUrl;//图片的Url
    private int width;//视频的宽度
    private int height;//视频的高度

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
