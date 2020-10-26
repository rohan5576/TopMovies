package com.example.topmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.topmovies.R;
import com.example.topmovies.model.Trailer;
import com.example.topmovies.util.Constants;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * TrailerAdapter class
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    /**
     * Context instance
     */
    private Context mContext;
    /**
     * Trailer list
     */
    private List<Trailer> mTrailerList;
    /**
     * TrailerAdapter constructor
     * @param mContext Context instance
     * @param mTrailerList trailer list
     */
    public TrailerAdapter(Context mContext, List<Trailer> mTrailerList) {
        this.mContext = mContext;
        this.mTrailerList = mTrailerList;
    }

    @NonNull
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent,false);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.TrailerViewHolder holder, int position) {
        final Trailer trailer = mTrailerList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(String.format(Constants.YOUTUBE_THUMBNAIL_URL, trailer.getKey()))
                .placeholder(R.color.colorPrimary)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mTrailerList.size();
    }

    public static class TrailerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail_trailer)
        ImageView thumbnail;
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
