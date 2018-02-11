package com.tada.shyam.movies_p2;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.reviewHolder>{

    public final Review_data[] reviewData;
    private Context mcontext;

    public ReviewAdapter(Context context, Review_data[] review) {

        reviewData = review;
        mcontext=context;
    }

    @Override
    public ReviewAdapter.reviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review, parent, false);


        return new ReviewAdapter.reviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.reviewHolder holder, int position) {
        holder.author.setText(reviewData[position].getAuthorName());
        holder.content.setText(reviewData[position].getContent());
    }

    @Override
    public int getItemCount() {
        if (reviewData == null) return 0;
        return reviewData.length;
    }

    public class reviewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.author_name)
        TextView author;
        @BindView(R.id.content)
        TextView content;

        public reviewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

}
