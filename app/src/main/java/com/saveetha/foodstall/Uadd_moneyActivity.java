package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Uadd_moneyActivity extends AppCompatActivity {

    private EditText amountEditText;
    private RadioGroup paymentRadioGroup;
    private Button addMoneyPayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uadd_money);

        amountEditText = findViewById(R.id.amountEditText);
        paymentRadioGroup = findViewById(R.id.paymentRadioGroup);
        addMoneyPayButton = findViewById(R.id.addMoneyPayButton);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        addMoneyPayButton.setOnClickListener(v -> {
            String amount = amountEditText.getText().toString();
            int selectedId = paymentRadioGroup.getCheckedRadioButtonId();

            if (amount.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            } else if (selectedId == -1) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            } else {
                String paymentMethod = "";
                if (selectedId == R.id.debitCardRadioButton) {
                    paymentMethod = "Debit/Credit Card";
                } else if (selectedId == R.id.upiRadioButton) {
                    paymentMethod = "UPI / Google Pay";
                } else if (selectedId == R.id.netBankingRadioButton) {
                    paymentMethod = "Net Banking";
                }

                Toast.makeText(this, "Adding ₹" + amount + " via " + paymentMethod, Toast.LENGTH_SHORT).show();
            }
        });
    }
}