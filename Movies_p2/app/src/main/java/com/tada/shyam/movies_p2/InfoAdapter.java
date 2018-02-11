package com.tada.shyam.movies_p2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.tada.shyam.movies_p2.Main2Activity.MOVIE;

public class InfoAdapter extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.info, container, false);
        Data selectedMovie = getActivity().getIntent().getParcelableExtra(MOVIE);
        String overview_text = selectedMovie.getOverView();
        TextView mOverview = view.findViewById(R.id.overviewTextView);
        mOverview.setText(overview_text);
        String originalTitle = selectedMovie.getOriginalTitle();
        TextView mtitle = view.findViewById(R.id.original_title);
        mtitle.setText(originalTitle);
        String user_rating = selectedMovie.getVoteAverage() + "/10";
        TextView muser = view.findViewById(R.id.user_rating);
        muser.setText(user_rating);
        String imageView2 = selectedMovie.getPosterPath();
        ImageView poster = view.findViewById(R.id.imageView2);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + imageView2).into(poster);
        String[] release_date = selectedMovie.getReleaseDate().split("-");
        TextView mrelease = view.findViewById(R.id.release_date);
        mrelease.setText(release_date[0]);

        return view;
        }


    }
