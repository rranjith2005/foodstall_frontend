package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Astall_detailsActivity extends AppCompatActivity {

    private View loadingOverlay;
    private ImageView loadingIcon;
    private String stallName, ownerName, requestDate, emailAddress, fssaiNumber, phoneNumber, address;
    private int stallImageResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.astall_details);

        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);

        // Get all data passed from the previous screen
        Intent intent = getIntent();
        stallName = intent.getStringExtra("STALL_NAME");
        ownerName = intent.getStringExtra("OWNER_NAME");
        requestDate = intent.getStringExtra("REQUEST_DATE");
        emailAddress = intent.getStringExtra("STALL_EMAIL");
        stallImageResId = intent.getIntExtra("STALL_IMAGE_RES_ID", R.drawable.stall_sample1);

        // Dummy data for fields not passed yet
        fssaiNumber = "12345678901234";
        phoneNumber = "+1 234-567-8900";
        address = "Block A, University Campus";

        populateStallDetails();

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        Button approveButton = findViewById(R.id.approveButton);
        Button rejectButton = findViewById(R.id.rejectButton);

        approveButton.setOnClickListener(v -> {
            loadingOverlay.setVisibility(View.VISIBLE);
            Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
            loadingIcon.startAnimation(rotation);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent approvalIntent = new Intent(Astall_detailsActivity.this, AapprovalActivity.class);
                approvalIntent.putExtra("STALL_NAME", stallName);
                approvalIntent.putExtra("OWNER_ID", generateOwnerId());
                startActivity(approvalIntent);
                finish();
            }, 1500);
        });

        rejectButton.setOnClickListener(v -> {
            Intent reasonIntent = new Intent(Astall_detailsActivity.this, AreasonActivity.class);
            reasonIntent.putExtra("STALL_NAME", stallName);
            reasonIntent.putExtra("OWNER_NAME", ownerName);
            startActivity(reasonIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loadingOverlay != null && loadingOverlay.getVisibility() == View.VISIBLE) {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
        }
    }

    private void populateStallDetails() {
        // Find all TextViews by their IDs from the XML
        TextView stallNameValue = findViewById(R.id.stallNameValue);
        TextView ownerNameValue = findViewById(R.id.ownerNameValue);
        TextView emailAddressValue = findViewById(R.id.emailAddressValue);
        TextView fssaiNumberValue = findViewById(R.id.fssaiNumberValue);
        TextView phoneNumberValue = findViewById(R.id.phoneNumberValue);
        TextView addressValue = findViewById(R.id.addressValue);
        TextView dateRequestedValue = findViewById(R.id.dateRequestedValue);
        ImageView stallDetailImage = findViewById(R.id.stallDetailImage);

        // Set all the data to the views
        stallNameValue.setText(stallName);
        ownerNameValue.setText(ownerName);
        emailAddressValue.setText(emailAddress);
        fssaiNumberValue.setText(fssaiNumber);
        phoneNumberValue.setText(phoneNumber);
        addressValue.setText(address);
        dateRequestedValue.setText(requestDate);
        stallDetailImage.setImageResource(stallImageResId);
    }

    private String generateOwnerId() {
        int randomNumber = new java.util.Random().nextInt(90000) + 10000;
        return "S" + randomNumber;
    }
}