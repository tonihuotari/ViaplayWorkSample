package com.example.tonihuotari.viaplayworksample.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tonihuotari.viaplayworksample.R;
import com.example.tonihuotari.viaplayworksample.api.ViaApiService;
import com.example.tonihuotari.viaplayworksample.db.DBCallback;
import com.example.tonihuotari.viaplayworksample.db.DBPage;
import com.example.tonihuotari.viaplayworksample.models.Page;
import com.example.tonihuotari.viaplayworksample.sync.SyncBus;
import com.squareup.otto.Subscribe;

public class SectionDetailsFragment extends Fragment {

    public final static String TAG = SectionDetailsFragment.class.getSimpleName();

    private final static String B_SECTION_ID = "SectionId";
    private final static String B_LAST_PATH_SEGMENT_OF_HREF = "LastPathSegmentOfHref";

    private String mSectionId;
    private String mLastPathSegmentOfHref;

    private TextView mTitle;
    private TextView mDescription;

    public static SectionDetailsFragment newInstance(String sectionId, String lastPathSegmentOfHref) {
        SectionDetailsFragment frag = new SectionDetailsFragment();

        frag.mSectionId = sectionId;
        frag.mLastPathSegmentOfHref = lastPathSegmentOfHref;

        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_section_details, container, false);

        mTitle = (TextView) view.findViewById(R.id.section_details_title);
        mDescription = (TextView) view.findViewById(R.id.section_details_description);

        if (savedInstanceState == null) {
            fetchSection(mLastPathSegmentOfHref);
        } else {
            mSectionId = savedInstanceState.getString(B_SECTION_ID);
            mLastPathSegmentOfHref = savedInstanceState.getString(B_LAST_PATH_SEGMENT_OF_HREF);
        }

        loadPage();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(B_SECTION_ID, mSectionId);
        outState.putString(B_LAST_PATH_SEGMENT_OF_HREF, mLastPathSegmentOfHref);

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

    private void renderSection(Page page) {
        mTitle.setText(page.getTitle());
        mDescription.setText(page.getDescription());
    }

    private void loadPage() {
        DBPage.loadPage(mSectionId, false, new DBCallback<Page>() {
            @Override
            public void onResult(Page section) {
                if (isAdded() && section != null) {
                    renderSection(section);
                }
            }

            @Override
            public void onFailed() {
                //not in db
            }
        });
    }

    private void fetchSection(String lastPathSegmentOfHref) {
        ViaApiService.getSection(lastPathSegmentOfHref);
    }

    @Subscribe
    public void sectionFetched(SyncBus.PageAvailableEvent event) {
        Log.d(TAG, "Bus subscription returned sectionFetched");
        loadPage();
    }

}
