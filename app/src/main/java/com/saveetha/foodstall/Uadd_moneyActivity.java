package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Uadd_moneyActivity extends AppCompatActivity {

    private EditText amountEditText;
    private RadioButton selectedRadioButton;
    private Button addMoneyPayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uadd_money);

        amountEditText = findViewById(R.id.amountEditText);
        addMoneyPayButton = findViewById(R.id.addMoneyPayButton);

        setupPaymentMethodListeners();

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        addMoneyPayButton.setOnClickListener(v -> {
            String amount = amountEditText.getText().toString();
            if (amount.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            } else if (selectedRadioButton == null) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            } else {
                String paymentMethod = getPaymentMethodName(selectedRadioButton.getId());
                Toast.makeText(this, "Adding ₹" + amount + " via " + paymentMethod, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupPaymentMethodListeners() {
        LinearLayout debitCardLayout = findViewById(R.id.debitCardLayout);
        LinearLayout upiLayout = findViewById(R.id.upiLayout);
        LinearLayout netBankingLayout = findViewById(R.id.netBankingLayout);
        RadioButton debitCardRadioButton = findViewById(R.id.debitCardRadioButton);
        RadioButton upiRadioButton = findViewById(R.id.upiRadioButton);
        RadioButton netBankingRadioButton = findViewById(R.id.netBankingRadioButton);

        View.OnClickListener listener = v -> {
            if (selectedRadioButton != null) {
                selectedRadioButton.setChecked(false);
            }

            if (v.getId() == R.id.debitCardLayout) {
                debitCardRadioButton.setChecked(true);
                selectedRadioButton = debitCardRadioButton;
            } else if (v.getId() == R.id.upiLayout) {
                upiRadioButton.setChecked(true);
                selectedRadioButton = upiRadioButton;
            } else if (v.getId() == R.id.netBankingLayout) {
                netBankingRadioButton.setChecked(true);
                selectedRadioButton = netBankingRadioButton;
            }
        };

        debitCardLayout.setOnClickListener(listener);
        upiLayout.setOnClickListener(listener);
        netBankingLayout.setOnClickListener(listener);
    }

    private String getPaymentMethodName(int radioButtonId) {
        if (radioButtonId == R.id.debitCardRadioButton) {
            return "Debit/Credit Card";
        } else if (radioButtonId == R.id.upiRadioButton) {
            return "UPI / Google Pay";
        } else if (radioButtonId == R.id.netBankingRadioButton) {
            return "Net Banking";
        }
        return "";
    }
}