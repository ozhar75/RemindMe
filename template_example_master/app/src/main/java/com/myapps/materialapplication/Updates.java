package com.myapps.materialapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;

/**
 * Created by ozhar on 22-04-2016.
 */
public class Updates extends Fragment{
    public static final String TAG = "updates";
    UpdatesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_update, container, false);
        adapter = new UpdatesAdapter(getActivity());
        RecyclerView view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.setHasFixedSize(true);
        view.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
