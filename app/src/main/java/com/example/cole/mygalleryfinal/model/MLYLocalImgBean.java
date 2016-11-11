package com.example.cole.mygalleryfinal.model;

import java.io.Serializable;

/**
 * 创建人: cole
 * 创建时间: 2016/11/11 下午3:11
 * 描述: 图片对象
 */

public class MLYLocalImgBean implements Serializable {

    private int photoId;
    private String photoPath;
    private int width;
    private int height;

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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

    @Override
    public String toString() {
        return "MLYLocalImgBean{" +
                "photoId=" + photoId +
                ", photoPath='" + photoPath + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
