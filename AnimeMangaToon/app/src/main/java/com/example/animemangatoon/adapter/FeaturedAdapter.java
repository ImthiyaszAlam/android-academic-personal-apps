package com.example.animemangatoon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animemangatoon.R;
import com.example.animemangatoon.model.FeaturedItem;

import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    private Context context;
    private List<FeaturedItem> featuredItems;

    // Constructor
    public FeaturedAdapter(Context context, List<FeaturedItem> featuredItems) {
        this.context = context;
        this.featuredItems = featuredItems;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyler_layout, parent, false);
        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        FeaturedItem featuredItem = featuredItems.get(position);

        // Set the data into the views
        holder.imageView.setImageResource(featuredItem.getImageResId());
        holder.featuredTextView.setText(featuredItem.getFeaturedText());
        holder.titleTextView.setText(featuredItem.getTitle());
        holder.descriptionTextView.setText(featuredItem.getDescription());

    }

    @Override
    public int getItemCount() {
        return featuredItems.size();
    }


    public void updateItems(List<FeaturedItem> updatedItems) {
        featuredItems.clear();
        featuredItems.addAll(updatedItems);
        notifyDataSetChanged();
    }
    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView featuredTextView;
        TextView titleTextView;
        TextView descriptionTextView;

        public FeaturedViewHolder(@NonNull View itemView) {

            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            featuredTextView = itemView.findViewById(R.id.featuredText);
            titleTextView = itemView.findViewById(R.id.titleText);
            descriptionTextView = itemView.findViewById(R.id.descriptionText);
        }
    }
}
