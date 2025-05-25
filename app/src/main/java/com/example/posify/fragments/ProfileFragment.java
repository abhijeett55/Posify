package com.example.posify.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.posify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {
    private TextView textViewUsername, textViewEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewUsername = view.findViewById(R.id.textViewUsername);
        textViewEmail = view.findViewById(R.id.textViewEmail);



        loadUserData();

        return view;
    }
    private void loadUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            textViewUsername.setText("User not logged in");
            textViewEmail.setText("");
            return;
        }
        String uid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        String email = documentSnapshot.getString("email");

                        textViewUsername.setText("Username: " + (name != null ? name : "N/A"));
                        textViewEmail.setText("Email: " + (email != null ? email : "N/A"));
                    } else {
                        textViewUsername.setText("Username: Not Found");
                        textViewEmail.setText("Email: Not Found");
                    }
                })
                .addOnFailureListener(e -> {
                    textViewUsername.setText("Username: Error");
                    textViewEmail.setText("Email: Error");
                });
    }



}
