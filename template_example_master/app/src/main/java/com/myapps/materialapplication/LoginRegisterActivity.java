package com.myapps.materialapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class LoginRegisterActivity extends AppCompatActivity {

    EditText userEmail, userPassword;
    String user_email, user_pass;
    TextView forgotpass;

    public LoginRegisterActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Firebase.setAndroidContext(this);
        userEmail = (EditText)findViewById(R.id.useremail);
        userPassword = (EditText)findViewById(R.id.password);
        forgotpass = (TextView)findViewById(R.id.tvPasswordForget);
        SpannableString content = new SpannableString("Forgot Password?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forgotpass.setText(content);
    }

    public void passForget(View view){
        Firebase mRef= new Firebase(Constants.FIREBASE_HOME_URL);
        mRef.resetPassword(userEmail.toString(), new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Password Reset Email Sent", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), firebaseError.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

    //User Account Creation
    public void userReg(View view){
        final Firebase mRef = new Firebase(Constants.FIREBASE_HOME_URL + "users");
        user_email = userEmail.getText().toString();
        user_pass = userPassword.getText().toString();

        //Validate Data
        if (TextUtils.isEmpty(user_pass) ){
            userPassword.setError("This field is required");
        }
        else if (!isPasswordValid(user_pass)){
            userPassword.setError("Password is too short");
        }
        else if (TextUtils.isEmpty(user_email)){
            userEmail.setError("This field is required");
        } else if (!isEmailValid(user_email)){
            userEmail.setError("This email address is invalid");
        }
        else {
            mRef.createUser(user_email, user_pass, new Firebase.ResultHandler() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_LONG).show();
                    Map<String, String> users = new HashMap<>();
                    users.put("email", user_email);
                    users.put("password", user_pass);
                    mRef.push().setValue(users);
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    Toast.makeText(getApplicationContext(), firebaseError.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }



    //User Login Authentication
    public void userLogin(View view){
        Firebase mRef = new Firebase(Constants.FIREBASE_HOME_URL);
        user_email = userEmail.getText().toString();
        user_pass = userPassword.getText().toString();

        //Validate data

        if (TextUtils.isEmpty(user_email)) {
            userEmail.setError("This field is required");
        }
        else if (!isEmailValid(user_email)){
            userEmail.setError("This email address is invalid");
        }
        else if (TextUtils.isEmpty(user_pass)){
            userPassword.setError("This field is necessary");
        }
        else {
            mRef.authWithPassword(user_email, user_pass, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    Toast.makeText(getApplicationContext(), firebaseError.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    //Validation Code
    private boolean isEmailValid(String user_email) {
        return user_email.contains("@");
    }

    private boolean isPasswordValid(String user_pass) {
        return user_pass.length() >= 6;
    }
}


