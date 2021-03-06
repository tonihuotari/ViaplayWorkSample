package com.example.tonihuotari.viaplayworksample.api;

import android.content.Context;
import android.util.Log;

import com.example.tonihuotari.viaplayworksample.db.DBPage;
import com.example.tonihuotari.viaplayworksample.models.Page;
import com.example.tonihuotari.viaplayworksample.models.Section;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class ViaApiService {

    private static final String TAG = ViaApiService.class.getSimpleName();

    private static ApiService mInstance;

    public static synchronized ApiService getService() {

        if (mInstance == null) {
            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint("https://content.viaplay.se/")
                            //.setLogLevel(RestAdapter.LogLevel.FULL) //uncomment this for logs
                    .setConverter(new GsonConverter(new Gson()));

            RestAdapter restAdapter = builder.build();

            mInstance = restAdapter.create(ApiService.class);
        }
        return mInstance;
    }

    public static void getRootPage() {

        getService().getRootPage(new Callback<Page>() {
            @Override
            public void success(Page page, Response response) {
                Log.d(TAG, "Successfully fetched root page");
                DBPage.savePage(page, true);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Failed to fetch root page");
            }
        });
    }

    public static void getSection(final String lastPathSegmentOfHref) {
        getService().getSection(lastPathSegmentOfHref, new Callback<Page>() {
            @Override
            public void success(Page page, Response response) {
                Log.d(TAG, "Successfully fetched section");
                DBPage.savePage(page, true);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Failed to fetched section");
            }
        });

    }

    public static Page getMockedPages(Context context) {

        try {
            String jsonString = readFile("response.json", context);

            Type t = new TypeToken<Page>() {
            }.getType();
            Page page = new Gson()
                    .fromJson(jsonString, t);
            return page;
        } catch (IOException e) {
            Log.d(TAG, "Failed to load response.json: " + e.getMessage());
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
