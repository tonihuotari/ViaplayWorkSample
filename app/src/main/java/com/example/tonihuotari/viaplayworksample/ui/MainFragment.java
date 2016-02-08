package com.example.tonihuotari.viaplayworksample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tonihuotari.viaplayworksample.R;
import com.example.tonihuotari.viaplayworksample.api.ApiCallback;
import com.example.tonihuotari.viaplayworksample.api.ViaApiService;
import com.example.tonihuotari.viaplayworksample.db.DBPage;
import com.example.tonihuotari.viaplayworksample.models.Page;
import com.example.tonihuotari.viaplayworksample.models.Section;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private final static String TAG = MainFragment.class.getSimpleName();

    private List<Section> mSections = new ArrayList<>();

    private Toolbar mToolbar;
    private SectionAdapter mAdapter;
    private TextView mDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        mDescription = (TextView) view.findViewById(R.id.main_description);
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        mAdapter = new SectionAdapter(mSections, new SectionAdapter.MainAdapterListener() {
            @Override
            public void sectionSelected(int position) {
                Log.d(TAG, "Clicked on: " + position);
                Section section = mSections.get(position);
                fetchSection(section);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        fetchRootPage();

        return view;
    }


    private void renderPage(Page page) {

        mToolbar.setTitle(page.getTitle());
        mDescription.setText(page.getDescription());

        mSections.clear();
        mSections.addAll(page.getSections());

        mAdapter.notifyDataSetChanged();
    }

    private void fetchRootPage() {
        ViaApiService.getRootPage(getContext(), new ApiCallback<Page>() {
            @Override
            public void onResponse(Page page) {
                Log.d(TAG, "Successfully got pages");

                DBPage.savePage(page, true);

                Page loadedPage = DBPage.loadPage(page.getSectionId(), true);

                renderPage(loadedPage);
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "Failed to fetch pages");
            }
        });
    }

    private void fetchSection(Section section) {
        ViaApiService.getSection(section, new ApiCallback<Page>() {
            @Override
            public void onResponse(Page page) {
                Log.d(TAG, "Successfully got page");
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "Failed to fetch section");
            }
        });

    }

}
