package com.nerojust.arkandarcsadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.models.users.UsersResponse;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ProductViewHolder> {

    private UsersResponse usersResponse;
    private Context context;

    public UsersAdapter(UsersResponse usersResponse, Context context) {
        this.usersResponse = usersResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_items, parent, false);
        return new UsersAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        //set animation for recycler view
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        String firstName = usersResponse.getResults().get(position).getFirstName();
        String lastName = usersResponse.getResults().get(position).getLastName();
        String fullName = firstName + " " + lastName;
        String gender = usersResponse.getResults().get(position).getGender();
        String phoneNumber = usersResponse.getResults().get(position).getPhoneNumber();
        String email = usersResponse.getResults().get(position).getEmail();
        String username = usersResponse.getResults().get(position).getUsername();

        holder.name.setText(fullName);
        holder.gender.setText(gender);
        holder.phoneNumber.setText(phoneNumber);
        holder.userName.setText(username);
        holder.emailAddress.setText(email);


        /*holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("userId", usersResponse.getResults().get(position).getId());
            intent.putExtra("userName", fullName);
            intent.putExtra("gender", gender);
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("username", username);
            intent.putExtra("emailAddress", email);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });*/

    }

    @Override
    public int getItemCount() {
        return usersResponse.getRecordCount();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        final private LinearLayout container;

        private TextView name, lastName, gender, phoneNumber, emailAddress, userName;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.layoutContainer);
            name = itemView.findViewById(R.id.name);
            gender = itemView.findViewById(R.id.gender);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            userName = itemView.findViewById(R.id.username);
            emailAddress = itemView.findViewById(R.id.email);


        }
    }
}
