package com.tada.shyam.movies_p2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.movieHolder>{



    private final Context context;
    private Data[] data;
    private final GridLayoutAdapterOnClickHandler click_handler;


    public GridLayoutAdapter(GridLayoutAdapterOnClickHandler clickHandler, Context c) {
        click_handler = clickHandler;
        context = c;
    }

    public interface GridLayoutAdapterOnClickHandler {

        void onClick(Data movie);
    }

    @Override
    public movieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image, parent, false);


        return new movieHolder(view);
    }

    @Override
    public void onBindViewHolder(movieHolder holder, int position) {

        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.length;
    }

    public class movieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.front_image)
        ImageView gridImage;
        @BindView(R.id.Movie_name)
        TextView name;


        public movieHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Data movie = data[adapterPosition];
            click_handler.onClick(movie);
        }

        public void bindView(int position) {
            Data movieData=data[position];
            String PosterPath=movieData.getPosterPath();
            if(movieData.getPosterPath() == null ){
                Picasso.with(context).load(R.drawable.no_image_icon).into(gridImage);
            }else{
                String posterPath = "http://image.tmdb.org/t/p/w185/" + PosterPath;
                Picasso.with(context).load(posterPath).into(gridImage);
                name.setText(data[position].getOriginalTitle());



            }
        }
    }

    public void setMovieData(Data[] movieData) {
        data = movieData;
        notifyDataSetChanged();
    }
}
