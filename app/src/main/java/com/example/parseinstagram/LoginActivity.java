package com.example.parseinstagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (!etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
                    login(username, password);
                    goMainActivity();
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSignupActivity();
            }
        });


    }

    private void goSignupActivity() {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
        finish();
    }

    private void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(LoginActivity.this, "The fields aren't filled out", Toast.LENGTH_SHORT).show();
            return;
        }
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e!=null) {
                    //TODO: better error handling
                    Log.d(TAG, "Encountered error during login");
                    e.printStackTrace();
                    return;
                }
                //TODO: navigate to new activity if the user has signed in properly
                Log.d(TAG, "Login successful");
            }
        });
    }

    private void goMainActivity() {
        Log.d(TAG, "Navigating to Main Activity");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
