package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Upayment_methodActivity extends AppCompatActivity {

    private View selectedPaymentMethod;
    private Button payButton;
    private RadioButton selectedRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upayment_method);

        payButton = findViewById(R.id.payButton);

        // Set up click listeners for all payment methods
        setupPaymentMethodListeners();

        // Set up the "Add Money" button listener
        findViewById(R.id.addMoneyButton).setOnClickListener(v -> {
            Toast.makeText(this, "Add Money clicked", Toast.LENGTH_SHORT).show();
        });

        // Set up the "Pay" button listener
        payButton.setOnClickListener(v -> {
            if (selectedPaymentMethod != null) {
                String paymentMethodName = getPaymentMethodName(selectedPaymentMethod);
                Toast.makeText(this, "Proceeding with payment via " + paymentMethodName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupPaymentMethodListeners() {
        // IDs for the clickable layouts
        int[] upiCardIds = {R.id.gpayCard, R.id.phonepeCard, R.id.paytmCard, R.id.applepayCard};
        int[] otherPaymentLayoutIds = {R.id.creditCardLayout, R.id.netBankingLayout};

        View.OnClickListener clickListener = v -> {
            // First, reset the state of the previously selected item
            if (selectedPaymentMethod != null) {
                selectedPaymentMethod.setSelected(false);
                resetColors(selectedPaymentMethod);
            }

            // Then, set the state of the new item
            v.setSelected(true);
            selectedPaymentMethod = v;
            updateSelectedState(v);
        };

        // Set the listener on all UPI cards
        for (int id : upiCardIds) {
            findViewById(id).setOnClickListener(clickListener);
        }

        // Set the listener on other payment method layouts
        for (int id : otherPaymentLayoutIds) {
            findViewById(id).setOnClickListener(clickListener);
        }
    }

    private void updateSelectedState(View selectedView) {
        // Update the radio button or text colors of the selected view
        RadioButton radioButton = selectedView.findViewById(R.id.upiRadioButton);
        if (radioButton != null) {
            radioButton.setChecked(true);
        }

        String paymentMethodName = getPaymentMethodName(selectedView);
        payButton.setText("Pay ₹458.00 via " + paymentMethodName);
    }

    private void resetColors(View view) {
        // This is where you'd reset the colors of the icon, text, etc.
        // For example, find the TextView inside the card and set its color back to default.
    }

    private String getPaymentMethodName(View view) {
        if (view.getId() == R.id.gpayCard) return "Google Pay";
        if (view.getId() == R.id.phonepeCard) return "PhonePe";
        if (view.getId() == R.id.paytmCard) return "Paytm";
        if (view.getId() == R.id.applepayCard) return "Apple Pay";
        if (view.getId() == R.id.creditCardLayout) return "Credit/Debit Card";
        if (view.getId() == R.id.netBankingLayout) return "Net Banking";
        return "";
    }
}