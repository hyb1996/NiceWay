package com.zewei.zewei.ui.main.discovery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zewei.zewei.R;
import com.zewei.zewei.model.DiscoveryPost;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.content)
    TextView mContent;

    @BindView(R.id.user_name)
    TextView mUserName;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(DiscoveryPost.Comment comment) {
        mUserName.setText(comment.getUser().getName());
        mContent.setText(comment.getContent());
    }
}
