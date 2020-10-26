package com.example.topmovies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.topmovies.model.Movies;
import com.example.topmovies.model.Trailer;
import com.example.topmovies.service.ApiClient;
import com.example.topmovies.service.ApiService;
import com.example.topmovies.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    /**
     * MutableLiveData for trailer list.
     */
    public static MutableLiveData<List<Trailer>> trailerList = new MutableLiveData<>();
    /**
     * MutableLiveData for movie list.
     */
    private static MutableLiveData<ArrayList<Movies>> moviesList = new MutableLiveData<>();
    /**
     * MutableLiveData for top movie list.
     */
    private static MutableLiveData<ArrayList<Movies>> moviesListTop = new MutableLiveData<>();
    /**
     * MutableLiveData for upcoming moview list.
     */
    private static MutableLiveData<ArrayList<Movies>> moviesListUpcoming = new MutableLiveData<>();
    /**
     * MutableLiveData for popular moview list.
     */
    private static MutableLiveData<ArrayList<Movies>> moviesListPopular = new MutableLiveData<>();
    /**
     * MutableLiveData for now movie list.
     */
    private static MutableLiveData<ArrayList<Movies>> moviesListNow = new MutableLiveData<>();
    /**
     * MutableLiveData for search movie list.
     */
    private static MutableLiveData<ArrayList<Movies>> moviesSearch = new MutableLiveData<>();
    /**
     * ApiService instance.
     */
    private ApiService mApiService = ApiClient.getRetrofitInstance().create(ApiService.class);
    /**
     * Composite Disposable instance.
     */
    private CompositeDisposable mCompositeDisposable =  new CompositeDisposable();;

    /**
     * Set Discover movies list.
     *
     * @param api      API string
     * @param language Language in which we need movie
     * @param page     Specify which page to query.
     */
    public void setMoviesDiscover(String api, String language, int page) {
        Disposable disposable =
                mApiService.getDiscoverMovies(api, language, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseMovies -> {
                            ArrayList<Movies> moviesArrayList =
                                    responseMovies.getResults();
                            moviesList.postValue(moviesArrayList);
                        }, throwable -> throwable.printStackTrace());
        mCompositeDisposable.add(disposable);
    }

    /**
     * Live data  for discover movies.
     *
     * @return movies list.
     */
    public LiveData<ArrayList<Movies>> getMoviesDiscover() {
        return moviesList;
    }

    /**
     * Set movies top list.
     *
     * @param api      API string
     * @param language Language in which we need movie
     * @param page     Specify which page to query.
     */
    public void setMoviesTopRated(String api, String language, int page) {
        Disposable disposable =
                mApiService.getTopRated(api, language, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseMovies -> {
                            ArrayList<Movies> moviesArrayList =
                                    responseMovies.getResults();
                            moviesListTop.postValue(moviesArrayList);
                        }, Throwable::printStackTrace);
        mCompositeDisposable.add(disposable);
    }

    /**
     * Live data for top movies list.
     *
     * @return movies list.
     */
    public LiveData<ArrayList<Movies>> getMoviesTopRated() {
        return moviesListTop;
    }


    /**
     * Set upcoming movies list.
     *
     * @param api      API string
     * @param language Language in which we need movie
     * @param page     Specify which page to query.
     */
    public void setMoviesUpcoming(String api, String language, int page) {
        Disposable disposable =
                mApiService.getUpcoming(api, language, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseMovies -> {
                            ArrayList<Movies> moviesArrayList =
                                    responseMovies.getResults();
                            moviesListUpcoming.postValue(moviesArrayList);
                        }, Throwable::printStackTrace);
        mCompositeDisposable.add(disposable);
    }

    /**
     * Live data for top movies list.
     *
     * @return movies list.
     */
    public LiveData<ArrayList<Movies>> getMoviesUpcoming() {
        return moviesListUpcoming;
    }

    /**
     * Set popular movies list.
     *
     * @param api      API string
     * @param language Language in which we need movie
     * @param page     Specify which page to query.
     */
    public void setMoviesPopular(String api, String language, int page) {
        Disposable disposable =
                mApiService.getPopular(api, language, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseMovies -> {
                            ArrayList<Movies> moviesArrayList =
                                    responseMovies.getResults();
                            moviesListPopular.postValue(moviesArrayList);
                        }, Throwable::printStackTrace);
        mCompositeDisposable.add(disposable);
    }

    /**
     * Live data for popular movies list.
     *
     * @return movies list.
     */
    public LiveData<ArrayList<Movies>> getMoviesPopular() {
        return moviesListPopular;
    }

    /**
     * Set now playing movies list.
     *
     * @param api      API string
     * @param language Language in which we need movie
     * @param page     Specify which page to query.
     */
    public void setMoviesNowPlaying(String api, String language, int page) {
        Disposable disposable =
                mApiService.getNowPlaying(api, language, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseMovies -> {
                            ArrayList<Movies> moviesArrayList =
                                    responseMovies.getResults();
                            moviesListNow.postValue(moviesArrayList);
                        }, Throwable::printStackTrace);
        mCompositeDisposable.add(disposable);
    }

    /**
     * Live data for top movies list.
     *
     * @return movies list.
     */
    public LiveData<ArrayList<Movies>> getMoviesNowPlaying() {
        return moviesListNow;
    }

    /**
     * Set now playing movies list.
     *
     * @param id       id
     * @param api      API string
     * @param language Specify language to show movie
     */
    public void setTrailerList(int id, String api, String language) {
        Disposable disposable =
                mApiService.getTrailers(id, api, language)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseTrailer -> {
                            List<Trailer> trailers = responseTrailer.getResults();
                            trailerList.postValue(trailers);
                        }, Throwable::printStackTrace);
        mCompositeDisposable.add(disposable);
    }

    /**
     * Live data for top movies trailer list.
     *
     * @return movies list.
     */
    public LiveData<List<Trailer>> getTrailer() {
        return trailerList;
    }


    /**
     * Set movies search list.
     *
     * @param query           query
     * @param arrayListMovies movies list
     */
    public void setMoviesSearch(String query,ArrayList<Movies> arrayListMovies) {
        ArrayList<Movies> searchMoviesArrayList = new ArrayList<>();
        for (Movies element : arrayListMovies){
            for(String movieTitle : element.getTitle().split(" ")){
                if(movieTitle.toLowerCase().startsWith(query)||
                        movieTitle.toUpperCase().startsWith(query)){
                    searchMoviesArrayList.add(element);
                }else{
                    Constants.containsIgnoreCase(movieTitle,query);
                }
            }
        }
        moviesSearch.postValue(searchMoviesArrayList);
    }

    /**
     * Live data for top movies search list.
     *
     * @return movies list.
     */
    public LiveData<ArrayList<Movies>> getMoviesSearch() {
        return moviesSearch;
    }

    /**
     * Reset CompositeDisposable.
     */
    public void reset() {
        mCompositeDisposable.dispose();
        mCompositeDisposable = null;
    }
}
