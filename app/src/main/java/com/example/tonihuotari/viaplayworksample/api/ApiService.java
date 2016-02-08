package com.example.tonihuotari.viaplayworksample.api;

import com.example.tonihuotari.viaplayworksample.models.Page;
import com.example.tonihuotari.viaplayworksample.models.Section;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ApiService {

    @GET("/androidv2-se")
    void getRootPage(Callback<Page> cb);

    @GET("/androidv2-se/{sectionName}")
    void getSection(@Path("sectionName") String sectionName, Callback<Page> cb);

}
