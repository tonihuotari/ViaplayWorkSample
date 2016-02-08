package com.example.tonihuotari.viaplayworksample.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page {

    private String type;
    private String title;
    private String description;
    private Links _links;

    private class Links {
        @SerializedName("viaplay:sections")
        private List<Section> sections;
    }

    public List<Section> getSections() {
        return _links.sections;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
