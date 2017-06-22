package com.rebeccafuller.movienight;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {
    List<Movies> mMoviesList;

    MovieRecyclerAdapter(List<Movies> moviesList) {
        mMoviesList = moviesList;
    }

    @Override
    public MovieRecyclerAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_recycler, parent, false);
        MovieViewHolder mvh = new MovieViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.title.setText(mMoviesList.get(position).title);
        holder.overview.setText(mMoviesList.get(position).overview);
        holder.date.setText(mMoviesList.get(position).date);
        holder.posterImage.setImageDrawable(Drawable.createFromPath(mMoviesList.get(position).poster));
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImage;
        TextView title, overview, date;

        MovieViewHolder(View itemView) {
            super(itemView);
            posterImage = (ImageView) itemView.findViewById(R.id.posterImageView);
            title = (TextView) itemView.findViewById(R.id.titleTextView);
            overview = (TextView) itemView.findViewById(R.id.overviewTextView);
            date = (TextView) itemView.findViewById(R.id.dateTextView);
        }
    }
}