package com.tada.shyam.movies_p2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class Database {
    public static ContentValues getMovieDetails(Data movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MoviesEntry.COLUMN_TITLE, movie.getOriginalTitle());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_OVERVIEW, movie.getOverView());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_ID, movie.getId());
        return contentValues;
    }

    public static Data[] getFavoriteMovies(Context context) {
        Cursor cursor = context.getContentResolver().query(MovieContract.MOVIES_CONTENT_URI,
                null,
                null,
                null,
                null);

        Data[] moviesList = new Data[cursor.getCount()];
        int count = 0;

        try {
            while (cursor != null && cursor.moveToNext() && count < cursor.getCount()) {
                Data movie = new Data();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_ID)));
                movie.setOverView(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_OVERVIEW)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_POSTER_PATH)));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_TITLE)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE)));
                moviesList[count] = movie;
                count++;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return moviesList;
    }
}
