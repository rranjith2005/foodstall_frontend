package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class USignupActivity extends AppCompatActivity {

    EditText etFullName, etID, etPassword, etConfirmPassword;
    RadioGroup rgRole;
    RadioButton rbUser, rbOwner;
    Button btnSignUp;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usignup);

        etFullName = findViewById(R.id.etFullName);
        etID = findViewById(R.id.etID);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        rgRole = findViewById(R.id.rgRole);
        rbUser = findViewById(R.id.rbUser);
        rbOwner = findViewById(R.id.rbOwner);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.tvLogin);

        btnSignUp.setOnClickListener(v -> {
            String name = etFullName.getText().toString().trim();
            String id = etID.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            int selectedRoleId = rgRole.getCheckedRadioButtonId();

            if(name.isEmpty() || id.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedRoleId == -1) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                String role = (selectedRoleId == R.id.rbUser) ? "User" : "Owner";
                // TODO: Add signup API call here
                Toast.makeText(this, "Sign Up Clicked\nName: " + name + "\nID: " + id + "\nRole: " + role, Toast.LENGTH_SHORT).show();
            }
        });

        tvLogin.setOnClickListener(v -> {
            // TODO: Navigate back to LoginActivity
            finish();
        });
    }
}
