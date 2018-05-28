package com.zewei.zewei.ui.main.search;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zewei.zewei.R;
import com.zewei.zewei.model.Hotspot;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotspotViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.heat)
    TextView mHeat;

    @BindView(R.id.rank)
    TextView mRank;

    @BindView(R.id.content)
    TextView mContent;

    @BindView(R.id.tag)
    TextView mTag;

    GradientDrawable mGradientDrawable;

    public HotspotViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mGradientDrawable = (GradientDrawable) mTag.getBackground();
    }

    public void bind(Hotspot hotspot, int position) {
        mHeat.setText(String.valueOf(hotspot.getHeat()));
        mRank.setText(String.valueOf(position + 1));
        mContent.setText(hotspot.getContent());
        String tag = hotspot.getTag();
        if (tag == null) {
            mTag.setVisibility(View.INVISIBLE);
        } else {
            mTag.setVisibility(View.VISIBLE);
            mTag.setText(tag);
            mGradientDrawable.setColor(tag.equals("新") ? 0xffff5722 : 0xffff9800);
        }

    }
}
