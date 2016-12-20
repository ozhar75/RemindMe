package com.myapps.materialapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by ozhar on 22-04-2016.
 */
public class Profile extends Fragment {

    public static final String TAG = "profile";
    EditText fname, lname, address, phone, emer, email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container,false);
        fname = (EditText) view.findViewById(R.id.etfirstName);
        lname = (EditText) view.findViewById(R.id.etlastname);
        address = (EditText) view.findViewById(R.id.etaddress);
        phone = (EditText) view.findViewById(R.id.etphone);
        emer = (EditText) view.findViewById(R.id.etemer);
        email = (EditText) view.findViewById(R.id.etemail);
        return view;
    }
}
