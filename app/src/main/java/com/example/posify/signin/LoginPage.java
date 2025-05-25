package com.example.posify.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.posify.MainActivity;
import com.example.posify.R;
import com.example.posify.orders.DashboardPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginPage extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvError, registerbtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_page);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvError = findViewById(R.id.tvError);
        registerbtn = findViewById(R.id.registerbtn);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> loginUser());
        registerbtn.setOnClickListener(v -> {
            // Navigate to register page (implement this activity)
            startActivity(new Intent(this, SigninPage.class));
        });


    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            tvError.setText("Please enter email and password.");
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginPage.this, "Login successful!", Toast.LENGTH_SHORT).show();

                        // Navigate to main activity
                        startActivity(new Intent(LoginPage.this, MainActivity.class));
                        finish();
                    } else {
                        tvError.setText("Login failed: " + task.getException().getMessage());
                        tvError.setVisibility(View.VISIBLE);
                    }
                });
    }
}