package com.tada.shyam.movies_p2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.tada.shyam.movies_p2.Main2Activity.MOVIE;

public class Trailer extends Fragment {

    private final static String TAG = "TRAILER_DATA";

    private Trailer_data[] mTrailers;
    private RecyclerView tRecyclerView;
    private TrailerAdapter mTrailerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tRecyclerView = (RecyclerView) inflater.inflate(R.layout.trailerlayout, container, false);

        Data selectedMovie = getActivity().getIntent().getParcelableExtra(MOVIE);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(trailerUrl(selectedMovie.getId()))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.d(TAG, jsonData);
                    if(response.isSuccessful()){
                        mTrailers = getTrailerData(jsonData);

                        if(getActivity() == null)
                            return;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tRecyclerView.setLayoutManager(new LinearLayoutManager(tRecyclerView.getContext()));
                                mTrailerAdapter = new TrailerAdapter(getActivity(), mTrailers);

                                tRecyclerView.setAdapter(mTrailerAdapter);
                            }
                        });

                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception caught", e );
                } catch (JSONException e) {
                    Log.e(TAG, "JSONException caught", e);
                }
            }
        });

        return tRecyclerView;
    }



    private static Trailer_data[] getTrailerData(String jsonData) throws JSONException {
        JSONObject trailers = new JSONObject(jsonData);
        JSONArray trailerResults = trailers.getJSONArray("results");
        Trailer_data[] movieTrailers = new Trailer_data[trailerResults.length()];

        for (int i = 0; i < trailerResults.length(); i++) {
            JSONObject movieTrailer = trailerResults.getJSONObject(i);
            Trailer_data data = new Trailer_data();
            data.setKey(movieTrailer.getString("key"));
            data.setName(movieTrailer.getString("name"));

            movieTrailers[i] = data;
        }

        return movieTrailers;
    }

    private String trailerUrl(int id){
        return "https://api.themoviedb.org/3/movie/"
                + id +"/videos?api_key="
                + Network.API_KEY;
    }



}
