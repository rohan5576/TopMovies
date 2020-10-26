package com.example.topmovies.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.topmovies.R;
import com.example.topmovies.adapter.MoviesAdapter;
import com.example.topmovies.adapter.TrailerAdapter;
import com.example.topmovies.model.Genre;
import com.example.topmovies.model.Movies;
import com.example.topmovies.util.Constants;
import com.example.topmovies.viewmodel.DetailViewModel;
import com.example.topmovies.viewmodel.MainViewModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * DetailActivity
 */
public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.rv_detail) public RecyclerView rvDetail;
    @BindView(R.id.tv_genre) public TextView tvGenre;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.poster_path) public RoundedImageView posterPath;
    @BindView(R.id.rb_movies) public RatingBar rbRating;
    @BindView(R.id.tv_desc) public TextView tvDesc;
    @BindView(R.id.tv_preview) public TextView tvPreview;
    @BindView(R.id.tv_date) public TextView tvDate;
    @BindView(R.id.rv_trailer) public RecyclerView rvTrailer;

    /**
     * DetailViewModel instance.
     */
    DetailViewModel mDetailViewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initView();
        getExtra();
        getDiscoverMovies();
    }

    /**
     * Initialize the view.
     */
    private void initView() {
        setRecyclerView();
    }

    /**
     * Get extra data.
     */
    private void getExtra() {
        final Movies movies = getIntent().getParcelableExtra(Constants.EXTRA_MOVIES);
        if (movies != null) {
            Glide.with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/original" + movies.getBackdropPath())
                    .placeholder(R.color.colorPrimary)
                    .into(posterPath);
            getDetail(movies);
            getTrailer(movies);
            setDetailData(movies);
        }
    }

    /**
     * Set Data to view.
     */
    @SuppressLint("SetTextI18n")
    private void setDetailData(Movies movies) {
        title.setText(movies.getTitle());
        rbRating.setRating(movies.getVoteAverage()/2);
        tvDesc.setText(movies.getOverview());
        tvPreview.setText(movies.getVoteCount() + " People");
        tvDate.setText(movies.getReleaseDate());
    }

    /**
     * Set Recycler view layout manager
     */
    private void setRecyclerView() {
        rvDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        rvDetail.setHasFixedSize(true);
        rvTrailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        rvTrailer.setHasFixedSize(true);
    }

    /**
     * Get Discover Movies List.
     */
    private void getDiscoverMovies() {
        MainViewModel moviesViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        moviesViewModel.setMoviesDiscover(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE);
        moviesViewModel.getMoviesDiscover().observe(this, movies -> {
            MoviesAdapter moviesAdapter = new MoviesAdapter(DetailActivity.this, movies);
            rvDetail.setAdapter(moviesAdapter);
        });
    }

    /**
     * Get Movies Details.
     */
    private void getDetail(final Movies movies){
        mDetailViewMode = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(DetailViewModel.class);
        mDetailViewMode.setDetailData(movies.getId(),Constants.API_KEY,Constants.LANGUAGE);
        mDetailViewMode.getDataDetail().observe(this, detailModel -> {
            if (detailModel.getGenres() != null){
                List<String> currentGenre = new ArrayList<>();
                for (Genre genre : detailModel.getGenres()){
                    currentGenre.add(genre.getName());
                }
                tvGenre.setText(TextUtils.join(", ", currentGenre));
            }
        });
    }

    /**
     * Get Trailer Details.
     */
    private void getTrailer(Movies movies){
       MainViewModel moviesViewModel = new ViewModelProvider(this,
               new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        moviesViewModel.setTrailerList(movies.getId(), Constants.API_KEY, Constants.LANGUAGE);
        moviesViewModel.getTrailer().observe(this, trailers -> {
            TrailerAdapter trailerAdapter = new TrailerAdapter(getApplicationContext(), trailers);
            rvTrailer.setAdapter(trailerAdapter);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDetailViewMode.reset();
    }

}