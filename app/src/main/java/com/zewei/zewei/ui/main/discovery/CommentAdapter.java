package com.zewei.zewei.ui.main.discovery;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zewei.zewei.R;
import com.zewei.zewei.model.DiscoveryPost;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {


    private List<DiscoveryPost.Comment> mCommentList;

    public CommentAdapter(List<DiscoveryPost.Comment> commentList) {
        mCommentList = commentList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.bind(mCommentList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    public void setComments(List<DiscoveryPost.Comment> comments) {
        mCommentList = comments;
        notifyDataSetChanged();
    }
}
