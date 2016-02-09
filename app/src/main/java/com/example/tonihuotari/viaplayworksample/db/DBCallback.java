package com.example.tonihuotari.viaplayworksample.db;


public interface DBCallback<T> {
    void onResult(T model);
    void onFailed(); //Not in db
}
