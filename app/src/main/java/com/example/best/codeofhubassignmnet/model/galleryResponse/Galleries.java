package com.example.best.codeofhubassignmnet.model.galleryResponse;

import java.util.List;

public class Galleries {
    private int total;
    private int per_page;
    private String user_id;
    private int page;
    private int pages;
    private List<Gallery> gallery;

    public Galleries() {
    }

    public Galleries(int total, int per_page, String user_id, int page, int pages, List<Gallery> gallery) {
        this.total = total;
        this.per_page = per_page;
        this.user_id = user_id;
        this.page = page;
        this.pages = pages;
        this.gallery = gallery;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }
}
