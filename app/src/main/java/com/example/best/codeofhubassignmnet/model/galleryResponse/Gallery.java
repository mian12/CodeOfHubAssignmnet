package com.example.best.codeofhubassignmnet.model.galleryResponse;

public class Gallery {

    private String id;
    private String gallery_id;
    private String url;
    private String owner;
    private String username;
    private String iconserver;
    private int iconfarm;
    private String primary_photo_id;
    private String date_create;
    private String date_update;
    private int count_photos;
    private int count_videos;
    private int count_total;
    private int count_views;
    private int count_comments;
    private Title title;
    private Description description;
    private String sort_group;
    private String primary_photo_server;
    private int primary_photo_farm;
    private String primary_photo_secret;

    public Gallery() {
    }

    public Gallery(String id, String gallery_id, String url, String owner, String username,
                   String iconserver, int iconfarm, String primary_photo_id, String date_create,
                   String date_update, int count_photos, int count_videos, int count_total,
                   int count_views, int count_comments, Title title, Description description,
                   String sort_group, String primary_photo_server, int primary_photo_farm,
                   String primary_photo_secret) {
        this.id = id;
        this.gallery_id = gallery_id;
        this.url = url;
        this.owner = owner;
        this.username = username;
        this.iconserver = iconserver;
        this.iconfarm = iconfarm;
        this.primary_photo_id = primary_photo_id;
        this.date_create = date_create;
        this.date_update = date_update;
        this.count_photos = count_photos;
        this.count_videos = count_videos;
        this.count_total = count_total;
        this.count_views = count_views;
        this.count_comments = count_comments;
        this.title = title;
        this.description = description;
        this.sort_group = sort_group;
        this.primary_photo_server = primary_photo_server;
        this.primary_photo_farm = primary_photo_farm;
        this.primary_photo_secret = primary_photo_secret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGallery_id() {
        return gallery_id;
    }

    public void setGallery_id(String gallery_id) {
        this.gallery_id = gallery_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIconserver() {
        return iconserver;
    }

    public void setIconserver(String iconserver) {
        this.iconserver = iconserver;
    }

    public int getIconfarm() {
        return iconfarm;
    }

    public void setIconfarm(int iconfarm) {
        this.iconfarm = iconfarm;
    }

    public String getPrimary_photo_id() {
        return primary_photo_id;
    }

    public void setPrimary_photo_id(String primary_photo_id) {
        this.primary_photo_id = primary_photo_id;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public String getDate_update() {
        return date_update;
    }

    public void setDate_update(String date_update) {
        this.date_update = date_update;
    }

    public int getCount_photos() {
        return count_photos;
    }

    public void setCount_photos(int count_photos) {
        this.count_photos = count_photos;
    }

    public int getCount_videos() {
        return count_videos;
    }

    public void setCount_videos(int count_videos) {
        this.count_videos = count_videos;
    }

    public int getCount_total() {
        return count_total;
    }

    public void setCount_total(int count_total) {
        this.count_total = count_total;
    }

    public int getCount_views() {
        return count_views;
    }

    public void setCount_views(int count_views) {
        this.count_views = count_views;
    }

    public int getCount_comments() {
        return count_comments;
    }

    public void setCount_comments(int count_comments) {
        this.count_comments = count_comments;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getSort_group() {
        return sort_group;
    }

    public void setSort_group(String sort_group) {
        this.sort_group = sort_group;
    }

    public String getPrimary_photo_server() {
        return primary_photo_server;
    }

    public void setPrimary_photo_server(String primary_photo_server) {
        this.primary_photo_server = primary_photo_server;
    }

    public int getPrimary_photo_farm() {
        return primary_photo_farm;
    }

    public void setPrimary_photo_farm(int primary_photo_farm) {
        this.primary_photo_farm = primary_photo_farm;
    }

    public String getPrimary_photo_secret() {
        return primary_photo_secret;
    }

    public void setPrimary_photo_secret(String primary_photo_secret) {
        this.primary_photo_secret = primary_photo_secret;
    }
}
