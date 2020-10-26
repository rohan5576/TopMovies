package com.example.topmovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topmovies.R;
import com.example.topmovies.adapter.MoviesAdapter;
import com.example.topmovies.model.Movies;
import com.example.topmovies.util.Constants;
import com.example.topmovies.viewmodel.MainViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_discover) public RecyclerView rvDiscover;
    @BindView(R.id.rv_top_rated) public RecyclerView rvTopRated;
    @BindView(R.id.rv_popular) public RecyclerView rvPopular;
    @BindView(R.id.rv_upcoming) public RecyclerView rvUpcoming;
    @BindView(R.id.rv_now_playing) public RecyclerView rvNowPlaying;
    @BindView(R.id.progress_bar_recommended) public ProgressBar pbRecommended;
    @BindView(R.id.pb_upcoming) public ProgressBar pbUpcoming;
    @BindView(R.id.pb_top) public ProgressBar pbTop;
    @BindView(R.id.pb_popular) public ProgressBar pbPopular;
    @BindView(R.id.pb_now_playing) public ProgressBar pbNowPlaying;
    @BindView(R.id.sv_movies) public SearchView svMovies;
    /**
     * Movies list.
     */
    private ArrayList<Movies> mMoviesList;
    /**
     * Movies Adapter.
     */
    private MoviesAdapter mMoviesAdapter;
    /**
     * Main view model instance.
     */
    private  MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        getMoviesData();
    }

    /**
     * Initialize the view.
     */
    private void initView() {
        setRecyclerView();
        setSearchView();
        showLoading(true);
    }

    /**
     * Set Recycler view layout manager
     */
    private void setRecyclerView() {
        rvDiscover.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));
        rvDiscover.setHasFixedSize(true);
        rvTopRated.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));
        rvTopRated.setHasFixedSize(true);
        rvPopular.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));
        rvPopular.setHasFixedSize(true);
        rvUpcoming.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));
        rvUpcoming.setHasFixedSize(true);
        rvNowPlaying.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));
        rvNowPlaying.setHasFixedSize(true);
    }

    /**
     * Show progress bar loading.
     *
     * @param state true progress will visible on screen.
     */
    private void showLoading(boolean state) {
        if (state) {
            pbRecommended.setVisibility(View.VISIBLE);
            pbTop.setVisibility(View.VISIBLE);
            pbPopular.setVisibility(View.VISIBLE);
            pbUpcoming.setVisibility(View.VISIBLE);
            pbNowPlaying.setVisibility(View.VISIBLE);
        } else {
            pbRecommended.setVisibility(View.GONE);
            pbTop.setVisibility(View.GONE);
            pbPopular.setVisibility(View.GONE);
            pbUpcoming.setVisibility(View.GONE);
            pbNowPlaying.setVisibility(View.GONE);
        }
    }

    /**
     * Get movies data.
     */
    private void getMoviesData() {
        mMoviesList = new ArrayList<>();
        mMainViewModel = new ViewModelProvider(this,new ViewModelProvider
                .NewInstanceFactory()).get(MainViewModel.class);
        mMainViewModel.setMoviesDiscover(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE);
        mMainViewModel.getMoviesDiscover().observe(this, (ArrayList<Movies> movies) -> {
            mMoviesList = movies;
            mMoviesAdapter = new MoviesAdapter(MainActivity.this, movies);
            rvDiscover.setAdapter(mMoviesAdapter);
            showLoading(false);
        });

        mMainViewModel.setMoviesTopRated(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE);
        mMainViewModel.getMoviesTopRated().observe(this, movies -> {
            mMoviesAdapter = new MoviesAdapter(MainActivity.this, movies);
            rvTopRated.setAdapter(mMoviesAdapter);
            showLoading(false);
        });

        mMainViewModel.setMoviesPopular(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE);
        mMainViewModel.getMoviesPopular().observe(this, movies -> {
            mMoviesAdapter = new MoviesAdapter(MainActivity.this, movies);
            rvPopular.setAdapter(mMoviesAdapter);
            showLoading(false);
        });


        mMainViewModel.setMoviesUpcoming(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE);
        mMainViewModel.getMoviesUpcoming().observe(this, movies -> {
            mMoviesAdapter = new MoviesAdapter(MainActivity.this, movies);
            rvUpcoming.setAdapter(mMoviesAdapter);
            showLoading(false);
        });


        mMainViewModel.setMoviesNowPlaying(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE);
        mMainViewModel.getMoviesNowPlaying().observe(this, movies -> {
            mMoviesAdapter = new MoviesAdapter(MainActivity.this, movies);
            rvNowPlaying.setAdapter(mMoviesAdapter);
            showLoading(false);
        });
    }

    private void setSearchView() {
        svMovies.setQueryHint
                (Html.fromHtml("<font color = #fff>"));
        svMovies.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra(Constants.EXTRA_QUERY, query);
                bundle.putParcelableArrayList(Constants.ARRAY_LIST_MOVIES, mMoviesList);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty ( newText ) ) {
                    mMoviesAdapter.getFilter().filter("");
                } else {
                    mMoviesAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainViewModel.reset();
    }

}