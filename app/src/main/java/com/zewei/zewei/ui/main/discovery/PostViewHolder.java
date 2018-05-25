package com.zewei.zewei.ui.main.discovery;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zewei.zewei.R;
import com.zewei.zewei.model.DiscoveryPost;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.user_name)
    TextView mUserName;

    @BindView(R.id.picture)
    ImageView mPicture;

    @BindView(R.id.like_count)
    TextView mLikeCount;

    @BindView(R.id.comment_count)
    TextView mCommentCount;

    @BindView(R.id.star_count)
    TextView mStarCount;

    @BindView(R.id.content)
    TextView mContent;

    @BindView(R.id.comments)
    RecyclerView mComments;

    private CommentAdapter mCommentAdapter;


    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mComments.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        mCommentAdapter = new CommentAdapter(Collections.emptyList());
        mComments.setAdapter(mCommentAdapter);
    }

    public void bind(DiscoveryPost post) {
        mUserName.setText(post.getUser().getName());
        mLikeCount.setText(String.valueOf(post.getLikeCount()));
        mCommentCount.setText(String.valueOf(post.getCommentCount()));
        mStarCount.setText(String.valueOf(post.getStarCount()));
        mContent.setText(post.getContent());
        post.loadPictureInto(mPicture);
        if (post.getComments().isEmpty()) {
            mComments.setVisibility(View.GONE);
        } else {
            mComments.setVisibility(View.VISIBLE);
            mCommentAdapter.setComments(post.getComments());
        }
    }
}
