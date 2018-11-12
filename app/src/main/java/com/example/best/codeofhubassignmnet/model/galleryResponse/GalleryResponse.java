package com.example.best.codeofhubassignmnet.model.galleryResponse;

public class GalleryResponse {
    private Galleries galleries;
    private String stat;

    public GalleryResponse() {
    }

    public GalleryResponse(Galleries galleries, String stat) {
        this.galleries = galleries;
        this.stat = stat;
    }

    public Galleries getGalleries() {
        return galleries;
    }

    public void setGalleries(Galleries galleries) {
        this.galleries = galleries;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
