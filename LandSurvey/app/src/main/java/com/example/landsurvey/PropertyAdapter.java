package com.example.landsurvey;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landsurvey.activity.PropertyDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {

    private List<Property> properties = new ArrayList<>();
    private final Context context;

    public PropertyAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.property_item, parent, false);
        return new PropertyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        Property currentProperty = properties.get(position);
        holder.propertyName.setText(currentProperty.getName());
        holder.propertyAddress.setText(currentProperty.getAddress());
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public void submitList(List<Property> properties) {
        this.properties = properties;
        notifyDataSetChanged();
    }

    class PropertyViewHolder extends RecyclerView.ViewHolder {
        private final TextView propertyName;
        private final TextView propertyAddress;

        public PropertyViewHolder(View itemView) {
            super(itemView);
            propertyName = itemView.findViewById(R.id.text_view_name);
            propertyAddress = itemView.findViewById(R.id.text_view_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Property clickedProperty = properties.get(position);
                        Intent intent = new Intent(context, PropertyDetailActivity.class);
                        intent.putExtra("property", clickedProperty); // Ensure Property implements Serializable or Parcelable
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
