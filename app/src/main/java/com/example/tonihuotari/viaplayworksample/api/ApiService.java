package com.example.tonihuotari.viaplayworksample.api;

import android.content.Context;
import android.util.Log;

import com.example.tonihuotari.viaplayworksample.models.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class ApiService {

    private static final String TAG = ApiService.class.getSimpleName();

    public static ApiService mInstance;

    public static ApiService getInstance() {
        //TODO make synchronized
        if(mInstance == null) {
            mInstance = new ApiService();
        }
        return mInstance;
    }

    public void getPages(Context context, ApiCallback<Page> cb) {

        cb.onResponse(getMockedPages(context));
    }

    public static Page getMockedPages(Context context) {

        try {
            String jsonString = readFile("response.json", context);

            Type t = new TypeToken<Page>() {}.getType();
            Page page = new Gson()
                    .fromJson(jsonString, t);
            return page;
        } catch (IOException e) {
            Log.d(TAG, "Failed to load resopnse.json: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static String readFile(String filename, Context context) throws IOException {
        InputStream is = context.getAssets().open(filename);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        reader.close();

        return out.toString();
    }


}
