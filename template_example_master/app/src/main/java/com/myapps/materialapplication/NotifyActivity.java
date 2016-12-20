package com.myapps.materialapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class NotifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        Intent intent = getIntent();
        final String medname = intent.getStringExtra("mname");
        final String medtime = intent.getStringExtra("mtime");
        final String medintake= intent.getStringExtra("mintake");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_notify);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.doc_insert);
        dialog.setTitle(medname);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_notify);

        final TextView medTime = (TextView) dialog.findViewById(R.id.tvTime);
        final TextView medIntake = (TextView) dialog.findViewById(R.id.tvIntake);

        View btnAdd = dialog.findViewById(R.id.btn_ok);
        View btnCancel = dialog.findViewById(R.id.btn_cancel);

        medTime.setText(medtime);
        medIntake.setText(medintake);
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase firebase = new Firebase("https://medremind.firebaseio.com/");
                AuthData authData = firebase.getAuth();
                String string = authData.getProviderData().get("email").toString();
                StringTokenizer tokens = new StringTokenizer(string, "@");
                String first = tokens.nextToken();
                Firebase mRef = new Firebase("https://medremind.firebaseio.com/updates/" + first);
                Map<String, String> updates = new HashMap<String, String>();
                updates.put("medname", medname);
                updates.put("message", "Missed");
                updates.put("medtime", medtime);
                mRef.push().setValue(updates);
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase firebase = new Firebase("https://medremind.firebaseio.com/");
                AuthData authData = firebase.getAuth();
                String string = authData.getProviderData().get("email").toString();
                StringTokenizer tokens = new StringTokenizer(string, "@");
                String first = tokens.nextToken();
                Firebase mRef = new Firebase("https://medremind.firebaseio.com/updates/" + first);
                Map<String, String> updates = new HashMap<String, String>();
                updates.put("medname", medname);
                updates.put("message", "Taken");
                updates.put("medtime", medtime);
                mRef.push().setValue(updates);
                dialog.dismiss();
            }
        });
    }
}
