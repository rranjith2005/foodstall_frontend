package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.Review;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Review> reviews;

    // Define constants for the two view types
    private static final int VIEW_TYPE_OLD_UI = 1;
    private static final int VIEW_TYPE_NEW_UI = 2;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public int getItemViewType(int position) {
        // Decide which view type to use based on the data
        // If stallName exists, it's the new UI. Otherwise, it's the old one.
        if (reviews.get(position).getStallName() != null) {
            return VIEW_TYPE_NEW_UI;
        } else {
            return VIEW_TYPE_OLD_UI;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the correct layout based on the view type
        if (viewType == VIEW_TYPE_NEW_UI) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_card, parent, false);
            return new NewReviewViewHolder(view);
        } else { // viewType == VIEW_TYPE_OLD_UI
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_review_item, parent, false);
            return new OldReviewViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Review review = reviews.get(position);
        // Bind data based on the holder's type
        if (holder.getItemViewType() == VIEW_TYPE_NEW_UI) {
            NewReviewViewHolder newHolder = (NewReviewViewHolder) holder;
            newHolder.stallName.setText(review.getStallName());
            newHolder.reviewerName.setText(review.getName());
            newHolder.reviewContent.setText(review.getText());
            newHolder.reviewDate.setText(review.getTime());
            newHolder.ratingBar.setRating(review.getRatingFloat());
        } else { // holder is OldReviewViewHolder
            OldReviewViewHolder oldHolder = (OldReviewViewHolder) holder;
            oldHolder.reviewerName.setText(review.getName());
            oldHolder.reviewText.setText(review.getText());
            oldHolder.reviewTime.setText(review.getTime());
            oldHolder.reviewRating.setText("★ " + review.getRatingString());
            oldHolder.profileImage.setImageResource(review.getProfileImageResId());
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    // --- ViewHolder for the NEW UI (item_review_card.xml) ---
    public static class NewReviewViewHolder extends RecyclerView.ViewHolder {
        TextView stallName, reviewerName, reviewContent, reviewDate;
        RatingBar ratingBar;

        public NewReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            stallName = itemView.findViewById(R.id.stallNameTextView);
            reviewerName = itemView.findViewById(R.id.reviewerNameTextView);
            reviewContent = itemView.findViewById(R.id.reviewContentTextView);
            reviewDate = itemView.findViewById(R.id.reviewDateTextView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    // --- ViewHolder for the OLD UI (card_review_item.xml) ---
    // This is your original ViewHolder, just renamed for clarity
    public static class OldReviewViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView reviewerName, reviewRating, reviewText, reviewTime;

        public OldReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.reviewer_profile_image);
            reviewerName = itemView.findViewById(R.id.reviewer_name);
            reviewRating = itemView.findViewById(R.id.review_rating);
            reviewText = itemView.findViewById(R.id.review_text);
            reviewTime = itemView.findViewById(R.id.review_time);
        }
    }
}