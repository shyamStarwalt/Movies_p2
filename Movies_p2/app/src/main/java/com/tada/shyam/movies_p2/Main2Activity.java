package com.tada.shyam.movies_p2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Data[]>,
        GridLayoutAdapter.GridLayoutAdapterOnClickHandler {


    @BindView(R.id.rv_image)
    RecyclerView recyclerView;
    @BindView(R.id.nointernet)
    TextView error;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;
    private GridLayoutAdapter gridLayoutAdapter;
    private static final String MOVIE_SORT = "key";
    private static final int MOVIE_LOAD = 0;
    public static final String MOVIE = "movie";
    private Bundle sort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        ButterKnife.bind(this);


        recyclerView.setHasFixedSize(true);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        gridLayoutAdapter = new GridLayoutAdapter(this, getApplicationContext());
        recyclerView.setAdapter(gridLayoutAdapter);

        Bundle sortBundle = new Bundle();
        sortBundle.putString(MOVIE_SORT, getResources().getString(R.string.popular));

        LoaderManager.LoaderCallbacks<Data[]> callback = Main2Activity.this;

        if(Network.isNetworkAvailable(this)) {

            progressBar.setVisibility(View.VISIBLE);

            getSupportLoaderManager().initLoader(MOVIE_LOAD, sortBundle, callback);

        }


    }


    @Override
    public Loader<Data[]> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<Data[]>(this) {

            Data[] cachedMovieData;

            @Override
            protected void onStartLoading() {
                if (cachedMovieData == null) {
                    forceLoad();
                } else {
                    super.deliverResult(cachedMovieData);
                }
            }

            @Override
            public Data[] loadInBackground() {

                String sortParameter = args.getString(MOVIE_SORT);

                if (sortParameter == getResources().getString(R.string.favorite)){
                    return Database.getFavoriteMovies(getApplicationContext());
                }

                URL movieRequestUrl = Network.buildUrl(sortParameter);

                try {
                    String jsonMovieResponse =getresponse(movieRequestUrl);


                    JSONObject movieResponse = new JSONObject(jsonMovieResponse);
                    JSONArray movieResults = movieResponse.getJSONArray("results");
                    Data[] movieData = new Data[movieResults.length()];

                    for (int i = 0; i < movieResults.length(); i++) {
                        JSONObject movieResult = movieResults.getJSONObject(i);
                        Data data = new Data();
                        data.setPosterPath(movieResult.getString("poster_path"));
                        data.setId(movieResult.getInt("id"));
                        data.setOriginalTitle(movieResult.getString("title"));
                        data.setOverView(movieResult.getString("overview"));
                        data.setReleaseDate(movieResult.getString("release_date"));
                        data.setVoteAverage(movieResult.getDouble("vote_average"));

                        movieData[i] = data;
                    }

                    return movieData;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
            @Override
            public void deliverResult(Data[] data) {
                cachedMovieData = data;
                super.deliverResult(data);
            }


        };
    }

    @Override
    public void onLoadFinished(Loader<Data[]> loader, Data[] data) {

        progressBar.setVisibility(View.INVISIBLE);
        gridLayoutAdapter.setMovieData(data);
        if (data == null) {
            showErrorMessage();
        }else{
            showMovieDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<Data[]> loader) {
        gridLayoutAdapter.setMovieData(null);
    }


    private void showMovieDataView() {
        recyclerView.setVisibility(View.VISIBLE);

        error.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {

        error.setVisibility(View.VISIBLE);

        recyclerView.setVisibility(View.INVISIBLE);
    }



    @Override
    public void onClick(Data movie) {
        Intent intent = new Intent(this, Tabbed.class);

        intent.putExtra(MOVIE, movie);

        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        sort = new Bundle();

        if(Network.isNetworkAvailable(this) && item.getItemId() == R.id.popular_item) {

            sort.putString(MOVIE_SORT, getResources().getString(R.string.popular));
            progressBar.setVisibility(View.VISIBLE);
            getSupportLoaderManager().restartLoader(MOVIE_LOAD, sort, this);

        }else if(Network.isNetworkAvailable(this) && item.getItemId() == R.id.top_rated_item) {

            sort.putString(MOVIE_SORT, getResources().getString(R.string.top_rated));
            progressBar.setVisibility(View.VISIBLE);
            getSupportLoaderManager().restartLoader(MOVIE_LOAD, sort, this);

        }else if(item.getItemId() == R.id.favorite_rated_item){

            sort.putString(MOVIE_SORT, getResources().getString(R.string.favorite));
            progressBar.setVisibility(View.VISIBLE);
            getSupportLoaderManager().restartLoader(MOVIE_LOAD, sort, this);

        }
        else if(Network.isNetworkAvailable(this) && item.getItemId() == R.id.upcoming) {

            sort.putString(MOVIE_SORT, getResources().getString(R.string.upcoming));
            progressBar.setVisibility(View.VISIBLE);
            getSupportLoaderManager().restartLoader(MOVIE_LOAD, sort, this);
        }
        else if(Network.isNetworkAvailable(this) && item.getItemId() == R.id.now) {

            sort.putString(MOVIE_SORT, getResources().getString(R.string.nowplaying));
            progressBar.setVisibility(View.VISIBLE);
            getSupportLoaderManager().restartLoader(MOVIE_LOAD, sort, this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( sort != null && sort.getString(MOVIE_SORT) == getResources().getString(R.string.favorite)) {
            getSupportLoaderManager().restartLoader(MOVIE_LOAD, sort, this);
        }
    }
    public static String getresponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }






}
