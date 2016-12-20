package com.myapps.materialapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by ozhar on 21-04-2016.
 */
public class MeasurementFragment extends Fragment {

    public static final String TAG = "measurement";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measurement, container, false);
        Button button = (Button) view.findViewById(R.id.btnWeight);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = getFragmentManager().findFragmentByTag(WeightFragment.TAG);
                if (fragment == null) {
                    fragment = new WeightFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, WeightFragment.TAG).commit();
            }
        });
        return view;
    }
}


