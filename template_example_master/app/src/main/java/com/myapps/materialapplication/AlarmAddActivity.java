package com.myapps.materialapplication;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by ozhar on 21-04-2016.
 */
public class AlarmAddActivity extends Fragment {

    public static final String TAG = "alarm";
    private boolean medType;
    String timeget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_add, container, false);
        final EditText medname = (EditText) view.findViewById(R.id.medicine_name);
        final TextView medtime = (TextView) view.findViewById(R.id.tvTime);
        final EditText medintake = (EditText) view.findViewById(R.id.medicine_intake);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.pickertime);

        Calendar now = Calendar.getInstance();

        timePicker.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(now.get(Calendar.MINUTE));
        Spinner spnType = (Spinner) view.findViewById(R.id.type);

        View btnAdd = view.findViewById(R.id.btn_ok);

        ArrayList<String> typeList = new ArrayList<String>();
        typeList.add("Tablet");
        typeList.add("Syrup");
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, typeList);
        spnType.setAdapter(spnAdapter);

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    medType = true;
                }
                else {
                    medType = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                //calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,timePicker.getCurrentHour(),timePicker.getCurrentMinute());
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                //Upload Firebase data
                String hour = timePicker.getCurrentHour().toString();
                String minute = timePicker.getCurrentMinute().toString();
                String name = medname.getText().toString();
                String time = hour.concat(":").concat(minute);
                String intake = medintake.getText().toString();
                String type = String.valueOf(medType);

                Firebase mRef = new Firebase(Constants.FIREBASE_HOME_URL);
                AuthData authData = mRef.getAuth();
                String uemail = authData.getProviderData().get("email").toString();
                StringTokenizer tokens = new StringTokenizer(uemail, "@");
                String first = tokens.nextToken();
                String second = tokens.nextToken();

                Firebase firebase = new Firebase("https://medremind.firebaseio.com/medicines/" + first);
                Map<String, String> med = new HashMap<>();
                med.put("medname", name);
                med.put("medtime", time);
                med.put("medintake", intake);
                med.put("type", type);
                firebase.push().setValue(med);

                Firebase mRef1 = new Firebase("https://medremind.firebaseio.com/updates/" + first);
                Map<String, String> updates = new HashMap<String, String>();
                updates.put("medname", name);
                updates.put("message", "Created");
                updates.put("medtime", time);
                mRef1.push().setValue(updates);

                //Set alarm
                setAlarm(calendar, name, time, intake);
                Fragment fragment;
                fragment = getFragmentManager().findFragmentByTag(HomeFragment.TAG);
                if (fragment == null) {
                    fragment = new HomeFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, HomeFragment.TAG).commit();
            }
        });
        return view;
    }
    private void setAlarm(Calendar targetCal, String medname, String  medtime, String medintake){
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        String name = medname;
        String time = medtime;
        String intake = medintake;

        intent.putExtra("medicinename", name);
        intent.putExtra("medicinetime", time);
        intent.putExtra("medicineintake", intake);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(getActivity().ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,targetCal.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(getActivity(),"Alarm added Succesfully at "  + targetCal.getTime().toString(),Toast.LENGTH_SHORT).show();
    }
}
