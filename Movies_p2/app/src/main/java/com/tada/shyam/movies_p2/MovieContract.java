package com.tada.shyam.movies_p2;

import android.net.Uri;
import android.provider.BaseColumns;


public class MovieContract {
    public static final String MOVIE_AUTHORITY = "com.tada.shyam.movies_p2";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + MOVIE_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final Uri MOVIES_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

    public static Uri buildMovieUriWithId(int id) {
        return MOVIES_CONTENT_URI.buildUpon().appendPath(Integer.toString(id))
                .build();
    }

    public static final class MoviesEntry implements BaseColumns {

        public static final String MOVIE_TABLE = "movies";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ID = "movie_id";
    }

}
