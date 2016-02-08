package com.example.tonihuotari.viaplayworksample.models;

import android.net.Uri;

public class Section {

    private String id;
    private String title;
    private String href;
    private String type;
    private String name;
    private String templated;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getTemplated() {
        return templated;
    }

    public String getLastPathSegmentOfHref() {
        return Uri.parse(href.replace("{?dtg}", "")).getLastPathSegment();
    }
}
