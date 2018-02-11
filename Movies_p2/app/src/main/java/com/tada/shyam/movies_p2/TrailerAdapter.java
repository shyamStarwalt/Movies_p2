package com.tada.shyam.movies_p2;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.trailerHolder> {

    private final Trailer_data[] trailerData;
    private Context mcontext;


    public TrailerAdapter(Context context, Trailer_data[] trailer) {

        trailerData = trailer;
        mcontext=context;


    }

    @Override
    public trailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer, parent, false);


        return new trailerHolder(view);
    }

    @Override
    public void onBindViewHolder(trailerHolder holder, int position) {
        holder.trailer.setText(trailerData[position].getName());

    }

    @Override
    public int getItemCount() {
        if (trailerData == null) return 0;
        return trailerData.length;
    }

    public class trailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailer_name)
        TextView trailer;

        public trailerHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            String videoUrl = trailerData[getAdapterPosition()].getVideoUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
            context.startActivity(intent);
        }
    }

}

