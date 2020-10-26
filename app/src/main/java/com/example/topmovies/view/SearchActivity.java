package com.example.topmovies.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topmovies.R;
import com.example.topmovies.adapter.MoviesAdapter;
import com.example.topmovies.model.Movies;
import com.example.topmovies.util.Constants;
import com.example.topmovies.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.rv_search) public RecyclerView recyclerView;
    @BindView(R.id.tv_query) public TextView tvQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        String query = getIntent().getStringExtra(Constants.EXTRA_QUERY);
        ArrayList arrayListMovies = Objects.requireNonNull
                (getIntent().getExtras()).getParcelableArrayList(Constants.ARRAY_LIST_MOVIES);
        setRecyclerView();
        MainViewModel moviesViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        moviesViewModel.setMoviesSearch(query, arrayListMovies);
        moviesViewModel.getMoviesSearch().observe(this, movies -> {
            MoviesAdapter moviesAdapter = new MoviesAdapter(SearchActivity.this, movies);
            recyclerView.setAdapter(moviesAdapter);
        });
        tvQuery.setText(query);
    }


    public void setRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
    }
}
