package com.myapps.materialapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by ozhar on 21-04-2016.
 */
public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder>{

    private List<Doctor> mDoctors;
    private Firebase firebase;

    public DoctorAdapter(){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_card_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Doctor doctor = mDoctors.get(position);
        holder.docName.setText("Dr." + doctor.getDocname());
        holder.docSpeciality.setText(doctor.getDocspeciality());
        holder.docPhone.setText(doctor.getDocphone());
        holder.docEmail.setText(doctor.getDocemail());
        if (doctor.isType()){
            holder.docImage.setImageResource(R.drawable.male);
        }
        else {
            holder.docImage.setImageResource(R.drawable.female);
        }
    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
    }

    public DoctorAdapter(Context context){
        mDoctors = new ArrayList<>();
        Firebase.setAndroidContext(context);
        Firebase mRef = new Firebase(Constants.FIREBASE_HOME_URL);
        AuthData authData = mRef.getAuth();
        String mail = authData.getProviderData().get("email").toString();
        StringTokenizer token = new StringTokenizer(mail, "@");
        String uemail = token.nextToken();
        firebase = new Firebase("https://medremind.firebaseio.com/doctors/" + uemail);
        firebase.addChildEventListener(new DoctorChildEventListener());
    }

    private class DoctorChildEventListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Doctor doctor = dataSnapshot.getValue(Doctor.class);
            doctor.setKey(dataSnapshot.getKey());
            mDoctors.add(0, doctor);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView docName;
        private TextView docSpeciality;
        private TextView docPhone;
        private TextView docEmail;
        private ImageView docImage;

        public ViewHolder(View itemView) {
            super(itemView);
            docName = (TextView) itemView.findViewById(R.id.dname);
            docSpeciality = (TextView) itemView.findViewById(R.id.dspeciality);
            docPhone = (TextView) itemView.findViewById(R.id.dphone);
            docEmail = (TextView) itemView.findViewById(R.id.demail);
            docImage = (ImageView) itemView.findViewById(R.id.mimage);
        }
    }

}
