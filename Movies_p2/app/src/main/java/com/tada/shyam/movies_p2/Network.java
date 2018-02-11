package com.tada.shyam.movies_p2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;



public class Network {
    private final static String MOVIE_BASE_URL = "https://api.themoviedb.org/3/";
    private final static String PARAM_QUERY = "api_key";
    public static String API_KEY = "17532055ffd96580f2439f8763e8811a";
     /*TODO : INSERT YOUR API HERE*/

    private static final String MOVIE = "movie";
    private final static String TAG = Network.class.getSimpleName();


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    public static URL buildUrl(String sortParam){

        sortParam = sortParam == null ? "" : sortParam;

        Uri uri;
        switch (sortParam) {
            case "popular":
                uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendPath(MOVIE)
                        .appendPath(sortParam)
                        .appendQueryParameter(PARAM_QUERY, API_KEY)
                        .build();
                break;
            case "top_rated":
                uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendPath(MOVIE)
                        .appendPath(sortParam)
                        .appendQueryParameter(PARAM_QUERY, API_KEY)
                        .build();
                break;
            case "upcoming":
                uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendPath(MOVIE)
                        .appendPath(sortParam)
                        .appendQueryParameter(PARAM_QUERY, API_KEY)
                        .build();
                break;
            case "now_playing":
                uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendPath(MOVIE)
                        .appendPath(sortParam)
                        .appendQueryParameter(PARAM_QUERY, API_KEY)
                        .build();
                break;

            default:
                uri = latestMovies();
                break;
        }

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    private static Uri latestMovies () {
        return Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(MOVIE)
                .appendPath("popular")
                .appendQueryParameter(PARAM_QUERY, API_KEY)
                .build();
    }




}
