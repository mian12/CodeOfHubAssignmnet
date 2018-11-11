package com.example.best.codeofhubassignmnet.model;

import java.util.List;

public class FlickerPublicPhotesResponse {

    public String title;
    public String link;
    public String description;
    public String modified;
    public String generator;
    public List<Item> items;

    public FlickerPublicPhotesResponse() {
    }

    public FlickerPublicPhotesResponse(String title, String link, String description, String modified, String generator, List<Item> items) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.modified = modified;
        this.generator = generator;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
