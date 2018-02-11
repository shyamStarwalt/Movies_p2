package com.tada.shyam.movies_p2;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindDrawable;

import static com.tada.shyam.movies_p2.Main2Activity.MOVIE;

public class Tabbed extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    private Data selectedMovie;
    @BindDrawable(R.drawable.images)

    Drawable beforeaction;

    @BindDrawable(R.drawable.favourites)

    Drawable afteraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        selectedMovie = getIntent().getParcelableExtra(MOVIE);


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        ImageView starImage = (ImageView) findViewById(R.id.star_image);

        if (isFavorite()) {
            starImage.setImageDrawable(beforeaction);
        } else {
            starImage.setImageDrawable(afteraction);
        }
    }

    public void animate(View view) {

        ImageView starImage=view.findViewById(R.id.star_image);
        if(isFavorite()){
            starImage.setImageDrawable(beforeaction);
            removeMovieFromFavorites();
            Snackbar.make(view, "Movie removed from Favorites", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
            starImage.setImageDrawable(afteraction);
            addMovieToFavorites();
            Snackbar.make(view, "Movie added to Favorites", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }
    private void removeMovieFromFavorites() {
        String selection = MovieContract.MoviesEntry.COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(selectedMovie.getId())};
        getContentResolver().delete(MovieContract.buildMovieUriWithId(selectedMovie.getId()),
                selection, selectionArgs);
    }
    synchronized private void addMovieToFavorites() {
        getContentResolver().insert(MovieContract.buildMovieUriWithId(selectedMovie.getId()),
                Database.getMovieDetails(selectedMovie));
    }
    public boolean isFavorite() {
        String[] projection = {MovieContract.MoviesEntry.COLUMN_ID};
        String selection = MovieContract.MoviesEntry.COLUMN_ID + " = " + selectedMovie.getId();

        Cursor cursor = getContentResolver().query(MovieContract.buildMovieUriWithId(selectedMovie.getId()),
                projection,
                selection,
                null,
                null,
                null);

        cursor.close();

        return cursor.getCount() > 0;
    }
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_abbed, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    InfoAdapter tab1=new InfoAdapter();
                    return tab1;
                case 1:
                    Trailer tab2=new Trailer();
                    return tab2;
                case 2:
                    Review tab3=new Review();
                    return tab3;
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }


        }

        @Override
        public int getCount() {

            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "INFO";
                case 1:
                    return "VIDEO";
                case 2:
                    return "REVIEW";
            }
            return null;
        }
    }
}
