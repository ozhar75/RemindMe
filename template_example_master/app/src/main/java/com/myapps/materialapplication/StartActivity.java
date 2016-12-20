package com.myapps.materialapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class StartActivity extends AppCompatActivity {

    public StartActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Firebase.setAndroidContext(this);

        //Firebase Authentication Code
        Firebase mRef = new Firebase(Constants.FIREBASE_HOME_URL);
        if (mRef.getAuth() == null || isExpired(mRef.getAuth())){
            switchToLoginFragment();
        }
        else {
            switchToHomeFragment();
        }
    }

    //Check if authorization is expired
    private boolean isExpired(AuthData authData){
        return (System.currentTimeMillis() / 1000) >= authData.getExpires();
    }

    //Start HomeActivity
    private void switchToHomeFragment(){
        startActivity(new Intent(StartActivity.this, MainActivity.class));
    }

    //Start LoginActivity
    private void switchToLoginFragment(){
        startActivity(new Intent(StartActivity.this, LoginRegisterActivity.class));
    }

}
