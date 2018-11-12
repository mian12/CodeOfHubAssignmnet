package com.example.best.codeofhubassignmnet.model.galleryResponse;

public class GalleryDetail {
    private  String galleryId;
    private String galleryUrl;
    private String userName;
    private String userId;

    public GalleryDetail() {
    }

    public GalleryDetail(String galleryId, String galleryUrl, String userName, String userId) {
        this.galleryId = galleryId;
        this.galleryUrl = galleryUrl;
        this.userName = userName;
        this.userId = userId;
    }

    public String getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(String galleryId) {
        this.galleryId = galleryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGalleryUrl() {
        return galleryUrl;
    }

    public void setGalleryUrl(String galleryUrl) {
        this.galleryUrl = galleryUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
