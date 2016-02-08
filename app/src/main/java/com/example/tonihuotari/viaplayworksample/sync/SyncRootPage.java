package com.example.tonihuotari.viaplayworksample.sync;

import android.content.Context;
import android.util.Log;

import com.example.tonihuotari.viaplayworksample.api.ApiCallback;
import com.example.tonihuotari.viaplayworksample.api.ViaApiService;
import com.example.tonihuotari.viaplayworksample.db.DBPage;
import com.example.tonihuotari.viaplayworksample.models.Page;

public class SyncRootPage {

    private void saveRootPage(Page page) {

    }

    private void fetchRootPage(Context context) {
        ViaApiService.getRootPage(context, new ApiCallback<Page>() {
            @Override
            public void onResponse(Page page) {
                DBPage.savePage(page, true);
            }

            @Override
            public void onFailure() {
                //Failed do something
            }
        });
    }

}
