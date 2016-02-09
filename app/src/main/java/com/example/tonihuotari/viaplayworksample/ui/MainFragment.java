package com.example.tonihuotari.viaplayworksample.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tonihuotari.viaplayworksample.R;
import com.example.tonihuotari.viaplayworksample.api.ViaApiService;
import com.example.tonihuotari.viaplayworksample.db.DBCallback;
import com.example.tonihuotari.viaplayworksample.db.DBPage;
import com.example.tonihuotari.viaplayworksample.models.Page;
import com.example.tonihuotari.viaplayworksample.models.Section;
import com.example.tonihuotari.viaplayworksample.sync.SyncBus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private final static String TAG = MainFragment.class.getSimpleName();

    private List<Section> mSections = new ArrayList<>();

    private SectionAdapter mAdapter;
    private TextView mDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        mDescription = (TextView) view.findViewById(R.id.main_description);

        mAdapter = new SectionAdapter(mSections, new SectionAdapter.MainAdapterListener() {
            @Override
            public void sectionSelected(int position) {
                Section section = mSections.get(position);

                SectionDetailsFragment frag = SectionDetailsFragment.newInstance(section.getId(), section.getLastPathSegmentOfHref());

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content, frag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        loadRootPage();
        if (savedInstanceState == null) {
            fetchRootPage();
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        SyncBus.getBus().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SyncBus.getBus().unregister(this);
    }


    private void renderPage(Page page) {
        mDescription.setText(page.getTitle());
        mSections.clear();
        mSections.addAll(page.getSections());

        mAdapter.notifyDataSetChanged();
    }

    private void loadRootPage() {
        DBPage.loadPage(null, true, new DBCallback<Page>() {
            @Override
            public void onResult(Page page) {
                renderPage(page);
            }

            @Override
            public void onFailed() {
                //Not in db
            }
        });
    }

    private void fetchRootPage() {
        ViaApiService.getRootPage();
    }

    @Subscribe
    public void pageFetched(SyncBus.PageAvailableEvent event) {
        //Checking that event returned is actually root page (root page is is null)
        if(event.isRootPage()) {
            loadRootPage();
        }
    }

}
