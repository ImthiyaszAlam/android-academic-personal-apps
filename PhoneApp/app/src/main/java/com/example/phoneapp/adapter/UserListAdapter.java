package com.example.phoneapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoneapp.activity.CallActivity;
import com.example.phoneapp.databinding.ItemsUserBinding;
import com.example.phoneapp.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    static Context context;
    private static OnItemClickListener listener;
    private static List<UserModel> userLists = new ArrayList<>();

    public interface OnItemClickListener {
        void onUpdateClick(UserModel user);
        void onDeleteClick(UserModel user);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.listener = itemClickListener;
    }

    public UserListAdapter(List<UserModel> userLists) {
        this.userLists = userLists;
    }

    public void addUserAtTop(UserModel user) {
        userLists.add(0, user);
        notifyItemInserted(0);
    }
    public void setUserLists(List<UserModel> users) {
        this.userLists = users;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UserListAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use View Binding
        ItemsUserBinding binding = ItemsUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userLists.get(position);

        holder.binding.userName.setText(user.getName());       // Display the name
        holder.binding.userMobile.setText(user.getMobile());   // Display the mobile number
        holder.binding.userEmail.setText(user.getEmail());     // Display the email


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CallActivity.class);
            intent.putExtra("name", user.getName());
            intent.putExtra("phoneNumber", user.getMobile());
            intent.putExtra("email", user.getEmail());
            v.getContext().startActivity(intent);
        });

        Log.d("UserAdapter", "Name: " + user.getName() + " Mobile: " + user.getMobile() + " Email: " + user.getEmail());
    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }

    private void showDeleteConfirmationDialog(UserModel user, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Delete item
                    deleteUser(user, position);
                })
                .setNegativeButton(android.R.string.no, null)
                .show();


    }


    private void deleteUser(UserModel user, int position) {
        userLists.remove(position);
        notifyItemRemoved(position);
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        public ItemsUserBinding binding;

        public UserViewHolder(@NonNull ItemsUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.updateBtn.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onUpdateClick(userLists.get(getAdapterPosition()));
                }
            });
            binding.deleteBtn.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(userLists.get(getAdapterPosition()));
                }
            });
        }
    }
}
