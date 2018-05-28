package com.zewei.zewei.ui.main.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zewei.zewei.R;
import com.zewei.zewei.model.Hotspot;

import java.util.List;

public class HotspotListAdapter extends RecyclerView.Adapter<HotspotViewHolder> {

    private List<Hotspot> mHotspots;

    public HotspotListAdapter(List<Hotspot> hotspots) {
        mHotspots = hotspots;
    }

    @Override
    public HotspotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotspotViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotspot, parent, false));
    }

    @Override
    public void onBindViewHolder(HotspotViewHolder holder, int position) {
        holder.bind(mHotspots.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mHotspots.size();
    }
}
