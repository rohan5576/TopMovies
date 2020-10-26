package com.example.topmovies.service;

import com.example.topmovies.model.DetailModel;
import com.example.topmovies.model.ResponseMovies;
import com.example.topmovies.model.ResponseTrailer;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *  API Service class
 */
public interface ApiService {
    /**
     * Get Discover Movies
     *
     * @param api      API key
     * @param language Language
     * @param page     page count
     * @return ResponseMovies Observable
     */
    @GET("discover/movie")
    Observable<ResponseMovies> getDiscoverMovies(
            @Query("api_key") String api,
            @Query("language") String language,
            @Query("page") int page
    );

    /**
     * Get Top Movies
     *
     * @param api      API key
     * @param language Language
     * @param page     page count
     * @return ResponseMovies Observable
     */
    @GET("movie/top_rated")
    Observable<ResponseMovies> getTopRated(

            @Query("api_key") String api,
            @Query("language") String language,
            @Query("page") int page
    );

    /**
     * Get Upcoming Movies
     *
     * @param api      API key
     * @param language Language
     * @param page     page count
     * @return ResponseMovies Observable
     */
    @GET("movie/upcoming")
    Observable<ResponseMovies> getUpcoming(
            @Query("api_key") String api,
            @Query("language") String language,
            @Query("page") int page
    );

    /**
     * Get Popular Movies
     *
     * @param api      API key
     * @param language Language
     * @param page     page count
     * @return ResponseMovies Observable
     */
    @GET("movie/popular")
    Observable<ResponseMovies> getPopular(
            @Query("api_key") String api,
            @Query("language") String language,
            @Query("page") int page
    );

    /**
     * Get Now playing Movies
     *
     * @param api      API key
     * @param language Language
     * @param page     page count
     * @return ResponseMovies Observable
     */
    @GET("movie/now_playing")
    Observable<ResponseMovies> getNowPlaying(
            @Query("api_key") String api,
            @Query("language") String language,
            @Query("page") int page
    );

    /**
     * Get Details Movies
     *
     * @param id      Movie id
     * @param api      API key
     * @param language Language
     * @return ResponseMovies Observable
     */
    @GET("movie/{movie_id}")
    Observable<DetailModel> getDetailMovies(
            @Path("movie_id") int id,
            @Query("api_key") String api,
            @Query("language") String language
    );

    /**
     * Get Movies Trailers
     *
     * @param id      Movie id
     * @param apiKEy      API key
     * @param language Language
     * @return ResponseMovies Observable
     */
    @GET("movie/{movie_id}/videos")
    Observable<ResponseTrailer> getTrailers(
            @Path("movie_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );
}
