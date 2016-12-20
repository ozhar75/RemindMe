package com.myapps.materialapplication;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by ozhar on 21-04-2016.
 */
public class DoctorFragment extends Fragment {

    public static final String TAG = "doctor";
    DoctorAdapter adapter;
    private boolean docType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_doctor, container, false);
        final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.doc_insert);
                dialog.setTitle("Add Doctor");
                dialog.setCancelable(false);

                final EditText docname = (EditText) dialog.findViewById(R.id.doctor_name);
                final EditText docspeciality = (EditText) dialog.findViewById(R.id.doctor_speciality);
                final EditText docphone = (EditText) dialog.findViewById(R.id.doctor_phone);
                final EditText docemail = (EditText) dialog.findViewById(R.id.doctor_email);

                Spinner spnType = (Spinner) dialog.findViewById(R.id.type);

                View btnAdd = dialog.findViewById(R.id.btn_ok);
                View btnCancel = dialog.findViewById(R.id.btn_cancel);

                ArrayList<String> typeList = new ArrayList<String>();
                typeList.add("Male");
                typeList.add("Female");
                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, typeList);
                spnType.setAdapter(spnAdapter);

                spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0){
                            docType = true;
                        }
                        else {
                            docType = false;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Upload Firebase data
                        String name = docname.getText().toString();
                        String time = docspeciality.getText().toString();
                        String intake = docphone.getText().toString();
                        String email = docemail.getText().toString();
                        String type = String.valueOf(docType);

                        Firebase mRef = new Firebase(Constants.FIREBASE_HOME_URL);
                        AuthData authData = mRef.getAuth();
                        String uemail = authData.getProviderData().get("email").toString();
                        StringTokenizer tokens = new StringTokenizer(uemail, "@");
                        String first = tokens.nextToken();

                        Firebase firebase = new Firebase("https://medremind.firebaseio.com/doctors/" + first);
                        Map<String, String> doc = new HashMap<>();
                        doc.put("docname", name);
                        doc.put("docspeciality", time);
                        doc.put("docphone", intake);
                        doc.put("docemail", email);
                        doc.put("type", type);
                        firebase.push().setValue(doc);
                        dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        adapter = new DoctorAdapter(getActivity());
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
