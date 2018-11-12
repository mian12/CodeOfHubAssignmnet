package com.example.best.codeofhubassignmnet.model.modelMyPhotes;

public class FlickerMyPhotesResponse {

    private Photos photos;
    private String stat;

    public FlickerMyPhotesResponse() {
    }

    public FlickerMyPhotesResponse(Photos photos, String stat) {
        this.photos = photos;
        this.stat = stat;
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
