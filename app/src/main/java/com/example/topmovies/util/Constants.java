package com.example.topmovies.util;

/**
 * Constants class
 */
public class Constants {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "6f0d2e184c822370f8786da6907bd6ff";
    public static final String IMAGE_PATH = "https://image.tmdb.org/t/p/w185";
    public static final String LANGUAGE = "en_US";
    public static final int PAGE = 1;
    public static final String EXTRA_MOVIES = "data_movies";
    public static final String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%s/0.jpg";
    public static final String EXTRA_QUERY = "query";
    public static final String ARRAY_LIST_MOVIES= "movies_list";


    /**
     * Search word in title using String.regionMatches().
     *
     * @param movieTitle   word inside movie title
     * @param searchString search string inside movie list.
     */
    public static void containsIgnoreCase(String movieTitle, String searchString) {
        final int length = searchString.length();
        if (length == 0)
            return; // Empty string is contained

        final char firstLowerCharacter = Character.toLowerCase(searchString.charAt(0));
        final char firstUpperCharacter = Character.toUpperCase(searchString.charAt(0));

        for (int i = movieTitle.length() - length; i >= 0; i--) {
            final char movieTitleCharacter = movieTitle.charAt(i);
            if (movieTitleCharacter != firstLowerCharacter && movieTitleCharacter != firstUpperCharacter)
                continue;

            if (movieTitle.regionMatches(true, i, searchString, 0, length))
                return;
        }
    }
}
