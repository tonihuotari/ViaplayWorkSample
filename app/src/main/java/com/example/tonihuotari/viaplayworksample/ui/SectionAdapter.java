package com.example.tonihuotari.viaplayworksample.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tonihuotari.viaplayworksample.R;
import com.example.tonihuotari.viaplayworksample.models.Section;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder>{

    private List<Section> mItems;
    private MainAdapterListener mListener;

    public interface MainAdapterListener {
        void sectionSelected(int position);
    }

    public SectionAdapter(List<Section> mItems, MainAdapterListener mListener) {
        this.mItems = mItems;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_item, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Section section = mItems.get(position);

        holder.title.setText(section.getTitle());
        holder.subtitle.setText(section.getLastPathSegmentOfHref());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mListener.sectionSelected(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, subtitle;
        
        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_title);
            subtitle = (TextView) itemView.findViewById(R.id.item_subtitle);
        }
    }
}
