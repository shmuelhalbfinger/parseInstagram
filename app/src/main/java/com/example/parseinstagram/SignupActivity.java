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
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG="SignupActivity";

    private EditText etNewUsername;
    private EditText etNewPassword;
    private EditText etNewEmail;
    private Button btnSignUp;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        etNewEmail = findViewById(R.id.etNewEmail);
        btnSignUp = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNewUsername.getText().toString().isEmpty() || etNewPassword.getText().toString().isEmpty() || etNewEmail.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "The fields aren't all filled out", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser user = new ParseUser();
                user.setUsername(etNewUsername.getText().toString());
                user.setPassword(etNewPassword.getText().toString());
                user.setEmail(etNewEmail.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null) {
                            Log.d(TAG, "Signup Completed!");
                        }
                        else {
                            Log.e(TAG, "Issue with signup");
                        }
                    }
                });

                if (!etNewUsername.getText().toString().isEmpty() && !etNewPassword.getText().toString().isEmpty() && !etNewEmail.getText().toString().isEmpty()){
                    login(etNewUsername.getText().toString(), etNewPassword.getText().toString());
                    goMainActivity();
                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLoginActivity();
            }
        });
    }

    private void login(String username, String password) {
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
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
