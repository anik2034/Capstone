package com.anik.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.anik.capstone.databinding.ActivityLoginBinding;
import com.anik.capstone.home.HomeActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            String errorMessage = getString(R.string.something_went_wrong);
            if (response != null && response.getError() != null && response.getError().getMessage() != null) {
                errorMessage = response.getError().getMessage();
            }
            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent signInIntent =
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                Collections.singletonList(
                                        new AuthUI.IdpConfig.GoogleBuilder().build()
                                )
                        )
                        .build();

        binding.googleSignInButton.setOnClickListener(v -> signInLauncher.launch(signInIntent));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}