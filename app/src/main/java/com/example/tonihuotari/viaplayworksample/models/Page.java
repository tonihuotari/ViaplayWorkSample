package com.example.tonihuotari.viaplayworksample.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page {

    private String type;
    private String title;
    private String description;
    private String sectionId;
    private Links _links;

    private class Links {
        @SerializedName("viaplay:sections")
        private List<Section> sections;
    }

    public Page(String type, String title, String description, String sectionId) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.sectionId = sectionId;
    }

    public List<Section> getSections() {
        return _links.sections;
    }

    public void setSections(List<Section> sections ) {
        if(_links == null) {
            _links = new Links();
        }
        _links.sections = sections;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getSectionId() {
        return sectionId;
    }
}
