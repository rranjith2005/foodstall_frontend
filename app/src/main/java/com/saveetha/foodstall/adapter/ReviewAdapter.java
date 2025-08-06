package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.Review;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final List<Review> reviews;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // You need to create this layout file: res/layout/card_review_item.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.reviewerName.setText(review.name);
        holder.reviewText.setText(review.text);
        holder.reviewTime.setText(review.time);
        holder.reviewRating.setText("★ " + review.rating);
        holder.profileImage.setImageResource(review.profileImageResId);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView reviewerName, reviewRating, reviewText, reviewTime;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.reviewer_profile_image);
            reviewerName = itemView.findViewById(R.id.reviewer_name);
            reviewRating = itemView.findViewById(R.id.review_rating);
            reviewText = itemView.findViewById(R.id.review_text);
            reviewTime = itemView.findViewById(R.id.review_time);
        }
    }
}