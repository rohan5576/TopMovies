package com.example.topmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.topmovies.R;
import com.example.topmovies.model.Movies;
import com.example.topmovies.util.Constants;
import com.example.topmovies.view.DetailActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MoviesAdapter class
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> implements Filterable {

    /**
     * Movies list
     */
    private ArrayList<Movies> mMoviesList;
    /**
     * Filtered Movies list
     */
    private ArrayList<Movies> mMoviesFilteredList;
    /**
     * Context instance
     */
    private Context mContext;

    /**
     * MoviesAdapter constructor
     * @param context Context instance
     * @param mMoviesList movies list
     */
    public MoviesAdapter(Context context, ArrayList<Movies> mMoviesList) {
        this.mContext = context;
        this.mMoviesList = mMoviesList;
        this.mMoviesFilteredList = mMoviesList;
    }


    @NonNull
    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies,parent,false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MoviesViewHolder holder, int position) {
        Movies movies = mMoviesFilteredList.get(position);
        holder.tvTitle.setText(movies.getTitle());
        holder.rbMovies.setRating(movies.getVoteAverage()/2);
        Glide.with(holder.itemView.getContext())
                .load(Constants.IMAGE_PATH + movies.getPosterPath())
                .placeholder(R.color.colorPrimary)
                .into(holder.posterPath);
    }

    @Override
    public int getItemCount() {
        return mMoviesFilteredList.size();
    }

    @Override
    public Filter getFilter() {
       return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mMoviesFilteredList = mMoviesList;
                } else {
                    ArrayList<Movies> filteredList = new ArrayList<>();
                    for (Movies element : mMoviesFilteredList){
                        for(String movieTitle : element.getTitle().split(" ")){
                            if(movieTitle.toLowerCase().startsWith(charString)||
                                    movieTitle.toUpperCase().startsWith(charString)){
                                filteredList.add(element);
                            } else {
                                Constants.containsIgnoreCase(movieTitle,charString);
                            }
                        }
                    }
                    mMoviesFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mMoviesFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mMoviesFilteredList = (ArrayList<Movies>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        @BindView(R.id.poster_path)
        RoundedImageView posterPath;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.rb_movies)
        RatingBar rbMovies;

        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movies movies = mMoviesFilteredList.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra(Constants.EXTRA_MOVIES, movies);
            v.getContext().startActivity(intent);
        }
    }


}
