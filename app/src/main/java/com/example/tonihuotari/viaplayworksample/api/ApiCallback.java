package com.example.tonihuotari.viaplayworksample.api;

public interface ApiCallback<T> {
    void onResponse(T response);

    void onFailure();
}
