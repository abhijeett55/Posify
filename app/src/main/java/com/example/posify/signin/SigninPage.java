package com.example.posify.signin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.posify.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SigninPage extends AppCompatActivity {
    private EditText etEmail, etPassword, etConfirmPassword;

    private TextView tvError;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signin_page);


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btRegister = findViewById(R.id.btRegister);
        tvError = findViewById(R.id.tvError);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        mAuth = FirebaseAuth.getInstance();

        btRegister.setOnClickListener(v -> registerUser());
    }

    @SuppressLint("SetTextI18n")
    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            tvError.setText("All fields are required.");
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            tvError.setText("Passwords do not match.");
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SigninPage.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SigninPage.this, LoginPage.class));
                        finish();
                    } else {
                        tvError.setText("Registration failed: " + Objects.requireNonNull(task.getException()).getMessage());
                        tvError.setVisibility(View.VISIBLE);
                    }
                });
    }

}